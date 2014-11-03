package sample.demo;

import sample.demo.aux.DocumentAdressUpdateView;
import sample.intention.*;
import sample.intention.structure.PopupBlenderOneArgSwing;

import java.util.concurrent.Callable;

/**
 * Opens a Swing Panel as Popup Dialog blocking the hole application and on Ok calculates an async result.
 *
 * @author oliver.guenther
 */
public class AsyncRunThenPopUp {

    public static void main(String[] args) {
        Global.init();
        String adress = "Hans Mustermann\nMusterstrasse 22\n12345 Musterhausen";
        // Backround as fist option.
//        Ui
//                .<String, DocumentAdressUpdateView>call(() -> { // I'm lazy, I'm not implementing some Run with Exception solution
//                    int c = 0;
//                    for (int i = 0; i < 20; i++) {
//                        Thread.sleep(500);
//                        System.out.println("In Background (" + i + ")");
//                        c = i;
//                    }
//                    return adress + " " + c;
//                })
//                .popupOkCancel(new PopupBlenderOneArgSwing<String, DocumentAdressUpdateView>() {
//
//                    @Override
//                    public DocumentAdressUpdateView build(String parameter) {
//                        return new DocumentAdressUpdateView(1, parameter, true);
//                    }
//                })
//                .onOk(new Listener<DocumentAdressUpdateView>() {
//
//                    @Override
//                    public void listen(DocumentAdressUpdateView t) {
//                        System.out.println(t);
//                    }
//                });

        Ui.exec(
                Ui
                .call(() -> {
                    System.out.print("Doing 2 sec pre work ... ");
                    Thread.sleep(2000);
                    System.out.println("done");
                    return "Hallo";
                })
                .popupOkCancel(p -> new DocumentAdressUpdateView(1, p, true))
                .onOk((t) -> {
                    System.out.print("Doing 2 sec post work ... ");
                    Thread.sleep(2000);
                    System.out.println("done");
                    return t.getAddress().length();
                })
        );

    }

    public static void longer() {
        Ui.exec(
                Ui.call(new Callable<String>() {

                    @Override
                    public String call() throws Exception {
                        return "Hallo";
                    }

                })
                .popupOkCancel(new PopupBlenderOneArgSwing<String, DocumentAdressUpdateView>() {

                    @Override
                    public DocumentAdressUpdateView build(String parameter) {
                        return new DocumentAdressUpdateView(1, parameter, true);
                    }
                })
                .onOk(new CallIt<DocumentAdressUpdateView, Integer>() {

                    @Override
                    public Integer call(DocumentAdressUpdateView t) throws Exception {
                        return 1;
                    }
                })
        );

    }

}
