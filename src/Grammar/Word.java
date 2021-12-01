package Grammar;

/**
 * Represents a Word that is recognized by a lexicon
 * <p>{@link #Word(String)}</p>
 * <p>{@link #Word()}</p>
 * <p>{@link #getWord()}</p>
 * <p>{@link #getPos()}</p>
 * <p>{@link #getRoot()}</p>
 * <p>{@link #getNumber()}</p>
 * <p>{@link #setWord(String)}</p>
 * <p>{@link #setPos(String)}</p>
 * <p>{@link #setRoot(String)}</p>
 * <p>{@link #setNumber(String)}</p>
 */
public class Word {
    private String word;
    private String pos;
    private String root;
    private String number;

    /**
     * Used to populate lexicon with read words from the Grammar/lexicon.txt
     * @param line String that represent a single line in Grammar/lexicon.txt
     */
    public Word(String line) {
        String [] lineSplitted = line.split(" ");
        word = lineSplitted[0];
        pos = lineSplitted[1];
        root = lineSplitted[2];
        number = lineSplitted[3];
    }

    /**
     * Used to create a Word object inside the {@link Parser.ParsedNode ParsedNode} class
     */
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
