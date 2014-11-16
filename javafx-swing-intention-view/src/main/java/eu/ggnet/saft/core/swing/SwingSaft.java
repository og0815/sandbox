package eu.ggnet.saft.core.swing;

import javafx.embed.swing.SwingNode;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

/**
 *
 * @author oliver.guenther
 */
public class SwingSaft {

    public static <T> T dispatch(Callable<T> callable) throws ExecutionException, InterruptedException, InvocationTargetException {
        FutureTask<T> task = new FutureTask(callable);
        if (EventQueue.isDispatchThread()) task.run();
        else EventQueue.invokeAndWait(task);
        return task.get();
    }

    public static SwingNode wrap(final JPanel p) throws ExecutionException, InterruptedException, InvocationTargetException {
        return dispatch(() -> {
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(p);
            return swingNode;
        });
    }

    /**
     * Special form of {@link SwingUtilities#getWindowAncestor(java.awt.Component) }, as it also verifies if the
     * supplied parameter is of type Window and if true returns it.
     *
     * @param c the component
     * @return null or a window.
     */
    public static Window windowAncestor(Component c) {
        if (c instanceof Window) return (Window) c;
        return SwingUtilities.getWindowAncestor(c);
    }

}
