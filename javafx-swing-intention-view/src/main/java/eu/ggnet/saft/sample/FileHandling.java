package eu.ggnet.saft.sample;

import eu.ggnet.saft.sample.aux.MainPanel;
import eu.ggnet.saft.core.SwingFx;
import eu.ggnet.saft.core.UiCore;

/**
 * Shows a file handling.
 *
 * @author oliver.guenther
 */
public class FileHandling {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());

        SwingFx.exec(SwingFx.openFileChosser()
                .onOk(f -> {
                    System.out.println("Ok pressed, File: " + f.getAbsolutePath());
                    return null;
                })
        );

    }

}
