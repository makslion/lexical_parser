package Parser;

import Grammar.POS;
import Grammar.Rule;
import Grammar.Rules;
import Grammar.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * CustomReader is used to read text files that contain lexicon, rules and text to parse.
 * <p>{@link #readLexicon()}</p>
 * <p>{@link #readRules(Rules)}</p>
 * <p>{@link #readSentence(File)}</p>
 */
public class CustomReader {
    private final String lexiconFileName = "src//Grammar//lexicon.txt";
    private final String rulesFileName = "src//Grammar//rules.txt";

    /**
     * readLexicon() reads a lexicon from the hardcoded text file at Grammar/lexicon.txt. It converts it into
     * an array list of Word
     * @return ArrayList<Word> with words read from the file or an empty ArrayList in case of error reading or processing
     */
    public ArrayList<Word> readLexicon(){
        ArrayList<Word> words = new ArrayList<>();
        File lexiconFile = new File(lexiconFileName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(lexiconFile));
            String line;

            while ((line = bufferedReader.readLine()) != null)
                words.add(new Word(line));

        } catch (FileNotFoundException e) {
            System.err.println("File "+lexiconFileName+" not found");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("IO Exception while reading lexicon file");
            e.printStackTrace();
        }
        return words;
    }

    /**
     * Read a set of grammar rules from hardcoded file at Grammar/rules.txt. It populates passed in Rules class with the
     * content of that file.
     * @param rulesClass rules object to be populated with Rules and parts of speech
     */
    public void readRules(Rules rulesClass){
        ArrayList<Rule> rules = new ArrayList<>();
        File rulesFile = new File(rulesFileName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(rulesFile));
            String line;
            rulesClass.setPartsOfSpeech(new POS(bufferedReader.readLine()));

            while ((line = bufferedReader.readLine()) != null)
                rules.add(new Rule(line));

        } catch (FileNotFoundException e) {
            System.err.println("File "+ rulesFileName +" not found");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("IO Exception while reading lexicon file");
            e.printStackTrace();
        }
        rulesClass.setRules(rules);
    }

    /**
     * readSentence reads sentences from provided file
     * @param file with sentences to be read
     * @return ArrayList of sentences where each sentence is an ArrayList of Strings that represent words
     */
    public ArrayList<ArrayList<String>> readSentence(File file){
        ArrayList<String> sentence = new ArrayList<>();
        ArrayList<ArrayList<String>> text = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sentence.clear();
                String [] lineSplit = line.split(" ");
                sentence.addAll(Arrays.asList(lineSplit));
                text.add(sentence);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File "+file.getName()+" at "+file.getPath()+" not found");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("IO Exception while reading lexicon file");
            e.printStackTrace();
        }

        return text;
    }
}
