import java.util.*;

public class TF_IDFCalculator {

    private static LinkedHashMap<String, Integer> termFrequencyMap(ArrayList<String> listOfWords, HashSet<String> dictionary) {
        LinkedHashMap<String, Integer> termFrequencyMap = new LinkedHashMap<>();
        for (String token : dictionary) {
            termFrequencyMap.put(token, termFrequency(listOfWords, token));
        }
        return termFrequencyMap;
    }

    private static int termFrequency(List<String> listOfWords, String term) {
        return Collections.frequency(listOfWords, term);
    }


    public static double termFrequency_IDF(DictionarizedArticle currentArticle,
                                            List<DictionarizedArticle> dictionarizedArticles,
                                            String term) {
        int N = dictionarizedArticles.size();
        int TF = termFrequency(currentArticle.getListOfWords(), term);
        int DF = documentFrequency(dictionarizedArticles, term);
        double IDF = Math.log10(N * 1.0 / DF);
        return TF * IDF;
    }

    public static List<Double> calculateTfIdfForAllWords(DictionarizedArticle currentArticle,
                                                         List<DictionarizedArticle> dictionarizedArticles,
                                                         List<String> dictionary) {
        List<Double> tf_idfVector = new ArrayList<>();
        for (String word : dictionary) {
            tf_idfVector.add(termFrequency_IDF(currentArticle, dictionarizedArticles, word));
        }
        return  tf_idfVector;
    }

    private static int documentFrequency(List<DictionarizedArticle> dictionarizedArticles, String term) {
        int numberOfTermsInDocs = 0;
        for (DictionarizedArticle dictionarizedArticle : dictionarizedArticles) {
            numberOfTermsInDocs += dictionarizedArticle.getListOfWords().contains(term) ? 1 : 0;
        }
        return numberOfTermsInDocs;
    }

}
