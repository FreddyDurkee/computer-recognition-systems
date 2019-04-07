package article;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FeaturedArticle implements Serializable {
    private ArrayList<String> label;
    private ArrayList<Double> featureVector;

    public FeaturedArticle(List<String> label, List<Double> featureVector) {
        this.label = (ArrayList<String>) label;
        this.featureVector = (ArrayList<Double>) featureVector;
    }

    public FeaturedArticle(ArrayList<String> label, ArrayList<Double> featureVector) {
        this.label = label;
        this.featureVector = featureVector;
    }

    public FeaturedArticle(ArrayList<Double> featureVector) {
        this.featureVector = featureVector;
        this.label = new ArrayList<>();
    }

    public FeaturedArticle(){
        this.featureVector = new ArrayList<>();
        this.label = new ArrayList<>();
    }
}