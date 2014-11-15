package sample.intention;

import javafx.application.Platform;
import javax.swing.JFrame;
import sample.intention.swing.SwingSaft;
import sample.intention.swing.exception.ExceptionUtil;
import sample.intention.swing.exception.SwingExceptionDialog;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 *
 * @author oliver.guenther
 */
// TODO: Title handler
public class UiCore {

    public static JFrame mainPanel = null;

    // We need the raw type here as we cannot get different typs of cosumers in and out.
    @SuppressWarnings("unchecked")
    private static final Map<Class, Consumer> exceptionConsumer = new HashMap<>();

    private static Consumer<Throwable> finalConsumer = (b) -> {
        Runnable r = () -> {
            SwingExceptionDialog.show(mainPanel, "Systemfehler", ExceptionUtil.extractDeepestMessage(b),
                    ExceptionUtil.toMultilineStacktraceMessages(b), ExceptionUtil.toStackStrace(b));
        };

        if (EventQueue.isDispatchThread()) r.run();
        else {
            try {
                EventQueue.invokeAndWait(r);
            } catch (InterruptedException | InvocationTargetException e) {
                // This will never happen.
            }
        }

    };

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
            catchException(ex);
        }
    }

    public static void startJavaFx() {

    }

    /**
     * Registers an extra renderer for an Exception in any stacktrace. HINT: There is no order or hierachy in the
     * engine. So if you register duplicates or have more than one match in a StackTrace, no one knows what might
     * happen.
     *
     * @param <T> type of the Exception
     * @param clazz the class of the Exception
     * @param consumer the consumer to handle it.
     */
    public static <T> void registerExceptionConsumer(Class<T> clazz, Consumer<T> consumer) {
        exceptionConsumer.put(clazz, consumer);
    }

    /**
     * Allows to overwrite the default final consumer of all exceptions.
     *
     * @param <T> type of consumer
     * @param consumer the consumer
     */
    public static <T> void overwriteFinalExceptionConsumer(Consumer<Throwable> consumer) {
        if (consumer != null) finalConsumer = consumer;
    }

    static void catchException(Throwable b) {
        for (Class<?> clazz : exceptionConsumer.keySet()) {
            if (ExceptionUtil.containsInStacktrace(clazz, b)) {
                exceptionConsumer.get(clazz).accept(ExceptionUtil.extractFromStraktrace(clazz, b));
                return;
            }
        }
        finalConsumer.accept(b);
    }

}
