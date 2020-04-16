package playground;

import java.awt.EventQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * JavaFX App
 */
public class JavaFxApplication extends Application {

    private WeakReference<Stage> wfx;

    private WeakReference<JFrame> wjf;

    private ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void start(Stage stage) {
        var fxlx = new Label("fx-x:");
        var fxxfield = new TextField();
        var fxly = new Label("fx-y:");
        var fxyfield = new TextField();

        fxxfield.setOnAction(e -> updateWfxLocattion(fxxfield, fxyfield));
        fxyfield.setOnAction(e -> updateWfxLocattion(fxxfield, fxyfield));

        var fxb = new Button("Show/Focus Fx");

        fxb.setOnAction(e -> {
            if (wfx == null || wfx.get() == null) {
                Stage fx = new Stage();
                var javaVersion = SystemInfo.javaVersion();
                var javafxVersion = SystemInfo.javafxVersion();
                var label = new Label("JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
                fx.setScene(new Scene(label));
                fx.show();
                wfx = new WeakReference<>(fx);
            } else if (wfx.get().isShowing()) {
                wfx.get().setIconified(false);
                wfx.get().toFront();
                wfx.get().requestFocus();
            } else {
                wfx.get().show();
            }
        });

        var slx = new Label("swing-x:");
        var sxfield = new TextField();
        var sly = new Label("swing-y:");
        var syfield = new TextField();

        sxfield.setOnAction(e -> updateWjfLocattion(sxfield, syfield));
        syfield.setOnAction(e -> updateWjfLocattion(sxfield, syfield));

        var sxb = new Button("Show/Focus Swing");

        sxb.setOnAction(e -> {
            if (wjf == null || wjf.get() == null || !wjf.get().isShowing()) {
                JFrame f = new JFrame("Demo Swing");
                f.getContentPane().add(new JLabel("Das ist jetzt eine tolles Swing Fenster"));
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.pack();
                wjf = new WeakReference<>(f);
                EventQueue.invokeLater(() -> {
                    f.setVisible(true);
                });
            } else {
                if (wjf.get().getExtendedState() == JFrame.ICONIFIED) {
                    wjf.get().setExtendedState(JFrame.NORMAL);
                }
                wjf.get().toFront();
                wjf.get().requestFocus();
            }
        });

        var garbageCollectButton = new Button("Enforce GC");
        garbageCollectButton.setOnAction(e -> System.gc());

        ses.scheduleAtFixedRate(() -> {
            try {
                if (wfx == null) {
                    System.out.print("wfx = null | ");
                } else if (wfx.get() == null) {
                    System.out.print("wfx.get() = null | ");
                } else {
                    System.out.print("wfx.get().isShowing() = " + wfx.get().isShowing() + " | ");
                }
                if (wjf == null) {
                    System.out.println("wjf = null");
                } else if (wjf.get() == null) {
                    System.out.println("wjf.get() = null");
                } else {
                    System.out.println("wjf.get().isShowing() = " + wjf.get().isShowing());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);

        var scene = new Scene(new FlowPane(fxlx, fxxfield, fxly, fxyfield, fxb, garbageCollectButton, slx, sxfield, sly, syfield, sxb), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void updateWfxLocattion(TextField xf, TextField yf) {
        if (wfx == null || wfx.get() == null) {
            return;
        }
        try {
            wfx.get().setX(Double.parseDouble(xf.getText()));
            wfx.get().setY(Double.parseDouble(yf.getText()));
        } catch (Exception e) {
            System.out.println("No Value");
        }
    }

    private void updateWjfLocattion(TextField xf, TextField yf) {
        if (wjf == null || wjf.get() == null) {
            return;
        }
        try {
            wjf.get().setLocation(Integer.parseInt(xf.getText()), Integer.parseInt(yf.getText()));
        } catch (Exception e) {
            System.out.println("No Value");
        }
    }

    @Override
    public void stop() throws Exception {
        if (wfx != null && wfx.get() != null) {
            wfx.get().close();
        }
        if (wjf != null && wjf.get() != null) {
            wjf.get().setVisible(false);
            wjf.get().dispose();

        }
        ses.shutdown();
    }

}
