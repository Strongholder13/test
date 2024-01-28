package campus.answers;

import java.util.Map;

public class WithdrawalAnswer extends Answer {
    private String answer;

    public WithdrawalAnswer( Map<Integer, Integer> getNotes) {
        StringBuilder result = new StringBuilder();

        for (Integer key : getNotes.keySet()) {
            result.append(key).append(" ").append(getNotes.get(key)).append("\n");
        }

        answer = result.append("OK").toString();
        System.out.println(answer);


    }
}
