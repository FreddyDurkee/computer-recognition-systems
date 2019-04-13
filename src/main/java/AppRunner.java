import article.Article;
import article.ArticleManager;
import file_extractor.Category;
import file_extractor.ReutersExtractor;
import org.javatuples.Pair;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


public class AppRunner {

    public static void main(String[] args) throws IOException {
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFolder = Paths.get(resourcesPath + "reuters21578");
        Path extractedReuters = Paths.get(resourcesPath + "extracted");

        List<String> labelsToClassification = new ArrayList<String>(Arrays.asList(
                "west-germany", "usa", "france", "uk", "canada", "japan"));


        ReutersExtractor reutersExtractor = new ReutersExtractor(reutersSgmFolder);
        ArticleManager articleManager = new ArticleManager();

        System.out.println("extract articles...");
        reutersExtractor.addCategoryFilter(Category.PLACES)
                .addLabelFilter(i -> i == 1)
                .addLabelNameFilter(labelsToClassification)
                .extractAllFiles(articleManager);

        Map<String, Long> map = articleManager.numberOfLabels();
        System.out.println("articles map = " + map);

        Pair<Set<Article>, Set<Article>> splitedArticles = articleManager.getTrainAndTestDataInProportion(40);

        Map<String, Long> trainData = splitedArticles.getValue0().stream().collect(groupingBy(article -> article.getLabel().get(0), counting()));
        System.out.println("train map = " + trainData);

        Map<String, Long> testData = splitedArticles.getValue1().stream().collect(groupingBy(article -> article.getLabel().get(0), counting()));
        System.out.println("test map = " + testData);

//        TreeSet<Article> articles = articleManager.getArticles();
//        TF_IDFExtractor featuresExtractor = new TF_IDFExtractor(articles);
//
//        System.out.println("extract features...");
//        List<FeaturedArticle> featuredArticles = featuresExtractor.extract();
//
//        KNN_Algorithm knn = new KNN_Algorithm(featuredArticles);
//        knn.KNN()


    }
}


