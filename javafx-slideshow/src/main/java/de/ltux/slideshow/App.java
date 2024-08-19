package de.ltux.slideshow;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
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

    private static final String IMAGES_FOLDER = "C:/Users/oliver.guenther/Pictures/slideshow"; // Change this if your images are in a different folder

    private ImageView front;

    private ImageView back;

    @Override
    public void start(Stage primaryStage) {
        // Load the images from the folder
        File folder = new File(IMAGES_FOLDER);
        File[] imageFiles = folder.listFiles((dir, name) -> name.matches(".*\\.(jpg|png|gif)$"));

        if (imageFiles == null || imageFiles.length == 0) {
            System.err.println("No images found in the folder.");
            return;
        }

        // Create the ImageView to display the images
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

        StackPane root = new StackPane(back, front);
        Scene scene = new Scene(root, 800, 600); // Adjust the scene size as needed

        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Slideshow JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        primaryStage.show();
    }

    private void startSlideshow(File[] imageFiles) {
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
        back.setImage(new Image(imageFiles[0].toURI().toString()));

        SequentialTransition st = new SequentialTransition();
        st.setCycleCount(Integer.MAX_VALUE);

        for (int n = 0; n < (imageFiles.length - 1); n++) {
            String fi = imageFiles[n].toURI().toString();
            int np1 = (n == imageFiles.length) ? 0 : n + 1;
            String bi = imageFiles[np1].toURI().toString();

            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Duration.seconds(FADETIME), evt -> {
                System.out.println("tl1. Fadeout Front Done, Fadetime=" + FADETIME);
                System.out.println("tl1. Load Front with " + fi);
                front.setImage(new Image(fi));
                System.out.println("tl1. Set Front.opacity=1");
                front.setOpacity(1);
                System.out.println("tl1. Loaded Back with " + bi);
                back.setImage(new Image(bi));

            }, new KeyValue(front.opacityProperty(), 0.0)));

            st.getChildren().add(tl);
            st.getChildren().add(new PauseTransition(Duration.seconds(DISPLAYTIME)));
        }

        st.play();

//        // Create a Timeline for the slideshow animation
//        Timeline tl1 = new Timeline();
//        tl1.getKeyFrames().add(new KeyFrame(Duration.seconds(FADETIME), evt -> {
//            System.out.println("tl1. Fadeout Front Done, Fadetime=" + FADETIME);
//            String frontImage = imageFiles[0].toURI().toString();
//            System.out.println("tl1. Load Front with " + frontImage);
//            front.setImage(new Image(frontImage));
//            System.out.println("tl1. Set Front.opacity=1");
//            front.setOpacity(1);
//            String backImage = imageFiles[1].toURI().toString();
//            System.out.println("tl1. Loaded Back with " + backImage);
//            back.setImage(new Image(backImage));
//
//        }, new KeyValue(front.opacityProperty(), 0.0)));
//
//        Timeline tl2 = new Timeline();
//        tl2.getKeyFrames().add(new KeyFrame(Duration.seconds(FADETIME), evt -> {
//            System.out.println("tl2. Fadeout Front Done, Fadetime=" + FADETIME);
//            String frontImage = imageFiles[1].toURI().toString();
//            System.out.println("tl2. Load Front with " + frontImage);
//            front.setImage(new Image(frontImage));
//            System.out.println("tl2. Set Front.opacity=1");
//            front.setOpacity(1);
//            String backImage = imageFiles[0].toURI().toString();
//            System.out.println("tl2. Loaded Back with " + backImage);
//            back.setImage(new Image(backImage));
//
//        }, new KeyValue(front.opacityProperty(), 0.0)));
//
//        SequentialTransition st = new SequentialTransition(
//                tl1,
//                new PauseTransition(Duration.seconds(DISPLAYTIME)),
//                tl2,
//                new PauseTransition(Duration.seconds(DISPLAYTIME)));
//        st.setCycleCount(Integer.MAX_VALUE);
//
//        st.play();
    }
}
