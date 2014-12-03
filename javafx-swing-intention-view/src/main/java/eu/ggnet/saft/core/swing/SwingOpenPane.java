package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.fx.FxSaft;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import java.awt.Window;
import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <T>
 * @param <R>
 */
public class SwingOpenPane<T, R extends Pane> extends AbstractSwingOpen<T, R> {

    public SwingOpenPane(Callable<T> before, Window parent, Modality modality, String id, Class<R> paneClass) {
        super(before, parent, modality, id, paneClass);
    }

    @Override
    protected T2<R> build(T parameter, Class<R> paneClass) throws Exception {
        FxSaft.ensurePlatformIsRunning();
        final R pane = FxSaft.construct(paneClass, parameter);
        JFXPanel p = FxSaft.wrap(pane);
        return new T2<>(p, pane);
    }

}
