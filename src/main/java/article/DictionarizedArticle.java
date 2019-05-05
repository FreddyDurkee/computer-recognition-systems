package article;

import lombok.Data;
import other.Preprocessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class DictionarizedArticle {
    private final ArrayList<String> label;
    private String articleText;
    private Set<String> dictionary;
    private List<String> listOfWords;

    public DictionarizedArticle(ArrayList<String> label, Set<String> tokens) {
        this.label = label;
        this.dictionary = tokens;
    }

    public DictionarizedArticle(ArrayList<String> label, Set<String> tokens, List<String> listOfWords) {
        this.label = label;
        this.dictionary = tokens;
        this.listOfWords = listOfWords;
    }

    public DictionarizedArticle(Article article) {
        this.label = article.getLabel();
        this.articleText = article.getTextAndTitle();
        this.listOfWords = createListOfWords(article.getTextAndTitle());
        this.dictionary = extractTokens();
    }

    private HashSet<String> extractTokens() {
        return Preprocessor.extractTokens(articleText);
    }

    private List<String> createListOfWords(String text){
        return Preprocessor.extractWords(text);
    }


}
