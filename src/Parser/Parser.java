package Parser;

import Grammar.Lexicon;
import Grammar.Rule;
import Grammar.Rules;
import Grammar.Word;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Parser object. Utilizes {@link Lexicon}, {@link Rules}, {@link Word} to parse text and output parsed tree
 * <p>{@link #Parser(CustomReader)}</p>
 * <p>{@link #parse(ArrayList)}</p>
 */
public class Parser {
    private ArrayList<String> text;
    private Queue<Word> taggedText;
    private final Lexicon lexicon;
    private final Rules rules;

    /**
     * @param reader reader to obtain {@link Lexicon} and {@link Rules} objects
     */
    public Parser(CustomReader reader) {
        lexicon = new Lexicon(reader);
        rules = new Rules(reader);
    }

    /**
     * Parse provided text. Will output a parse tree to a terminal.
     * @param text ArrayList that represents a sentence and each  String inside represents a word in a sentence.
     */
    public void parse (ArrayList<String> text){
        this.text = text;
        System.out.println("Checking lexicon");
        if (!lexiconRecognized()){
            System.err.println("Lexicon is not recognized");
            System.exit(1);
        } else {
            tagText();
            buildTree();
        }
    }

    /**
     * @return false if at least one word in passed text in no recognized by a lexicon
     */
    private boolean lexiconRecognized(){
        for (String word : text)
            if (!lexicon.contains(word))
                return false;
        return true;
    }

    /**
     * Tags passed in text. Produces Queue<Word> taggedText as a result
     */
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

    /**
     * Build and outputs a parseTree
     */
    private void buildTree(){
        System.out.println("Building tree");
        ParsedNode tree = expandRule(rules.getBaseRule(), true);
        if (!taggedText.isEmpty()){
            System.err.println("evaluated all rules but have "+text+" left");
            System.exit(1);
        }
        System.out.println("Checking tree numbers");
        checkTreeNumbers(tree);
        System.out.println("Tree build:");
        System.out.println(tree+"\n");
    }

    /**
     * Recursive method that expands rule passed inside.
     * @param rule to be expanded and parsed
     * @param lastRule used to track the last rule in case multiple were tried
     * @return a {@link ParsedNode} that contains parsed Rule
     */
    private ParsedNode expandRule(Rule rule, boolean lastRule){
        ParsedNode node = new ParsedNode();
        node.setPos(rule.getHead());
        List<String> describeRule = rule.describeRule();
        for (int j = 0; j < describeRule.size(); j++) {
            String childRule = describeRule.get(j);
            boolean lastChildRule = (j == describeRule.size() - 1) && lastRule;
            if (isTerminal(childRule)) {
                // consuming token on valid terminal
                Word currentWord = taggedText.poll();
                if (currentWord == null)
                    return null;
                if (currentWord.getPos().equals(childRule)) {
                    // this node will be a leaf in a tree. Exit statement for a recursion
                    ParsedNode childNode = new ParsedNode();
                    childNode.setWord(currentWord);
                    node.addChildren(childNode);
                } else if (lastRule) {
                    System.err.println("Evaluating rule: " + rule);
                    System.err.println("Looking for " + childRule + " but found " + currentWord.getPos());
                    System.exit(1);
                }
            // if it is a non-terminal
            } else {
                //filtering rules
                ArrayList<Rule> matchedRules = getValidRules(rules.getRule(childRule), taggedText, lastChildRule);

                for (Rule currentRule : matchedRules) {
                    if (taggedText.isEmpty()) {
                        System.err.println("Was about to evaluate " + currentRule);
                        System.err.println("but out of text");
                        System.exit(1);
                    }
                    // recursive call only in valid rules
                    node.addChildren(expandRule(currentRule, lastChildRule));
                }
            }
        }
        return node;
    }


    /**
     * Used to remove rules from the ArrayList that will not satisfy passed inside text
     * @param matchedRules list of rules to validate
     * @param passedText text to be used for rules validation
     * @param lastRule boolean to track the last rule
     * @return cleaned up list of rules
     */
    private ArrayList<Rule> getValidRules(ArrayList<Rule> matchedRules, Queue<Word> passedText, boolean lastRule){
        matchedRules.removeIf(rule -> !predictRule(rule, passedText, lastRule));
        return matchedRules;
    }


    /**
     * Check if passed inside rule can parse give text
     * @param rule rule to che checked
     * @param passedText text at the current state of parsing
     * @param lastRule boolean to track the last rule
     * @return true if text satisfies rule and false otherwise
     */
    private boolean predictRule(Rule rule, Queue<Word> passedText, boolean lastRule){
        // in case of the incomplete sentence
        if (passedText.isEmpty())
            return false;

        // deep copy of a text
        Queue<Word> taggedTextCopy = new ArrayDeque<>(passedText);
        for (String childRule : rule.describeRule()){
            // exit statement of recursion
            if (isTerminal(childRule)) {
                // not consuming token as multiple call can be made on the same text
                Word currentWord = taggedTextCopy.peek();
                if (currentWord == null)
                    return false;
                if (currentWord.getPos().equals(childRule)) {
                    taggedTextCopy.poll();
                } else
                    return false;
            } else{
                ArrayList<Rule> subRules = getValidRules(rules.getRule(childRule), taggedTextCopy, lastRule);
                return !subRules.isEmpty();
            }
        }
        return !lastRule || taggedTextCopy.isEmpty();
    }

    /**
     * @param candidate string to be checked
     * @return true if passed in string is a terminal (e.g v or det) and false if it is non-terminal (e.g. VP, S)
     */
    private boolean isTerminal(String candidate){
        for (int i = 0; i < candidate.length(); i++){
            if (Character.isUpperCase(candidate.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * Ensures that nodes inside the tree are matching each other's numbers (NP numbers must match verb in VP)
     * @param tree tree to be checked/
     */
    private void checkTreeNumbers(ParsedNode tree){
        ParsedNode vp, np;
        vp = np = new ParsedNode();

        for (ParsedNode child : tree.getChildren()){
            if (child.getPOS().equals("NP"))
                np = child;
            else if (child.getPOS().equals("VP"))
                vp = child;
            else
                checkTreeNumbers(child);
        }
        if (!vp.getNumber().contains(np.getNumber()) && !np.getNumber().contains(vp.getNumber())){
            System.err.println("Numbers in VP vs NP does not match");
            System.err.println("NP("+np.getNumber()+"): "+np);
            System.err.println("VP("+vp.getNumber()+"): "+vp);
            System.exit(1);
        }

    }
}
