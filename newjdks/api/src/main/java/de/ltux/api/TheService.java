/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.api;

import de.ltux.pojo.one.Person;
import de.ltux.pojo.three.Paper;
import de.ltux.pojo.three.Stone;
import javax.ejb.Remote;

/**
 *
 * @author oliver.guenther
 */
@Remote
public interface TheService {
    
    Stone getStone();
    
    Person getPerson();
    
    Paper getPaper();
    
    CustomerMetaData getCustomerMetaData();
}
