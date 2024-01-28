package campus;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        CashMachine atm = new CashMachine();
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            controller.operate(command, atm);

            }

        }

    }
