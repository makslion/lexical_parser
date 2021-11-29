package Parser;

import Grammar.Lexicon;
import Grammar.Rules;

import java.util.ArrayList;

public class Parser {
    private ArrayList<String> text;
    private Lexicon lexicon;
    private Rules rules;

    public Parser(CustomReader reader, ArrayList<String> text) {
        this.text = text;
        lexicon = new Lexicon(reader);
        rules = new Rules(reader);
    }

    public void parse (){
        System.out.println("Checking lexicon");
        if (!lexiconRecognized()){
            System.err.println("Lexicon is not recognized");
            System.exit(1);
        } else {
            System.out.println("Lexicon is recognized");
        }
    }

    private boolean lexiconRecognized(){
        for (String word : text)
            if (!lexicon.contains(word))
                return false;
        return true;
    }
}
