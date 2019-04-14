import article.ArticleManager;
import file_extractor.Category;
import file_extractor.ReutersExtractor;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import utils.TestResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReutersExtractorTest {

    private static Resource testResource;
    public static final String RESOURCES_PATH = ReutersExtractorTest.class.getClassLoader().getResource("").getPath().replaceFirst("/", "");

    @BeforeAll
    public static void setUp() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(RESOURCES_PATH + "reuters21578" + "/reut2-014.sgm"));
        String content = String.join("\n", lines);
        testResource = new TestResource(content);
    }

    @Test
    void extractPlaces() {
        // Given
        String example = "<TOPICS><D>acq</D></TOPICS>\n" +
                "<PLACES><D>usa</D></PLACES>\n" +
                "<PEOPLE></PEOPLE>";
        ArrayList<String> actual;
        ReutersExtractor reutersExtractor = new ReutersExtractor();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("usa");
        Pattern PLACES_PATTERN = Pattern.compile("<PLACES>(.*?)</PLACES>");

        // When
        actual = reutersExtractor.extractSomeElements(example, PLACES_PATTERN);

        // Then
        assertEquals(expected, actual);
        assertEquals(1, actual.size());
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
        actual = reutersExtractor.extractPattern(example, TITLE_PATTERN);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void extractFile() {
        // Create Article Manager
        ArticleManager articleManager = new ArticleManager();

        // Set resources directory
        //        Path reutersSgmFile = Paths.get(resourcesPath + "reuters21578" + "/reut2-014.sgm");
        Resource reutersSgmFile = testResource;

        // Create Reuters Extractor
        ReutersExtractor reutersExtractor = new ReutersExtractor();

        // Extract Data
        reutersExtractor.addCategoryFilter(Category.PLACES)
                .addLabelFilter(i -> i == 1)
                .extractFile(reutersSgmFile, articleManager);

        // Print One Article
        assertEquals(374, articleManager.getArticles().size());
        Iterator iterator = articleManager.getArticles().iterator();
        System.out.println(iterator.next().toString());
    }

    @Test
    void numberOfLabels() {
        // Create Article Manager
        ArticleManager articleManager = new ArticleManager();

        // Set resources directory
        Resource reutersSgmFile = testResource;

        // Create Reuters Extractor
        ReutersExtractor reutersExtractor = new ReutersExtractor();

        // Extract Data
        reutersExtractor.addCategoryFilter(Category.PLACES)
                .addLabelFilter(i -> i == 1)
                .extractFile(reutersSgmFile, articleManager);

        // Print One Article
        Map<String, Long> map = articleManager.numberOfLabels();

        //3. Test map entry, best!
        assertThat(map, IsMapContaining.hasEntry("usa", 239L));
        assertThat(map, IsMapContaining.hasEntry("uk", 22L));

    }
}