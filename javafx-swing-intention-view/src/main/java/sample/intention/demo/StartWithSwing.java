package sample.intention.demo;

import sample.intention.*;

/**
 *
 * @author oliver.guenther
 */
public class StartWithSwing {

    public static void main(String[] args) {
        // Somethere very far away
        UiCore.startSwing(() -> new MainPanel());
        //
        String adress = "Hans Mustermann\nMusterstrasse 22\n12345 Musterhausen";
        // Swing Panel in Swing Dialog
        Ui.popupOkCancel(() -> new DocumentAdressUpdateView(1, adress, true)) // Needs to be in the UI Thread, should block all
                .showAndWait() // Halve halv
                .onOk(v -> System.out.println(v.getAddress()));

        // JavaFX Pane in Swing Dialog.
        Ui.popupOkCancel(() -> new RevenueReportSelectorPane())
                .showAndWait() // Halve halv
                .onOk(v -> System.out.println(v));

        // Now lets think about some Backroungrunning solution.
    }

}
