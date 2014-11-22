package eu.ggnet.saft.sample;

import eu.ggnet.saft.core.Ui;
import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.sample.aux.DocumentAdressUpdateView;
import eu.ggnet.saft.sample.aux.MainPanel;
import javax.swing.JDialog;

import java.awt.Dialog;
import java.awt.Label;

/**
 * Opens a Swing Panel as Popup Dialog blocking the hole application.
 *
 * Shows:
 * <ul>
 * <li>Swing JPanel in UI Chain</li>
 * <li>onOk optional Chain continue</li>
 * <li>@OnOk - try deleting the hole address field</li>
 * <li></li>
 * </ul>
 *
 * @author oliver.guenther
 */
public class SwingPopupSwingParentSwing {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());

        final JDialog d = new JDialog(UiCore.mainPanel, "ExtraDialog", Dialog.ModalityType.MODELESS);
        Label label = new Label("Ein extra Dialog");
        d.getContentPane().add(label);
        d.pack();
        d.setLocation(500, 500);
        d.setVisible(true);

        String adress = "Hans Mustermann\nMusterstrasse 22\n12345 Musterhausen";
        // Swing Panel in Swing Dialog
        Ui.exec(
                Ui
                .parent(label)
                .popupOkCancel((t) -> new DocumentAdressUpdateView(1, adress, true)) // Needs to be in the UI Thread, should block all
                .onOk(v -> {
                    System.out.println(v.getAddress());
                    return null;
                }) // Hint: in the implementations, most of the time, we have some result. Than the short form is possible.
        );
    }
}