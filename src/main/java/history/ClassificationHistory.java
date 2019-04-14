package history;

import lombok.Data;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClassificationHistory implements Serializable {

    private Map<Integer, List<ClassifiedSample>> repository = new HashMap<>();

    public void add(ClassifiedSample item) {
        if(!repository.containsKey(item.getK())){
            repository.put(item.getK(),new ArrayList<>());
        }

        repository.get(item.getK()).add(item);
    }

    public void saveToFile(File selectedFile) throws IOException {
        FileOutputStream f = new FileOutputStream(selectedFile);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(repository);
        o.close();
        f.close();
    }

    public void saveToFile(String path) throws IOException {
        File file = new File(path);
        saveToFile(file);
    }

    public void loadFromFile(File selectedFile) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(selectedFile);
        ObjectInputStream oi = new ObjectInputStream(fi);
        this.repository = (Map<Integer, List<ClassifiedSample>>) oi.readObject();
        oi.close();
        fi.close();
    }

    public void loadFromFile(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        loadFromFile(file);
    }

    public void saveToFileOnlyLabels(File file, int k) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (ClassifiedSample i : this.repository.getOrDefault(k, new ArrayList<>())) {
            writer.write(i.labelsToString());
            writer.write("\n");
        }
        writer.close();
    }

}
