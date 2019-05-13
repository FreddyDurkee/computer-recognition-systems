package other;

import article.FeaturedArticle;

import java.util.*;

public class LabelKNN extends KNN_Algorithm {

    public LabelKNN(List<FeaturedArticle> treningData) {
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

    @Override
    List<String> predictAllLabels(HashMap<String, Integer> labelCounter) {
        List<String> predicedLabels = new ArrayList<>();
        Long all=labelCounter.values().stream().mapToLong(Long::new).sum();
        HashMap.Entry<String, Integer> maxLabel = null;
        for(Map.Entry<String, Integer> labelInfo : labelCounter.entrySet()) {
            if((labelInfo.getValue().doubleValue()/all.doubleValue())>=0.3){
                predicedLabels.add(labelInfo.getKey());
            }
            if(maxLabel==null || labelInfo.getValue().compareTo(maxLabel.getValue()) > 0){
                maxLabel = labelInfo;
            }
        }
        if(predicedLabels.isEmpty()){
            predicedLabels.add(maxLabel.getKey());
        }
        return predicedLabels;
    }
}
