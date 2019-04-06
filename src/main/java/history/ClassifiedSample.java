package history;

import article.FeaturedArticle;
import lombok.Data;
import metrics.MetricsType;

import java.io.Serializable;

@Data
public class ClassifiedSample implements Serializable {

    private FeaturedArticle sample;
    private MetricsType metricsType;
    private int k;

    public ClassifiedSample(FeaturedArticle sample, int k, MetricsType metricsType){
        this.sample = sample;
        this.k = k;
        this.metricsType = metricsType;
    }

}
