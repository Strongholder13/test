package campus;


import java.util.HashSet;
import java.util.Set;

public class Validator {

    private static final Set<Integer> values = new HashSet<>(Set.of(10, 50, 100, 500));

    public Validator() {
    }


    public static boolean isValidCurrency(String currency) {
        return currency.matches("[A-Z]{3}");
    }

    public static boolean isValidValue(int value) {
        return values.contains(value);
    }

}
