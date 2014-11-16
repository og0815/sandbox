package eu.ggnet.saft.core.fx;

import eu.ggnet.saft.core.all.UiOk;
import eu.ggnet.saft.core.all.UiCreator;
import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.CallableA1;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <V>
 */
public class FxOk<V> implements UiOk<V> {

    private final OnceCaller<OkCancelResult<V>> before;

    public FxOk(Callable<OkCancelResult<V>> before) {
        this.before = new OnceCaller<>(before);
    }

    @Override
    public <R> UiCreator<R> onOk(CallableA1<V, R> function) {
        return new FxCreator<>(UiUtil.onOk(function, before));
    }

    @Override
    public OkCancelResult<V> call() throws Exception {
        return before.get();
    }

}
