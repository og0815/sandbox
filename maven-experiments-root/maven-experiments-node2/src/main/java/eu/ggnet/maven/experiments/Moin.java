/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.maven.experiments;

import org.slf4j.impl.SimpleLogger;

/**
 *
 * @author oliver.guenther
 */
public class Moin {
    public static void main(String[] args) {
        System.out.println(new Moin().moin());
        SimpleLogger l;
    }
    
    public String moin() {
        return "moin";
    }
}
