/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.saft.core.all;

import eu.ggnet.saft.core.all.OnceCaller;
import eu.ggnet.saft.core.all.UiUtil;
import eu.ggnet.saft.core.swing.SwingCreator;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public abstract class AbstractCreator<T> implements UiCreator<T> {

    protected OnceCaller<T> before;

    public AbstractCreator(Callable<T> callable) {
        before = new OnceCaller<>(callable);
    }

    @Override
    public <D> SwingCreator<D> call(Callable<D> callable) {
        return new SwingCreator<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            return callable.call();
        });
    }

    @Override
    public Callable<Void> osOpen() {
        return UiUtil.osOpen(before);
    }

    /**
     * It this is the terminal instance, execute call or submit to an Executor, for exmple Ui.exec().
     *
     * @return result.
     * @throws Exception might throw exception.
     */
    @Override
    public T call() throws Exception {
        return before.get();
    }

}
