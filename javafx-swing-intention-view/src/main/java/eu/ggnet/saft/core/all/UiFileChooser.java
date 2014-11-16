package eu.ggnet.saft.core.all;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.*;

import eu.ggnet.saft.core.fx.FxSaft;
import eu.ggnet.saft.core.swing.SwingOk;

/**
 * A FileChooser, even if it's FX based, it is totally exceptalbe in a Swing environment.
 *
 * @author oliver.guenther
 */
public class UiFileChooser {

    public SwingOk<File> open() {
        return open(null);
    }

    public SwingOk<File> open(String title) {
        return new SwingOk<>(() -> {
            FxSaft.ensurePlatformIsRunning();

            FutureTask<File> fileChosserTask = new FutureTask<>(() -> {
                FileChooser fileChooser = new FileChooser();
                if (title == null) fileChooser.setTitle("Open File");
                else fileChooser.setTitle(title);
                // TODO: Reuse a Stage in JavaFx environment.
                return fileChooser.showOpenDialog(new Stage());
            });

            Platform.runLater(fileChosserTask);
            File file = fileChosserTask.get();
            return new OkCancelResult<>(file, file != null);
        });
    }
}
