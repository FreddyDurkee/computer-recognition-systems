package other;

import article.Article;
import article.DictionarizedArticle;
import article.FeaturedArticle;
import gnu.trove.iterator.TDoubleIterator;
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

    public TF_IDFExtractor(Set<Article> articles, TF_IDFExtractor trainExtractor) {
        LOGGER.debug("extract DictionarizedArticles...");
        this.dictArticles = Preprocessor.convertToDicionarizedArticles(articles);
        LOGGER.debug("create dictionary...");
        this.dictionary = trainExtractor.getDictionary();
        this.TF_IDFcalculator = new TF_IDFCalculator(trainExtractor.TF_IDFcalculator.getDF());
    }


    @Override
    public List<FeaturedArticle> extract() {
        int i = 1;
        int n = dictArticles.size();
        List<FeaturedArticle> featuredArticles = new ArrayList<>();
        double maxVal = 0;
        for (DictionarizedArticle dictArticle : dictArticles) {
            TDoubleArrayList vectorOfFeatures = TF_IDFcalculator.calculateTfIdfForAllWords(dictArticle, dictArticles, dictionary);
            if(vectorOfFeatures.max() > maxVal){
                maxVal = vectorOfFeatures.max();
            }
            featuredArticles.add(new FeaturedArticle(dictArticle.getLabel(), vectorOfFeatures));
            LOGGER.info("Extracting features:" + i + "/" + n);
            i += 1;
        }
        return  normalizeTFIDF(featuredArticles, maxVal);
    }

    private List<FeaturedArticle> normalizeTFIDF(List<FeaturedArticle> featuredArticles, double maxVal ){
        List<FeaturedArticle> normailzedFeaturedArticles = new ArrayList<>();
        for(FeaturedArticle featuredArticle : featuredArticles){
            TDoubleArrayList normalizedTFIDF = normalize(featuredArticle.getFeatureVector(), 0, maxVal);
            normailzedFeaturedArticles.add(new FeaturedArticle(featuredArticle.getLabel(), normalizedTFIDF));
        }
        return normailzedFeaturedArticles;
    }

    private TDoubleArrayList normalize(TDoubleArrayList list, double min, double max){
        TDoubleArrayList normalizedVector = new TDoubleArrayList();
        TDoubleIterator iter = list.iterator();
        while ( iter.hasNext() ) {
            double normVal = (iter.next() - min)/(max - min);
            normalizedVector.add(normVal);
        }
        return  normalizedVector;
    }
}
