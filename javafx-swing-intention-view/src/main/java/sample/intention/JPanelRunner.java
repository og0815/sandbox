/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.intention;

import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JPanel;
import sample.intention.old.OkCancelDialog;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
// First Step Swing only.
public class JPanelRunner<T extends JPanel> {

    private final JPanelBuilder<T> builder;

    private final OkResult<T> result = new OkResult<>();

    JPanelRunner(JPanelBuilder<T> builder) {
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
        OkCancelDialog<T> dialog = new OkCancelDialog<>(UiCore.mainPanel, APPLICATION_MODAL, "TODO", builder.build());
        dialog.pack();
        dialog.setLocationRelativeTo(UiCore.mainPanel);
        dialog.setVisible(true);
        result.ok = dialog.isOk();
        result.payload = dialog.getSubContainer();
    }

}
