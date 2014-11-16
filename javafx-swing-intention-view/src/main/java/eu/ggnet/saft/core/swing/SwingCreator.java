package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.CallableA1;
import eu.ggnet.saft.core.fx.FxSaft;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import javax.swing.JPanel;

import java.awt.Dialog;
import java.awt.Window;
import java.io.File;
import java.util.concurrent.*;

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
            return callable.call();
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
                dialog.setModalityType(toSwing(modality));
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
                dialog.setModalityType(toSwing(modality));
                dialog.pack();
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
                return new OkCancelResult<>(dialog.getSubContainer(), dialog.isOk());
            });
        }, parent, modality);
    }

    @Override
    public SwingOk<File> open() {
        return open(null);
    }

    @Override
    public SwingOk<File> open(String title) {
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

    private Dialog.ModalityType toSwing(Modality m) {
        if (m == null) return Dialog.ModalityType.APPLICATION_MODAL;
        switch (m) {
            case APPLICATION_MODAL:
                return Dialog.ModalityType.APPLICATION_MODAL;
            case WINDOW_MODAL:
                return Dialog.ModalityType.DOCUMENT_MODAL;
            case NONE:
                return Dialog.ModalityType.MODELESS;
        }
        return Dialog.ModalityType.APPLICATION_MODAL;
    }

}
