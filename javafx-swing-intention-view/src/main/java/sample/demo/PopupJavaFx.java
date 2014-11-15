package sample.demo;

import javafx.application.Platform;
import sample.demo.aux.RevenueReportSelectorPane;
import sample.intention.Ui;

/**
 * Opening a JavaFX Pane as popup Dialog, blocking the hole application.
 *
 * @author oliver.guenther
 */
public class PopupJavaFx {

    public static void main(String[] args) {
        Global.init();

        // JavaFX Pane in Swing Dialog.
        Ui.exec(
                Ui.popupOkCancelFx((x) -> new RevenueReportSelectorPane())
                .onOk(v -> {
                    System.out.println(v);
                    Platform.exit();
                    return null;
                })
        );

    }

}
