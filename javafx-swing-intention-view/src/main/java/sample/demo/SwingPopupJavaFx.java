package sample.demo;

import eu.ggnet.saft.core.Ui;
import eu.ggnet.saft.core.UiCore;
import sample.demo.aux.MainPanel;
import sample.demo.aux.RevenueReportSelectorPane;

/**
 * Opening a JavaFX Pane as popup Dialog, blocking the hole application.
 *
 * @author oliver.guenther
 */
public class SwingPopupJavaFx {

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
