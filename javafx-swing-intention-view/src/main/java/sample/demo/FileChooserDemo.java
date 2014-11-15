package sample.demo;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author oliver.guenther
 */
public class FileChooserDemo {

    public static void main(String[] args) {

//        JFileChooser fc = new JFileChooser();
//        fc.showOpenDialog(null);
        JFXPanel p = new JFXPanel();
        Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.showOpenDialog(new Stage());
        });
    }

}
