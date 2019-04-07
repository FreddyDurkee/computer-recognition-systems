package article;

import gnu.trove.list.array.TDoubleArrayList;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FeaturedArticle implements Serializable {
    private List<String> label;
    private List<String> predictedLabel;
    private TDoubleArrayList featureVector;

    public FeaturedArticle(List<String> label, TDoubleArrayList featureVector) {
        this.label = label;
        this.featureVector = featureVector;
        this.predictedLabel = new ArrayList<>();
    }

    public FeaturedArticle(TDoubleArrayList featureVector) {
        this.featureVector = featureVector;
        this.predictedLabel = new ArrayList<>();
    }

}