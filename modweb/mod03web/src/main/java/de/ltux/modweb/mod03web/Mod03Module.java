package de.ltux.modweb.mod03web;

import de.ltux.modweb.api.ModuleProvider;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Mod03Module implements ModuleProvider {

    @Override
    public String getName() {
        return "Module 03";
    }

    @Override
    public String getId() {
        return "de.ltux.modweb.mod02";
    }

    @Override
    public String getDescription() {
        return "Module 03 provides functionality C. as War";
    }

    @Override
    public String getDefaultAction() {
        return "/mod03info.jsf";
    }
    
}
