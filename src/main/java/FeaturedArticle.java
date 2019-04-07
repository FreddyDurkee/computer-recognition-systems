import gnu.trove.list.array.TDoubleArrayList;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeaturedArticle {

    private final List<String> label;
    private final TDoubleArrayList featureVector;

    public FeaturedArticle(List<String> label, TDoubleArrayList featureVector) {
        this.label = label;
        this.featureVector = featureVector;
    }
}
