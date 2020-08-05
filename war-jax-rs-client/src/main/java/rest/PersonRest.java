/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author oliver.guenther
 */
public class PersonRest implements Persons {

    @Override
    public List<Person> findAll() {
        return Arrays.asList(
                Person.create("Max", 10, LocalDate.now()),
                Person.create("Klas", 20, LocalDate.now()));
    }

}
