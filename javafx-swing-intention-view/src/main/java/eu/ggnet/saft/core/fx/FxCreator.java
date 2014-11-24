package eu.ggnet.saft.core.fx;

import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.CallableA1;
import eu.ggnet.saft.core.swing.SwingOpenPanel;
import eu.ggnet.saft.core.swing.SwingSaft;
import java.io.File;
import java.util.concurrent.*;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import static javafx.stage.Modality.APPLICATION_MODAL;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public class FxCreator<T> extends AbstractCreator<T> {

    private final Window parent;

    private final Modality modality;

    private final Logger L = LoggerFactory.getLogger(FxCreator.class);

    public FxCreator(Callable<T> before, Window parent, Modality modality) {
        super(before);
        this.parent = parent;
        this.modality = modality;
    }

    public FxCreator<?> modality(Modality modality) {
        return new FxCreator<>(before.getCallable(), parent, modality);
    }

    @Override
    public <D> FxCreator<D> call(Callable<D> callable) {
        return new FxCreator<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            UiCore.backgroundActivityProperty().set(true);
            D r = callable.call();
            UiCore.backgroundActivityProperty().set(false);
            return r;
        }, parent, modality);
    }

    @Override
    public <R extends Pane> FxOk<R> popupOkCancelFx(CallableA1<T, R> builder) {
        return new FxOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get();
            final R pane = builder.call(parameter); // Call outside all ui threads assumed

            return FxSaft.dispatch(() -> {
                OkCancelStage<R> s = new OkCancelStage(UiUtil.extractTitle(pane).orElse("Auswahldialog"), pane);
                s.initOwner(parent);
                if (modality == null) s.initModality(APPLICATION_MODAL);
                else s.initModality(modality);
                L.warn("setLocationRelativeTo in JavaFx Mode not yet implemented");
                s.showAndWait();
                return new OkCancelResult<>(pane, s.isOk());
            });

        }, parent, modality);
    }

    @Override
    public <R extends JPanel> FxOk<R> popupOkCancel(CallableA1<T, R> builder) {
        return new FxOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final R pannel = builder.call(before.get()); // Call outside all ui threads assumed
            final SwingNode node = SwingSaft.wrap(pannel);

            return FxSaft.dispatch(() -> {
                OkCancelStage<SwingNode> s = new OkCancelStage(UiUtil.extractTitle(pannel).orElse("Auswahldialog"), node);
                s.initOwner(parent);
                if (modality == null) s.initModality(APPLICATION_MODAL);
                else s.initModality(modality);
                L.warn("setLocationRelativeTo in JavaFx Mode not yet implemented");
                s.showAndWait();
                return new OkCancelResult<>(pannel, s.isOk());
            });
        }, parent, modality);
    }

    @Override
    public FxOk<File> openFileChooser() {
        return openFileChooser(null);
    }

    @Override
    public FxOk<File> openFileChooser(String title) {
        return new FxOk<>(() -> {
            File file = FxSaft.dispatch(() -> {
                FileChooser fileChooser = new FileChooser();
                if (title == null) fileChooser.setTitle("Open File");
                else fileChooser.setTitle(title);
                return fileChooser.showOpenDialog(UiCore.mainStage);
            });
            return new OkCancelResult<>(file, file != null);
        }, parent, modality);
    }

    @Override
    public <R extends JPanel> SwingOpenPanel<T> open(Class<R> clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <R extends JPanel> SwingOpenPanel<T> open(Class<R> clazz, Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
