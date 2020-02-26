/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.freebuilder;

import org.inferred.freebuilder.FreeBuilder;

/**
 * Person representation.
 * 
 * @author oliver.guenther
 */
@FreeBuilder
public interface Person {

    class Builder extends Person_Builder {};
    
    /**
     * Returns the frist name.
     * 
     * @return the first name
     */
    String firstName();
    
    /**
     * Return the last name.
     * 
     * @return the last name.
     */
    String lastName();
    
    /**
     * Returns the age.
     * 
     * @return the age.
     */
    int age();
    
}
