package de.ltux.modweb.mod01;

import de.ltux.modweb.api.ModuleProvider;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class Mod01Module implements ModuleProvider {

    @Override
    public String getName() {
        return "Module 01";
    }

    @Override
    public String getId() {
        return "example.modweb.mod01";
    }

    @Override
    public String getDescription() {
        return "Module 01 provides functionality A.";
    }

    @Override
    public String getDefaultAction() {
        return "/mod01.jsf";
    }
    
}
