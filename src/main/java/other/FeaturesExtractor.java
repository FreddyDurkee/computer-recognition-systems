package other;

import article.FeaturedArticle;

import java.util.*;
import java.util.stream.Collectors;

public interface FeaturesExtractor {
    public List<FeaturedArticle> extract();
}
