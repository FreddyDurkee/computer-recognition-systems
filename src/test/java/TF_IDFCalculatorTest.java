import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TF_IDFCalculatorTest {

    @Test
    void termFrequency_IDF() {
        double DELTA = 1e-4;

        // Given
        ArrayList<DictionarizedArticle> dictionarizedArticles = new ArrayList<>();

        Article article1 = new Article();
        String newLabel = "label1";
        article1.addLabel(newLabel);
        article1.setTitle("The best memories");
        article1.setText("I just wanna the night");

        Article article2 = new Article();
        String newLabel2 = "label2";
        article2.addLabel(newLabel2);
        article2.setTitle("Best memories");
        article2.setText("It's late");

        DictionarizedArticle dictArticle1 = new DictionarizedArticle(article1);
        DictionarizedArticle dictArticle2 = new DictionarizedArticle(article2);

        dictionarizedArticles.add(dictArticle1);
        dictionarizedArticles.add(dictArticle2);

        // When

        List<String> dictionary = new ArrayList<>( Preprocessor.createDictionary(dictionarizedArticles));
        TF_IDFCalculator TF_IDFcalculator = new TF_IDFCalculator(dictionarizedArticles, dictionary);

        // Then


        assertEquals(0, TF_IDFcalculator.termFrequency_IDF(dictArticle1, dictionarizedArticles, "best"), DELTA);
        assertEquals(0, TF_IDFcalculator.termFrequency_IDF(dictArticle1, dictionarizedArticles, "memori"), DELTA);
        assertEquals(0.301, TF_IDFcalculator.termFrequency_IDF(dictArticle1, dictionarizedArticles, "i"), DELTA);
        assertEquals(0.301, TF_IDFcalculator.termFrequency_IDF(dictArticle1, dictionarizedArticles, "just"), DELTA);
        assertEquals(0.301, TF_IDFcalculator.termFrequency_IDF(dictArticle1, dictionarizedArticles, "wanna"), DELTA);
        assertEquals(0.301, TF_IDFcalculator.termFrequency_IDF(dictArticle1, dictionarizedArticles, "night"), DELTA);
    }
}
