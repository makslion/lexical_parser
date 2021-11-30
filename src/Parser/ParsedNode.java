package Parser;

import Grammar.Rule;

import java.util.ArrayList;

public class ParsedNode {
    private ArrayList<ParsedNode> children = new ArrayList<>();
    private String element;
    private String pos;

    public ParsedNode() {
    }

    public void addCurrent(String element, String pos){
        this.element = element;
        this.pos = pos;
    }

    public void addChildren(ParsedNode parsedNode){
        children.add(parsedNode);
    }

    @Override
    public String toString() {
        return null;
    }
}
