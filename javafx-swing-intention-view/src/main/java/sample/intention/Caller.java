package sample.intention;

import javafx.scene.layout.Pane;
import javax.swing.JPanel;
import sample.intention.structure.PopupBlenderOneArgSwing;

import java.util.concurrent.*;

/**
 *
 * @author oliver.guenther
 */
public class Caller<V> {

    private final Callable<V> callable;

    public Caller(Callable<V> callable) {
        this.callable = callable;
    }

    public <T extends JPanel> JPanelRunner<T> popupOkCancel(PopupBlenderOneArgSwing<V, T> builder) {
        return null;//new JPanelRunner<>(builder.build(parameter));
    }

    public <T extends Pane> PaneRunner<T> popupOkCancel(PaneBuilder<T> builder) {
//        verifyCore();
        return new PaneRunner<>(builder);
    }

}
