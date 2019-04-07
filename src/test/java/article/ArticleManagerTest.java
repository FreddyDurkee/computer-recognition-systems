package article;

import article.Article;
import article.ArticleManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.TreeSet;

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
}