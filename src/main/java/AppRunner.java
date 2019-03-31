import org.apache.lucene.benchmark.utils.ExtractReuters;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AppRunner {
    public static void main(String[] args) {
        Path currentDir = Paths.get(".").toAbsolutePath();
        Path reutersSgmFolder = Paths.get(currentDir.toString(), "reuters21578");
        Path extractedReutersFolder = Paths.get(currentDir.toString(), "extractedReuters");
        try {
            ExtractReuters reuters = new ExtractReuters(reutersSgmFolder, extractedReutersFolder);
            reuters.extract();
        } catch (IOException e) {
            Logger.getLogger(Dataset.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

