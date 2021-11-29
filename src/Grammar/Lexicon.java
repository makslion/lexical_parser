package Grammar;

import Parser.CustomReader;

import java.util.ArrayList;

public class Lexicon {
    ArrayList<Word> words;

    public Lexicon(CustomReader reader) {
        words = reader.readLexicon();
    }

    public boolean contains(String word){
        for (Word value : words)
            if (value.getWord().equalsIgnoreCase(word))
                return true;
        return false;
    }
}
