/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.mapstruct;

import org.inferred.freebuilder.FreeBuilder;

/**
 *
 * @author oliver.guenther
 */
@FreeBuilder
public interface Car {

    String description();
    
    int constructionYear();

    public static Builder builder() {
        return new Builder();
    }

    class Builder extends Car_Builder {
    }
}
