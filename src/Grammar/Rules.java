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
}
