package other;

import article.Article;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class TextFeaturesExtractorBuilder {

    public static final Logger LOGGER = LogManager.getLogger(TextFeaturesExtractorBuilder.class);

    @Getter
    private Set<Article> testArticles;
    @Getter
    private Set<Article> trainArticles;
    @Getter
    private double tfIdfTresholdVal = -1;

    public TextFeaturesExtractorBuilder(Set<Article> testArticles, Set<Article> trainArticles) {
        this.testArticles = testArticles;
        this.trainArticles = trainArticles;
    }

    public TextFeaturesExtractor buid() {
        return new TextFeaturesExtractor(this);
    }

    public TextFeaturesExtractorBuilder setTfIdfTresholdVal(double tfIdfTresholdVal) {
        try {
            if (tfIdfTresholdVal > 1 || tfIdfTresholdVal < 0) {
                throw new IllegalArgumentException("TF-IDF value must be from 0 to 1");
            }
        }
        catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
        }
        this.tfIdfTresholdVal = tfIdfTresholdVal;
        return this;
    }
}
