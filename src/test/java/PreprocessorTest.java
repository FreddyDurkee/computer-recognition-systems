import article.Article;
import article.DictionarizedArticle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.collection.IsMapContaining;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreprocessorTest {

    @Test
    public void whenInvoked_extractTokens_thenDictionarizedArticleCreatedWithTokens() {

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
        DictionarizedArticle dictionarizedArticle = Preprocessor.extractTokens(article);
        HashSet<String> expectedTokens = new HashSet<>(Arrays.asList(
                "eindhoven", "netherland", "april", "nv", "philip",
                "gloeilampenfabrieken", "lt", "pgloa", "share", "due",
                "start", "trade", "new", "york", "stock", "exchang",
                "april", "chairman", "cor", "van", "der", "klugt",
                "told", "annual", "sharehold", "meet"));

        // Then
        assertEquals(expectedTokens, dictionarizedArticle.getDictionary());

    }

    @Test
    public void whenInvoked_createDictionary_thenCreateMapWithTokens() {

        // Given
        ArrayList<String> label1 = new ArrayList<String>() {{add("usa");}};
        ArrayList<String> label2 = new ArrayList<String>() {{add("canada");}};

        HashSet<String> tokens1 = new HashSet<>(Arrays.asList(
                "start", "york", "told", "annual","meet"));
        HashSet<String> tokens2 = new HashSet<>(Arrays.asList(
                "cat", "meet", "york", "seek"));

        DictionarizedArticle dictionarizedArticle1 = new DictionarizedArticle(label1, tokens1);
        DictionarizedArticle dictionarizedArticle2 = new DictionarizedArticle(label2, tokens2);

        ArrayList<DictionarizedArticle> dictionarizedArticles = new ArrayList<DictionarizedArticle>();
        dictionarizedArticles.add(dictionarizedArticle1);
        dictionarizedArticles.add(dictionarizedArticle2);

        // When

        Map<String,Integer> dictionary = Preprocessor.createDictionary(dictionarizedArticles);

        // Then

        assertThat(dictionary.size(), is(7));
        assertThat(dictionary, IsMapContaining.hasEntry("start", 0));
        assertThat(dictionary, IsMapContaining.hasEntry("york", 0));
        assertThat(dictionary, IsMapContaining.hasEntry("told", 0));
        assertThat(dictionary, IsMapContaining.hasEntry("annual", 0));
        assertThat(dictionary, IsMapContaining.hasEntry("meet", 0));
        assertThat(dictionary, IsMapContaining.hasEntry("cat", 0));
        assertThat(dictionary, IsMapContaining.hasEntry("seek", 0));
    }

}
