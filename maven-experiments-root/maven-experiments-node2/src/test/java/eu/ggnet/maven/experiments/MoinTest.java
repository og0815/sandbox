/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.maven.experiments;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author oliver.guenther
 */
public class MoinTest {
    

    @Test
    public void testMoin() {
        System.out.println("moin");
        Moin instance = new Moin();
        String expResult = "moin";
        String result = instance.moin();
        assertEquals(expResult, result);
    }
    
}
