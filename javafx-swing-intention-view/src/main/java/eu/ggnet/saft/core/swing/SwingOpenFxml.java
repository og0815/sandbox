package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.aux.FxController;
import eu.ggnet.saft.core.fx.FxSaft;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;

import java.awt.Window;
import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <T>
 * @param <R>
 */
public class SwingOpenFxml<T, R extends FxController> extends AbstractSwingOpen<T, R> {

    public SwingOpenFxml(Callable<T> before, Window parent, Modality modality, String id, Class<R> controllerClass) {
        super(before, parent, modality, id, controllerClass);
    }

    @Override
    protected T2<R> build(T parameter, Class<R> controllerClass) throws Exception {
        FxSaft.ensurePlatformIsRunning();
        FXMLLoader loader = FxSaft.constructFxml(controllerClass, parameter);
        JFXPanel p = FxSaft.wrap(loader.getRoot());
        return new T2<>(p, loader.getController());
    }

}
