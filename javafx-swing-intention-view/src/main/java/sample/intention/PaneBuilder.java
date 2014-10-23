package sample.intention;

import javafx.scene.layout.Pane;

/**
 *
 * @author oliver.guenther
 */
public interface PaneBuilder<T extends Pane> {

    T build();
}
