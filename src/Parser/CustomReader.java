package Parser;

import Grammar.POS;
import Grammar.Rule;
import Grammar.Rules;
import Grammar.Word;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class CustomReader {
    private String lexiconFileName = "src//Grammar//lexicon.txt";
    private String rulesFileName = "src//Grammar//rules.txt";

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

    public void readRules(Rules rulesClass){
        ArrayList<Rule> rules = new ArrayList<>();
        File rulesFile = new File(rulesFileName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(rulesFile));
            String line;

            // read first line
            rulesClass.setPartsOfSpeech(new POS(bufferedReader.readLine()));

            while ((line = bufferedReader.readLine()) != null)
                rules.add(new Rule(line));

        } catch (FileNotFoundException e) {
            System.err.println("File "+lexiconFileName+" not found");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("IO Exception while reading lexicon file");
            e.printStackTrace();
        }
        rulesClass.setRules(rules);
    }

    public ArrayList<String> readSentence(File file){
        ArrayList<String> sentence = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(file);

            int character;
            while ( (character = fileReader.read()) != -1){
                char convertedCharacter = (char) character;

                if (convertedCharacter == ' '){
                    sentence.add(word.toString());
                    word = new StringBuilder();
                } else{
                    word.append(convertedCharacter);
                }
            }

            // add the final word if the sentence ended not with a space
            if (word.length() > 0 && !word.toString().equals(" "))
                sentence.add(word.toString());

        } catch (FileNotFoundException e) {
            System.err.println("File "+file.getPath()+" not found!");
            e.printStackTrace();
        } catch (IOException e){
            System.err.println("IO Exception while reading the file");
            e.printStackTrace();
        }
        return sentence;
    }



    public String getLexiconFileName() {
        return lexiconFileName;
    }

    public void setLexiconFileName(String lexiconFileName) {
        this.lexiconFileName = lexiconFileName;
    }

    public String getRulesFileName() {
        return rulesFileName;
    }

    public void setRulesFileName(String rulesFileName) {
        this.rulesFileName = rulesFileName;
    }
}
