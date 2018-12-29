/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.pojo.four;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author oliver.guenther
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Water {
    
    boolean clean;
    
    int temperature;
    
    public static void main(String[] args) {
        var w = new Water();
        w.setClean(true); // maven doesn't compile. Seams some lombok - module java - magic
        // seams to be an live issue : https://github.com/rzwitserloot/lombok/issues/1723
        System.out.println(w);
    }
    
}
