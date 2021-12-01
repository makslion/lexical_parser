package Grammar;

import Parser.CustomReader;

import java.util.ArrayList;

/**
 * Holds all rules for the grammar. Used to access rules
 * <p>{@link #Rules(CustomReader)}</p>
 * <p>{@link #setPartsOfSpeech(POS)}</p>
 * <p>{@link #setRules(ArrayList)}</p>
 * <p>{@link #getRule(String)}</p>
 * <p>{@link #getBaseRule()}</p>
 */
public class Rules {
    private POS partsOfSpeech;
    private ArrayList<Rule> rules;

    /**
     * @param reader to read the Grammar/rules.txt file and populate rules ArrayList
     */
    public Rules(CustomReader reader) {
        reader.readRules(this);
    }

    public void setPartsOfSpeech(POS partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    /**
     * Queries stored in memory rules and return all rules that start with the same head
     * @param head head of the rules to match
     * @return ArrayList of matched rules. Of no rules were matched returns empty ArrayList. If no rules were read
     * returns null.
     */
    public ArrayList<Rule> getRule(String head){
        ArrayList<Rule> rulesMatched = new ArrayList<>();
        for (Rule rule : rules){
            if (rule.getHead().equals(head))
                rulesMatched.add(rule);
        }
        if (rules.isEmpty())
            return null;
        else
            return rulesMatched;
    }

    /**
     * @return the base rule of the grammar that starts with "S ->"
     */
    public Rule getBaseRule(){
        for (Rule rule : rules)
            if (rule.getHead().equals("S"))
                return rule;
        return null;
    }
}
