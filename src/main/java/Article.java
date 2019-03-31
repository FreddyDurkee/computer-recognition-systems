import java.util.ArrayList;

public class Article {
    private final ArrayList<String> label;
    private final String text;

    public Article() {
        this.label = new ArrayList<String>();
        this.text = new String();
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public String getText() {
        return text;
    }
}
