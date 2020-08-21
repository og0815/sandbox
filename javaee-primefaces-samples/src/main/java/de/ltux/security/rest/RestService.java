/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.security.rest;

import de.ltux.security.Roles;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author oliver.guenther
 */
@Path("/service")
@Produces(MediaType.TEXT_PLAIN)
public class RestService {

    @Inject
    private SecurityContext sc;

    @GET
    public String get() {
        return "Service works";
    }

    @RolesAllowed(Roles.DEPOSIT_VIEW)
    @Path("view")
    @GET
    public String view() {
        return "Viewing";
    }

    @RolesAllowed(Roles.DEPOSIT_CREATE)
    @Path("create")
    @GET
    public String create() {
        return "Creating";
    }

    @Path("view/allowed")
    @GET
    public boolean isViewAllowed() {
        return sc.isCallerInRole(Roles.DEPOSIT_VIEW);
    }

    @Path("create/allowed")
    @GET
    public boolean isCreateAllowed() {
        return sc.isCallerInRole(Roles.DEPOSIT_CREATE);
    }

}
