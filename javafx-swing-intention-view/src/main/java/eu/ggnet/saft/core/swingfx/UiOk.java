/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.saft.core.swingfx;

import eu.ggnet.saft.core.all.OkCancelResult;
import eu.ggnet.saft.core.aux.CallableA1;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <V>
 */
public interface UiOk<V> extends Callable<OkCancelResult<V>> {

    <R> UiCreator<R> onOk(CallableA1<V, R> function);

}
