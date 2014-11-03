package sample.demo;

import sample.demo.aux.MainPanel;
import sample.intention.UiCore;

/**
 * Helper Class to switch the UiCore configuration for all Samples at once.
 *
 * @author oliver.guenther
 */
public class Global {

    /**
     * Call before every sample.
     */
    public static void init() {
        // Init Core as Swing and supply a Panel a Main application window.
        UiCore.startSwing(() -> new MainPanel());

    }
}
