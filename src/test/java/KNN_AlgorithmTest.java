import metrics.EuclideanMetrics;
import metrics.Metrics;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class KNN_AlgorithmTest {

    @Test
    void getLowestIndexes() throws Exception {
        // Given
        ArrayList<Double> example = new ArrayList<>();
        example.add(6.9); // 1
        example.add(4.2); // 2
        example.add(5.6); // 3
        example.add(4.2); // 4
        example.add(2.5); // 5
        example.add(1.3); // 6

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(4);
        expected.add(1);

        // When
        KNN_Algorithm knn = new KNN_Algorithm();
        ArrayList<Integer> actual = knn.getLowestIndexes(example,3);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void calculateMedianLabel(){
        // Given
        KNN_Algorithm knn = new KNN_Algorithm();

        ArrayList<String> example = new ArrayList<>();
        example.add("Japan");
        example.add("Germany");
        example.add("USA");
        example.add("Norway");
        example.add("Japan");
        example.add("USA");

        // When
        String actual = knn.calculateMedianLabel(example);

        // Then
        assertTrue(actual.equals("USA") || actual.equals("Japan"));
    }

    @Test
    void KNN() throws Exception {
        // Przykład dla przestrzenii dwuwymiarowej, gdzie: USA i Japan x 3, Germany x 2
        // Given


        // PUNKTY USA x3
        ArrayList<Double> featuresUSA1 = new ArrayList<>();
        featuresUSA1.add(2.0);
        featuresUSA1.add(5.0);

        ArrayList<Double> featuresUSA2 = new ArrayList<>();
        featuresUSA2.add(2.0);
        featuresUSA2.add(4.0);

        ArrayList<Double> featuresUSA3 = new ArrayList<>();
        featuresUSA3.add(3.0);
        featuresUSA3.add(5.0);

        // PUNKTY JAPAN x3
        ArrayList<Double> featuresJapan1 = new ArrayList<>();
        featuresJapan1.add(6.0);
        featuresJapan1.add(5.0);

        ArrayList<Double> featuresJapan2 = new ArrayList<>();
        featuresJapan2.add(6.0);
        featuresJapan2.add(4.0);

        ArrayList<Double> featuresJapan3 = new ArrayList<>();
        featuresJapan3.add(5.0);
        featuresJapan3.add(5.0);

        // PUNKTY GERMANY x2
        ArrayList<Double> featuresGermany1 = new ArrayList<>();
        featuresGermany1.add(4.0);
        featuresGermany1.add(2.0);

        ArrayList<Double> featuresGermany2 = new ArrayList<>();
        featuresGermany2.add(3.0);
        featuresGermany2.add(3.0);

        // LABELS
        ArrayList<String> germany = new ArrayList<>();
        germany.add("Germany");

        ArrayList<String> usa = new ArrayList<>();
        usa.add("USA");

        ArrayList<String> japan = new ArrayList<>();
        japan.add("Japan");

        // 8 Featured Article
        FeaturedArticle fa1 = new FeaturedArticle(usa,featuresUSA1);
        FeaturedArticle fa2 = new FeaturedArticle(usa,featuresUSA2);
        FeaturedArticle fa3 = new FeaturedArticle(usa,featuresUSA3);
        FeaturedArticle fa4 = new FeaturedArticle(japan, featuresJapan1);
        FeaturedArticle fa5 = new FeaturedArticle(japan, featuresJapan2);
        FeaturedArticle fa6 = new FeaturedArticle(japan, featuresJapan3);
        FeaturedArticle fa7 = new FeaturedArticle(germany, featuresGermany1);
        FeaturedArticle fa8 = new FeaturedArticle(germany, featuresGermany2);

        ArrayList<FeaturedArticle> trainingData = new ArrayList<>();
        trainingData.add(fa1);
        trainingData.add(fa2);
        trainingData.add(fa3);
        trainingData.add(fa4);
        trainingData.add(fa5);
        trainingData.add(fa6);
        trainingData.add(fa7);
        trainingData.add(fa8);

        KNN_Algorithm knn = new KNN_Algorithm(trainingData);

        // FeaturedVectors
        ArrayList<Double> ald1 = new ArrayList<>(); // USA or Japan
        ald1.add(4.0);
        ald1.add(5.0);

        ArrayList<Double> ald2 = new ArrayList<>(); // Germany
        ald2.add(3.0);
        ald2.add(2.0);

        ArrayList<Double> ald3 = new ArrayList<>(); // Japan
        ald3.add(6.0);
        ald3.add(3.0);

        // FeaturedArticles - artykuły do klasyfikacji
        FeaturedArticle faUsaOrJapan = new FeaturedArticle(ald1);
        FeaturedArticle faGermany = new FeaturedArticle(ald2);
        FeaturedArticle faJapan = new FeaturedArticle(ald3);

        // Oczekiwana klasyfikacja: k = 2 / Japan 50% / USA 50% (odległość euklidesowa)
        Metrics metrics = new EuclideanMetrics();

        String UsaOrJapan = knn.KNN(faUsaOrJapan,2, metrics);
        String Germany = knn.KNN(faGermany, 2, metrics);
        String Japan = knn.KNN(faJapan, 2, metrics);


        System.out.println(UsaOrJapan);
        assertTrue(UsaOrJapan.equals("USA") || UsaOrJapan.equals("Japan"));
        assertTrue(Germany.equals("Germany"));
        assertTrue(Japan.equals("Japan"));
    }
}