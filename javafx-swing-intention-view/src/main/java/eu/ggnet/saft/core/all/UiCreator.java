package eu.ggnet.saft.core.all;

import eu.ggnet.saft.core.aux.CallableA1;
import eu.ggnet.saft.core.swing.SwingOpenPane;
import eu.ggnet.saft.core.swing.SwingOpenPanel;
import java.io.File;
import java.util.concurrent.Callable;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;

/**
 * Interface to all Ui's.
 *
 * @author oliver.guenther
 * @param <T>
 */
public interface UiCreator<T> extends Callable<T> {

    // HINT: we need the CallableA1, because we want to have the type of the return value of the builder in the next stage (e.g. onOk())
    <R extends JPanel> UiOk<R> popupOkCancel(CallableA1<T, R> builder);

    <R extends Pane> UiOk<R> popupOkCancelFx(CallableA1<T, R> builder);

    public <D> UiCreator<D> call(Callable<D> callable);

    public Callable<Void> osOpen();

    public <R extends JPanel> SwingOpenPanel<T, R> openJ(String key, CallableA1<T, R> builder);

    public <R extends Pane> SwingOpenPane<T, R> open(String key, CallableA1<T, R> builder);

    public UiOk<File> openFileChooser();

    public UiOk<File> openFileChooser(String title);
}
