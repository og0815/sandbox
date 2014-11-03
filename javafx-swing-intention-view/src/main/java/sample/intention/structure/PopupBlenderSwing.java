package sample.intention.structure;

import javax.swing.JPanel;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public interface PopupBlenderSwing<P, T extends JPanel> {

    T build(P parameter);

}
