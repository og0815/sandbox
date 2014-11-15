package sample.intention.swing;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author oliver.guenther
 */
public class FxSaft {

    private static JFXPanel startHelper = null;

    private static boolean started = false;

    public static void ensurePlatformIsRunning() {
        if (!started) {
            startHelper = new JFXPanel();
            started = true;
        }
    }

    public static JFXPanel wrap(Pane p) throws InterruptedException {
        final JFXPanel fxp = jfxPanel();
        final CountDownLatch cdl = new CountDownLatch(1);
        if (Platform.isFxApplicationThread()) {
            fxp.setScene(new Scene(p));
            cdl.countDown();
        } else {
            Platform.runLater(() -> {
                fxp.setScene(new Scene(p));
                cdl.countDown();
            });
        }
        cdl.await();
        return fxp;
    }

    private static JFXPanel jfxPanel() {
        JFXPanel result;
        if (startHelper != null) {
            result = startHelper;
            startHelper = null;
        } else {
            result = new JFXPanel();
        }
        return result;
    }

}
