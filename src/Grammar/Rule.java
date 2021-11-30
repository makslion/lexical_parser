package Grammar;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String head;
    private List<String> rule = new ArrayList<>();

    public Rule(String line) {
        String [] lineSplitted = line.split(" ");
        head = lineSplitted[0];
        for (int i = 1; i < lineSplitted.length; i++)
            rule.add(lineSplitted[i]);
    }

    public String getHead() {
        return head;
    }

    public List<String> describeRule() {
        return rule;
    }

    @Override
    public String toString() {
        String tail = "";
        for (String entry : rule)
            tail += entry+" ";
        return head+" -> "+tail;
    }
}
