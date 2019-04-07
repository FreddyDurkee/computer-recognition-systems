import article.Article;
import article.ArticleManager;
import file_extractor.Category;
import file_extractor.ReutersExtractor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;

public class AppRunner {

    public static void main(String[] args) throws IOException {
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFolder = Paths.get(resourcesPath + "reuters21578");
        Path extractedReuters = Paths.get(resourcesPath + "extracted");


        ReutersExtractor reutersExtractor = new ReutersExtractor(reutersSgmFolder);
        ArticleManager articleManager = new ArticleManager();

        System.out.println("extract articles...");
        reutersExtractor.extractAllFiles(articleManager, Category.PLACES);

        TreeSet<Article> articles = articleManager.getArticles();
//        other.TF_IDFExtractor featuresExtractor = new other.TF_IDFExtractor(articles);

        System.out.println("extract features...");
//        List<FeaturedArticle> featuredArticles = featuresExtractor.extract();



    }
}


