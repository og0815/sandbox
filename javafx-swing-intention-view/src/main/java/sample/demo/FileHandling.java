package sample.demo;

import sample.demo.aux.MainPanel;
import eu.ggnet.saft.core.Ui;
import eu.ggnet.saft.core.UiCore;

/**
 * Shows a file handling.
 *
 * @author oliver.guenther
 */
public class FileHandling {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());

        Ui.exec(
                Ui.openFileChosser()
                .onOk(f -> {
                    System.out.println("Ok pressed, File: " + f.getAbsolutePath());
                    return null;
                })
        );

    }

}
