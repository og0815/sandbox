/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.webbox.rop;

import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author olive
 */
@Stateless
public class CalculatorBean implements CalculatorRemote {

    private final Logger L = LoggerFactory.getLogger(CalculatorBean.class);

    @Override
    public int add(int a, int b) {
        L.info("Adding {} and {}", a, b);
        return a + b;
    }

}
