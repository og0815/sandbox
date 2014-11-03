package sample.intention;

import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import sample.old.OkCancelDialog;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
// First Step Swing only.
public class PaneRunner<T extends Pane> {

    private final PaneBuilder<T> builder;

    private final OkResult<T> result = new OkResult<>();

    PaneRunner(PaneBuilder<T> builder) {
        this.builder = builder;
    }

    public OkResult<T> showAndWait() {
        try {
            if (EventQueue.isDispatchThread()) runInternal();
            else
                EventQueue.invokeAndWait(() -> runInternal());
        } catch (InterruptedException | InvocationTargetException ex) {
            // TODO find a solution.
        }
        // TODO validate, that result is ok.
        return result;
    }

    private void runInternal() {
        // Handle a selective parent
        final JFXPanel jfxPanel = new JFXPanel();
        final T payload = builder.build();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                jfxPanel.setScene(new Scene(payload));
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException ex) {
            // TODO: HAndel me
        }

        OkCancelDialog<JFXPanel> dialog = new OkCancelDialog<>(UiCore.mainPanel, APPLICATION_MODAL, "TODO", jfxPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(UiCore.mainPanel);
        dialog.setVisible(true);
        result.ok = dialog.isOk();
        result.payload = payload;
    }

}
