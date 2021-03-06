package eu.ggnet.saft.core.support;

import eu.ggnet.saft.core.Ui;

/**
 * May be implemented by a Pane or JPanel, is called by using {@link Ui#choiceFx(java.lang.Class) }, if ok is pressed.
 */
public interface OnOk {

    /**
     * Is called before a closing of surrounding element, by Ok pressing.
     *
     * @return true if the closing operation may continue as allowed, or false if it should be stopped.
     */
    boolean onOk();

}
