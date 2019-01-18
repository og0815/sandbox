package de.ltux.modweb.api;

public interface ModuleProvider {

    String getName();
    
    String getId();
    
    String getDescription();
    
    String getDefaultAction();
    
}
