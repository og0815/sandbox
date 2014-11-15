package sample.demo;

import sample.demo.aux.DocumentAdressUpdateView;
import sample.demo.aux.MainPanel;
import sample.intention.*;
import sample.intention.structure.CallableA1;

import java.util.concurrent.Callable;

/**
 * Opens a Swing Panel as Popup Dialog blocking the hole application and on Ok calculates an async result.
 *
 * @author oliver.guenther
 */
public class AsyncRunThenPopUp {

    private static class HardWorker {

        public static <T> T work2s(String worktype, T t) throws InterruptedException {
            System.out.print("Doing 2 sec " + worktype + " work ... ");
            Thread.sleep(2000);
            System.out.println("done");
            return t;
        }

    }

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());
        Ui.exec(
                Ui
                .call(() -> HardWorker.work2s("per", "Eine leere Adresse"))
                .popupOkCancel(p -> new DocumentAdressUpdateView(1, p, true))
                .onOk((t) -> HardWorker.work2s("middle", t.getAddress()))
                .popupOkCancel(p -> new DocumentAdressUpdateView(1, p, true))
                .onOk((t) -> HardWorker.work2s("post", t.getAddress()))
        );
    }

    public static void longer() {
        // A JAva 7 View.
        Ui.exec(
                Ui.call(new Callable<String>() {

                    @Override
                    public String call() throws Exception {
                        return "Hallo";
                    }

                })
                .popupOkCancel(new CallableA1<String, DocumentAdressUpdateView>() {

                    @Override
                    public DocumentAdressUpdateView call(String parameter) {
                        return new DocumentAdressUpdateView(1, parameter, true);
                    }
                })
                .onOk(new CallableA1<DocumentAdressUpdateView, Integer>() {

                    @Override
                    public Integer call(DocumentAdressUpdateView t) throws Exception {
                        return 1;
                    }
                })
        );

    }

}
