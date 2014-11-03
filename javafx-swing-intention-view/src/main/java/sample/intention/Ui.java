package sample.intention;

import javax.swing.JPanel;
import sample.intention.structure.PopupBlenderOneArgSwing;
import sample.intention.swing.*;

import java.util.concurrent.Callable;

/**
 * The main entry point.
 *
 * @author oliver.guenther
 */
public class Ui {

    public static <T> SwingPopupOneArg<T> call(Callable<T> callable) {
        return new SwingPopupOneArg<>(callable);
    }

    public static <T, R extends JPanel> SwingOk<R> popupOkCancel(PopupBlenderOneArgSwing<T, R> builder) {
        return new SwingPopupOneArg().popupOkCancel(builder);
    }

    public static <V> void exec(Callable<V> ie) {
        // See CompletableFuture(), might be cooler.
        // Return of Futur might
        new Thread() {
            @Override
            public void run() {
                try {
                    ie.call();
                } catch (Exception e) {
                    UiCore.handleException(e);
                }
            }
        }.start();
    }

}
