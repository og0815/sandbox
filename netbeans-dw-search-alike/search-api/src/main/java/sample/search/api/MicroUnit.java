/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.search.api;

import lombok.ToString;

/**
 *
 * @author oliver.guenther
 */
@ToString
public class MicroUnit {
    
    public final int uniqueUnitId;
    
    public final String shortDescription;

    public MicroUnit(int uniqueUnitId, String shortDescription) {
        this.uniqueUnitId = uniqueUnitId;
        this.shortDescription = shortDescription;
    }
    
}
