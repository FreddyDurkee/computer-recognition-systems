import lombok.Data;

import java.util.ArrayList;

@Data
public class FeaturedArticle {
    private ArrayList<String> label;
    private ArrayList<Double> featureVector;

    public FeaturedArticle(ArrayList<String> label, ArrayList<Double> featureVector) {
        this.label = label;
        this.featureVector = featureVector;
    }

    public FeaturedArticle(ArrayList<Double> featureVector) {
        this.featureVector = featureVector;
    }
}