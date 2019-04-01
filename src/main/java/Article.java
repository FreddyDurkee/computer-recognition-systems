import lombok.Data;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@Data
public class Article implements Comparable<Article> {

    private ArrayList<String> label;
    private String text;
    private String title;

    public Article() {
        this.label = new ArrayList<>();
        this.text = new String();
        this.title = new String();
    }

    public Article(ArrayList<String> labels, String title, String text){
        this.label = labels;
        this.text = text;
        this.title = title;
    }

    public void addLabel(String label){
        this.label.add(label);
    }

    public void clearLabels(){
        this.label.clear();
    }

    public String getTextAndTitle(){
        return getTitle()+"\n"+getText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(label, article.label) &&
                Objects.equals(text, article.text) &&
                Objects.equals(title, article.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, text, title);
    }

    @Override
    public int compareTo(Article o) {
        if(this.hashCode() == o.hashCode()){
            if(this.equals(o)){
                return 0;
            }
        }
        return -1;
    }
}
