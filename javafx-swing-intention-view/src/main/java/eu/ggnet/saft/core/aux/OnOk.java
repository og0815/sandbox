package eu.ggnet.saft.core.aux;

/**
 * If implemented by a Pane or JPanel, the onOk is called if ok is pressed.
 */
public interface OnOk {

    /**
     * Is called before a closing of surrounding element, by Ok pressing.
     *
     * @return true if the closing operation may continue as allowed, or false if it should be stopped.
     */
    boolean onOk();

}
