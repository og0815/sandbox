package sample.intention;

import javafx.scene.layout.Pane;
import javax.swing.JPanel;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * The main entry point.
 *
 * @author oliver.guenther
 */
public class Ui2 {

    public static <T extends JPanel> JPanelRunner<T> popupOkCancel(JPanelBuilder<T> builder) {
        verifyCore();
        // the Swing way
        return new JPanelRunner<>(builder.build());
    }

    public static <T extends Pane> PaneRunner<T> popupOkCancel(PaneBuilder<T> builder) {
        verifyCore();
        return new PaneRunner<>(builder);
    }

    public static <V> Caller<V> call(Callable<V> callable) {
        verifyCore();
        return new Caller<>(callable);
    }

    private static void verifyCore() {
        Objects.requireNonNull(UiCore.mainPanel);
        // verify that the core is running.
    }
}
