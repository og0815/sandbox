package eu.ggnet.saft.core.swingfx;

import java.awt.EventQueue;
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

}
