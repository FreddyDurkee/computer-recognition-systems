import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreprocessorTest {

    @Test
    public void extractTokens() {

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
        HashSet<String> currentTokens = Preprocessor.extractTokens(article.getTextAndTitle());
        HashSet<String> expectedTokens = new HashSet<>(Arrays.asList(
                "eindhoven", "netherland", "april", "nv", "philip",
                "gloeilampenfabrieken", "lt", "pgloa", "share", "due",
                "start", "trade", "new", "york", "stock", "exchang",
                "april", "chairman", "cor", "van", "der", "klugt",
                "told", "annual", "sharehold", "meet"));

        // Then
        assertEquals(expectedTokens, currentTokens);
    }


    @Test
    public void extractWords() {

        // Given
        Article article = new Article();
        String newLabel = "netherlands";
        article.addLabel(newLabel);
        article.setTitle("EINDHOVEN, NETHERLANDS, APRIL 8");
        article.setText("NV Philips\n" +
                "Gloeilampenfabrieken &lt;PGLO.AS> shares are due to start trading\n" +
                "on the New York Stock Exchange on April 14, Philips chairman");

        // When
        List<String> currentTokens = Preprocessor.extractWords(article.getTextAndTitle());
        ArrayList<String> expectedTokens = new ArrayList<>(Arrays.asList(
                "eindhoven", "netherland", "april", "8", "nv", "philip",
                "gloeilampenfabrieken", "lt", "pgloa", "share", "ar", "due",
                "to", "start", "trade", "on", "the", "new", "york", "stock",
                "exchang", "on", "april", "14", "philip", "chairman"));

        // Then
        assertEquals(expectedTokens, currentTokens);

    }

    @Test
    public void createDictionary() {

        // Given
        ArrayList<String> label1 = new ArrayList<String>() {{ add("usa");}};
        ArrayList<String> label2 = new ArrayList<String>() {{ add("canada");}};

        HashSet<String> tokens1 = new HashSet<>(Arrays.asList( "start", "york", "told", "annual", "meet"));
        HashSet<String> tokens2 = new HashSet<>(Arrays.asList( "cat", "meet", "york", "seek"));

        DictionarizedArticle dictionarizedArticle1 = new DictionarizedArticle(label1, tokens1);
        DictionarizedArticle dictionarizedArticle2 = new DictionarizedArticle(label2, tokens2);

        ArrayList<DictionarizedArticle> dictionarizedArticles = new ArrayList<>();
        dictionarizedArticles.add(dictionarizedArticle1);
        dictionarizedArticles.add(dictionarizedArticle2);

        // When

        HashSet<String> dictionary = Preprocessor.createDictionary(dictionarizedArticles);

        // Then
        HashSet<String> expectedDictionary = new HashSet<String>() {{
            add("start");
            add("york");
            add("told");
            add("annual");
            add("meet");
            add("cat");
            add("seek");
        }};
        assertThat(dictionary.size(), is(7));
        assertEquals(expectedDictionary, dictionary);
    }

}
