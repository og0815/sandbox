/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.webbox;

import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author olive
 */
@Stateless
public class Inspector {

    private final Logger L = LoggerFactory.getLogger(Inspector.class);

    public List<NameClassPair> jndi(String suffix) {
        try {
            return JndiUtil.inspect(new InitialContext(), suffix);
        } catch (NamingException ex) {
            L.warn("Jndi Tree Module Name inspection on Suffix {} failed: {}", suffix, ex.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

}
