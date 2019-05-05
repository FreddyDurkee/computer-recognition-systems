package other;

import article.DictionarizedArticle;
import gnu.trove.list.array.TDoubleArrayList;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class TF_IDFCalculator {

    public static final Logger LOGGER = LogManager.getLogger(TF_IDFCalculator.class);

    private final Map<String, Integer> DF;
    private int tf;

    public TF_IDFCalculator(List<DictionarizedArticle> dictionarizedArticles, List<String> dictionary) {
        this.DF = createDocumentFrequencyMap(dictionarizedArticles, dictionary);
    }

    public TF_IDFCalculator(Map<String, Integer> DF) {
        this.DF = DF;
    }

    public double termFrequency_IDF(DictionarizedArticle currentArticle,
                                    List<DictionarizedArticle> dictionarizedArticles,
                                    String term) {
        int N = dictionarizedArticles.size();
        int tf = termFrequency(currentArticle.getListOfWords(), term);
        int DF = this.DF.get(term);
        if (DF == 0) {
            throw new ArithmeticException("Divide by zero! DF value is 0!");
        }
        double IDF = Math.log10(N * 1.0 / DF);
        return tf * IDF;


    }

    public TDoubleArrayList calculateTfIdfForAllWords(DictionarizedArticle currentArticle,
                                                      List<DictionarizedArticle> dictionarizedArticles,
                                                      List<String> dictionary) {
        TDoubleArrayList tf_idfVector = new TDoubleArrayList();
        for (String word : dictionary) {
            try {
                tf_idfVector.add(termFrequency_IDF(currentArticle, dictionarizedArticles, word));
            }
            catch (ArithmeticException e){
                LOGGER.warn(e.getMessage());
            }
        }
        return tf_idfVector;
    }

    private static Map<String, Integer> createDocumentFrequencyMap(List<DictionarizedArticle> dictionarizedArticles, List<String> dictionary) {
        Map<String, Integer> DF = dictionary.stream().collect(Collectors.toMap(x -> x, x -> 0));
        for (String word : dictionary) {
            Integer numberOfWordInDocs = documentFrequency(dictionarizedArticles, word);
            DF.put(word, numberOfWordInDocs);
        }
        return DF;
    }

    public static LinkedHashMap<String, Integer> termFrequencyMap(List<String> listOfWords, HashSet<String> dictionary) {
        LinkedHashMap<String, Integer> termFrequencyMap = new LinkedHashMap<>();
        for (String token : dictionary) {
            termFrequencyMap.put(token, termFrequency(listOfWords, token));
        }
        return termFrequencyMap;
    }

    public static int termFrequency(List<String> listOfWords, String term) {
        return Collections.frequency(listOfWords, term);
    }

    private static int documentFrequency(List<DictionarizedArticle> dictionarizedArticles, String term) {
        int numberOfTermsInDocs = 0;
        for (DictionarizedArticle dictionarizedArticle : dictionarizedArticles) {
            numberOfTermsInDocs += dictionarizedArticle.getDictionary().contains(term) ? 1 : 0;
        }
        return numberOfTermsInDocs;
    }

}
