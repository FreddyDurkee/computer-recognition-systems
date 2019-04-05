import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureSelectorTest {

    @Test
    void termFrequency() {
        // Given
        ArrayList<String> words = new ArrayList<>(Arrays.asList("colombia", "open", "april", "opening", "coffe", "open"));

        // When Then
        assertEquals(1, FeaturesSelector.termFrequency(words, "colombia"));
        assertEquals(2, FeaturesSelector.termFrequency(words, "open"));
        assertEquals(1, FeaturesSelector.termFrequency(words, "april"));
        assertEquals(1, FeaturesSelector.termFrequency(words, "opening"));
        assertEquals(1, FeaturesSelector.termFrequency(words, "coffe"));
        assertEquals(0, FeaturesSelector.termFrequency(words, "cat"));
    }

    @Test
    void documentFrequency() {
        // Given
        ArrayList<DictionarizedArticle> dictionarizedArticles = new ArrayList<>();

        Article article1 = new Article();
        String newLabel = "label1";
        article1.addLabel(newLabel);
        article1.setTitle("The best memories");
        article1.setText("I just wanna let it go for the night");

        Article article2 = new Article();
        String newLabe2 = "label2";
        article2.addLabel(newLabel);
        article2.setTitle("Best memories");
        article2.setText("It's getting late but I don't mind");

        dictionarizedArticles.add(new DictionarizedArticle(article1));
        dictionarizedArticles.add(new DictionarizedArticle(article2));

        // When Then

        assertEquals(2, FeaturesSelector.documentFrequency(dictionarizedArticles, "i"));
        assertEquals(2, FeaturesSelector.documentFrequency(dictionarizedArticles, "best"));
        assertEquals(2, FeaturesSelector.documentFrequency(dictionarizedArticles, "memori"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "mind"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "late"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "don't"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "get"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "it'"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "it"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "let"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "go"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "the"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "just"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "wanna"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "for"));
        assertEquals(1, FeaturesSelector.documentFrequency(dictionarizedArticles, "night"));
        assertEquals(0, FeaturesSelector.documentFrequency(dictionarizedArticles, "be"));
    }


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

        // When Then

        assertEquals(0.602, FeaturesSelector.termFrequency_IDF(dictArticle1, dictionarizedArticles, "the"), DELTA);
        assertEquals(0, FeaturesSelector.termFrequency_IDF(dictArticle1, dictionarizedArticles, "best"), DELTA);
        assertEquals(0, FeaturesSelector.termFrequency_IDF(dictArticle1, dictionarizedArticles, "memori"), DELTA);
        assertEquals(0.301, FeaturesSelector.termFrequency_IDF(dictArticle1, dictionarizedArticles, "i"), DELTA);
        assertEquals(0.301, FeaturesSelector.termFrequency_IDF(dictArticle1, dictionarizedArticles, "just"), DELTA);
        assertEquals(0.301, FeaturesSelector.termFrequency_IDF(dictArticle1, dictionarizedArticles, "wanna"), DELTA);
        assertEquals(0.301, FeaturesSelector.termFrequency_IDF(dictArticle1, dictionarizedArticles, "night"), DELTA);
    }
}
