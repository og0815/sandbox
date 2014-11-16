package eu.ggnet.saft.core.swingfx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.concurrent.*;

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

    /**
     * Dispatches the Callable to the Platform Ui Thread.
     *
     * @param <T> Return type of callable
     * @param callable the callable to dispatch
     * @return the result of the callable
     * @throws InterruptedException see {@link CountDownLatch#await() }
     * @throws ExecutionException see {@link FutureTask#get() }
     */
    public static <T> T dispatch(Callable<T> callable) throws InterruptedException, ExecutionException {
        FutureTask<T> futureTask = new FutureTask<>(callable);
        final CountDownLatch cdl = new CountDownLatch(1);
        if (Platform.isFxApplicationThread()) {
            futureTask.run();
            cdl.countDown();
        } else {
            Platform.runLater(() -> {
                futureTask.run();
                cdl.countDown();
            });
        }
        cdl.await();
        return futureTask.get();
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
