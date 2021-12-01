package Grammar;

public class Word {
    private String word;
    private String pos;
    private String root;
    private String number;

    public Word(String line) {
        String [] lineSplitted = line.split(" ");
        word = lineSplitted[0];
        pos = lineSplitted[1];
        root = lineSplitted[2];
        number = lineSplitted[3];
    }
    public Word() {
    }

    public String getWord() {
        return (word == null) ? "" : word;
    }

    public String getPos() {
        return (pos == null) ? "" : pos;
    }

    public String getRoot() {
        return (root == null) ? "" : root;
    }

    public String getNumber() {
        return (number == null) ? "" : number;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
