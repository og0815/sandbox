package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.*;
import eu.ggnet.saft.core.fx.FxSaft;
import java.awt.Window;
import java.io.File;
import java.util.concurrent.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public class SwingCreator<T> extends AbstractCreator<T> {

    private final Logger L = LoggerFactory.getLogger(SwingCreator.class);

    private final Window parent;

    private final Modality modality;

    public SwingCreator(Callable<T> before, Window parent, Modality modality) {
        super(before);
        this.parent = parent;
        this.modality = modality;
    }

    public SwingCreator<?> modality(Modality modality) {
        return new SwingCreator<>(before.getCallable(), parent, modality);
    }

    @Override
    public <D> SwingCreator<D> call(Callable<D> callable) {
        return new SwingCreator<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            UiCore.backgroundActivityProperty().set(true);
            D r = callable.call();
            UiCore.backgroundActivityProperty().set(false);
            return r;
        }, parent, modality);
    }

    @Override
    public <R extends Pane> SwingOk<R> choiceFx(Class<R> paneClazz) {
        return new SwingOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get();
            FxSaft.ensurePlatformIsRunning();
            final R pane = FxSaft.construct(paneClazz, parameter);
            JFXPanel p = FxSaft.wrap(pane);
            return SwingSaft.wrapAndShow(parent, p, modality, pane);
        }, parent, modality);
    }

    @Override
    public <R extends JPanel> SwingOk<R> choiceSwing(Class<R> panelClazz) {
        return new SwingOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get(); // Call outside all ui threads assumed
            final R panel = SwingSaft.construct(panelClazz, parameter);
            return SwingSaft.wrapAndShow(parent, panel, modality, panel);
        }, parent, modality);
    }

    @Override
    public <R extends JPanel> SwingOpenPanel<T, R> openSwing(String key, CallableA1<T, R> builder) {
        return new SwingOpenPanel<>(before.getCallable(), parent, modality, key, builder);
    }

    public <R extends Pane> SwingOpenPane<T, R> openFx(String key, CallableA1<T, R> builder) {
        return new SwingOpenPane<>(before.getCallable(), parent, modality, key, builder);
    }

    @Override
    public SwingOk<File> openFileChooser() {
        return openFileChooser(null);
    }

    @Override
    public SwingOk<File> openFileChooser(String title) {
        return new SwingOk<>(() -> {
            FxSaft.ensurePlatformIsRunning();
            File file = FxSaft.dispatch(() -> {
                FileChooser fileChooser = new FileChooser();
                if (title == null) fileChooser.setTitle("Open File");
                else fileChooser.setTitle(title);
                return fileChooser.showOpenDialog(new Stage());
            });
            return new OkCancelResult<>(file, file != null);
        }, parent, modality);
    }

}
