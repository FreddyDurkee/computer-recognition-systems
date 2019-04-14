package other;

import article.FeaturedArticle;
import com.google.common.collect.Lists;

import java.util.*;

public class SingleLabelKNN extends KNN_Algorithm {

    public SingleLabelKNN(List<FeaturedArticle> treningData) {
        super(treningData);
    }

    @Override
    /**
     * Gets the most common label. In case of many label with same occurrence number, takes first
     */
    List<String> predictLabels(HashMap<String, Integer> labelCounter){
        int maxValue = Collections.max(
                labelCounter.entrySet(),
                (o1, o2) -> o1.getValue() > o2.getValue() ? 1 : -1).getValue();

        List<String> predictedLabels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : labelCounter.entrySet()) {
            if (entry.getValue().equals(maxValue)) {
                predictedLabels.add(entry.getKey());
                break;
            }
        }

        return predictedLabels;
    }
}
