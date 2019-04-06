import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AppRunner {

    public static void main(String[] args) {
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFolder = Paths.get(resourcesPath + "reuters21578");
        Path extractedReuters = Paths.get(resourcesPath + "extracted");


        ArrayList<Article> articles = new ArrayList<>();
        TF_IDFExtractor featuresExtractor = new TF_IDFExtractor(articles);
        featuresExtractor.extract();



    }
}


