package eu.ggnet.saft.sample;

import eu.ggnet.saft.core.SwingFx;
import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.sample.aux.*;
import javax.swing.JButton;

/**
 * Opening a JavaFX Pane as popup Dialog, blocking the hole application.
 *
 * @author oliver.guenther
 */
public class OpenWithSwing {

    public static void main(String[] args) {
        UiCore.startSwing(() -> {
            MainPanelAddButtons main = new MainPanelAddButtons();

            JButton b = new JButton("Swing:Once as Application");
            b.addActionListener((e) -> SwingFx.exec(SwingFx.openJ(PanelOnceDialog.class.getName(), (t) -> new PanelOnceDialog())));
            main.getButtonPanel().add(b);

            b = new JButton("Swing:Multiple, id : 1");
            b.addActionListener((e) -> SwingFx.exec(SwingFx.openJ(UnitViewer.class + " 1", (t) -> new UnitViewer())));
            main.getButtonPanel().add(b);

            b = new JButton("Swing:Multiple, id : 2");
            b.addActionListener((e) -> SwingFx.exec(SwingFx.openJ(UnitViewer.class + " 2", (t) -> new UnitViewer())));
            main.getButtonPanel().add(b);

            b = new JButton("Swing:Multiple, id : 3 , with precall");
            b.addActionListener((e) -> SwingFx.exec(SwingFx
                    .call(() -> "Das ist der Riesentext für Unit 3")
                    .openSwing(UnitViewer.class + " 3", (t) -> new UnitViewer(t))
            ));
            main.getButtonPanel().add(b);

            b = new JButton("JavaFx:Once as Application");
            b.addActionListener((e) -> SwingFx.exec(SwingFx.open("MainPanelBuilder", (t) -> new MainPaneBuilder().call())));
            main.getButtonPanel().add(b);

            return main;
        });

        // SwingFx.openSwing(UnitView.class,"12345").exec();
        // ui.openSwing(UnitView.class,"12345").prepare((UnitView v) -> v.setValue("lannnger String")).exec();
        // JavaFX Pane in Swing Dialog.
    }

}
