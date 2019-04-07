import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
public class DictionarizedArticle {
    private final ArrayList<String> label;
    private HashSet<String> dictionary;
    private Article article;
    private List<String> listOfWords;

    public DictionarizedArticle(ArrayList<String> label, HashSet<String> tokens) {
        this.label = label;
        this.dictionary = tokens;
    }

    public DictionarizedArticle(Article article) {
        this.label = article.getLabel();
        this.article = article;

        this.listOfWords = createListOfWords(article.getTextAndTitle());
        this.dictionary = extractTokens();
    }

    private HashSet<String> extractTokens() {
        return Preprocessor.extractTokens(article.getTextAndTitle());
    }

    private List<String> createListOfWords(String text){
        return Preprocessor.extractWords(text);
    }


}
