package sample.demo;

import sample.demo.aux.DocumentAdressUpdateView;
import sample.intention.Ui;

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
        Ui.exec(
                Ui.popupOkCancel((t) -> new DocumentAdressUpdateView(1, adress, true)) // Needs to be in the UI Thread, should block all
                .onOk(v -> {
                    System.out.println(v.getAddress());
                    return null;
                }) // Hint: in the implementations, most of the time, we have some result. That the short form is possible.
        );
    }
}
