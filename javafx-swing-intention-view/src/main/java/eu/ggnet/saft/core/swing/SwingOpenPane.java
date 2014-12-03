package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.*;
import eu.ggnet.saft.core.aux.Frame;
import eu.ggnet.saft.core.fx.FxSaft;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <T>
 * @param <R>
 */
public class SwingOpenPane<T, R extends Pane> implements Callable<Window> {

    private final OnceCaller<T> before;

    // Never Null, because in the fallback case
    private final Window parent;

    private final Modality modality;

    private final String id;

    private final Class<R> paneClass;

    public SwingOpenPane(Callable<T> before, Window parent, Modality modality, String id, Class<R> paneClass) {
        this.before = new OnceCaller<>(before);
        this.parent = parent;
        this.modality = modality;
        this.id = id;
        this.paneClass = paneClass;
    }

    @Override
    public Window call() throws Exception {
        String key = paneClass.getName() + (id == null ? "" : ":" + id);
        // Look into existing Instances and push up to the front if exist.
        if (UiCore.swingActiveWindows.containsKey(key)) {
            Window window = UiCore.swingActiveWindows.get(key).get();
            if (window == null || !window.isVisible()) /* cleanup saftynet */ UiCore.swingActiveWindows.remove(key);
            else {
                if (window instanceof JFrame) ((JFrame) window).setExtendedState(JFrame.NORMAL);
                window.toFront();
                return window;
            }
        }

        // Here it's clear, that our instance does not exist, so we create one.
        if (before.ifPresentIsNull()) return null; // Chainbreaker
        final T parameter = before.get(); // Call outside all ui threads assumed. Parameter null dosn't mean chainbreaker.
        FxSaft.ensurePlatformIsRunning();

        final R pane = FxSaft.construct(paneClass, parameter);
        JFXPanel p = FxSaft.wrap(pane);

        Window window = SwingSaft.dispatch(() -> {
            Window w = null;
            if (paneClass.getAnnotation(Frame.class) != null) {
                // TODO: Reuse Parent and Modality ?
                JFrame frame = new JFrame();
                frame.setTitle(UiUtil.title(paneClass, id));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(p);
                w = frame;
            } else {
                JDialog dialog = new JDialog(parent);
                dialog.setModalityType(UiUtil.toSwing(modality).orElse(ModalityType.MODELESS));  // This is an "application", default no modaltiy at all
                // Parse the Title somehow usefull.
                dialog.setTitle(UiUtil.title(paneClass, id));
                dialog.getContentPane().add(p);
                w = dialog;
            }
            w.pack();
            w.setLocationRelativeTo(parent);
            w.setVisible(true);
            return w;
        });

        SwingSaft.enableCloser(window, pane);

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
