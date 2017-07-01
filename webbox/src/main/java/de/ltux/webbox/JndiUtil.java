/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.webbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 *
 * @author olive
 */
public class JndiUtil {

    public static List<NameClassPair> inspect(Context context, String... prefixes) {
        Objects.requireNonNull(context, "Context must not be null");
        Objects.requireNonNull(prefixes, "At least one prefix must be supplied");

        List<NameClassPair> result = new ArrayList<>();
        for (String prefix : prefixes) {
            try {
                NamingEnumeration<NameClassPair> list = context.list(prefix);
                while (list != null && list.hasMore()) {
                    try {
                        result.add(list.next());
                    } catch (NamingException ex) {
                    }
                }
            } catch (NamingException ex) {
            }
        }
        return result;
    }

}
