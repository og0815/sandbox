/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.lombokdemo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;
import org.inferred.freebuilder.FreeBuilder;

/**
 *
 * @author oliver.guenther
 */
@FreeBuilder
@JsonDeserialize(builder = FreePerson.Builder.class)
public interface FreePerson {
    
    String getFirstName();
    
    String getLastName();
    
    int getAge();
    
    LocalDateTime getBirthTime();
    
    class Builder extends FreePerson_Builder {};
    
}
