package eu.ggnet.saft.sample.aux;

import java.util.concurrent.Callable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import static javafx.scene.text.Font.font;

/**
 *
 * @author oliver.guenther
 */
public class MainPaneBuilder implements Callable<Pane> {

    @Override
    public Pane call() throws Exception {
        Label l = new Label("Die JavaFx Main Application");
        l.setFont(font(50));
        BorderPane p = new BorderPane(l);
        return p;
    }

}
