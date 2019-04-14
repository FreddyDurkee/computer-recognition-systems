package history;

import article.FeaturedArticle;
import lombok.Data;
import metrics.MetricsType;

import java.io.Serializable;
import java.util.List;

@Data
public class ClassifiedSample implements Serializable {

    private final List<String> winnerLabels;
    private FeaturedArticle sample;
    private MetricsType metricsType;
    private int k;

    public ClassifiedSample(FeaturedArticle sample, List<String> winnerLabels, int k, MetricsType metricsType) {
        this.sample = sample;
        this.winnerLabels = winnerLabels;
        this.k = k;
        this.metricsType = metricsType;
    }

    //TODO:FIXME
    public String labelsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getStringLabel(this.sample.getLabel()));
        stringBuilder.append(";");
        stringBuilder.append(getStringLabel(this.winnerLabels));

        return stringBuilder.toString();
    }

    public String getStringLabel(List<String> labels){
        return String.join(",",labels);
    }

}
