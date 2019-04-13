import article.Article;
import article.ArticleManager;
import file_extractor.Category;
import file_extractor.LabelCounter;
import file_extractor.ReutersExtractor;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReutersExtractorTest {

    @Test
    void extractPlaces() {
        // Given
        String example = "<TOPICS><D>acq</D></TOPICS>\n" +
                "<PLACES><D>usa</D></PLACES>\n" +
                "<PEOPLE></PEOPLE>";
        ArrayList<String> actual;
        ReutersExtractor reutersExtractor = new ReutersExtractor();
        ArrayList<String>  expected = new ArrayList<>();
        expected.add("usa");
        Pattern PLACES_PATTERN = Pattern.compile("<PLACES>(.*?)</PLACES>");

        // When
        actual = reutersExtractor.extractSomeElements(example, PLACES_PATTERN);

        // Then
        assertEquals(expected,actual);
        assertEquals(1,actual.size());
    }

    @Test
    void extractTitle() {
        // Given
        Pattern TITLE_PATTERN = Pattern.compile("<TITLE>(.*?)</TITLE>");
        String example = "<UNKNOWN> \n" +
                "&#5;&#5;&#5;A RM\n" +
                "&#22;&#22;&#1;f0977&#31;reute\n" +
                "b f BC-U.S.-FIRST-TIME-JOBLE   03-05 0080</UNKNOWN>\n" +
                "<TEXT>&#2;\n" +
                "<TITLE>U.S. FIRST TIME JOBLESS CLAIMS FALL IN WEEK</TITLE>\n" +
                "<DATELINE>    WASHINGTON, March 5 - </DATELINE><BODY>New applications for unemployment\n" +
                "insurance benefits fell to a seasonally adjusted 332,900 in the\n" +
                "week ended Feb 21 from 368,400 in the prior week, the Labor\n" +
                "Department said.\n" +
                "    The number of people actually receiving benefits under\n" +
                "regular state programs totaled 3,014,400 in the week ended Feb\n" +
                "14, the latest period for which that figure was available.\n" +
                "    That was up from 2,997,800 the previous week.\n" +
                "     \n" +
                " Reuter\n" +
                "&#3;</BODY></TEXT>";
        String expected = "U.S. FIRST TIME JOBLESS CLAIMS FALL IN WEEK";
        ReutersExtractor reutersExtractor = new ReutersExtractor();
        String actual;

        // When
        actual = reutersExtractor.extractPattern(example,TITLE_PATTERN);

        // Then
        assertEquals(expected,actual);
    }

    @Test
    void extractFile() {
        // Create Article Manager
        ArticleManager articleManager = new ArticleManager();

        // Set resources directory
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFile = Paths.get(resourcesPath + "reuters21578" + "/reut2-014.sgm");

        // Create Reuters Extractor
        ReutersExtractor reutersExtractor = new ReutersExtractor();

        // Extract Data
        reutersExtractor.addCategoryFilter(Category.PLACES)
                .addLabelFilter(i -> i==1)
                .extractFile(reutersSgmFile,articleManager);

        // Print One Article
        assertEquals(721, articleManager.getArticles().size());
        Iterator iterator = articleManager.getArticles().iterator();
        System.out.println(iterator.next().toString());
    }

    @Test
    void numberOfLabels() {
        // Create Article Manager
        ArticleManager articleManager = new ArticleManager();

        // Set resources directory
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFile = Paths.get(resourcesPath + "reuters21578" + "/reut2-014.sgm");

        // Create Reuters Extractor
        ReutersExtractor reutersExtractor = new ReutersExtractor();

        // Extract Data
        reutersExtractor.addCategoryFilter(Category.PLACES)
                .addLabelFilter(i -> i==1)
                .extractFile(reutersSgmFile,articleManager);

        // Print One Article
        Map<String, Long> map = articleManager.numberOfLabels();

        //3. Test map entry, best!
//        assertThat(map, IsMapContaining.hasEntry("spain", new Long(1)));
        assertThat(map, IsMapContaining.hasEntry("usa", new Long(474)));
        assertThat(map, IsMapContaining.hasEntry("brazil", new Long(1)));
        assertThat(map, IsMapContaining.hasEntry("japan", new Long(23)));
        assertThat(map, IsMapContaining.hasEntry("hong-kong", new Long(6)));
        assertThat(map, IsMapContaining.hasEntry("pakistan", new Long(1)));
        assertThat(map, IsMapContaining.hasEntry("india", new Long(3)));
        assertThat(map, IsMapContaining.hasEntry("malaysia", new Long(2)));
        assertThat(map, IsMapContaining.hasEntry("west-germany", new Long(8)));

    }

    @Test
    void extractAllFiles() {
        // Create Article Manager
        ArticleManager articleManager = new ArticleManager();

        // Set resources directory
        String resourcesPath = AppRunner.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");
        Path reutersSgmFolder = Paths.get(resourcesPath + "reuters21578");

        // Setup Extractor
        ReutersExtractor reutersExtractor = new ReutersExtractor(reutersSgmFolder);

        // Extract All Files in Directory
        try {
            reutersExtractor.addCategoryFilter(Category.PLACES)
                    .addLabelFilter(i -> i==1)
                    .extractAllFiles(articleManager);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get one article
        Iterator iterator = articleManager.getArticles().iterator();

        Article example = (Article) iterator.next();
        System.out.println("Labels: "+example.getLabel().toString());
        System.out.println("Title: "+ example.getTitle());
        System.out.println("Text: "+ example.getText());
    }


}