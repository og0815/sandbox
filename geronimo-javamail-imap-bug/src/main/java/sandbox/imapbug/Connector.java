package sandbox.imapbug;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.mail.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

/**
 *
 * @author oliver.guenther
 */
public class Connector extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Imap Bug Demo");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.addColumn(0, new Label("Protocol:"), new Label("Host:"), new Label("User:"), new Label("Pass:"));

        final TextField protocolField = new TextField("imaps");
        final TextField hostField = new TextField("imap-mail.outlook.com");
        final TextField userField = new TextField("");
        final PasswordField passField = new PasswordField();

        grid.addColumn(1, protocolField, hostField, userField, passField);

        Button ok = new Button("Ok");
        ok.setOnAction((ActionEvent event) -> {
            try {
                boolean connect = connect(protocolField.getText(), hostField.getText(), userField.getText(), passField.getText());
                String msg = (connect ? "Connection successfull" : "Connection unsccessfull");
                show("Result", msg, 200, 30);
            } catch (MessagingException | NullPointerException ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                show(ex.getClass().getSimpleName(), ex.getMessage() + "\n" + sw.toString(), 800, 600);
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            primaryStage.close();
        });
        grid.addRow(4, ok, cancel);

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void show(String title, String msg, int h, int w) {
        TextArea textArea = new TextArea(msg);
        textArea.setEditable(false);
        Scene scene = new Scene(new StackPane(textArea), h, w);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static boolean connect(String protocol, String host, String user, String pass) throws MessagingException, NullPointerException {
        Properties p = new Properties();
        p.put("mail.store.protocol", protocol);
        p.put("mail.host", host);
        p.put("mail." + protocol + ".connectiontimeout", 3000);
        p.put("mail." + protocol + ".timeout", 3000);
        Session ss = Session.getInstance(p);
        Store s = ss.getStore();
        s.connect(user, pass);
        if (!s.isConnected()) {
            return false;
        }
        s.close();
        return true;
    }

}
