import article.Article;
import article.FeaturedArticle;
import gnu.trove.list.array.TDoubleArrayList;
import org.junit.jupiter.api.Test;
import other.TF_IDFExtractor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TF_IDFEXtractorTest {

    public static final double ERROR_DELTA = 1e-4;

    @Test
    void extract() {

        // Given
        Set<Article> articles = new LinkedHashSet<>();

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

        TDoubleArrayList firstFeatureVector = featuredArticles.get(0).getFeatureVector();
        TDoubleArrayList secondFeatureVector = featuredArticles.get(1).getFeatureVector();

        assertEquals(2, featuredArticles.size());

        int memoriIdx = featuresExtractor.getDictionary().indexOf("memori");
        int lateIdx = featuresExtractor.getDictionary().indexOf("late");
        int nightIdx = featuresExtractor.getDictionary().indexOf("night");
        int iIdx = featuresExtractor.getDictionary().indexOf("i");
        int bestIdx = featuresExtractor.getDictionary().indexOf("best");
        int justIdx = featuresExtractor.getDictionary().indexOf("just");
        int wannaIdx = featuresExtractor.getDictionary().indexOf("wanna");
        int itIdx = featuresExtractor.getDictionary().indexOf("it'");


        assertEquals(0, firstFeatureVector.get(memoriIdx), ERROR_DELTA);
        assertEquals(0, firstFeatureVector.get(lateIdx), ERROR_DELTA);
        assertEquals(1, firstFeatureVector.get(nightIdx), ERROR_DELTA);
        assertEquals(1, firstFeatureVector.get(iIdx), ERROR_DELTA);
        assertEquals(0, firstFeatureVector.get(bestIdx), ERROR_DELTA);
        assertEquals(1, firstFeatureVector.get(justIdx), ERROR_DELTA);
        assertEquals(1, firstFeatureVector.get(wannaIdx), ERROR_DELTA);
        assertEquals(0, firstFeatureVector.get(itIdx), ERROR_DELTA);

        assertEquals(0, secondFeatureVector.get(memoriIdx), ERROR_DELTA);
        assertEquals(1, secondFeatureVector.get(lateIdx), ERROR_DELTA);
        assertEquals(0, secondFeatureVector.get(nightIdx), ERROR_DELTA);
        assertEquals(0, secondFeatureVector.get(iIdx), ERROR_DELTA);
        assertEquals(0, secondFeatureVector.get(bestIdx), ERROR_DELTA);
        assertEquals(0, secondFeatureVector.get(justIdx), ERROR_DELTA);
        assertEquals(0, secondFeatureVector.get(wannaIdx), ERROR_DELTA);
        assertEquals(1, secondFeatureVector.get(itIdx), ERROR_DELTA);

    }
}
