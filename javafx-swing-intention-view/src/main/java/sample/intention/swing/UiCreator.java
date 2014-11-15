package sample.intention.swing;

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;
import sample.intention.UiCore;
import sample.intention.structure.*;
import sample.old.OkCancelDialog;

import java.awt.Desktop;
import java.io.File;
import java.util.concurrent.*;

import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public class UiCreator<T> implements Callable<T> {

    private final OnceCaller<T> before;

    public UiCreator(Callable<T> before) {
        this.before = new OnceCaller<>(before);
    }

    public UiCreator() {
        this(null);
    }

    public <D> UiCreator<D> call(Callable<D> callable) {
        return new UiCreator<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            return callable.call();
        });
    }

    public Callable<Void> osOpen() {
        return () -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            T beforeResult = before.get();
            if (beforeResult == null) return null; // Null result is also useless here.
            if (beforeResult instanceof File) {
                Desktop.getDesktop().open((File) beforeResult);
            } else {
                throw new IllegalArgumentException("No Os support for Object Type: " + beforeResult.getClass());
            }
            return null;
        };
    }

    public <R extends Pane> UiOk<R> popupOkCancelFx(CallableA1<T, R> builder) {
        return new UiOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get();
            FxSaft.ensurePlatformIsRunning();
            final R pane = builder.call(parameter); // Call outside all ui threads assumed
            JFXPanel p = FxSaft.wrap(pane);
            return SwingSaft.dispatch(() -> {
                OkCancelDialog<JFXPanel> dialog = new OkCancelDialog<>(UiCore.mainPanel, APPLICATION_MODAL, "TODO", p);
                dialog.pack();
                dialog.setLocationRelativeTo(UiCore.mainPanel);
                dialog.setVisible(true);
                return new OkCancelResult<>(pane, dialog.isOk());
            });
        });
    }

    public <R extends JPanel> UiOk<R> popupOkCancel(CallableA1<T, R> builder) {
        return new UiOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get(); // Call outside all ui threads assumed

            return SwingSaft.dispatch(() -> {
                OkCancelDialog<R> dialog = new OkCancelDialog<>(UiCore.mainPanel, APPLICATION_MODAL, "TODO", builder.call(parameter));
                dialog.pack();
                dialog.setLocationRelativeTo(UiCore.mainPanel);
                dialog.setVisible(true);
                return new OkCancelResult<>(dialog.getSubContainer(), dialog.isOk());
            });
        });
    }

    /**
     * It this is the terminal instance, execute call or submit to an Executor, for exmple Ui.exec().
     *
     * @return result.
     * @throws Exception might throw exception.
     */
    @Override
    public T call() throws Exception {
        return before.get();
    }

}
