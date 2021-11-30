package Grammar;

import Parser.CustomReader;

import java.util.ArrayList;

public class Rules {
    private POS partsOfSpeech;
    private ArrayList<Rule> rules;

    public Rules(CustomReader reader) {
        reader.readRules(this);
    }

    public void setPartsOfSpeech(POS partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
    }

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

    public Rule getBaseRule(){
        for (Rule rule : rules)
            if (rule.getHead().equals("S"))
                return rule;
        return null;
    }
}
