import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TF_IDFEXtractorTest {

    @Test
    void extract() {
        double DELTA = 1e-4;

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

        List<Double> firstFeatureVector = featuredArticles.get(0).getFeatureVector();
        List<Double> secondFeatureVector = featuredArticles.get(1).getFeatureVector();

        assertEquals(2, featuredArticles.size());

        int memoriIdx = featuresExtractor.getDictionary().indexOf("memori");
        int lateIdx = featuresExtractor.getDictionary().indexOf("late");
        int nightIdx = featuresExtractor.getDictionary().indexOf("night");
        int iIdx = featuresExtractor.getDictionary().indexOf("i");
        int bestIdx = featuresExtractor.getDictionary().indexOf("best");
        int justIdx = featuresExtractor.getDictionary().indexOf("just");
        int wannaIdx = featuresExtractor.getDictionary().indexOf("wanna");
        int itIdx = featuresExtractor.getDictionary().indexOf("it'");

//        System.out.println(memoriIdx  + " -> " +  featuresExtractor.getDictionary().get(memoriIdx));
//        System.out.println(lateIdx  + " -> " +  featuresExtractor.getDictionary().get(lateIdx));
//        System.out.println(nightIdx  + " -> " +  featuresExtractor.getDictionary().get(nightIdx));
//        System.out.println(iIdx  + " -> " +  featuresExtractor.getDictionary().get(iIdx));
//        System.out.println(bestIdx  + " -> " +  featuresExtractor.getDictionary().get(bestIdx));
//        System.out.println(justIdx  + " -> " +  featuresExtractor.getDictionary().get(justIdx));
//        System.out.println(wannaIdx  + " -> " +  featuresExtractor.getDictionary().get(wannaIdx));
//        System.out.println(itIdx  + " -> " +  featuresExtractor.getDictionary().get(itIdx));

        assertEquals(0, firstFeatureVector.get(memoriIdx), DELTA);
        assertEquals(0, firstFeatureVector.get(lateIdx), DELTA);
        assertEquals(0.301, firstFeatureVector.get(nightIdx), DELTA);
        assertEquals(0.301, firstFeatureVector.get(iIdx), DELTA);
        assertEquals(0, firstFeatureVector.get(bestIdx), DELTA);
        assertEquals(0.301, firstFeatureVector.get(justIdx), DELTA);
        assertEquals(0.301, firstFeatureVector.get(wannaIdx), DELTA);
        assertEquals(0, firstFeatureVector.get(itIdx), DELTA);

        assertEquals(0, secondFeatureVector.get(memoriIdx), DELTA);
        assertEquals(0.301, secondFeatureVector.get(lateIdx), DELTA);
        assertEquals(0, secondFeatureVector.get(nightIdx), DELTA);
        assertEquals(0, secondFeatureVector.get(iIdx), DELTA);
        assertEquals(0, secondFeatureVector.get(bestIdx), DELTA);
        assertEquals(0, secondFeatureVector.get(justIdx), DELTA);
        assertEquals(0, secondFeatureVector.get(wannaIdx), DELTA);
        assertEquals(0.301, secondFeatureVector.get(itIdx), DELTA);

    }
}
