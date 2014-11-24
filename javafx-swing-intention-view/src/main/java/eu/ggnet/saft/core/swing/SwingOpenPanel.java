package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.CallableA1;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import javafx.stage.Modality;
import javafx.util.Pair;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public class SwingOpenPanel<T> implements Callable<Window> {

    private final static Logger L = LoggerFactory.getLogger(SwingOpenPanel.class);

    private final OnceCaller<T> before;

    // Never Null, because in the fallback case
    private final Window parent;

    private final Modality modality;

    // Never Null, this is the least information we must have.
    private final Class<? extends JPanel> clazz;

    // Optional Identifier. If set, this is combined with the class to the Key, implies more that one instance is possible.
    private final Object id;

    private CallableA1<T, ? extends JPanel> construct;

    public SwingOpenPanel(Callable<T> before, Class<? extends JPanel> clazz, Object id, Window parent, Modality modality) {
        this.before = new OnceCaller<>(before);
        this.parent = parent;
        this.modality = modality;
        this.clazz = clazz;
        this.id = id;
    }

    public <V extends JPanel> SwingOpenPanel<T> construct(CallableA1<T, V> construct) {
        this.construct = construct;
        return this;
    }

    @Override
    public Window call() throws Exception {
        final Pair<Class<?>, Object> key = new Pair<>(clazz, id); // A Pair works with null as id :-)

        // 1. Case: clazz only set, only once open.
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
        final T parameter = before.get(); // Call outside all ui threads assumed
        // TODO: Think about the parameter.

        // Here come all the choices.
        // 1. clazz is set, everything else not. Try to create an Instance of the class.
        if (construct == null) {
            construct = (t) -> {
                JPanel panel = clazz.getConstructor().newInstance();
                if (t == null) return panel; // No call before, because a null breaks the chain.
                if (!(panel instanceof Consumer)) return panel; // No Consumer, ignore pre call value.
                try {
                    ((Consumer<T>) panel).accept(t);
                } catch (ClassCastException e) {
                    L.warn("Ui:" + clazz.getName() + " implements Consumer, but not of type " + t.getClass().getName() + " which is expected");
                }
                return panel;
            };
        }

        JDialog window = SwingSaft.dispatch(() -> {
            JPanel panel = construct.call(parameter);
            // Case: Use JDialog
            JDialog ui = new JDialog(parent);
            ui.setModalityType(UiUtil.toSwing(modality).orElse(ModalityType.MODELESS));  // This is an "application", default no modaltiy at all
            ui.setTitle(UiUtil.extractTitle(panel).orElse("Dialog : " + clazz.getSimpleName() + (id == null ? "" : " " + id)));
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
