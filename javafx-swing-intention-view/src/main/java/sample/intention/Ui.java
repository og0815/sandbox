package sample.intention;

import javax.swing.JPanel;
import sample.intention.structure.CallableA1;
import sample.intention.swing.*;

import java.util.concurrent.Callable;

/**
 * The main entry point.
 *
 * @author oliver.guenther
 */
public class Ui {

    public static <T> UiCreator<T> call(Callable<T> callable) {
        return new UiCreator<>(callable);
    }

    public static <T, R extends JPanel> SwingOk<R> popupOkCancel(CallableA1<T, R> callableA1) {
        return new UiCreator().popupOkCancel(callableA1);
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
