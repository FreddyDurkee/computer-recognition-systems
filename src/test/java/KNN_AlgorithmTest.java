import article.FeaturedArticle;
import gnu.trove.list.array.TDoubleArrayList;
import metrics.EuclideanMetrics;
import metrics.Metrics;
import org.junit.jupiter.api.Test;
import other.KNN_Algorithm;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class KNN_AlgorithmTest {

    @Test
    void getLowestIndexes() throws Exception {
        // Given
        TDoubleArrayList example = new TDoubleArrayList();
        example.add(6.9); // 1
        example.add(4.2); // 2
        example.add(5.6); // 3
        example.add(4.2); // 4
        example.add(2.5); // 5
        example.add(1.3); // 6

        ArrayList<Integer> expected = new  ArrayList<>();
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

        ArrayList<String> example =  new ArrayList<>();
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
        TDoubleArrayList featuresUSA1 = new TDoubleArrayList();
        featuresUSA1.add(2.0);
        featuresUSA1.add(5.0);

        TDoubleArrayList featuresUSA2 = new TDoubleArrayList();
        featuresUSA2.add(2.0);
        featuresUSA2.add(4.0);

        TDoubleArrayList featuresUSA3 = new TDoubleArrayList();
        featuresUSA3.add(3.0);
        featuresUSA3.add(5.0);

        // PUNKTY JAPAN x3
        TDoubleArrayList featuresJapan1 = new TDoubleArrayList();
        featuresJapan1.add(6.0);
        featuresJapan1.add(5.0);

        TDoubleArrayList featuresJapan2 = new TDoubleArrayList();
        featuresJapan2.add(6.0);
        featuresJapan2.add(4.0);

        TDoubleArrayList featuresJapan3 = new TDoubleArrayList();
        featuresJapan3.add(5.0);
        featuresJapan3.add(5.0);

        // PUNKTY GERMANY x2
        TDoubleArrayList featuresGermany1 = new TDoubleArrayList();
        featuresGermany1.add(4.0);
        featuresGermany1.add(2.0);

        TDoubleArrayList featuresGermany2 = new TDoubleArrayList();
        featuresGermany2.add(3.0);
        featuresGermany2.add(3.0);

        // LABELS
        ArrayList<String> germany = new ArrayList<>();
        germany.add("Germany");

        ArrayList<String> usa =  new ArrayList<>();
        usa.add("USA");

        ArrayList<String> japan =  new ArrayList<>();
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

        ArrayList<FeaturedArticle> trainingData =  new ArrayList<>();
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
        TDoubleArrayList ald1 = new TDoubleArrayList(); // USA or Japan
        ald1.add(4.0);
        ald1.add(5.0);

        TDoubleArrayList ald2 = new TDoubleArrayList(); // Germany
        ald2.add(3.0);
        ald2.add(2.0);

        TDoubleArrayList ald3 = new TDoubleArrayList(); // Japan
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

        assertTrue(UsaOrJapan.equals("USA") || UsaOrJapan.equals("Japan"));
        assertTrue(Germany.equals("Germany"));
        assertTrue(Japan.equals("Japan"));
    }
}