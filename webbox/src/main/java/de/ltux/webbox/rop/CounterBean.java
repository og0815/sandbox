/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.webbox.rop;

import javax.ejb.Stateful;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author olive
 */
@Stateful
public class CounterBean implements CounterRemote {

    private final Logger L = LoggerFactory.getLogger(CounterBean.class);

    private int counter = 0;

    @Override
    public int count() {
        counter++;
        L.info("Counting {}", counter);
        return counter;
    }

}
