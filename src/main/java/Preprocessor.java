import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import java.io.IOException;
import java.util.*;


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
        }
        return tokens;
    }

    public static ArrayList<String> extractWords(String text) {
        Analyzer analyzer;
        HashSet<String> tokens = new HashSet<>();
        try {
            analyzer = CustomAnalyzer.builder()
                    .withTokenizer(StandardTokenizerFactory.class)
                    .addTokenFilter("lowercase")
                    .build();
            tokens = analyze(text, analyzer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(tokens);
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

    public  static  HashSet<String> createDictionary(List<DictionarizedArticle> dictionarizedArticles){
        HashSet<String> dictionary = new HashSet<>();
        for(DictionarizedArticle dictionarizedArticle : dictionarizedArticles){
            for(String token : dictionarizedArticle.getDictionary()){
                dictionary.add(token);
            }
        }
        return dictionary;
    }


}
