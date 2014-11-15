package sample.demo;

import sample.demo.aux.MainPanel;
import sample.intention.Ui;
import sample.intention.UiCore;

/**
 * A Simple Exception handling Example.
 *
 * @author oliver.guenther
 */
public class SimpleException {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());

        Ui.exec(
                Ui.call(() -> {
                    throw new IllegalAccessException("Sinnlos");
                })
        );
    }

}
