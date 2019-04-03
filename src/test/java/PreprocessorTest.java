import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreprocessorTest {

    @Test
    public void whenUseCustomAnalyzerBuilder_thenAnalyzed() throws IOException {

        // Given
        Article article = new Article();
        String newLabel = "netherlands";
        article.addLabel(newLabel);
        article.setTitle("EINDHOVEN, NETHERLANDS, APRIL 8");
        article.setText("NV Philips\n" +
                "Gloeilampenfabrieken &lt;PGLO.AS> shares are due to start trading\n" +
                "on the New York Stock Exchange on April 14, Philips chairman\n" +
                "Cor van der Klugt told the annual shareholders meeting.");

        // When
        DictionarizedArticle dictionarizedArticle = Preprocessor.extract(article);
        HashSet<String> expectedTokens = new HashSet<>(Arrays.asList(
                "eindhoven", "netherland", "april", "nv", "philip",
                "gloeilampenfabrieken", "lt" , "pgloa", "share", "due",
                "start", "trade", "new", "york", "stock", "exchang",
                "april", "chairman", "cor", "van", "der", "klugt",
                "told", "annual", "sharehold", "meet"));

        // Then
        assertEquals(expectedTokens, dictionarizedArticle.getDictionary());

    }

}
