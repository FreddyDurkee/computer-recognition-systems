package other;

import article.Article;
import article.DictionarizedArticle;
import article.FeaturedArticle;
import gnu.trove.list.array.TDoubleArrayList;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Data
public class TF_IDFExtractor implements FeaturesExtractor {

    public static final Logger LOGGER = LogManager.getLogger(TF_IDFExtractor.class);

    private final TF_IDFCalculator TF_IDFcalculator;
    private final List<DictionarizedArticle> dictArticles;
    private final List<String> dictionary;

    public TF_IDFExtractor(Set<Article> articles) {
        LOGGER.debug("extract DictionarizedArticles...");
        this.dictArticles = Preprocessor.convertToDicionarizedArticles(articles);
        LOGGER.debug("create dictionary...");
        this.dictionary = Collections.unmodifiableList(new ArrayList<>(Preprocessor.createDictionary(dictArticles)));
        this.TF_IDFcalculator = new TF_IDFCalculator(dictArticles, dictionary);
    }

    public TF_IDFExtractor(Set<Article> articles, List<String> dictionary) {
        LOGGER.debug("extract DictionarizedArticles...");
        this.dictArticles = Preprocessor.convertToDicionarizedArticles(articles);
        LOGGER.debug("create dictionary...");
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
            LOGGER.info("Extracting features:" + i + "/" + n);
            i += 1;
        }
        return  featuredArticles;
    }
}
