package campus;

import campus.answers.CheckAnswer;
import campus.answers.DepositAnswer;
import campus.answers.ErrorAnswer;
import campus.answers.WithdrawalAnswer;

import java.util.HashMap;
import java.util.Map;

public class CashMachine implements Operations {
    private final HashMap<String, HashMap<Integer, Integer>> cashMap;
    public CashMachine() {

        this.cashMap = new HashMap<>();
    }


    @Override
    public void deposit(String currency, int value, int number) {

        cashMap.putIfAbsent(currency, new HashMap<>());
        cashMap.get(currency).put(value, cashMap.get(currency).getOrDefault(value, 0) + number);
        new DepositAnswer();

    }

    @Override
    public void withdrawal(String currency, int amount) {
        HashMap<Integer, Integer> currencyNotes = this.getCurrencyAccount(currency);

        if (currencyNotes == null || this.getCurrencySum(currency) < amount) {
            new ErrorAnswer();
            return;
        }

        HashMap<Integer, Integer> getNotes = getWithdrawMap(currencyNotes, amount);

        if (getNotes != null) {
            new WithdrawalAnswer(getNotes);
        } else {
            new ErrorAnswer();
        }
    }


    @Override
    public void checkBalance() {
        new CheckAnswer(this.cashMap);

    }

    public HashMap<Integer, Integer> getCurrencyAccount(String currency){
        return cashMap.get(currency);
    }

    public int getCurrencySum(String currency) {

        int sum = 0;
        if (this.getCurrencyAccount(currency) != null) {
            for (Map.Entry<Integer, Integer> entrySet : this.getCurrencyAccount(currency).entrySet()) {
                sum += entrySet.getKey() * entrySet.getValue();
            }
        }

        return sum;
    }

    public HashMap<Integer, Integer> getWithdrawMap(HashMap<Integer, Integer> currencyNotes, int amount){
        HashMap<Integer, Integer> getNotes = new HashMap<>();


        for (int noteValue : currencyNotes.keySet()) {
            int noteCount = Math.min(amount / noteValue, currencyNotes.get(noteValue));
            amount -= noteValue * noteCount;

            if (noteCount > 0) {
                getNotes.put(noteValue, noteCount);
                currencyNotes.put(noteValue, currencyNotes.get(noteValue) - noteCount);
            }
        }

        if (amount == 0) {
            return getNotes;
        } else {
            getNotes.forEach((noteValue, noteCount) -> {
                currencyNotes.put(noteValue, currencyNotes.get(noteValue) + noteCount);
            });
            return null;
        }
    }
}
