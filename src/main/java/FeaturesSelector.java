import java.util.*;

public class FeaturesSelector {

    public static LinkedHashMap<String, Integer> termFrequencyMap(ArrayList<String> listOfWords, HashSet<String> dictionary) {
        LinkedHashMap<String, Integer> termFrequencyMap = new LinkedHashMap<>();
        for (String token : dictionary) {
            termFrequencyMap.put(token, termFrequency(listOfWords, token));
        }
        return termFrequencyMap;
    }

    public static int termFrequency(ArrayList<String> listOfWords, String term) {
        return Collections.frequency(listOfWords, term);

    }


    public static double termFrequency_IDF(DictionarizedArticle currentArticle,
                                           ArrayList<DictionarizedArticle> dictionarizedArticles,
                                           String term) {
        int N = dictionarizedArticles.size();
        int TF = termFrequency(currentArticle.getListOfWords(), term);
        int DF = documentFrequency(dictionarizedArticles, term);
        double IDF = Math.log10(N * 1.0 / DF);
        return TF * IDF;
    }

    public static int documentFrequency(ArrayList<DictionarizedArticle> dictionarizedArticles, String term) {
        int numberOfTermsInDocs = 0;
        for (DictionarizedArticle dictionarizedArticle : dictionarizedArticles) {
            numberOfTermsInDocs += dictionarizedArticle.getListOfWords().contains(term) ? 1 : 0;
        }
        return numberOfTermsInDocs;
    }

}
