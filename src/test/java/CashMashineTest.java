
import campus.CashMachine;
import campus.Controller;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashMashineTest {


    @Test
    public void getCurrencySumTest() {
        CashMachine atm = new CashMachine();
        atm.deposit("USD", 10, 5);
        atm.deposit("USD", 50, 3);
        atm.deposit("EUR", 20, 7);
        atm.deposit("CHF", 20, 7);

        int usdSum = atm.getCurrencySum("USD");
        assertEquals(10 * 5 + 50 * 3, usdSum);

        int eurSum = atm.getCurrencySum("EUR");
        assertEquals(20 * 7, eurSum);

        int rubSum = atm.getCurrencySum("RUB");
        assertEquals(0, rubSum);

        int chfSum = atm.getCurrencySum("CHF");
        assertEquals(20 * 7, chfSum);
    }


    @Test
    public void depositValidInputTest() {

        CashMachine atm = new CashMachine();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        atm.deposit("USD", 10, 5);
        String expectedOutput = "OK";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void getBalanceTest() {

        CashMachine atm = new CashMachine();
        atm.deposit("CHF", 100, 5);
        atm.deposit("USD", 10, 48);
        atm.deposit("USD", 100, 29);

        String input = "?";
        String expectedOutput = "CHF 100 5\nUSD 10 48\nUSD 100 29\nOK";

        testGetOperation(input, expectedOutput, atm);
    }

    @Test
    public void getBalanceWithIntermediateUpdateTest() {

        CashMachine atm = new CashMachine();
        atm.deposit("CHF", 100, 5);
        atm.deposit("USD", 10, 48);
        atm.deposit("USD", 100, 29);

        String withdrawalInputCurrency = "USD";
        int withdrawalValue = 50;

        String updateExpectedOutput = "10 5\nOK";
        testUpdateOperation(withdrawalInputCurrency, withdrawalValue, updateExpectedOutput, atm);

        String getBalanceInput = "?";
        String expectedOutput = "CHF 100 5\nUSD 10 43\nUSD 100 29\nOK";

        testGetOperation(getBalanceInput, expectedOutput, atm);
    }
    private void testUpdateOperation(String input, int value, String expectedOutput, CashMachine atm) {
        InputStream originalSystemIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        atm.withdrawal(input, value);

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