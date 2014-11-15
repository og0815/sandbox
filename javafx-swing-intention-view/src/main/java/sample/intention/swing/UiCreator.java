package sample.intention.swing;

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;
import sample.intention.UiCore;
import sample.intention.structure.*;
import sample.old.OkCancelDialog;

import java.util.concurrent.*;

import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;

/**
 *
 * @author oliver.guenther
 */
public class UiCreator<T> implements Callable<T> {

    private Callable<T> callable;

    public UiCreator(Callable<T> callable) {
        this.callable = callable;
    }

    public UiCreator() {
    }

    public <T> UiCreator<T> call(Callable<T> callable) {
        return new UiCreator<>(callable);
    }

    public <R extends Pane> UiOk<R> popupOkCancelFx(CallableA1<T, R> builder) {
        return new UiOk<>(new Callable<OkCancelResult<R>>() {

            @Override
            public OkCancelResult<R> call() throws Exception {
                final T parameter = (callable == null ? null : callable.call());
                FxSaft.ensurePlatformIsRunning();

                final R pane = builder.call(parameter);

                JFXPanel p = FxSaft.wrap(pane);

                return SwingSaft.dispatch(() -> {
                    OkCancelDialog<JFXPanel> dialog = new OkCancelDialog<>(UiCore.mainPanel, APPLICATION_MODAL, "TODO", p);
                    dialog.pack();
                    dialog.setLocationRelativeTo(UiCore.mainPanel);
                    dialog.setVisible(true);
                    return new OkCancelResult<>(pane, dialog.isOk());
                });
            }
        });
    }

    public <R extends JPanel> UiOk<R> popupOkCancel(CallableA1<T, R> builder) {
        return new UiOk<>(new Callable<OkCancelResult<R>>() {

            @Override
            public OkCancelResult<R> call() throws Exception {
                final T parameter = (callable == null ? null : callable.call());

                return SwingSaft.dispatch(() -> {
                    OkCancelDialog<R> dialog = new OkCancelDialog<>(UiCore.mainPanel, APPLICATION_MODAL, "TODO", builder.call(parameter));
                    dialog.pack();
                    dialog.setLocationRelativeTo(UiCore.mainPanel);
                    dialog.setVisible(true);
                    return new OkCancelResult<>(dialog.getSubContainer(), dialog.isOk());
                });
            }
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
        return (callable == null ? null : callable.call());
    }

}
