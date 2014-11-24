package eu.ggnet.saft.sample;

import eu.ggnet.saft.core.Ui;
import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.sample.aux.*;
import javax.swing.JButton;

/**
 * Opening a JavaFX Pane as popup Dialog, blocking the hole application.
 *
 * @author oliver.guenther
 */
public class SwingOpenSwing {

    public static void main(String[] args) {
        UiCore.startSwing(() -> {
            MainPanelAddButtons main = new MainPanelAddButtons();
            JButton b = new JButton("Open Once as Application");
            b.addActionListener((e) -> Ui.exec(Ui.open(PanelOnceDialog.class)));
            main.getButtonPanel().add(b);

            b = new JButton("Open Multiple, id : 1");
            b.addActionListener((e) -> Ui.exec(Ui.open(UnitViewer.class, "1")));
            main.getButtonPanel().add(b);

            b = new JButton("Open Multiple, id : 2");
            b.addActionListener((e) -> Ui.exec(Ui.open(UnitViewer.class, "2")));
            main.getButtonPanel().add(b);

            b = new JButton("Open Multiple, id : 3 , with precall");
            b.addActionListener((e) -> Ui.exec(
                    Ui
                    .call(() -> "Das ist der Riesentext für Unit 3")
                    .open(UnitViewer.class, "3")
            ));
            main.getButtonPanel().add(b);

            b = new JButton("Open Multiple, id : 4 , with precall and construct");
            b.addActionListener((e) -> Ui.exec(
                    Ui
                    .call(() -> "Das ist der Riesentext für Unit 4 mit eigenem Construct.")
                    .open(UnitViewer.class, "4")
                    .construct((v) -> {
                        UnitViewer uv = new UnitViewer();
                        uv.accept(v);
                        return uv;
                    })
            ));
            main.getButtonPanel().add(b);

            return main;
        });

        // Ui.open(UnitView.class,"12345").exec();
        // ui.open(UnitView.class,"12345").prepare((UnitView v) -> v.setValue("lannnger String")).exec();
        // JavaFX Pane in Swing Dialog.
    }

}
