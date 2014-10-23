package sample.intention;

import javax.swing.JPanel;

/**
 *
 * @author oliver.guenther
 * @param <T>
 */
public interface JPanelBuilder<T extends JPanel> {

    T build();

}
