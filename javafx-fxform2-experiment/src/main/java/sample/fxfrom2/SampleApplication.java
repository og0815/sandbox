/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.fxfrom2;

import com.dooapp.fxform.FXForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author oliver.guenther
 */
public class SampleApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Customer c = new Customer("Herr", "Hans", "Wurst", 12);
        FXForm<Customer> form = new FXForm<>(c);
        BorderPane root = new BorderPane(form);

        Button show = new Button("Show Customer");
        show.setOnAction(e -> {
            Stage dialog = new Stage();
            dialog.initOwner(primaryStage);
            dialog.setScene(new Scene(new BorderPane(new TextArea(c.toString()))));
            dialog.show();
        });

        root.setBottom(show);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Test of FxForms");
        primaryStage.show();
    }

}
