package article;

import org.javatuples.Pair;
import lombok.Data;

import java.util.*;

import static java.util.stream.Collectors.*;

@Data
public class ArticleManager {

    private TreeSet<Article> articles;

    public ArticleManager(TreeSet<Article> articles) {
        this.articles = articles;
    }

    public Map<String, Long> numberOfLabels() {
        return articles.stream().collect(groupingBy(article -> article.getLabel().get(0), counting()));
    }

    public Map<String, Long> numberOfAllLabels(){
        Map<String, Long> result = new HashMap<>();
        for(Article article:articles){
            for(String label: article.getLabel()){
                if(result.containsKey(label))
                {
                    result.put(label,result.get(label)+Long.valueOf(1));
                } else {
                    result.put(label, Long.valueOf(1));
                }
            }
        }
        return result;
    }

    public ArticleManager() {
        this.articles = new TreeSet<>();
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

    public void clearArticles() {
        this.articles.clear();
    }

    public int getAmountArticles() {
        return this.articles.size();
    }

    public Pair<Set<Article>, Set<Article>> splitDataInProportion(int traintPersentage) {
        Set<Article> trainSet = new TreeSet<>();
        Set<Article> testSet = new TreeSet<>();

        Map<String, Long> frequencyOfLabelsInAllArticles = numberOfLabels();
        Map<String, Long> labelsCounter = new HashMap<>();

        for (String labelName : frequencyOfLabelsInAllArticles.keySet()) {
            labelsCounter.put(labelName, Long.valueOf(0));
        }

        for (Article article : articles) {
            String labelName = article.getLabel().get(0);
            if (labelsCounter.get(labelName) < Math.floor(frequencyOfLabelsInAllArticles.get(labelName) * traintPersentage * 0.01)) {
                trainSet.add(article);
                labelsCounter.put(labelName, labelsCounter.get(labelName) + 1);
            } else {
                testSet.add(article);
            }
        }

        return new Pair<>(trainSet, testSet);
    }

    public TreeSet<Article> getArticlesWithSpecificNumberLabel(int number) {
        TreeSet<Article> articles = new TreeSet<>();
        Iterator iterator = this.articles.iterator();
        Article art;
        while (iterator.hasNext()) {
            art = (Article) iterator.next();
            if (art.getLabel().size() == number) {
                articles.add(art);
            }
        }
        return articles;
    }

}
