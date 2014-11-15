package sample.intention;

import javafx.application.Platform;
import javax.swing.JFrame;
import sample.intention.swing.SwingSaft;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

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

        Platform.setImplicitExit(false);

        try {
            SwingSaft.dispatch(() -> {
                mainPanel = new JFrame();
                mainPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainPanel.getContentPane().add(builder.call());
                mainPanel.pack();
                mainPanel.setLocationByPlatform(true);
                mainPanel.setVisible(true);
                mainPanel.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        Platform.exit();
                    }

                });
                return mainPanel;
            });
        } catch (InterruptedException | InvocationTargetException | ExecutionException ex) {
            // TODO: Handlw
        }
    }

    public static void startJavaFx() {

    }

}
