package eu.ggnet.saft.core.swingfx;

import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.CallableA1;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <V>
 */
public class SwingOk<V> implements UiOk<V> {

    private final OnceCaller<OkCancelResult<V>> before;

    public SwingOk(Callable<OkCancelResult<V>> before) {
        this.before = new OnceCaller<>(before);
    }

    @Override
    public <R> SwingCreator<R> onOk(CallableA1<V, R> function) {
        return new SwingCreator<>(UiUtil.onOk(function, before));
    }

    @Override
    public OkCancelResult<V> call() throws Exception {
        return before.get();
    }

}
