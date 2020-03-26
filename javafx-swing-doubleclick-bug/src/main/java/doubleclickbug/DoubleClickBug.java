/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doubleclickbug;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class DoubleClickBug {

    private static void initAndShowGUI() {
        // This method is invoked on the EDT thread
        JFrame frame = new JFrame("Swing and JavaFX");

        final JFXPanel fxPanel = new JFXPanel();

        JList<String> list = new JList<>(new String[]{"One", "Two", ""});

        JSplitPane jp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, fxPanel);

        frame.add(jp);
        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Platform.runLater(() -> {
            initFX(fxPanel);
        });
    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
        Group root = new Group();
        Scene scene = new Scene(root, Color.ALICEBLUE);
        ListView<String> l = new ListView<>();
        l.getItems().addAll("One", "Two", "Three");

        l.setOnMouseClicked(e -> {
            System.out.println("ClickCount=" + e.getClickCount() + " | Event:" + e);
        });

        root.getChildren().add(l);

        return (scene);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> initAndShowGUI());
    }

}
