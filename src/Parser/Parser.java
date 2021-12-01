package Parser;

import Grammar.Lexicon;
import Grammar.Rule;
import Grammar.Rules;
import Grammar.Word;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
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
    }

    private void buildTree(){
        System.out.println("Building tree");
        ParsedNode tree = expandRule(rules.getBaseRule(), true);
//        if out terminals - success
//        else if !has rules and !has terminals throw an exception
        if (!taggedText.isEmpty()){
            System.err.println("evaluated all rules but have "+text+" left");
            System.exit(1);
        }
        System.out.println("Checking tree numbers");
        checkTreeNumbers(tree);
        System.out.println("Tree build:");
        System.out.println(tree);
//        print tree
    }

    // takes non terminal and returns node
    private ParsedNode expandRule(Rule rule, boolean lastRule){
//        create a new node
        ParsedNode node = new ParsedNode();
        //        for each in rule
        node.setPos(rule.getHead());
        List<String> describeRule = rule.describeRule();
        for (int j = 0; j < describeRule.size(); j++) {
            String childRule = describeRule.get(j);
            boolean lastChildRule = (j == describeRule.size() - 1) && lastRule;
            if (isTerminal(childRule)) {
                Word currentWord = taggedText.peek();
                //                if match add to node
                if (currentWord.getPos().equals(childRule)) {
                    // consume token
                    taggedText.poll();
                    ParsedNode childNode = new ParsedNode();
                    childNode.setWord(currentWord);
                    node.addChildren(childNode);
                    //                if don't throw exception
                } else if (lastRule) {
                    System.err.println("Evaluating rule: " + rule);
                    System.err.println("Looking for " + childRule + " but found " + currentWord.getPos());
                    System.exit(1);
                }
            } else {
                ArrayList<Rule> matchedRules = rules.getRule(childRule);
                if (matchedRules.size() > 1) {
                    matchedRules = getValidRules(matchedRules, taggedText, lastChildRule);
                }

                for (int i = 0; i < matchedRules.size(); i++) {
                    Rule currentRule = matchedRules.get(i);
                    if (taggedText.isEmpty()) {
                        System.err.println("Was about to evaluate " + currentRule);
                        System.err.println("but out of text");
                        System.exit(1);
                    }
                    node.addChildren(expandRule(currentRule, lastChildRule));
                }
            }
        }
        return node;
    }


    private ArrayList<Rule> getValidRules(ArrayList<Rule> matchedRules, Queue<Word> passedText, boolean lastRule){
        matchedRules.removeIf(rule -> !predictRule(rule, passedText, lastRule));
        return matchedRules;
    }


    private boolean predictRule(Rule rule, Queue<Word> passedText, boolean lastRule){
        Queue<Word> taggedTextCoppy = new ArrayDeque<>(passedText);
        for (String childRule : rule.describeRule()){
            if (isTerminal(childRule)) {
                Word currentWord = taggedTextCoppy.peek();
                if (currentWord.getPos().equals(childRule)) {
                    taggedTextCoppy.poll();
                } else
                    return false;
            } else{
                ArrayList<Rule> subRules = getValidRules(rules.getRule(childRule), taggedTextCoppy, lastRule);
                return !subRules.isEmpty();
            }
        }
        return !lastRule || taggedTextCoppy.isEmpty();
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


    private void checkTreeNumbers(ParsedNode tree){
        ParsedNode vp, np;
        vp = np = new ParsedNode();

        for (ParsedNode child : tree.getChildren()){
            if (child.getPOS().equals("NP"))
                np = child;
            else if (child.getPOS().equals("VP"))
                vp = child;
        }
        if (!vp.getNumber().contains(np.getNumber()) && !np.getNumber().contains(vp.getNumber())){
            System.err.println("Numbers in VP vs NP does not match");
            System.err.println("NP("+np.getNumber()+"): "+np);
            System.err.println("VP("+vp.getNumber()+"): "+vp);
            System.exit(1);
        }

    }
}
