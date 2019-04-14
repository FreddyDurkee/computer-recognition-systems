package history;

import article.FeaturedArticle;
import gnu.trove.list.array.TDoubleArrayList;
import metrics.MetricsType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClassifiedSampleTest {

    @Test
    void labelsToString() {
        TDoubleArrayList vector = new TDoubleArrayList();
        ArrayList<String> labels = new ArrayList<>();

        vector.add(5.0);
        vector.add(6.0);

        labels.add("usa");
        labels.add("japan");

        ArrayList<String> predictedLabels = new ArrayList<>();
        predictedLabels.add("usa");

        FeaturedArticle featuredArticle = new FeaturedArticle(labels, vector);
        ClassifiedSample classifiedSample = new ClassifiedSample(featuredArticle, predictedLabels, 2, MetricsType.EUCLIDEAN);

        assertEquals("usa,japan;usa",classifiedSample.labelsToString());
    }
}