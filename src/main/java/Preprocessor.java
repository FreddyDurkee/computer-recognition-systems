import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Preprocessor {


    public Preprocessor() {
    }

    public static DictionarizedArticle extract(Article article) {
        Analyzer analyzer = null;
        try {
            analyzer = CustomAnalyzer.builder()
                    .withTokenizer("standard")
                    .addTokenFilter("lowercase")
                    .addTokenFilter("stop")
                    .addTokenFilter("porterstem")
                    .addTokenFilter("capitalization")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> result = null;
        try {
            result = analyze(article.getText(), analyzer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashSet<String> tokens = new HashSet<String>(result);
        tokens.addAll(result);
        return new DictionarizedArticle(article.getLabel(), tokens);
    }

    private static List<String> analyze(String text, Analyzer analyzer) throws IOException {
        List<String> result = new ArrayList<String>();
        TokenStream tokenStream = analyzer.tokenStream(null, text);
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            result.add(attr.toString());
        }
        return result;
    }



    private void tokenize(String text) {
        throw new NotImplementedException();
    }


}
