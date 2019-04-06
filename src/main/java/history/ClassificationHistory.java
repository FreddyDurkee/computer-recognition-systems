package history;

import lombok.Data;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;

@Data
public class ClassificationHistory implements Serializable {

    private ArrayList<ClassifiedSample> repository = new ArrayList<>();

    public void add(ClassifiedSample item){
        repository.add(item);
    }

    public void remove(ClassifiedSample item){
        repository.remove(item);
    }

    public void saveToFile() throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
                FileOutputStream f = new FileOutputStream(selectedFile);
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(repository);
                o.close();
                f.close();
        }
    }

    public void loadFromFile(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                FileInputStream fi = new FileInputStream(selectedFile);
                ObjectInputStream oi = new ObjectInputStream(fi);
                this.repository = (ArrayList<ClassifiedSample>) oi.readObject();
                oi.close();
                fi.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("Error initializing stream");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
