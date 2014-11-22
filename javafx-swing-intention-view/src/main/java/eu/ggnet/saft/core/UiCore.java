package eu.ggnet.saft.core;

import eu.ggnet.saft.core.exception.ExceptionUtil;
import eu.ggnet.saft.core.exception.SwingExceptionDialog;
import eu.ggnet.saft.core.fx.FxSaft;
import eu.ggnet.saft.core.swing.SwingSaft;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.*;
import javafx.stage.Stage;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 *
 * @author oliver.guenther
 */
public class UiCore {

    public static JFrame mainPanel = null;

    public static Stage mainStage = null;

    /**
     * Holds a mapping of all Scenes in JFXPanels.
     */
    public static Map<Scene, JFXPanel> swingParentHelper = new WeakHashMap<>();

    private final static BooleanProperty backgroundActivity = new SimpleBooleanProperty();

    // We need the raw type here. Otherwise we cannot get different typs of cosumers in and out.
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

    public static BooleanProperty backgroundActivityProperty() {
        return backgroundActivity;
    }

    /**
     * interim Mode, Saft connects to a running environment.
     *
     * @param mainView
     */
    public static void continueSwing(JFrame mainView) {
        if (isRunning()) throw new IllegalStateException("UiCore is already initialised and running");
        Platform.setImplicitExit(false); // Need this, as we asume many javafx elements opening and closing.
        mainPanel = mainView;
        mainPanel.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                Platform.exit();
            }

        });
    }

    /**
     * Starts the Core in Swing mode, may only be called once.
     *
     * @param <T>
     * @param builder
     */
    public static <T extends Component> void startSwing(final Callable<T> builder) {
        if (isRunning()) throw new IllegalStateException("UiCore is already initialised and running");

        Platform.setImplicitExit(false); // Need this, as we asume many javafx elements opening and closing.

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

    /**
     * Starts the Ui in JavaFx variant.
     *
     * This also assumes two things:
     * <ul>
     * <li>The JavaFX Platfrom is already running (as a Stage already exists), most likely created through default
     * lifecycle of javaFx</li>
     * <li>This Stage will always be open or the final to be closed, so implicitExit is ok</li>
     * </ul>
     *
     * @param <T> type restriction.
     * @param primaryStage the primaryStage for the application, not yet visible.
     * @param builder the build for the main ui.
     */
    public static <T extends Parent> void startJavaFx(Stage primaryStage, final Callable<T> builder) {
        if (isRunning()) throw new IllegalStateException("UiCore is already initialised and running");
        mainStage = primaryStage;
        try {
            FxSaft.dispatch(() -> {
                T node = builder.call();
                mainStage.setScene(new Scene(node));
                mainStage.centerOnScreen();
                mainStage.sizeToScene();
                mainStage.show();
                return null;
            });
        } catch (ExecutionException | InterruptedException e) {
            catchException(e);
        }
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

    public static boolean isRunning() {
        return mainPanel != null || mainStage != null;
    }

    public static boolean isFx() {
        return (mainStage != null);
    }

    public static boolean isSwing() {
        return (mainPanel != null);
    }

}
