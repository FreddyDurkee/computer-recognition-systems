package history;

import lombok.Data;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;

@Data
public class ClassificationHistory implements Serializable {

    private ArrayList<ClassifiedSample> repository = new ArrayList<>();

    public void add(ClassifiedSample item) {
        repository.add(item);
    }

    public void remove(ClassifiedSample item) {
        repository.remove(item);
    }

    public void saveToFile() throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            saveToFile(selectedFile);
        }
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

    public void loadFromFile() throws IOException, ClassNotFoundException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            loadFromFile(selectedFile);
        }
    }

    public void loadFromFile(File selectedFile) throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(selectedFile);
        ObjectInputStream oi = new ObjectInputStream(fi);
        this.repository = (ArrayList<ClassifiedSample>) oi.readObject();
        oi.close();
        fi.close();
    }

    public void loadFromFile(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        loadFromFile(file);
    }

    // Zwraca plik tekstowy o takich linijkach
    // reallabels1,reallabels2;predictedLabel\n
    public void saveToFileOnlyLabels(File file) throws IOException {
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(file));
        for (ClassifiedSample i : this.repository) {
            writer.write(i.labelsToString());
        }
        writer.close();
    }

    public void saveToFileOnlyLabels() throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            saveToFileOnlyLabels(selectedFile);
        }
    }

    public void saveToFileOnlyLabels(String path) throws IOException {
        File file = new File(path);
        saveToFileOnlyLabels(file);
    }



}
