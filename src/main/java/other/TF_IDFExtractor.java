package other;

import article.Article;
import article.DictionarizedArticle;
import article.FeaturedArticle;
import gnu.trove.list.array.TDoubleArrayList;
import lombok.Data;

import java.util.*;

@Data
public class TF_IDFExtractor implements FeaturesExtractor {

    private final TF_IDFCalculator TF_IDFcalculator;
    private final List<DictionarizedArticle> dictArticles;
    private final List<String> dictionary;

    public TF_IDFExtractor(Set<Article> articles) {
        System.out.println("extract DictionarizedArticles...");
        this.dictArticles = Preprocessor.convertToDicionarizedArticles(articles);
        System.out.println("create dictionary...");
        this.dictionary = Collections.unmodifiableList(new ArrayList<>(Preprocessor.createDictionary(dictArticles)));
        this.TF_IDFcalculator = new TF_IDFCalculator(dictArticles, dictionary);
    }

    public TF_IDFExtractor(Set<Article> articles, List<String> dictionary) {
        System.out.println("extract DictionarizedArticles...");
        this.dictArticles = Preprocessor.convertToDicionarizedArticles(articles);
        System.out.println("create dictionary...");
        this.dictionary = dictionary;
        this.TF_IDFcalculator = new TF_IDFCalculator(dictArticles, dictionary);
    }

    @Override
    public List<FeaturedArticle> extract() {
        int i = 1;
        int n = dictArticles.size();
        List<FeaturedArticle> featuredArticles = new ArrayList<>();
        for (DictionarizedArticle dictArticle : dictArticles) {
            TDoubleArrayList vectorOfFeatures = TF_IDFcalculator.calculateTfIdfForAllWords(dictArticle, dictArticles, dictionary);
            featuredArticles.add(new FeaturedArticle(dictArticle.getLabel(), vectorOfFeatures));
            System.out.println(i + "/" + n);
            i += 1;
        }
        return  featuredArticles;
    }
}
