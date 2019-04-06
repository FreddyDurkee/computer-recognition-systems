import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Preprocessor {


    public Preprocessor() {
    }

    public static HashSet<String> extractTokens(String text) {
        Analyzer analyzer;
        HashSet<String> tokens = new HashSet<>();
        try {
            analyzer = CustomAnalyzer.builder()
                    .withTokenizer(StandardTokenizerFactory.class)
                    .addTokenFilter("lowercase")
                    .addTokenFilter("stop")
                    .addTokenFilter("porterstem")
                    .build();
            text = removeNumbers(text);
            tokens = analyze(text, analyzer);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error on token extraction", e);
        }
        return tokens;
    }

    public static List<String> extractWords(String text) {
        PorterStemmer stemmer = new PorterStemmer();
        List<String> listOfWords = new ArrayList<>();
        text = removeDots(text);
        String[] arrOfSWords = text.split("[,; ?!&@#$%^*()+-.<>\\n]+");
        for (String word : arrOfSWords) {
            stemmer.setCurrent(word.toLowerCase());
            stemmer.stem();
            listOfWords.add(stemmer.getCurrent());
        }
        return listOfWords;
    }

    private static HashSet<String> analyze(String text, Analyzer analyzer) throws IOException {
        HashSet<String> result = new HashSet<>();
        TokenStream tokenStream = analyzer.tokenStream(null, text);
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            result.add(attr.toString());
        }
        return result;
    }

    private static String removeNumbers(String text) {
        return text.replaceAll("[0-9,.]+", "");
    }

    private static String removeDots(String text) {
        return text.replaceAll("[.]+", "");
    }

    public  static  HashSet<String> createDictionary(List<DictionarizedArticle> dictionarizedArticles){
        HashSet<String> dictionary = new HashSet<>();
        for(DictionarizedArticle dictionarizedArticle : dictionarizedArticles){
            dictionary.addAll(dictionarizedArticle.getDictionary());
        }
        return dictionary;
    }


    public static List<DictionarizedArticle> convertToDicionarizedArticles(List<Article> articles){
        return articles.parallelStream().map(article -> new DictionarizedArticle(article)).collect(Collectors.toList());
    }

}
