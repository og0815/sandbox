package sample.intention.swing;

import javax.swing.JPanel;
import sample.intention.UiCore;
import sample.intention.structure.*;
import sample.old.OkCancelDialog;

import java.awt.EventQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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

    public <R extends JPanel> void popup(CallableA1<T, R> builder) {

    }

    public <R extends JPanel> SwingOk<R> popupOkCancel(CallableA1<T, R> builder) {
        return new SwingOk<>(new Callable<OkCancelResult<R>>() {

            @Override
            public OkCancelResult<R> call() throws Exception {
                final T parameter = (callable == null ? null : callable.call());
                //  if (EventQueue.isDispatchThread()) Throw some error ?!
                FutureTask<OkCancelResult<R>> futureTask = new FutureTask<>(() -> {
                    OkCancelDialog<R> dialog = new OkCancelDialog<>(UiCore.mainPanel, APPLICATION_MODAL, "TODO", builder.call(parameter));
                    dialog.pack();
                    dialog.setLocationRelativeTo(UiCore.mainPanel);
                    dialog.setVisible(true);
                    return new OkCancelResult<>(dialog.getSubContainer(), dialog.isOk());
                });

                if (EventQueue.isDispatchThread()) futureTask.run();
                else EventQueue.invokeAndWait(futureTask);

                return futureTask.get();
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
