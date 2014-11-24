package eu.ggnet.saft.core.all;

import eu.ggnet.saft.core.aux.CallableA1;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;

import java.io.File;
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

    public UiOk<File> openFileChooser();

    public UiOk<File> openFileChooser(String title);
}
