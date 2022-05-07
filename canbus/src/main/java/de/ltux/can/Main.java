package de.ltux.can;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.IOException;

/**
 *
 * @author oliver.guenther
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        var app = new CanWithArgs();
        JCommander jc = JCommander.newBuilder()
                .addObject(app).
                build();

        try {
            jc.parse(args);
            app.run();
        } catch (ParameterException | IllegalStateException e) {
            System.out.println(e.getLocalizedMessage());
            jc.usage();
        }
    }

}
