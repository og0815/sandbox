package sample.intention;

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
        if (!ok) return;
        listener.listen(payload);
    }

    public void onOkAsync(Listener<T> listener) {
        if (!ok) return;
        final ExecutorService es = Executors.newFixedThreadPool(2);
        final Future<?> future = es.submit(() -> listener.listen(payload));
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

//    public <X> void onOkAsync(Callback<T, X> callback) {
//        callback.call(payload);
//        // Make sure we get all the exceptions.
//    }
}
