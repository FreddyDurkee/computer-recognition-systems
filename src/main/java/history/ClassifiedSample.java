package history;

import article.FeaturedArticle;
import lombok.Data;
import metrics.MetricsType;

import java.io.Serializable;
import java.util.List;

@Data
public class ClassifiedSample implements Serializable {

    private FeaturedArticle sample;
    private MetricsType metricsType;
    private int k;

    public ClassifiedSample(FeaturedArticle sample, int k, MetricsType metricsType) {
        this.sample = sample;
        this.k = k;
        this.metricsType = metricsType;
    }

    public String labelsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getStringLabel(this.sample.getLabel()));
        stringBuilder.append(";");
        stringBuilder.append(getStringLabel(this.sample.getPredictedLabel())+"\n");

        return stringBuilder.toString();
    }

    public String getStringLabel(List<String> labels){
        String result = "";
        for(String label : labels){
            if(result.equals("")){
                result+=label;
            } else {
                result+=","+label;
            }
        }
        return result;
    }

}
