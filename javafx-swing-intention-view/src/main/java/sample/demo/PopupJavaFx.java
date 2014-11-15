package sample.demo;

/**
 * Opening a JavaFX Pane as popup Dialog, blocking the hole application.
 *
 * @author oliver.guenther
 */
public class PopupJavaFx {

    public static void main(String[] args) {
        Global.init();

        // JavaFX Pane in Swing Dialog.
        // Later
//        Ui2.popupOkCancel(() -> new RevenueReportSelectorPane())
//                .showAndWait() // Halve halv
//                .onOk(v -> System.out.println(v));
    }

}
