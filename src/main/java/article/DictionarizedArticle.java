package article;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;

@Data
public class DictionarizedArticle {
    private final ArrayList<String> label;
    private final HashSet<String> dictionary;

    public DictionarizedArticle(ArrayList<String> label, HashSet<String> tokens) {
        this.label = label;
        this.dictionary = tokens;
    }
}
