package eu.ggnet.saft.core.all;

import eu.ggnet.saft.core.aux.CallableA1;
import eu.ggnet.saft.core.aux.FxController;
import eu.ggnet.saft.core.swing.*;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Interface to all Ui's.
 *
 * @author oliver.guenther
 * @param <T>
 */
public interface UiCreator<T> extends Callable<T> {

    <R extends JPanel> UiOk<R> choiceSwing(Class<R> panelClazz);

    <R extends Pane> UiOk<R> choiceFx(Class<R> paneClazz);

    <R extends FxController> UiOk<R> choiceFxml(Class<R> controllerClass);

    public <D> UiCreator<D> call(Callable<D> callable);

    public Callable<Void> osOpen();

    public <R extends JPanel> SwingOpenPanel<T, R> openSwing(String key, CallableA1<T, R> builder);

    public <R extends Pane> SwingOpenPane<T, R> openFx(String key, CallableA1<T, R> builder);

    public UiOk<File> openFileChooser();

    public UiOk<File> openFileChooser(String title);
}
