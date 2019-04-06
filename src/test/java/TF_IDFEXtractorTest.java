import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TF_IDFEXtractorTest {

    @Test
    void extract() {
        double DELTA = 1e-4;

        // Given
        List<Article> articles = new ArrayList<>();

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

        articles.add(article1);
        articles.add(article2);

        TF_IDFExtractor featuresExtractor = new TF_IDFExtractor(articles);

        // When
        List<FeaturedArticle> featuredArticles = featuresExtractor.extract();
        // Then

//        featuredArticlesWords = [memori, late, night, i, best, just, wanna, it']

        List<Double> firstFeatureVector = featuredArticles.get(0).getFeatureVector();
        List<Double> secondFeatureVector = featuredArticles.get(1).getFeatureVector();

        assertEquals(2, featuredArticles.size());

        assertEquals(0, firstFeatureVector.get(0), DELTA);
        assertEquals(0, firstFeatureVector.get(1), DELTA);
        assertEquals(0.301, firstFeatureVector.get(2), DELTA);
        assertEquals(0.301, firstFeatureVector.get(3), DELTA);
        assertEquals(0, firstFeatureVector.get(4), DELTA);
        assertEquals(0.301, firstFeatureVector.get(5), DELTA);
        assertEquals(0.301, firstFeatureVector.get(6), DELTA);
        assertEquals(0, firstFeatureVector.get(7), DELTA);

        assertEquals(0, secondFeatureVector.get(0), DELTA);
        assertEquals(0.301, secondFeatureVector.get(1), DELTA);
        assertEquals(0, secondFeatureVector.get(2), DELTA);
        assertEquals(0, secondFeatureVector.get(3), DELTA);
        assertEquals(0, secondFeatureVector.get(4), DELTA);
        assertEquals(0, secondFeatureVector.get(5), DELTA);
        assertEquals(0, secondFeatureVector.get(6), DELTA);
        assertEquals(0.301, secondFeatureVector.get(7), DELTA);

    }
}
