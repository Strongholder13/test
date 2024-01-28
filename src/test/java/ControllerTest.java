
import campus.CashMachine;
import campus.Controller;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    @Test
    public void putValidInputTest() {
        String input = "+ USD 10 5";
        String expectedOutput = "OK";

        testPutOperation(input, expectedOutput);
    }

    @Test
    public void putInvalidCurrencyTest() {
        String input = "? ddr 10 5";
        String expectedOutput = "ERROR";

        testPutOperation(input, expectedOutput);
    }

    @Test
    public void putInvalidValueTest() {
        String input = "+ USD 25 5";
        String expectedOutput = "ERROR";

        testPutOperation(input, expectedOutput);
    }

    @Test
    public void putInvalidNumberTest() {
        String input = "+ USD 10 -5";
        String expectedOutput = "ERROR";

        testPutOperation(input, expectedOutput);
    }

    private void testPutOperation(String input, String expectedOutput) {
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        CashMachine atm = new CashMachine();
        Controller controller = new Controller();

        controller.operate(input, atm);

        System.setIn(originalSystemIn);
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }



    @Test
    public void updateTest() {

        String input = "- USD 20";
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        CashMachine atm = new CashMachine();
        Controller controller = new Controller();
        controller.operate(input, atm);
        System.setIn(originalSystemIn);
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));


        assertEquals("ERROR", outputStreamCaptor.toString().trim());
    }

    @Test
    public void getTest() {

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        CashMachine atm = new CashMachine();
        Controller controller = new Controller();
        controller.get("?", atm);

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));


    }


    @Test
    public void updateValidInputTest() {

        CashMachine atm = new CashMachine();
        Controller controller = new Controller();
        controller.put("+ USD 10 5", atm);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        String input = "- USD 20";
        controller.update(input, atm);
        String expectedOutput = "10 2\nOK";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void updateInvalidCurrencyTest() {
        CashMachine atm = new CashMachine();
        atm.deposit("USD", 10, 5);
        Controller controller = new Controller();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        String input = "- InvalidCurrency 20";
        controller.update(input, atm);
        String expectedOutput = "ERROR";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void updateInvalidAmountTest() {
        CashMachine atm = new CashMachine();
        atm.deposit("USD", 10, 5);
        Controller controller = new Controller();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        String input = "- USD -20";
        controller.update(input, atm);
        String expectedOutput = "ERROR";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }


    @Test
    public void getBalanceTest() {

        CashMachine atm = new CashMachine();
        Controller controller = new Controller();

        controller.put("+ CHF 100 5", atm);
        controller.put("+ USD 10 48", atm);
        controller.put("+ USD 100 29", atm);

        String input = "?";
        String expectedOutput = "CHF 100 5\nUSD 10 48\nUSD 100 29\nOK";

        testGetOperation(input, expectedOutput, atm);
    }


    @Test
    public void getBalanceWithIntermediateUpdateTest() {
        CashMachine atm = new CashMachine();
        Controller controller = new Controller();

        controller.operate("+ CHF 100 5", atm);
        controller.put("+ USD 10 48", atm);
        controller.put("+ USD 100 29", atm);

        String updateInput = "- USD 50";
        String updateExpectedOutput = "10 5\nOK";

        testUpdateOperation(updateInput, updateExpectedOutput, atm);

        String getBalanceInput = "?";
        String expectedOutput = "CHF 100 5\nUSD 10 43\nUSD 100 29\nOK";

        testGetOperation(getBalanceInput, expectedOutput, atm);
    }



    private void testUpdateOperation(String input, String expectedOutput, CashMachine cashMachine) {
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        Controller controller = new Controller();

        controller.operate(input, cashMachine);

        System.setIn(originalSystemIn);
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    private void testGetOperation(String input, String expectedOutput, CashMachine cashMachine) {
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        Controller controller = new Controller();

        controller.operate(input, cashMachine);

        System.setIn(originalSystemIn);
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        String[] expectedLines = expectedOutput.split("\n");
        assertEquals(expectedLines[expectedLines.length - 1], "OK");

        Map<String, Map<Integer, Integer>> expectedBalances = new HashMap<>();
        for (int i = 0; i < expectedLines.length - 1; i++) {
            String[] parts = expectedLines[i].split("\\s+");
            String currency = parts[0];
            int value = Integer.parseInt(parts[1]);
            int number = Integer.parseInt(parts[2]);

            expectedBalances.computeIfAbsent(currency, k -> new HashMap<>()).put(value, number);
        }

        for (String currency : expectedBalances.keySet()) {
            assertEquals(expectedBalances.get(currency), cashMachine.getCurrencyAccount(currency));
        }
    }


}