package sample.intention;

import javafx.application.Platform;
import javax.swing.JFrame;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

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
     * @param builder
     */
    public static void startSwing(final JPanelBuilder builder) {
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

    private static void runSwing(JPanelBuilder builder) {
        mainPanel = new JFrame();
        mainPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainPanel.getContentPane().add(builder.build());
        mainPanel.pack();
        mainPanel.setLocationByPlatform(true);
        mainPanel.setVisible(true);
    }

    public static void startJavaFx() {

    }

    public static void handleException(Exception e) {
        e.printStackTrace();
    }

}
