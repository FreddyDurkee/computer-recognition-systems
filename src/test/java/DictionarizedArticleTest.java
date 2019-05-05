import article.Article;
import article.DictionarizedArticle;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sun.jmx.snmp.ThreadContext.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionarizedArticleTest {

    @Test
    void getDictionary() {
        // Given
        Article article = new Article();
        String newLabel = "netherlands";
        article.addLabel(newLabel);
        article.setTitle("COLOMBIA OPENS APRIL/MAY COFFEE REGISTRATIONS");
        article.setText("Colombia opened coffee export registrations for April " +
                        "and May with the National Coffee Growers' Federation " +
                        "setting no limit, Gilberto Arango, president of the " +
                        "private exporters' association, said.");
        DictionarizedArticle dictionarizedArticle = new DictionarizedArticle(article);

        // When
        Set<String> extractedTokens =  dictionarizedArticle.getDictionary();

        HashSet<String> expectedTokens = new HashSet<>(Arrays.asList(
                "colombia", "open", "april", "mai", "coffe", "registr",
                "export", "nation", "grower", "feder", "set", "limit",
                "gilberto", "arango", "presid", "privat", "associ", "said"));

        // Then
        assertEquals(expectedTokens, extractedTokens);
    }

    @Test
    void Should_Dictionary_And_ListOfWords_Correspond() {
        // Given
        Article article = new Article();
        String newLabel = "netherlands";
        article.addLabel(newLabel);
        article.setTitle("COLOMBIA OPENS APRIL/MAY COFFEE REGISTRATIONS");
        article.setText("Colombia opened coffee export registrations for April " +
                "and May with the National Coffee Growers' Federation ");
        DictionarizedArticle dictionarizedArticle = new DictionarizedArticle(article);



        // When
        Set<String> extractedTokens =  dictionarizedArticle.getDictionary();
        List<String> listOfWords = dictionarizedArticle.getListOfWords();


        Set<String> expecedExtractedTokens = new HashSet<>(Arrays.asList("registr", "mai", "nation", "april", "export", "grower", "colombia", "open", "coffe", "feder"));

        // Then

        assertEquals(extractedTokens, expecedExtractedTokens);

        assertTrue(listOfWords.contains("registr"));
        assertTrue(listOfWords.contains("mai"));
        assertTrue(listOfWords.contains("nation"));
        assertTrue(listOfWords.contains("april"));
        assertTrue(listOfWords.contains("export"));
        assertTrue(listOfWords.contains("grower"));
        assertTrue(listOfWords.contains("colombia"));
        assertTrue(listOfWords.contains("open"));
        assertTrue(listOfWords.contains("coffe"));
        assertTrue(listOfWords.contains("feder"));
    }
}
