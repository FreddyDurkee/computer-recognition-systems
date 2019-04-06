import java.util.*;

public class TF_IDFExtractor implements FeaturesExtractor {

    private final List<DictionarizedArticle> dictArticles;
    private final List<String> dictionary;

    public TF_IDFExtractor(List<Article> articles) {
        this.dictArticles = Preprocessor.convertToDicionarizedArticles(articles);
        this.dictionary = new ArrayList<>(Preprocessor.createDictionary(dictArticles));
    }


    @Override
    public List<FeaturedArticle> extract() {
        List<FeaturedArticle> featuredArticles = new ArrayList<>();
        for (DictionarizedArticle dictArticle : dictArticles) {
            List<Double> vectorOfFeatures = TF_IDFCalculator.calculateTfIdfForAllWords(dictArticle, dictArticles, dictionary);
            featuredArticles.add(new FeaturedArticle(dictArticle.getLabel(), vectorOfFeatures));
        }
        return  featuredArticles;
    }
}
