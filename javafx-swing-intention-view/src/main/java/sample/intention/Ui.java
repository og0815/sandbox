package sample.intention;

import javafx.scene.layout.Pane;
import javax.swing.JPanel;
import sample.intention.structure.CallableA1;
import sample.intention.swing.*;

import java.io.File;
import java.util.concurrent.*;

/**
 * The main entry point.
 *
 * Some rules which I invented on the way:
 * <ul>
 * <li>Result of null is indicator to break the chain</li>
 * <li></li>
 * <li></li>
 * <li></li>
 * </ul>
 *
 * @author oliver.guenther
 */
public class Ui {

    public static <R> UiCreator<R> call(Callable<R> callable) {
        return new UiCreator<>(callable);
    }

    public static <T, R extends Pane> UiOk<R> popupOkCancelFx(CallableA1<T, R> callableA1) {
        return new UiCreator().popupOkCancelFx(callableA1);
    }

    public static <T, R extends JPanel> UiOk<R> popupOkCancel(CallableA1<T, R> callableA1) {
        return new UiCreator().popupOkCancel(callableA1);
    }

    public static UiOk<File> openFileChosser(String title) {
        return new UiFileChooser().open(title);
    }

    public static UiOk<File> openFileChosser() {
        return new UiFileChooser().open();
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
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
