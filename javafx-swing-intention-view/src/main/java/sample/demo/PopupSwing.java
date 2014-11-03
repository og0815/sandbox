package sample.demo;

import sample.intention.Ui2;
import sample.demo.aux.DocumentAdressUpdateView;

/**
 * Opens a Swing Panel as Popup Dialog blocking the hole application.
 *
 * @author oliver.guenther
 */
public class PopupSwing {

    public static void main(String[] args) {
        Global.init();

        String adress = "Hans Mustermann\nMusterstrasse 22\n12345 Musterhausen";
        // Swing Panel in Swing Dialog
        Ui2.popupOkCancel(() -> new DocumentAdressUpdateView(1, adress, true)) // Needs to be in the UI Thread, should block all
                .showAndWait() // Halve halv
                .onOk(v -> System.out.println(v.getAddress()));
    }
}
