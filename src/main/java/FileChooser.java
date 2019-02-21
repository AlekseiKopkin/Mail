import javax.swing.*;
import java.io.File;

public class FileChooser {

    private JFileChooser chooser = new JFileChooser();

    public FileChooser(){
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile());
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
        }
    }

    public String getName(){
        return chooser.getSelectedFile().getName();
    }

    public File getSelectedFile(){
        return chooser.getSelectedFile();
    }
}

