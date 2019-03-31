import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DictionarizedArticle {
    private final ArrayList<String> label;
    private final HashSet<String> dictionary;

    public DictionarizedArticle(ArrayList<String> label, HashSet<String> tokens) {
        this.label = label;
        this.dictionary = tokens;
    }
}
