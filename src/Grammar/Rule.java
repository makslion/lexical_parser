package Grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a single rule in a grammar
 * <p>{@link #Rule(String)}</p>
 * <p>{@link #getHead()}</p>
 * <p>{@link #describeRule()}</p>
 * <p>{@link #toString()}</p>
 */
public class Rule {
    private final String head;
    private final List<String> rule = new ArrayList<>();

    /**
     * @param line String that represents a line from Grammar/rules.txt
     */
    public Rule(String line) {
        String [] lineSplit = line.split(" ");
        head = lineSplit[0];
        rule.addAll(Arrays.asList(lineSplit).subList(1, lineSplit.length));
    }

    /**
     * @return the head of a rule. For example in case "S -> NP VP" it will return "S"
     */
    public String getHead() {
        return head;
    }

    /**
     * @return the body of the rule. For example in case "S -> NP VP" it will return "NP VP" stored as a separate
     * strings in an ArrayList
     */
    public List<String> describeRule() {
        return rule;
    }

    /**
     * Overridden {@link Object#toString()} method.
     * @return representation of the rule for example: S -> NP VP
     */
    @Override
    public String toString() {
        StringBuilder tail = new StringBuilder();
        for (String entry : rule)
            tail.append(entry).append(" ");
        return head+" -> "+tail;
    }
}
