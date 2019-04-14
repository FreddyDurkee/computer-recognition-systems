package article;

import gnu.trove.list.array.TDoubleArrayList;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FeaturedArticle implements Serializable {
    private List<String> label;
    private TDoubleArrayList featureVector;

    public FeaturedArticle(List<String> label, TDoubleArrayList featureVector) {
        this.label = label;
        this.featureVector = featureVector;
    }

    public FeaturedArticle(TDoubleArrayList featureVector) {
        this.featureVector = featureVector;
    }

}