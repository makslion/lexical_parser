package Parser;

import Grammar.Rule;
import Grammar.Word;

import java.util.ArrayList;

public class ParsedNode {
    private ArrayList<ParsedNode> children = new ArrayList<>();
    private String element;
    private String pos;

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setWord(Word word){
        this.pos = word.getPos();
        this.element = word.getWord();
    }

    public void addChildren(ParsedNode parsedNode){
        children.add(parsedNode);
    }

    @Override
    public String toString() {
        String output = "[";
        if (!children.isEmpty()){
            output += pos;
            for (ParsedNode node : children)
                output += node.toString();
            output += "]";
        } else
            output += pos+"["+element+"]]";
        return output;
    }

    // [S[NP[det[the]][n[man]]][VP[v[bites]][NP[det[a]][n[dog]]]]]
}
