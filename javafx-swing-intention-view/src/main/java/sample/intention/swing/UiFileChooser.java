package sample.intention.swing;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.*;

/**
 * A FileChooser.
 *
 * @author oliver.guenther
 */
public class UiFileChooser {

    public UiOk<File> open() {
        return new UiOk<>(() -> {
            // First the UI chooser
            FxSaft.ensurePlatformIsRunning();
            // TODO: Reuse the Stage in JavaFx environment.

            FutureTask<File> fileChosserTask = new FutureTask<>(() -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                return fileChooser.showOpenDialog(new Stage());
            });

            Platform.runLater(fileChosserTask);
            File file = fileChosserTask.get();
            return new OkCancelResult<>(file, file != null);
        });
    }
}
