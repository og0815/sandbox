package sample.demo;

import sample.demo.aux.DocumentAdressUpdateView;
import sample.intention.Ui2;

/**
 * Opens a Swing Panel as Popup Dialog blocking the hole application and on Ok calculates an async result.
 *
 * @author oliver.guenther
 */
public class PopupSwingOnOkAsyncCall {

    public static void main(String[] args) {
        Global.init();
        String adress = "Hans Mustermann\nMusterstrasse 22\n12345 Musterhausen";
        // Now lets think about some Backroungrunning solution.
        Ui2.popupOkCancel(() -> new DocumentAdressUpdateView(1, adress, true)) // Needs to be in the UI Thread, should block all
                .showAndWait() // Halve halv
                .onOkCall(v -> {
                    int count = 0;
                    for (int i = 0; i < 20; i++) {
                        Thread.sleep(250);
                        System.out.println("In Background (" + i + "), processing " + v.getAddress());
                        count++;
                    }
                    return count;
                })
                .done(v -> System.out.println("Done: " + v));

    }

}
