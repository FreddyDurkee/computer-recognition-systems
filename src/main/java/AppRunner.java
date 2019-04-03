import org.apache.lucene.benchmark.utils.ExtractReuters;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AppRunner {

    public static void main(String[] args) {
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFolder = Paths.get(resourcesPath + "reuters21578");
        Path extractedReuters = Paths.get(resourcesPath + "extracted");


        ArrayList<Article> articles = new ArrayList<Article>();

        List<DictionarizedArticle> dictionarizedArticleList = articles.parallelStream().map(Preprocessor::extract).collect(Collectors.toList());


    }

//
//    public static void streamFunction(){
//        ArrayList<String> arr = new ArrayList<>();
//        arr.stream().map(s -> Arrays.asList(s.split(" ")) ).flatMap(x -> x.stream());
//        arr.stream().filter((i)-> i.contains("kasztan")).map((s)-> s.toLowerCase());
//        arr.stream().forEach(System.out::println);
//        System.out.println
//    }
}


