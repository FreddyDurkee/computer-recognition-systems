import lombok.Data;

import java.util.TreeSet;

@Data
public class ArticleManager {

    private TreeSet<Article> articles;

    public ArticleManager(TreeSet<Article> articles) {
        this.articles = articles;
    }

    public ArticleManager(){
        this.articles = new TreeSet<>();
    }

    public void addArticle(Article article){
        this.articles.add(article);
    }

    public void clearArticles(){
        this.articles.clear();
    }

    public int getAmountArticles(){
        return this.articles.size();
    }

}
