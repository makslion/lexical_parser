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
}
