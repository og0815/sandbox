package eu.ggnet.saft.core;

import eu.ggnet.saft.core.all.UiCreator;
import eu.ggnet.saft.core.all.UiOk;
import eu.ggnet.saft.core.fx.FxCreator;
import eu.ggnet.saft.core.swing.SwingCreator;
import eu.ggnet.saft.core.swing.SwingOk;
import eu.ggnet.saft.core.all.UiFileChooser;
import eu.ggnet.saft.core.aux.CallableA1;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;

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
// TODO: If doof, open FileChooser only swing.

    public static <R> UiCreator<R> call(Callable<R> callable) {
        if (UiCore.isRunning() && UiCore.isFx()) return new FxCreator<>(callable);
        if (UiCore.isRunning() && UiCore.isSwing()) return new SwingCreator<>(callable);
        throw new IllegalStateException("UiCore not initalized");
    }

    public static <T, R extends Pane> UiOk<R> popupOkCancelFx(CallableA1<T, R> callableA1) {
        if (UiCore.isRunning() && UiCore.isFx()) return new FxCreator().popupOkCancelFx(callableA1);
        if (UiCore.isRunning() && UiCore.isSwing()) return new SwingCreator().popupOkCancelFx(callableA1);
        throw new IllegalStateException("UiCore not initalized");
    }

    public static <T, R extends JPanel> UiOk<R> popupOkCancel(CallableA1<T, R> callableA1) {
        if (UiCore.isRunning() && UiCore.isFx()) return new FxCreator().popupOkCancel(callableA1);
        if (UiCore.isRunning() && UiCore.isSwing()) return new SwingCreator().popupOkCancel(callableA1);
        throw new IllegalStateException("UiCore not initalized");
    }

    public static SwingOk<File> openFileChosser(String title) {
        return new UiFileChooser().open(title);
    }

    public static SwingOk<File> openFileChosser() {
        return new UiFileChooser().open();
    }

    public static <V> void exec(Callable<V> ie) {
        // See CompletableFuture(), might be cooler.
        // Return of Futur might
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    ie.call();
                } catch (Exception e) {
                    UiCore.catchException(e);
                }
            }
        };
        t.setDaemon(true); // Shut down on application shutdown.
        t.start();
    }

}
