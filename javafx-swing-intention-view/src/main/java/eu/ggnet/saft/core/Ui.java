package eu.ggnet.saft.core;

import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.*;
import eu.ggnet.saft.core.fx.FxCreator;
import eu.ggnet.saft.core.fx.FxSaft;
import eu.ggnet.saft.core.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.*;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger L = LoggerFactory.getLogger(Ui.class);

    public static <R> UiCreator<R> parent(Component parent) {
        if (UiCore.isRunning() && UiCore.isFx()) {
            L.warn("Using a swing component as parent in JavaFx Mode is not yet implemented");
            return new FxCreator<>(null, UiCore.mainStage, null); // TODO: Find a way to get a Stage from a Swing embedded component.
        }
        if (UiCore.isRunning() && UiCore.isSwing())
            return new SwingCreator<>(null, SwingSaft.windowAncestor(parent).orElse(UiCore.mainPanel), null);
        throw new IllegalStateException("UiCore not initalized");
    }

    public static <R> UiCreator<R> parent(Parent parent) {
        if (UiCore.isRunning() && UiCore.isFx())
            return new FxCreator<>(null, FxSaft.windowAncestor(parent), null);
        if (UiCore.isRunning() && UiCore.isSwing())
            return new SwingCreator<>(null, SwingSaft.windowAncestor(parent).orElse(UiCore.mainPanel), null);
        throw new IllegalStateException("UiCore not initalized");
    }

    private static <R> UiCreator<R> creator() {
        if (UiCore.isRunning() && UiCore.isFx())
            return new FxCreator<>(null, UiCore.mainStage, null);
        if (UiCore.isRunning() && UiCore.isSwing())
            return new SwingCreator<>(null, UiCore.mainPanel, null);
        throw new IllegalStateException("UiCore not initalized");
    }

    public static <R> UiCreator<R> call(Callable<R> callable) {
        return creator().call(callable);
    }

    public static <T, R extends Pane> UiOk<R> popupOkCancelFx(CallableA1<T, R> callableA1) {
        return Ui.<T>creator().popupOkCancelFx(callableA1);
    }

    public static <T, R extends JPanel> UiOk<R> popupOkCancel(CallableA1<T, R> callableA1) {
        return Ui.<T>creator().popupOkCancel(callableA1);
    }

    public static UiOk<File> openFileChosser(String title) {
        return Ui.<File>creator().openFileChooser(title);

    }

    public static UiOk<File> openFileChosser() {
        return Ui.<File>creator().openFileChooser();
    }

    public static <T, R extends JPanel> SwingOpenPanel<T, R> openJ(String key, CallableA1<T, R> callableA1) {
        return new SwingCreator<T>(null, UiCore.mainPanel, null).openJ(key, callableA1);
    }

    public static <T, R extends Pane> SwingOpenPane<T, R> open(String key, CallableA1<T, R> callableA1) {
        return new SwingCreator<T>(null, UiCore.mainPanel, null).open(key, callableA1);
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
