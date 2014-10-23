package sample.intention.old;

import java.awt.Window;

/**
 *
 * @author oliver.guenther
 */
public interface IView<T extends Window> {

    void setParent(T window);

}
