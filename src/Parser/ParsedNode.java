package Parser;

import Grammar.Word;

import java.util.ArrayList;

/**
 * Represent a node in a Parse Tree
 * <p>{@link #setPos(String)}</p>
 * <p>{@link #setWord(Word)}</p>
 * <p>{@link #addChildren(ParsedNode)}</p>
 * <p>{@link #getNumber()}</p>
 * <p>{@link #getPOS()}</p>
 * <p>{@link #getChildren()}</p>
 * <p>{@link #getChild(String)}</p>
 * <p>{@link #toString()}</p>
 */
public class ParsedNode {
    private final ArrayList<ParsedNode> children = new ArrayList<>();
    private Word word = new Word();

    /**
     * Assigns Part Of Speech to a current node
     * @param pos String representing POS
     */
    public void setPos(String pos) {
        this.word.setPos(pos);
    }

    /**
     * Sets word for a current node. Used only in leaf nodes
     * @param word to be set
     */
    public void setWord(Word word){
        this.word = word;
    }

    /**
     * Adds a child {@link ParsedNode} to a current node. Also, assigns a Number to this node if it is a leaf node and checks
     * for a number matching inside the node
     * @param parsedNode node to be added
     */
    public void addChildren(ParsedNode parsedNode){
        // if current node is NP
        if (word.getPos().contains("NP")) {
            // if it is the first leaf to be added to NP
            if (children.isEmpty()) {
                children.add(parsedNode);
                word.setNumber(parsedNode.getNumber());
            } else {
                // if the current number is more restricted
                if (parsedNode.getNumber().contains(this.word.getNumber())) {
                    children.add(parsedNode);
                // if leaf number is more restricted
                } else if (this.word.getNumber().contains(parsedNode.getNumber())){
                    children.add(parsedNode);
                    this.word.setNumber(parsedNode.getNumber());
                // if numbers don't match
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
        // if current node is VP
        } else if (word.getPos().contains("VP")){
            // if leaf is verb assign it's number to the node
            if (parsedNode.getPOS().equals("v")){
                this.word.setNumber(parsedNode.getNumber());
                children.add(parsedNode);
            } else
                children.add(parsedNode);
        // if current node is not NP or NP we don't care about numbers
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

    /**
     * Gets a specific child. If multiple child matcher - returns first.
     * @param pos Part Of Speech to match the child
     * @return matched child
     */
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

    /**
     * Overridden {@link Object#toString()} method.
     * @return a bracketed representation of a node: [POS [word]] or [POS [childNode]] depending on having children
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("[");
        if (!children.isEmpty()){
            output.append(word.getPos());
            for (ParsedNode node : children)
                output.append(node.toString());
            output.append("]");
        } else
            output.append(word.getPos()).append("[").append(word.getWord()).append("]]");
        return output.toString();
    }
}
