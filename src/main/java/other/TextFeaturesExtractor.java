package other;

import article.Article;
import article.DictionarizedArticle;
import article.FeaturedArticle;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.list.array.TDoubleArrayList;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class TextFeaturesExtractor {

    public static final Logger LOGGER = LogManager.getLogger(TextFeaturesExtractor.class);

    private final List<String> dictionary;
    private final TF_IDFExtractor tfIdfTestExtractor;
    private final List<DictionarizedArticle> dictTestArticles;
    private final TF_IDFExtractor tfIdfTrainExtractor;
    private final List<DictionarizedArticle> dictTrainArticles;
    private List<String> keyWords;
    public double TF_IDF_THRESHOLD_VALUE;

    public TextFeaturesExtractor(TextFeaturesExtractorBuilder builder) {
        if (builder.getTfIdfTresholdVal() != -1) {
            TF_IDF_THRESHOLD_VALUE = builder.getTfIdfTresholdVal();
        }
        tfIdfTestExtractor = new TF_IDFExtractor(builder.getTestArticles());
        dictTestArticles = tfIdfTestExtractor.getDictArticles();
        tfIdfTrainExtractor = new TF_IDFExtractor(builder.getTrainArticles(), tfIdfTestExtractor);
        dictTrainArticles = tfIdfTrainExtractor.getDictArticles();
        dictionary = tfIdfTestExtractor.getDictionary();
        keyWords = extractKeyWords();
    }


    public List<String> extractKeyWords() {
        Set<String> keyWords = new HashSet<>();

        for (FeaturedArticle featuredArticle : tfIdfTestExtractor.extract()) {
            TDoubleIterator featureIter = featuredArticle.getFeatureVector().iterator();
            int idx = 0;
            while (featureIter.hasNext()) {
                double feature = featureIter.next();
                if (feature > TF_IDF_THRESHOLD_VALUE) {
                    keyWords.add(dictionary.get(idx));
                }
                idx++;
            }
        }
        LOGGER.info("key words size: " + keyWords.size());
        return keyWords.stream().collect(Collectors.toList());
    }


    private List<FeaturedArticle> extract(List<DictionarizedArticle> dictArticles) {
        List<FeaturedArticle> featuredArticles = new ArrayList<>();
        for (DictionarizedArticle dictArticle : dictArticles) {
            double[] wordsOccureInArticle = ifWordsOccursFeature(dictArticle, keyWords);
            double[] numberOfKeyWordsInArticle = numberOfKeyWordsFeature(dictArticle, keyWords);
            double[] frequencyOfKeyWordsInArticle = frequencyOfKeyWordsFeature(dictArticle, keyWords);
            double[] concatedVectors = ArrayUtils.addAll(wordsOccureInArticle, numberOfKeyWordsInArticle);
            double[] featuresVector = ArrayUtils.addAll(concatedVectors, frequencyOfKeyWordsInArticle);
            featuredArticles.add(new FeaturedArticle(dictArticle.getLabel(), new TDoubleArrayList(featuresVector)));
        }
        LOGGER.info("Lenght of features vector: " + featuredArticles.get(0).getFeatureVector().size());
        return featuredArticles;
    }

    public List<FeaturedArticle> extractTestSet() {
        return extract(dictTestArticles);
    }

    public List<FeaturedArticle> extractTrainSet() {
        return extract(dictTrainArticles);
    }


    public static double[] ifWordsOccursFeature(DictionarizedArticle dictArticle, List<String> keyWords) {
        List<String> listOfWordsInArticle = dictArticle.getDictionary().stream().collect(Collectors.toList());
        int max = keyWords.size();
        double[] wordsOccuresVector = new double[max];
        for (int i = 0; i < max; ++i) {
            int wordfrequency = TF_IDFCalculator.termFrequency(listOfWordsInArticle, keyWords.get(i));
            if (wordfrequency > 0) {
                wordsOccuresVector[i] = 1;
            } else {
                wordsOccuresVector[i] = 0;
            }
        }
        return wordsOccuresVector;
    }

    public static double[] numberOfKeyWordsFeature(DictionarizedArticle dictArticle, List<String> keyWords) {
        double numberOfKeyWords = 0;
        for (String word : keyWords) {
            if (dictArticle.getDictionary().contains(word)) {
                numberOfKeyWords++;
            }
        }
        double[] numberOfKeyWordsVector = new double[1];
        numberOfKeyWordsVector[0] = numberOfKeyWords;
        return numberOfKeyWordsVector;
    }

    public static double[] frequencyOfKeyWordsFeature(DictionarizedArticle dictArticle, List<String> keyWords) {
        List<String> listOfWordsInArticle = dictArticle.getListOfWords();
        int max = keyWords.size();
        double[] frequencyOfKeyWordsVector = new double[max];
        for (int i = 0; i < max; ++i) {
            int wordfrequency = TF_IDFCalculator.termFrequency(listOfWordsInArticle, keyWords.get(i));

            frequencyOfKeyWordsVector[i] = wordfrequency;

        }
        return frequencyOfKeyWordsVector;
    }
}
