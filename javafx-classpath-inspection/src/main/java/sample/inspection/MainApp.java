package sample.inspection;

import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        List<String> all = ManifestClasspathScanner.find();
        all.forEach(x -> System.out.println(x));
        System.out.println("------");

        all.stream().filter(x -> x.startsWith("dw")).map(x -> x.substring(0, x.length() - 4)).forEach(x -> System.out.println(x));

        //        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        //
        //        Scene scene = new Scene(root);
        //        scene.getStylesheets().add("/styles/Styles.css");
        //
        //        stage.setTitle("JavaFX and Maven");
        //        stage.setScene(scene);
        stage.show();
        stage.close();
        stop();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the application can not be launched through
     * deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
