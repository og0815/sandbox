package sample.intention;

import java.util.Objects;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;

/**
 * The main entry point.
 *
 * @author oliver.guenther
 */
public class Ui {

    public static <T extends JPanel> JPanelRunner<T> popupOkCancel(JPanelBuilder<T> builder) {
        verifyCore();
        // the Swing way
        return new JPanelRunner<>(builder);
    }

    public static <T extends Pane> PaneRunner<T> popupOkCancel(PaneBuilder<T> builder) {
        verifyCore();
        return new PaneRunner<>(builder);
    }

    private static void verifyCore() {
        Objects.requireNonNull(UiCore.mainPanel);
        // verify that the core is running.
    }
}
