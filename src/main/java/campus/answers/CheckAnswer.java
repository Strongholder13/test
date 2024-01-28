package campus.answers;

import java.util.HashMap;

public class CheckAnswer extends Answer {

    private String answer;

    public CheckAnswer(HashMap<String, HashMap<Integer, Integer>> total) {
        StringBuilder result = new StringBuilder();

        total.keySet().stream()
                .sorted()
                .forEach(currency -> total.get(currency).keySet().stream()
                        .sorted()
                        .forEach(value -> result.append(currency).append(" ").append(value)
                                .append(" ").append(total.get(currency).get(value)).append("\n")));

        answer = result.append("OK").toString();
        System.out.println(answer);
    }
}
