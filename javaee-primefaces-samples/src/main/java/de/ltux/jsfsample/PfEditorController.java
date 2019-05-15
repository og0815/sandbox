package de.ltux.jsfsample;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oliver.guenther
 */
@Named
@SessionScoped
public class PfEditorController implements Serializable {
    
    private final static Logger L = LoggerFactory.getLogger(PfEditorController.class);
    
    private String name;

    public String getName() {
        L.debug("getName() called",name);
        return name;
    }

    public void setName(String name) {        
        L.debug("setName({}) called",name);
        this.name = name;
        L.info("Name was set to '{}'", name);
    }
        
}
