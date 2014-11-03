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
public class SwingPopupOneArg<T> {

    private Callable<T> callable;

    public SwingPopupOneArg(Callable<T> callable) {
        this.callable = callable;
    }

    public SwingPopupOneArg() {
    }

    public <R extends JPanel> SwingOk<R> popupOkCancel(PopupBlenderOneArgSwing<T, R> builder) {
        return new SwingOk<>(new Callable<OkCancelResult<R>>() {

            @Override
            public OkCancelResult<R> call() throws Exception {
                final T parameter = (callable == null ? null : callable.call());
                //  if (EventQueue.isDispatchThread()) Throw some error ?!
                FutureTask<OkCancelResult<R>> futureTask = new FutureTask<>(() -> {
                    OkCancelDialog<R> dialog = new OkCancelDialog<>(UiCore.mainPanel, APPLICATION_MODAL, "TODO", builder.build(parameter));
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

}
