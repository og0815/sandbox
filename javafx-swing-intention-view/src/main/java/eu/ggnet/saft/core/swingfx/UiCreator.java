/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.saft.core.swingfx;

import eu.ggnet.saft.core.aux.CallableA1;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public interface UiCreator<T> extends Callable<T> {

    <R extends JPanel> UiOk<R> popupOkCancel(CallableA1<T, R> builder);

    <R extends Pane> UiOk<R> popupOkCancelFx(CallableA1<T, R> builder);

    public <D> UiCreator<D> call(Callable<D> callable);

    public Callable<Void> osOpen();

}
