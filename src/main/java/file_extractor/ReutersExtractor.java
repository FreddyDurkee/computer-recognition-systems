package file_extractor;

import article.Article;
import article.ArticleManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReutersExtractor {

    private Path reutersDir;

    Pattern ELEMENT_PATTERN = Pattern.compile("<D>(.*?)</D>");
    Pattern PLACES_PATTERN = Pattern.compile("<PLACES>(.*?)</PLACES>");
    Pattern TITLE_PATTERN = Pattern.compile("<TITLE>(.*?)</TITLE>");
    Pattern TEXT_PATTERN = Pattern.compile("<BODY>(.*?)</BODY>");
    Pattern TOPICS_PATTERN = Pattern.compile("<TOPICS>(.*?)</TOPICS>");

    private static String[] META_CHARS = {"&", "<", ">", "\"", "'"};

    private static String[] META_CHARS_SERIALIZATIONS = {"&amp;", "&lt;",
            "&gt;", "&quot;", "&apos;"};


    public ReutersExtractor(Path reutersDir) {
        this.reutersDir = reutersDir;
    }

    public ReutersExtractor() {
        this.reutersDir = null;
    }

    public void extractFile(Path sgmFile, ArticleManager articleManager, Category category) {
        String title;
        String text;
        ArrayList<String> labels = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(sgmFile, StandardCharsets.ISO_8859_1)) {
            StringBuilder buffer = new StringBuilder(1024);
            String line = null;
            while ((line = reader.readLine()) != null) { // when we see a closing reuters tag, flush the file
                if (line.indexOf("</REUTERS") == -1) {
                    // Replace the SGM escape sequences
                    buffer.append(line).append(' ');// accumulate the strings for now,
                    // then apply regular expression to
                    // get the pieces,
                } else {
                    // Get data via pattern to temporary variables

                    switch(category){
                        case PLACES: {
                            labels = extractSomeElements(buffer.toString(), PLACES_PATTERN);
                            break;
                        }
                        case TOPICS: {
                            labels = extractSomeElements(buffer.toString(), TOPICS_PATTERN);
                            break;
                        }
                    }

                    title = extractPattern(buffer.toString(),TITLE_PATTERN);
                    text = extractPattern(buffer.toString(),TEXT_PATTERN);

                    // Clear article text
                    for (int i = 0; i < META_CHARS_SERIALIZATIONS.length; i++) {
                        text = text.replaceAll(META_CHARS_SERIALIZATIONS[i], META_CHARS[i]);
                        title = title.replaceAll(META_CHARS_SERIALIZATIONS[i], META_CHARS[i]);
                    }

                    // Add article
                    // Not load article with empty labels
                    if(labels.size() != 0){
                        articleManager.addArticle(new Article(labels,title,text));
                    }



                    // Clear
                    buffer.setLength(0);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void extractAllFiles(ArticleManager dataSet, Category category) throws IOException {
        dataSet.getArticles().clear();

        long count = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(reutersDir, "*.sgm")) {
            for (Path sgmFile : stream) {
                extractFile(sgmFile, dataSet, category); // Load File to dataset
                count++;
            }
        }
        if (count == 0) {
            System.err.println("No .sgm files in " + reutersDir);
        }
    }

    public ArrayList<String> extractSomeElements(String text, Pattern pattern) {
        ArrayList<String> result = new ArrayList<>();

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    Matcher matcher_place = ELEMENT_PATTERN.matcher(matcher.group(i));
                    while (matcher_place.find()) {
                        for (int k = 1; k <= matcher_place.groupCount(); k++) {
                            if (matcher_place.group(k) != null) {
                                result.add(matcher_place.group(k));
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    public String extractPattern(String text, Pattern pattern) {
        String result = "null";

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    result = matcher.group(i);
                }
            }
        }
        return result;
    }

}
