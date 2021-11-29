import Parser.CustomReader;
import Parser.Parser;

import java.io.File;

public class Main {
    private static String textToRead = "src//parseme.txt";

    public static void main(String[] args) {
        File text = new File(textToRead);
        if (text.exists()){
            CustomReader reader = new CustomReader();
            Parser parser = new Parser(reader, reader.readSentence(text));
            parser.parse();
        } else {
            System.out.println("not found file "+textToRead+" to read");
            System.exit(1);
        }
    }
}