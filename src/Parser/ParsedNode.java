package Parser;

import Grammar.Rule;
import Grammar.Word;

import java.util.ArrayList;

public class ParsedNode {
    private ArrayList<ParsedNode> children = new ArrayList<>();
    private Word word = new Word();

    public void setPos(String pos) {
        this.word.setPos(pos);
    }

    public void setWord(Word word){
        this.word = word;
    }

    public void addChildren(ParsedNode parsedNode){
        if (word.getPos().contains("NP")) {
            if (children.isEmpty()) {
                children.add(parsedNode);
                word.setNumber(parsedNode.getNumber());
            } else {
                if (parsedNode.getNumber().contains(this.word.getNumber())) {
                    children.add(parsedNode);
                } else if (this.word.getNumber().contains(parsedNode.getNumber())){
                    children.add(parsedNode);
                    this.word.setNumber(parsedNode.getNumber());
                } else {
                    System.err.println("Number mismatch for " + word.getPos());
                    System.err.print("Already have: ");
                    for (ParsedNode child : children) {
                        System.err.print(child.word.getWord() + "(" + child.getNumber() + ") ");
                    }
                    System.err.println("\nAnd was trying to add " + parsedNode.word.getWord() + "(" + parsedNode.getNumber() + ")");
                    System.exit(1);
                }
            }
        } else if (word.getPos().contains("VP")){
            if (parsedNode.getPOS().equals("v")){
                this.word.setNumber(parsedNode.getNumber());
                children.add(parsedNode);
            }
        } else
            children.add(parsedNode);
    }

    public String getNumber() {
        return word.getNumber();
    }

    public String getPOS() {
        return word.getPos();
    }

    public ArrayList<ParsedNode> getChildren() {
        return children;
    }

    public ParsedNode getChild(String pos){
        if (children.isEmpty())
            return null;
        else{
            for (ParsedNode child : children)
                if (child.getPOS().equals(pos))
                    return child;
        }
        return null;
    }

    @Override
    public String toString() {
        String output = "[";
        if (!children.isEmpty()){
            output += word.getPos();
            for (ParsedNode node : children)
                output += node.toString();
            output += "]";
        } else
            output += word.getPos()+"["+word.getWord()+"]]";
        return output;
    }
}
