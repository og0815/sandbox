package eu.ggnet.saft.sample;

import eu.ggnet.saft.core.Ui;
import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.sample.aux.MainPaneBuilder;
import eu.ggnet.saft.sample.aux.RevenueReportSelectorPane;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Opening a JavaFX Pane as popup Dialog, blocking the hole application.
 *
 * @author oliver.guenther
 */
public class JavaFxPopupJavaFx extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UiCore.startJavaFx(primaryStage, new MainPaneBuilder());

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