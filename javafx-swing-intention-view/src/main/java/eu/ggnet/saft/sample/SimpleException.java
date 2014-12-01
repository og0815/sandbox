package eu.ggnet.saft.sample;

import eu.ggnet.saft.sample.aux.MainPanel;
import eu.ggnet.saft.core.SwingFx;
import eu.ggnet.saft.core.UiCore;

/**
 * A Simple Exception handling Example.
 *
 * @author oliver.guenther
 */
public class SimpleException {

    public static void main(String[] args) {
        UiCore.startSwing(() -> new MainPanel());

        SwingFx.exec(SwingFx.call(() -> {
                    throw new IllegalAccessException("Sinnlos");
                })
        );
    }

}
