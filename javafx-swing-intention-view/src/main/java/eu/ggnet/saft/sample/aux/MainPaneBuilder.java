package eu.ggnet.saft.sample.aux;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.util.concurrent.Callable;

import static javafx.scene.text.Font.font;

/**
 *
 * @author oliver.guenther
 */
public class MainPaneBuilder implements Callable<Parent> {

    @Override
    public Parent call() throws Exception {
        Label l = new Label("Die JavaFx Main Application");
        l.setFont(font(50));
        BorderPane p = new BorderPane(l);
        return p;
    }

}
