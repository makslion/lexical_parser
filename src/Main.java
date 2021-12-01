import Parser.CustomReader;
import Parser.Parser;

import java.io.File;
import java.util.ArrayList;

public class Main {
    private static final String textToRead = "src//parseme.txt";

    public static void main(String[] args) {
        File text = new File(textToRead);
        if (text.exists()) {
            CustomReader reader = new CustomReader();
            Parser parser = new Parser(reader);
            for (ArrayList<String> sentence : reader.readSentence(text))
                parser.parse(sentence);
        } else {
            System.out.println("not found file " + textToRead + " to read");
            System.exit(1);
        }
    }
}