import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Preprocessor {


    public Preprocessor() {
    }

    public static DictionarizedArticle extractTokens(Article article) {
        Analyzer analyzer;
        HashSet<String> tokens = new HashSet<>();
        String articleBody = removeNumbers(article.getTextAndTitle());
        try {
            analyzer = CustomAnalyzer.builder()
                    .withTokenizer(StandardTokenizerFactory.class)
                    .addTokenFilter("lowercase")
                    .addTokenFilter("stop")
                    .addTokenFilter("porterstem")
                    .build();
            tokens = analyze(articleBody, analyzer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DictionarizedArticle(article.getLabel(), tokens);
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

    public  static  LinkedHashMap<String,Integer> createDictionary(List<DictionarizedArticle> dictionarizedArticles){
        LinkedHashMap<String,Integer> dictionary = new LinkedHashMap<>();
        for(DictionarizedArticle dictionarizedArticle : dictionarizedArticles){
            for(String token : dictionarizedArticle.getDictionary()){
                dictionary.put(token, 0);
            }
        }
        return dictionary;
    }


}
