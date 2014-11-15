package sample.demo;

import javafx.application.Platform;
import sample.intention.Ui;

/**
 * Shows a file handling.
 *
 * @author oliver.guenther
 */
public class FileHandling {

    public static void main(String[] args) {

        Ui.exec(
                Ui.openFileChosser()
                .onOk(f -> {
                    System.out.println("Ok pressed, File: " + f.getAbsolutePath());
                    Platform.exit();
                    return null;
                })
        );

    }

}
