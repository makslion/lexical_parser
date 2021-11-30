package Parser;

import Grammar.Lexicon;
import Grammar.Rule;
import Grammar.Rules;
import Grammar.Word;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Parser {
    private ArrayList<String> text;
    private Queue<Word> taggedText;
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
            tagText();
            buildTree();
        }
    }

    private boolean lexiconRecognized(){
        for (String word : text)
            if (!lexicon.contains(word))
                return false;
        return true;
    }


    private void tagText(){
        System.out.println("Tagging text");
        taggedText = new ArrayDeque<>();
        for (String word : text){
            Word tmp = lexicon.getWord(word);
            if (tmp == null){
                System.err.println("Haven't found word: \""+word+"\" in a lexicon");
                System.exit(1);
            }
            taggedText.add(tmp);
        }
        System.out.println("Text tagged");
    }

    private void buildTree(){
        System.out.println("Building tree");
        ParsedNode tree = expandRule(rules.getBaseRule(), false);
//        if out terminals - success
//        else if !has rules and !has terminals throw an exception
        if (!text.isEmpty()){
            System.err.println("evaluated all rules but have "+text+" left");
            System.exit(1);
        }
        System.out.println("Tree build");
//        print tree
    }

    // takes non terminal and returns node
    private ParsedNode expandRule(Rule rule, boolean canFail){
//        create a new node
        ParsedNode node = new ParsedNode();
        //        for each in rule
        for (String childRule : rule.describeRule()){
            if (isTerminal(childRule)){
                Word currentWord = taggedText.peek();
                //                if match add to node
                if (currentWord.getPos().equals(childRule)) {
                    // consume token
                    taggedText.poll();
                    node.addCurrent(childRule, currentWord.getPos());
                    //                if don't throw exception
                } else if (!canFail){
                    System.err.println("Evaluating rule: " + rule);
                    System.err.println("Looking for " + childRule + " but found " + currentWord.getPos());
                    System.exit(1);
                }
            } else {
                ArrayList<Rule> matchedRules = rules.getRule(childRule);
                if (matchedRules.size() > 1){
                    matchedRules = getValidRules();
                }

                for (int i = 0; i < matchedRules.size(); i++){
                    Rule currentRule = matchedRules.get(i);
                    canFail = i < matchedRules.size() - 1;
                    if (taggedText.isEmpty()) {
                        System.err.println("Was about to evaluate " + currentRule);
                        System.err.println("but out of text");
                        System.exit(1);
                    }
                    boolean ruleEvaluates = predictRule(currentRule, new ArrayDeque<>(taggedText));
                    if (ruleEvaluates)
                        node.addChildren(expandRule(currentRule,canFail));
                    else if (!canFail){
                        System.err.println("Run out of rules trying to parse: "+printQueue(taggedText));
                        System.err.println("Was looking for rules "+currentRule.getHead()+" -> ...");
                        System.exit(1);
                    }

                }
            }
        }
        return node;
    }


    private ArrayList<Rule> getValidRules(){
        return null;
    }


    private boolean predictRule(Rule rule, Queue<Word> taggedTextCoppy){
        boolean ruleValid = true;

        for (String childRule : rule.describeRule()){
            // how to fail VP -> v
            // and pass VP -> v NP

        }

        return ruleValid;
    }

    public boolean isTerminal(String candidate){
        for (int i = 0; i < candidate.length(); i++){
            if (Character.isUpperCase(candidate.charAt(i)))
                return false;
        }
        return true;
    }

    private String printQueue(Queue<Word> queue){
        String output = "";
        while (!queue.isEmpty())
            output += queue.poll().getWord()+" ";
        return output;
    }
}
