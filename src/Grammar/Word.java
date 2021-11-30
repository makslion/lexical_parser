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
}
