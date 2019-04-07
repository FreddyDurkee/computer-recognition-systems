import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class TF_IDFCalculator {

    private final Map<String, Integer> DF;

    public TF_IDFCalculator(List<DictionarizedArticle> dictionarizedArticles, List<String> dictionary){
        this.DF = createDocumentFrequencyMap(dictionarizedArticles, dictionary);

    }

    public double termFrequency_IDF(DictionarizedArticle currentArticle,
                                    List<DictionarizedArticle> dictionarizedArticles,
                                    String term) {
        int N = dictionarizedArticles.size();
        int TF = termFrequency(currentArticle.getListOfWords(), term);
        int DF = this.DF.get(term);
        double IDF = Math.log10(N * 1.0 / DF);
        return TF * IDF;
    }

    public List<Double> calculateTfIdfForAllWords(DictionarizedArticle currentArticle,
                                                  List<DictionarizedArticle> dictionarizedArticles,
                                                  List<String> dictionary) {
        List<Double> tf_idfVector = new ArrayList<>();
        for (String word : dictionary) {
            tf_idfVector.add(termFrequency_IDF(currentArticle, dictionarizedArticles, word));
        }
        return  tf_idfVector;
    }

    private static Map<String, Integer> createDocumentFrequencyMap(List<DictionarizedArticle> dictionarizedArticles, List<String> dictionary){
        Map<String, Integer> DF = dictionary.stream().collect(Collectors.toMap(x -> x, x -> 0));
        for (String word : dictionary) {
            Integer numberOfWordInDocs = documentFrequency(dictionarizedArticles, word);
            DF.put(word, numberOfWordInDocs);
        }
        return  DF;
    }

    private static LinkedHashMap<String, Integer> termFrequencyMap(List<String> listOfWords, HashSet<String> dictionary) {
        LinkedHashMap<String, Integer> termFrequencyMap = new LinkedHashMap<>();
        for (String token : dictionary) {
            termFrequencyMap.put(token, termFrequency(listOfWords, token));
        }
        return termFrequencyMap;
    }

    private static int termFrequency(List<String> listOfWords, String term) {
        return Collections.frequency(listOfWords, term);
    }

    private static int documentFrequency(List<DictionarizedArticle> dictionarizedArticles, String term) {
        int numberOfTermsInDocs = 0;
        for (DictionarizedArticle dictionarizedArticle : dictionarizedArticles) {
            numberOfTermsInDocs += dictionarizedArticle.getListOfWords().contains(term) ? 1 : 0;
        }
        return numberOfTermsInDocs;
    }

}
