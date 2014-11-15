package sample.intention.swing;

import sample.intention.structure.CallableA1;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <V>
 */
public class UiOk<V> implements Callable<OkCancelResult<V>> {

    private final OnceCaller<OkCancelResult<V>> before;

    public UiOk(Callable<OkCancelResult<V>> before) {
        this.before = new OnceCaller<>(before);
    }

    public <R> UiCreator<R> onOk(CallableA1<V, R> function) {
        return new UiCreator<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            OkCancelResult<V> result = before.get();
            if (!result.ok) return null;  // Break Chain on demand
            return function.call(result.value);
        });
    }

    @Override
    public OkCancelResult<V> call() throws Exception {
        return before.get();
    }

}
