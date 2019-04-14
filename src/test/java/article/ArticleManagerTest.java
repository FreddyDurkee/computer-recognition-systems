package article;

import article.Article;
import article.ArticleManager;
import org.hamcrest.collection.IsMapContaining;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ArticleManagerTest {

    @Test
    void addArticle() {
        // Given
        ArticleManager articleManager = new ArticleManager();
        assertEquals(0,articleManager.getArticles().size());
        Article example = new Article();

        // When
        articleManager.addArticle(example);

        // Then
        assertEquals(1,articleManager.getArticles().size());
    }

    @Test
    void clearArticles() {
        // Given
        ArticleManager articleManager = new ArticleManager();
        Article example = new Article();
        articleManager.addArticle(example);
        assertEquals(1,articleManager.getArticles().size());

        // When
        articleManager.clearArticles();

        // Then
        assertEquals(0,articleManager.getArticles().size());
    }

    @Test
    void getAmountArticles() {
        // Given
        ArticleManager articleManager = new ArticleManager();
        Article example = new Article();
        articleManager.addArticle(example);

        // When
        int amountOfArticles = articleManager.getAmountArticles();

        // Then
        assertEquals(1,amountOfArticles);
    }

    @Test
    void getArticlesWithSpecificNumberLabel() {
        // Given
        ArrayList<String> labels1 = new ArrayList<>();
        labels1.add("usa");

        ArrayList<String> labels2 = new ArrayList<>();
        labels2.add("japan");
        labels2.add("usa");

        Article a1 = new Article(labels1, "title", "text");
        Article a2 = new Article(labels1, "title", "text");

        ArticleManager articleManager = new ArticleManager();
        articleManager.addArticle(a1);
        articleManager.addArticle(a2);

        // When
        TreeSet<Article> actual = articleManager.getArticlesWithSpecificNumberLabel(1);

        // Then
        assertEquals(1,actual.size());
    }

    @Test
    void splitDataInProportion() {
        // Given
        ArrayList<String> labels1 = new ArrayList<>();
        labels1.add("usa");

        ArrayList<String> labels2 = new ArrayList<>();
        labels2.add("japan");

        ArrayList<String> labels3 = new ArrayList<>();
        labels3.add("canada");

        Article a1 = new Article(labels1, "title1", "text");
        Article a2 = new Article(labels1, "title2", "text");
        Article a3 = new Article(labels1, "title3", "text");
        Article a4 = new Article(labels1, "title4", "text");
        Article a5 = new Article(labels1, "title5", "text");
        Article a6 = new Article(labels1, "title6", "text");
        Article a7 = new Article(labels2, "title1", "text");
        Article a8 = new Article(labels2, "title2", "text");
        Article a9 = new Article(labels2, "title3", "text");
        Article a10 = new Article(labels3, "title1", "text");
        Article a11 = new Article(labels3, "title2", "text");
        Article a12 = new Article(labels3, "title3", "text");
        Article a13 = new Article(labels3, "title4", "text");


        ArticleManager articleManager = new ArticleManager();
        articleManager.addArticle(a1);
        articleManager.addArticle(a2);
        articleManager.addArticle(a3);
        articleManager.addArticle(a4);
        articleManager.addArticle(a5);
        articleManager.addArticle(a6);
        articleManager.addArticle(a7);
        articleManager.addArticle(a8);
        articleManager.addArticle(a9);
        articleManager.addArticle(a10);
        articleManager.addArticle(a11);
        articleManager.addArticle(a12);
        articleManager.addArticle(a13);

        // When
        Pair<Set<Article>, Set<Article>> splitedArticles = articleManager.splitDataInProportion(40);
        Map<String, Long> trainData = splitedArticles.getValue0().stream().collect(groupingBy(article -> article.getLabel().get(0), counting()));
        Map<String, Long> testData = splitedArticles.getValue1().stream().collect(groupingBy(article -> article.getLabel().get(0), counting()));

        // Then
        assertThat(trainData, IsMapContaining.hasEntry("usa", new Long(2)));
        assertThat(trainData, IsMapContaining.hasEntry("japan", new Long(1)));
        assertThat(trainData, IsMapContaining.hasEntry("canada", new Long(1)));
        assertThat(testData, IsMapContaining.hasEntry("usa", new Long(4)));
        assertThat(testData, IsMapContaining.hasEntry("japan", new Long(2)));
        assertThat(testData, IsMapContaining.hasEntry("canada", new Long(3)));

    }
}