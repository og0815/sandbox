package sample.intention.swing;

import sample.intention.CallIt;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 */
public class SwingOk<V> {

    private Callable<OkCancelResult<V>> before;

    public SwingOk(Callable<OkCancelResult<V>> before) {
        this.before = before;
    }

    public <R> Callable<R> onOk(CallIt<V, R> function) {
        return () -> {
            OkCancelResult<V> result = before.call();
            if (result.ok) {
                return function.call(result.value);
            }
            return null;
        };
    }

}
