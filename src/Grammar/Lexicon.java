package Grammar;

import Parser.CustomReader;

import java.util.ArrayList;

/**
 * Lexicon is used to hold all recognized words by the parser
 * <p>{@link #Lexicon(CustomReader)}</p>
 * <p>{@link #contains(String)}</p>
 * <p>{@link #getWord(String)}</p>
 */
public class Lexicon {
    private final ArrayList<Word> words;

    /**
     * @param reader {@link CustomReader CustomReader} object to obtain a lexicon from the Grammar/lexicon.txt
     */
    public Lexicon(CustomReader reader) {
        words = reader.readLexicon();
    }

    /**
     * contains() checks lexicon for existence of particular word
     * @param word String that will be used to search for the word in a lexicon
     * @return true if word exists in a lexicon. False otherwise
     */
    public boolean contains(String word){
        for (Word value : words)
            if (value.getWord().equalsIgnoreCase(word))
                return true;
        return false;
    }

    /**
     * @param word String to be used to search lexicon for a word
     * @return {@link Word Word} object if word exists in a lexicon and null otherwise
     */
    public Word getWord(String word){
        for (Word currentWord : words)
            if (currentWord.getWord().equals(word))
                return currentWord;
        return null;
    }
}
