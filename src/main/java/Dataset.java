import java.io.File;
import java.util.LinkedList;

public class Dataset {

    private LinkedList<String> label;
    private String data;

    public Dataset(LinkedList<String> label, String data) {
        this.label = label;
        this.data = data;
    }
}
