package de.ltux.slideshow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final List<String> FALLBACK_IMAGE_FOLDERS = List.of("C:/Users/oliver.guenther/Pictures/Backgrounds and Wallpapers", "/home/oliver.guenther/Pictures/slideshow");

    private ImageView front;

    private ImageView back;

    private ImageView override;

    private File imageFolder = null;

    private Optional<File> detectParameterImageFolder() {
        if (getParameters().getNamed().containsKey("images")) {
            File folder = new File(getParameters().getNamed().get("images"));
            if (folder.exists() && folder.isDirectory() && folder.listFiles((dir, name) -> name.matches(".*\\.(jpg|png|gif)$")).length > 0) {
                System.out.println("Using Parameter Image Folder: " + folder);
                return Optional.of(folder);
            } else {
                System.err.println("Paramter Inmage folder does not exist or contain images: " + folder);
                return Optional.empty();
            }
        }
        System.err.println("No Parameter -images=PathToImages given.");
        return Optional.empty();
    }

    private Optional<File> detectFallbackImageFolder() {
        for (String folderName : FALLBACK_IMAGE_FOLDERS) {
            File folder = new File(folderName);
            if (folder.exists() && folder.isDirectory() && folder.listFiles((dir, name) -> name.matches(".*\\.(jpg|png|gif)$")).length > 0) {
                System.out.println("Using Fallback Image Folder: " + folder);
                return Optional.of(folder);
            }
        }
        System.err.println("No usable fallback image folder found.");
        return Optional.empty();
    }

    @Override
    public void init() throws Exception {
        imageFolder = detectParameterImageFolder()
                .or(() -> detectFallbackImageFolder())
                .orElseThrow(() -> new RuntimeException("No usable image folder found. Use -images= to select path."));
    }

    @Override
    public void start(Stage primaryStage) {
        File[] imageFiles = imageFolder.listFiles((dir, name) -> name.matches(".*\\.(jpg|png|gif)$"));

        // Create the ImageView to display the images
        override = new ImageView();
        override.fitHeightProperty().bind(primaryStage.heightProperty());
        override.fitWidthProperty().bind(primaryStage.widthProperty());
        override.setPreserveRatio(true);
        override.setOpacity(0);

        try (InputStream is = this.getClass().getResourceAsStream("override.png")) {
            override.setImage(new Image(is));
        } catch (IOException ex) {
            System.err.println("Exception on Override load: " + ex.getLocalizedMessage());
        }

        front = new ImageView();
        front.fitHeightProperty().bind(primaryStage.heightProperty());
        front.fitWidthProperty().bind(primaryStage.widthProperty());
        front.setPreserveRatio(true);

        back = new ImageView();
        back.fitHeightProperty().bind(primaryStage.heightProperty());
        back.fitWidthProperty().bind(primaryStage.widthProperty());
        back.setPreserveRatio(true);

        // Start the slideshow
        startSlideshow(imageFiles);

        StackPane root = new StackPane(back, front, override);
        Scene scene = new Scene(root, 800, 600); // Adjust the scene size as needed

        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Slideshow JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        primaryStage.show();
    }

    private void startSlideshow(File[] imageFiles) {
        // Mind 3 bilder müssen es sein.
        /*
        Vorbedingung: front und back haben RESQmatic bilder (oder irgendwas). Zeiten: fadetime. displaytime
        n = 0
        tl1
         tl1-1. fadeout front mit Fadetime
         on finish:
           - lade bild-n front
           - setzte front auf sichtbar opachity = 1
           - n = n + 1
           - if n = lenght n = 0
           - lade bild-n back        
        pausetransition mit displaytime
         */

        int FADETIME = 2;
        int DISPLAYTIME = 3;

        front.setImage(new Image(imageFiles[0].toURI().toString()));
        front.setOpacity(1.0);
        back.setImage(new Image(imageFiles[1].toURI().toString()));

        final Duration SEC_2 = Duration.millis(2000);
        final Duration SEC_3 = Duration.millis(3000);

        PauseTransition pauseAfter = new PauseTransition(Duration.seconds(15));
        FadeTransition ftin = new FadeTransition(SEC_3);
        ftin.setFromValue(0);
        ftin.setToValue(1);
        PauseTransition pauseIn = new PauseTransition(Duration.seconds(5));
        FadeTransition ftout = new FadeTransition(SEC_3);
        ftout.setFromValue(1);
        ftout.setToValue(0);

        SequentialTransition sequenceOverride = new SequentialTransition();
        sequenceOverride.setNode(override);
        sequenceOverride.getChildren().addAll(ftin, pauseIn, ftout, pauseAfter);
        sequenceOverride.setCycleCount(Integer.MAX_VALUE);

        SequentialTransition sequenceNormal = new SequentialTransition(front);
        sequenceNormal.setCycleCount(Integer.MAX_VALUE);

        //            /*
        // Start
//            fi 1
//            bi n+1
// Loop 1
//               puase
//            fi fade out
//            fi n+2
//               puase
//            fi fade in
//            bi n+3
// Loop 2
//               puase
//            fi fade out
//            fi n+4
//               puase
//            fi fade in
//            bi n+5
//            ----
//            */
//            
//            
//            
        for (int n = 1; n < (imageFiles.length - 1); n++) {
            int n1 = (n == imageFiles.length) ? 0 : n; // Two images case
            int n2 = (n == imageFiles.length) ? 0 : n1 + 1; // Image Collection is even. 

            sequenceNormal.getChildren().add(new PauseTransition(Duration.seconds(DISPLAYTIME)));
            
            FadeTransition frontFadeout = new FadeTransition(Duration.seconds(FADETIME));
            frontFadeout.setFromValue(1);
            frontFadeout.setToValue(0);
            frontFadeout.setOnFinished(e -> front.setImage(new Image(imageFiles[n1].toURI().toString())));
            sequenceNormal.getChildren().add(frontFadeout);

            sequenceNormal.getChildren().add(new PauseTransition(Duration.seconds(DISPLAYTIME)));

            FadeTransition frontFadein = new FadeTransition(Duration.seconds(FADETIME));
            frontFadein.setFromValue(0);
            frontFadein.setToValue(1);
            frontFadein.setOnFinished(e -> back.setImage(new Image(imageFiles[n2].toURI().toString())));
            sequenceNormal.getChildren().add(frontFadein);

        }

        // Lösung mit Timeline
//        SequentialTransition sequenceNormal = new SequentialTransition(front);
//        sequenceNormal.setCycleCount(Integer.MAX_VALUE);
//
//        for (int n = 0; n < (imageFiles.length - 1); n++) {
//            String fi = imageFiles[n].toURI().toString();
//            int np1 = (n == imageFiles.length) ? 0 : n + 1;
//            String bi = imageFiles[np1].toURI().toString();
//            
//            Timeline tl = new Timeline();
//            tl.getKeyFrames().add(new KeyFrame(Duration.seconds(FADETIME), evt -> {
//                System.out.println("tl1. Fadeout Front Done, Fadetime=" + FADETIME);
//                System.out.println("tl1. Load Front with " + fi);
//                front.setImage(new Image(fi));
//                System.out.println("tl1. Set Front.opacity=1");
//                front.setOpacity(1);
//                System.out.println("tl1. Loaded Back with " + bi);
//                back.setImage(new Image(bi));
//
//            }, new KeyValue(front.opacityProperty(), 0.0)));
//
//            sequenceNormal.getChildren().add(tl);
//            sequenceNormal.getChildren().add(new PauseTransition(Duration.seconds(DISPLAYTIME)));
//        }
//        sequenceNormal.play();

        ParallelTransition pt = new ParallelTransition(sequenceOverride, sequenceNormal);
        pt.play();

    }
}
