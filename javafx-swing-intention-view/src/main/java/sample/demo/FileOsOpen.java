package sample.demo;

import sample.demo.aux.MainPanel;
import sample.intention.Ui;
import sample.intention.UiCore;

/**
 * Shows a file handling.
 *
 * @author oliver.guenther
 */
public class FileOsOpen {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());

        Ui.exec(
                Ui.openFileChosser("Bitte Datei auswählen, die das Betriebsystem öffnen kann")
                .onOk(f -> f) // Push value though.
                .osOpen()
        );

    }

}
