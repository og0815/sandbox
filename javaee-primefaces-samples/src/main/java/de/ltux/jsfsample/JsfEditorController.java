package de.ltux.jsfsample;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author oliver.guenther
 */
@Named
@SessionScoped
public class JsfEditorController implements Serializable {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
}
