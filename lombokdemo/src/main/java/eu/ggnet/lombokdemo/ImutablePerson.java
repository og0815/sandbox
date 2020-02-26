/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.lombokdemo;

/**
 *
 * @author oliver.guenther
 */
public class ImutablePerson {
 
    public final String fristName;
    
    public final String lastName;
    
    public final int age;

    public ImutablePerson(String fristName, String lastName, int age) {
        this.fristName = fristName;
        this.lastName = lastName;
        this.age = age;
    }
        
}
