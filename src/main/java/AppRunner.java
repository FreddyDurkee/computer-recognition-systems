import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class AppRunner {

    public static void main(String[] args) throws IOException {
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFolder = Paths.get(resourcesPath + "reuters21578");
        Path extractedReuters = Paths.get(resourcesPath + "extracted");


        ReutersExtractor reutersExtractor = new ReutersExtractor(reutersSgmFolder);
        ArticleManager articleManager = new ArticleManager();

        System.out.println("extract articles...");
        reutersExtractor.extractAllFiles(articleManager);

        TreeSet<Article> articles = articleManager.getArticles();
        TF_IDFExtractor featuresExtractor = new TF_IDFExtractor(articles);

        System.out.println("extract features...");
        List<FeaturedArticle> featuredArticles = featuresExtractor.extract();



    }
}


