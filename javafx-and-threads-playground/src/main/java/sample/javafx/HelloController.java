package sample.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private Label messageLabel;

    private HelloPojo helloPojo;

    public void setHelloPojo(HelloPojo helloPojo) {
        this.helloPojo = helloPojo;
        firstNameField.textProperty().bindBidirectional(helloPojo.firstNameProperty());
        lastNameField.textProperty().bindBidirectional(helloPojo.lastNameProperty());
    }

    public void doComputable() {
        log.info("Fx({}): doComputable start", Thread.currentThread());

        try {
            // Hint: I read something, that someone got back to the fx thread in the Completeable Future chain.
            // I can't remember there and I also don't know how this should be possible.
            // So here I played arround, but ... No, not possible as I expected.
            Void get = CompletableFuture.supplyAsync(() -> {
                log.info("supplyAsync({}): start", Thread.currentThread());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    //
                }
                log.info("supplyAsync({}): sleep complete", Thread.currentThread());
                return 10;
            }).handle(new BiFunction<Integer, Throwable, String>() {

                @Override
                public String apply(Integer t, Throwable u) {
                    log.info("handle({}): apply", Thread.currentThread());
                    return "Muh";
                }
            }).thenAccept((t) -> log.info("thenAccept({})", Thread.currentThread()))
                    .get();
        } catch (InterruptedException | ExecutionException ex) {

        }

        log.info("Fx({}): doComputable end", Thread.currentThread());
    }

    public void sayHello() {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        StringBuilder builder = new StringBuilder();

        if (!StringUtils.isEmpty(firstName)) {
            builder.append(firstName);
        }

        if (!StringUtils.isEmpty(lastName)) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(lastName);
        }

        if (builder.length() > 0) {
            String name = builder.toString();
            log.debug("Saying hello to " + name);
            messageLabel.setText("Hello " + name);
        } else {
            log.debug("Neither first name nor last name was set, saying hello to anonymous person");
            messageLabel.setText("Hello mysterious person");
        }
        log.debug("{}", helloPojo);
    }

}
