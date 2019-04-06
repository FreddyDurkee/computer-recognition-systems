package article;

import article.Article;
import article.ArticleManager;
import org.junit.jupiter.api.Test;

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
}