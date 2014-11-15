package sample.intention.swing;

import sample.intention.structure.CallableA1;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 */
public class SwingOk<V> implements Callable<OkCancelResult<V>> {

    private final Callable<OkCancelResult<V>> before;

    public SwingOk(Callable<OkCancelResult<V>> before) {
        this.before = before;
    }

    public <R> UiCreator<R> onOk(CallableA1<V, R> function) {
        return new UiCreator<>(() -> {
            OkCancelResult<V> result = before.call();
            if (result.ok) {
                return function.call(result.value);
            }
            return null;
        });
    }

    @Override
    public OkCancelResult<V> call() throws Exception {
        return (before == null ? null : before.call());
    }

}
