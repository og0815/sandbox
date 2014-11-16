package eu.ggnet.saft.core.fx;

import eu.ggnet.saft.core.all.AbstractCreator;
import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.OkCancelResult;
import eu.ggnet.saft.core.all.UiUtil;
import eu.ggnet.saft.core.aux.CallableA1;
import eu.ggnet.saft.core.swing.SwingSaft;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;
import eu.ggnet.saft.core.swing.OkCancelDialog;

import java.util.concurrent.*;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public class FxCreator<T> extends AbstractCreator<T> {

    public FxCreator(Callable<T> before) {
        super(before);
    }

    public FxCreator() {
        this(null);
    }

    @Override
    public <R extends Pane> FxOk<R> popupOkCancelFx(CallableA1<T, R> builder) {
        return new FxOk<>(() -> {
            if (before.ifPresentIsNull()) return null; // Chainbreaker
            final T parameter = before.get();
            final R pane = builder.call(parameter); // Call outside all ui threads assumed

            return FxSaft.dispatch(() -> {
                OkCancelStage s = new OkCancelStage(UiUtil.extractTitle(pane).orElse("Auswahldialog"), pane);
                s.initOwner(UiCore.mainStage);
                s.showAndWait();
                return new OkCancelResult<>(pane, s.isOk());
            });

        });
    }

    @Override
    public <R extends JPanel> FxOk<R> popupOkCancel(CallableA1<T, R> builder) {
        return new FxOk<>(() -> {
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
