/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.security.rest;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author oliver.guenther
 */
@ApplicationPath("/rest")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(RestService.class);
    }

}
