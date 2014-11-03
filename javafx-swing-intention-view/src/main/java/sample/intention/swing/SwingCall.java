package sample.intention.swing;

import javax.swing.JPanel;
import sample.intention.structure.PopupOneArg;

import java.util.concurrent.Callable;

/**
 *
 * @author oliver.guenther
 * @param <P>
 */
public class SwingCall<P, T extends JPanel> {

    private Callable<P> callable;

    public PopupOneArg<P, T> call(Callable<P> callable) {
        this.callable = callable;
        return null;
    }

    public P internalExec() throws Exception {
        // No Before for now.
        return callable.call(); // Remember ? We are in another thread, so the direct call is logical.
    }

}
