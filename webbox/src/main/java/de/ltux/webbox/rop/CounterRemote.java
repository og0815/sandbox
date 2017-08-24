/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.webbox.rop;

import javax.ejb.Remote;

/**
 *
 * @author olive
 */
@Remote
public interface CounterRemote {

    int count();

}
