package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.CallableA1;
import javafx.stage.Modality;

import java.awt.Window;
import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <V>
 */
public class SwingOpen<V> {

    private final OnceCaller<OkCancelResult<V>> before;

    private final Window parent;

    private final Modality modality;

    public SwingOpen(Callable<OkCancelResult<V>> before, Window parent, Modality modality) {
        this.before = new OnceCaller<>(before);
        this.parent = parent;
        this.modality = modality;
    }

    public <R> SwingCreator<R> onOk(CallableA1<V, R> function) {
        return new SwingCreator<>(UiUtil.onOk(function, before), parent, modality);
    }

    public OkCancelResult<V> call() throws Exception {
        // Dismal here.

        return before.get();
    }

}
