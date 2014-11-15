package sample.demo;

import javax.swing.JOptionPane;
import sample.demo.aux.MainPanel;
import sample.intention.Ui;
import sample.intention.UiCore;

/**
 * A Simple Exception handling Example.
 *
 * @author oliver.guenther
 */
public class OverwriteExceptions {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());
        UiCore.registerExceptionConsumer(IllegalArgumentException.class, (t) -> {
            JOptionPane.showMessageDialog(null, "Important:" + t.getClass().getSimpleName() + " : " + t.getMessage());
        });

        Ui.exec(
                Ui.call(() -> {
                    throw new IllegalArgumentException("Sinnlos");
                })
        );
    }

}
