/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.ltux.mapstruct;

import java.util.Optional;
import org.inferred.freebuilder.FreeBuilder;

/**
 *
 * @author oliver.guenther
 */
@FreeBuilder
public interface CarDto {

    String description();
    
    Optional<Integer> constructionYear();
    
    public static Builder builder() {
        return new Builder();
    }

    class Builder extends CarDto_Builder {
        
        public Builder() {
            constructionYear(1976);
        }
    }
}
