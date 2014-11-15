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
        return open(null);
    }

    public UiOk<File> open(String title) {
        return new UiOk<>(() -> {
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
