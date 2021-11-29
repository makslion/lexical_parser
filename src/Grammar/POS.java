package Grammar;

import java.util.Arrays;
import java.util.List;

public class POS {
    List<String> partsOfSpeech;
    public POS(String line) {
        partsOfSpeech = Arrays.stream(line.split(" ")).toList();
    }
}
