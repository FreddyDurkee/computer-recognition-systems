package history;

import article.FeaturedArticle;
import gnu.trove.list.array.TDoubleArrayList;
import metrics.MetricsType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClassificationHistoryTest {

    @Test
    void saveAndLoadToFile() throws IOException, ClassNotFoundException {
        String testPath = this.getClass().getResource("").getPath().replaceFirst("/", "");

        // Save
        ClassificationHistory classificationHistory = new ClassificationHistory();

        TDoubleArrayList vector = new TDoubleArrayList();
        ArrayList<String> labels = new ArrayList<>();

        vector.add(5.0);
        vector.add(6.0);

        labels.add("usa");
        labels.add("japan");

        FeaturedArticle featuredArticle = new FeaturedArticle(labels, vector);

        ClassifiedSample classifiedSampleToSave = new ClassifiedSample(featuredArticle, new ArrayList<>(), 2, MetricsType.EUCLIDEAN);

        classificationHistory.add(classifiedSampleToSave);

        classificationHistory.saveToFile(testPath+"test.data");

        // Load
        classificationHistory.loadFromFile(testPath+"test.data");

        ClassifiedSample classifiedSample = classificationHistory.getRepository().get(2).get(0);
        assertEquals(5.0, classifiedSample.getSample().getFeatureVector().get(0));
        assertEquals(6.0, classifiedSample.getSample().getFeatureVector().get(1));
        assertEquals("usa", classifiedSample.getSample().getLabel().get(0));
        assertEquals("japan", classifiedSample.getSample().getLabel().get(1));
        assertEquals(2, classifiedSample.getK());
        assertEquals(MetricsType.EUCLIDEAN, classifiedSample.getMetricsType());
    }

    @Test
    void saveToFileOnlyLabels() throws IOException {
        String testPath = this.getClass().getResource("").getPath().replaceFirst("/", "");

        // Save
        ClassificationHistory classificationHistory = new ClassificationHistory();

        TDoubleArrayList vector = new TDoubleArrayList();
        ArrayList<String> labels = new ArrayList<>();

        vector.add(5.0);
        vector.add(6.0);

        labels.add("usa");
        labels.add("japan");

        FeaturedArticle featuredArticle = new FeaturedArticle(labels, vector);

        ClassifiedSample classifiedSampleToSave = new ClassifiedSample(featuredArticle, new ArrayList<>(), 2, MetricsType.EUCLIDEAN);

        classificationHistory.add(classifiedSampleToSave);

        classificationHistory.saveToFileOnlyLabels(new File(testPath+"labels-test.data"),2);
    }

}