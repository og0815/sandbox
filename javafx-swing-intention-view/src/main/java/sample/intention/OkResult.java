package sample.intention;

import java.awt.EventQueue;
import java.util.concurrent.*;

/**
 *
 * @author oliver.guenther
 */
public class OkResult<T> {

    T payload;

    boolean ok;

    // Not run in the Ui Thread.... Good or Bad?
    public void onOk(Listener<T> listener) {
        if (!ok) {
            return;
        }
        listener.listen(payload);
    }

    public void onOkRun(RunIt<T> run) {
        if (!ok) {
            return;
        }
        final ExecutorService es = Executors.newFixedThreadPool(2);
        final Future<?> future = es.submit(() -> {
            run.run(payload);
            return null;
        });
        // Catch Embedded Exceptions
        es.execute(() -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); // Todo Use ui Util
            } finally {
                es.shutdown();
            }
        });
    }

    public <V> Done<V> onOkCall(CallIt<T, V> callIt) {
        final Done<V> done = new Done<V>();
        if (!ok) {
            return done;
        }
        final ExecutorService es = Executors.newFixedThreadPool(2);
        final Future<V> future = es.submit(() -> {
            return callIt.call(payload);
        });

        // Catch Embedded Exceptions
        es.execute(() -> {
            try {
                final V result = future.get(); // Waits and might throw exception.
                EventQueue.invokeLater(() -> done.executeDone(result));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); // Todo Use ui Util
            } finally {
                es.shutdown();
            }
        });
        return done;
    }

}
