import org.apache.lucene.benchmark.utils.ExtractReuters;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppRunner {

    public static void main(String[] args) {
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFolder = Paths.get(resourcesPath + "reuters21578");
        Path extractedReuters = Paths.get(resourcesPath + "extracted");
        try {
            ExtractReuters reuters = new ExtractReuters(reutersSgmFolder, extractedReuters);
            reuters.extract();
        } catch (IOException e) {
            Logger.getLogger(ArticleManager.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
