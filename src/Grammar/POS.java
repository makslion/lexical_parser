package Grammar;

import java.util.Arrays;
import java.util.List;

/**
 * Represents Parts Of Speech recognized by parser
 * <p>{@link #POS(String)}</p>
 */
public class POS {
    private final List<String> partsOfSpeech;

    /**
     * @param line String that will be split and stored in a List
     */
    public POS(String line) {
        partsOfSpeech = Arrays.stream(line.split(" ")).toList();
    }
}
