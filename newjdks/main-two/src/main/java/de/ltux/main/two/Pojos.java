/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.main.two;

import de.ltux.pojo.one.Person;
import de.ltux.pojo.two.Song;

/**
 *
 * @author oliver.guenther
 */
public class Pojos {

    public static void main(String[] args) {
        var p = new Person("Max", "Mustermann", 23); // using automodule.
        var s = new Song("Jingle Bells", "Depeche Mode", 123); // using a module projekt in a clean project.
        System.out.println(p);
        System.out.println(p.toModuleInfo());
        System.out.println(s);
        System.out.println(s.toModuleInfo());
    }

}
