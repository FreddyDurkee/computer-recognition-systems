package article;

import lombok.Data;

import java.util.Iterator;
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

//    public void addArticle(Article article, d)

    public TreeSet<Article> getArticlesWithSpecificNumberLabel(int number){
        TreeSet<Article> articles = new TreeSet<>();
        Iterator iterator = this.articles.iterator();
        Article art;
        while(iterator.hasNext()){
            art = (Article) iterator.next();
            if(art.getLabel().size() == number){
                articles.add(art);
            }
        }
        return articles;
    }

}
