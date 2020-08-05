/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;
import org.inferred.freebuilder.FreeBuilder;

/**
 *
 * @author oliver.guenther
 */
@FreeBuilder
@JsonDeserialize(builder = Person.Builder.class)
public interface Person {

    class Builder extends Person_Builder {
    }

    static Person create(String name, int age, LocalDate day) {
        return new Builder().name(name).age(age).birthDay(day).build();
    }

    String name();

    int age();

    LocalDate birthDay();

}
