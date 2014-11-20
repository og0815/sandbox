package sample.javafx;

import java.awt.SplashScreen;
import java.net.URL;
import java.net.URLClassLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Starting Hello JavaFX and Maven demonstration application");
        log.info("HostServices: " + getHostServices());
        log.info("Codebase: " + getHostServices().getCodeBase());
        log.info("DocumentBase: " + getHostServices().getDocumentBase());
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        log.info("System Classloader: " + cl);

        URL[] urls = ((URLClassLoader) cl).getURLs();
        for (int i = 0; i < urls.length; i++) {
            log.info("System Classloader URL[{}]: {}", i, urls[i]);
        }

        String fxmlFile = "/fxml/hello.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
        HelloController c = loader.getController();

        HelloPojo huhu = new HelloPojo("Hu", "Ho");
        log.debug("FirstNamePropertie:{}", huhu.firstNameProperty());
        c.setHelloPojo(huhu);

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 400, 200);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Hello JavaFX and Maven");
        stage.setScene(scene);
        SplashScreen s = SplashScreen.getSplashScreen();
        stage.show();
        if (s != null) {
            s.close();
        }
    }
}
