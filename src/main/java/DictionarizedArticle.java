import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;

@Data
public class DictionarizedArticle {
    private final ArrayList<String> label;
    private HashSet<String> dictionary;
    private Article article;
    private ArrayList<String> listOfWords;

    public DictionarizedArticle(ArrayList<String> label, HashSet<String> tokens) {
        this.label = label;
        this.dictionary = tokens;
    }

    public DictionarizedArticle(Article article) {
        this.label = article.getLabel();
        this.article = article;

        this.listOfWords = createListOfWords(article.getTextAndTitle());
    }

    public DictionarizedArticle extractTokens() {
        this.dictionary = Preprocessor.extractTokens(article.getTextAndTitle());
        return this;
    }

    private ArrayList<String> createListOfWords(String text){
        return Preprocessor.extractWords(text);
    }


}
