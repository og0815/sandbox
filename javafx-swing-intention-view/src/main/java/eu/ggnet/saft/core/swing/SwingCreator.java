package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.CallableA1;
import eu.ggnet.saft.core.fx.FxSaft;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.io.File;
import java.util.concurrent.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import javax.swing.JPanel;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public class SwingCreator<T> extends AbstractCreator<T> {

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
    public <R extends Pane> SwingOk<R> popupOkCancelFx(CallableA1<T, R> builder) {
        return new SwingOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get();
            FxSaft.ensurePlatformIsRunning();
            final R pane = builder.call(parameter); // Call outside all ui threads assumed
            JFXPanel p = FxSaft.wrap(pane);
            return SwingSaft.dispatch(() -> {
                OkCancelDialog<JFXPanel> dialog = new OkCancelDialog<>(parent, p);
                dialog.setTitle(UiUtil.extractTitle(pane).orElse("Auswahldialog"));
                dialog.setModalityType(UiUtil.toSwing(modality).orElse(ModalityType.APPLICATION_MODAL));
                dialog.pack();
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
                return new OkCancelResult<>(pane, dialog.isOk());
            });
        }, parent, modality);
    }

    @Override
    public <R extends JPanel> SwingOk<R> popupOkCancel(CallableA1<T, R> builder) {
        return new SwingOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get(); // Call outside all ui threads assumed

            return SwingSaft.dispatch(() -> {
                R panel = builder.call(parameter);
                OkCancelDialog<R> dialog = new OkCancelDialog<>(parent, panel);
                dialog.setTitle(UiUtil.extractTitle(panel).orElse("Auswahldialog"));
                dialog.setModalityType(UiUtil.toSwing(modality).orElse(ModalityType.APPLICATION_MODAL));
                dialog.pack();
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
                return new OkCancelResult<>(dialog.getSubContainer(), dialog.isOk());
            });
        }, parent, modality);
    }

    @Override
    public <R extends JPanel> SwingOpenPanel<T> open(Class<R> clazz) {
        return new SwingOpenPanel<>(before.getCallable(), clazz, null, parent, modality);
    }

    @Override
    public <R extends JPanel> SwingOpenPanel<T> open(Class<R> clazz, Object id) {
        return new SwingOpenPanel<>(before.getCallable(), clazz, id, parent, modality);
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
