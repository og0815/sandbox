package eu.ggnet.saft.sample;

import eu.ggnet.saft.core.SwingFx;
import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.sample.aux.MainPanel;
import eu.ggnet.saft.sample.aux.RevenueReportSelectorPane;

/**
 * Opening a JavaFX Pane as popup Dialog, blocking the hole application.
 *
 * @author oliver.guenther
 */
public class SwingPopupJavaFx {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());

        // JavaFX Pane in Swing Dialog.
        SwingFx.exec(SwingFx.choiceFx(RevenueReportSelectorPane.class)
                .onOk(v -> {
                    System.out.println(v);
                    return null;
                })
        );

    }

}
