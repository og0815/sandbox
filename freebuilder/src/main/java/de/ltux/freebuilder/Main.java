/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.freebuilder;

/**
 *
 * @author oliver.guenther
 */
public class Main {

    public static void main(String[] args) {
        Person p = new Person.Builder().firstName("Hans").lastName("Mustermann").age(20).build();
        System.out.println(p);
    }

}
