package other;

import article.FeaturedArticle;
import history.ClassificationHistory;
import history.ClassifiedSample;
import lombok.Data;
import metrics.Metrics;

import java.util.*;
import java.util.stream.Collectors;

@Data
public abstract class KNN_Algorithm {

    private List<FeaturedArticle> trainingData;

    private ClassificationHistory classificationHistory;

    public KNN_Algorithm(List<FeaturedArticle> trainingData) {
        this.trainingData = trainingData;
        this.classificationHistory = new ClassificationHistory();
    }

    public KNN_Algorithm() {
        this.trainingData = new ArrayList<>();
        this.classificationHistory = new ClassificationHistory();
    }


    public void KNN(FeaturedArticle sample, List<Integer> listOfK, Metrics metrics) throws Exception {
        Map<FeaturedArticle, Double> articleToDistance = new HashMap<>();

        for (FeaturedArticle data : trainingData) {
            Double distance = metrics.calculate(
                    data.getFeatureVector(),
                    sample.getFeatureVector()
            );
            articleToDistance.put(data, distance);
        }

        List<Map.Entry<FeaturedArticle, Double>> sortedFeatureToDistance = articleToDistance.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).collect(Collectors.toList());
        for (int k : listOfK){
            ArrayList<String> foundLabels = new ArrayList<>();
            for (Map.Entry<FeaturedArticle, Double> nearest : sortedFeatureToDistance.subList(0,k)) {
                foundLabels.addAll(nearest.getKey().getLabel());
            }
            List<String> winnerLabels = findLabels(foundLabels);
            classificationHistory.add(new ClassifiedSample(sample, winnerLabels, k, metrics.getMetricsType()));
        }
    }

    private List<String> findLabels(ArrayList<String> labels) {
        HashMap<String, Integer> labelCounter = new HashMap<>();
        for (String label : labels) {
            if (labelCounter.containsKey(label)) {
                labelCounter.put(label, labelCounter.get(label) + 1);
            } else {
                labelCounter.put(label, 1);
            }
        }
        return predictAllLabels(labelCounter);
    }

    abstract List<String> predictLabels(HashMap<String, Integer> labelCounter);

    abstract List<String> predictAllLabels(HashMap<String, Integer> labelCounter);

}
