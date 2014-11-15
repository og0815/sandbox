package sample.intention;

import javafx.application.Platform;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 */
// TODO: Title handler
public class UiCore {

    public static JFrame mainPanel = null;

    /**
     * Starts the Core in Swing mode, may only be called once.
     *
     * @param <T>
     * @param builder
     */
    public static <T extends Component> void startSwing(final Callable<T> builder) {
        if (mainPanel != null) {
            throw new RuntimeException("Ui already initialized and running in Swing mode");
        }
        if (EventQueue.isDispatchThread()) {
            runSwing(builder);
        } else {
            try {
                EventQueue.invokeAndWait(() -> runSwing(builder));
            } catch (InterruptedException | InvocationTargetException ex) {
                // TODO: Handlw
            }
        }
        Platform.setImplicitExit(false);
    }

    private static <T extends Component> void runSwing(Callable<T> builder) {
        try {
            mainPanel = new JFrame();
            mainPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainPanel.getContentPane().add(builder.call());
            mainPanel.pack();
            mainPanel.setLocationByPlatform(true);
            mainPanel.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            // TODO: Our Exception handler
        }
    }

    public static void startJavaFx() {

    }

    public static void handleException(Exception e) {
        e.printStackTrace();
    }

}
