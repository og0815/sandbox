package sample.intention.structure;

import javax.swing.JPanel;

import java.util.concurrent.Callable;

/**
 * Puts a callable on the stack for background execution and used the result to popup a dialog.
 *
 * @author oliver.guenther
 */
public interface Caller {

    <V, T extends JPanel> PopupOneArg<V, T> call(Callable<V> callable);

}
