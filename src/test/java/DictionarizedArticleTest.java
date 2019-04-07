import article.Article;
import article.DictionarizedArticle;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        HashSet<String> extractedTokens =  dictionarizedArticle.getDictionary();

        HashSet<String> expectedTokens = new HashSet<>(Arrays.asList(
                "colombia", "open", "april", "mai", "coffe", "registr",
                "export", "nation", "grower", "feder", "set", "limit",
                "gilberto", "arango", "presid", "privat", "associ", "said"));

        // Then
        assertEquals(expectedTokens, extractedTokens);
    }
}
