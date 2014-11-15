package sample.demo;

import sample.demo.aux.MainPanel;
import sample.demo.aux.RevenueReportSelectorPane;
import sample.intention.Ui;
import sample.intention.UiCore;

/**
 * Opening a JavaFX Pane as popup Dialog, blocking the hole application.
 *
 * @author oliver.guenther
 */
public class PopupJavaFx {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());

        // JavaFX Pane in Swing Dialog.
        Ui.exec(
                Ui.popupOkCancelFx((x) -> new RevenueReportSelectorPane())
                .onOk(v -> {
                    System.out.println(v);
                    return null;
                })
        );

    }

}
