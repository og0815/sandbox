package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.*;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import javafx.stage.Modality;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author oliver.guenther
 * @param <T>
 * @param <R>
 */
public class SwingOpenPanel<T, R extends JPanel> implements Callable<Window> {

    private final OnceCaller<T> before;

    // Never Null, because in the fallback case
    private final Window parent;

    private final Modality modality;

    private final String key;

    private final CallableA1<T, R> factory;

    private String title;

    public SwingOpenPanel(Callable<T> before, Window parent, Modality modality, String key, CallableA1<T, R> factory) {
        this.before = new OnceCaller<>(before);
        this.parent = parent;
        this.modality = modality;
        this.key = key;
        this.factory = factory;
        this.title = key;
    }

    public SwingOpenPanel<T, R> title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Window call() throws Exception {

        // Look into existing Instances and push up to the front if exist.
        if (UiCore.swingActiveWindows.containsKey(key)) {
            Window get = UiCore.swingActiveWindows.get(key).get();
            if (get == null || !get.isVisible()) /* cleanup saftynet */ UiCore.swingActiveWindows.remove(key);
            else {
                get.toFront();
                return get;
            }
        }

        // Here it's clear, that our instance does not exist, so we create one.
        if (before.ifPresentIsNull()) return null; // Chainbreaker
        final T parameter = before.get(); // Call outside all ui threads assumed. Parameter null dosn't mean chainbreaker.

        JDialog window = SwingSaft.dispatch(() -> {
            JPanel panel = factory.call(parameter);
            // Case: Use JDialog
            JDialog ui = new JDialog(parent);
            ui.setModalityType(UiUtil.toSwing(modality).orElse(ModalityType.MODELESS));  // This is an "application", default no modaltiy at all
            ui.setTitle(UiUtil.extractTitle(panel).orElse(title));
            ui.getContentPane().add(panel);
            ui.pack();
            ui.setLocationRelativeTo(parent);
            ui.setVisible(true);
            return ui;
        });
        UiCore.swingActiveWindows.put(key, new WeakReference<>(window));

        // Removes on close.
        window.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                // Clean us up.
                UiCore.swingActiveWindows.remove(key);
            }

        });

        return window;
    }

}
