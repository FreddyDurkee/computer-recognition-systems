import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeaturedArticle {

    private final List<String> label;
    private final List<Double> featureVector;

    public FeaturedArticle(List<String> label, List<Double> featureVector) {
        this.label = label;
        this.featureVector = featureVector;
    }
}
