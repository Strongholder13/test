package campus;

import campus.answers.ErrorDataAnswer;



public class Controller {


    public Controller() {
    }

    public void operate(String message, CashMachine cashMachine) {

        switch (message.charAt(0)) {
            case '+' -> this.put(message, cashMachine);
            case '-' -> this.update(message, cashMachine);
            case '?' -> this.get(message, cashMachine);
            default -> new ErrorDataAnswer();
        }

    }

    public void put(String message, CashMachine cashMachine) {
        String[] commands = message.split("\\s+");

        if (commands.length != 4) {
            new ErrorDataAnswer();
        }
        try {
            String currency = commands[1];
            int value = Integer.parseInt(commands[2]);
            int number = Integer.parseInt(commands[3]);
            if (!Validator.isValidCurrency(currency) || !Validator.isValidValue(value) || number <= 0) {
                new ErrorDataAnswer();
            } else {
                cashMachine.deposit(currency, value, number);
            }
        } catch (NumberFormatException e) {
            new ErrorDataAnswer();
        }


    }

    public void update(String message, CashMachine cashMachine) {
        String[] commands = message.split("\\s+");
        if (commands.length != 3) {
            new ErrorDataAnswer();
            return;
        }
        try {
            String currency = commands[1];
            int amount = Integer.parseInt(commands[2]);

            if (!Validator.isValidCurrency(currency) || amount <= 0) {
                new ErrorDataAnswer();
            } else {
                cashMachine.withdrawal(currency, amount);
            }
        } catch (NumberFormatException e) {
            new ErrorDataAnswer();
        }
    }

    public void get(String message, CashMachine cashMachine) {
        if (message.equals("?")){
            cashMachine.checkBalance();
        } else {
            new ErrorDataAnswer();
        }


    }






}
