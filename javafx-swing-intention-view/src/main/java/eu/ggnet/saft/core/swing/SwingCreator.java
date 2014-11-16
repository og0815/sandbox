package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.fx.FxSaft;
import eu.ggnet.saft.core.all.AbstractCreator;
import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.OkCancelResult;
import eu.ggnet.saft.core.all.UiUtil;
import eu.ggnet.saft.core.aux.CallableA1;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;

import java.util.concurrent.*;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public class SwingCreator<T> extends AbstractCreator<T> {

    public SwingCreator(Callable<T> before) {
        super(before);
    }

    public SwingCreator() {
        this(null);
    }

    @Override
    public <R extends Pane> SwingOk<R> popupOkCancelFx(CallableA1<T, R> builder) {
        return new SwingOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get();
            FxSaft.ensurePlatformIsRunning();
            final R pane = builder.call(parameter); // Call outside all ui threads assumed
            JFXPanel p = FxSaft.wrap(pane);
            return SwingSaft.dispatch(() -> {
                OkCancelDialog<JFXPanel> dialog = new OkCancelDialog<>(UiCore.mainPanel, UiUtil.extractTitle(pane).orElse("Auswahldialog"), p);
                dialog.pack();
                dialog.setLocationRelativeTo(UiCore.mainPanel);
                dialog.setVisible(true);
                return new OkCancelResult<>(pane, dialog.isOk());
            });
        });
    }

    @Override
    public <R extends JPanel> SwingOk<R> popupOkCancel(CallableA1<T, R> builder) {
        return new SwingOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get(); // Call outside all ui threads assumed

            return SwingSaft.dispatch(() -> {
                R panel = builder.call(parameter);
                OkCancelDialog<R> dialog = new OkCancelDialog<>(UiCore.mainPanel, UiUtil.extractTitle(panel).orElse("Auswahldialog"), panel);
                dialog.pack();
                dialog.setLocationRelativeTo(UiCore.mainPanel);
                dialog.setVisible(true);
                return new OkCancelResult<>(dialog.getSubContainer(), dialog.isOk());
            });
        });
    }

}
