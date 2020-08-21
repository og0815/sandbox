/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.security;

import java.util.Arrays;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import org.slf4j.Logger;

/**
 *
 * @author oliver.guenther
 */
@Stateless
@DeclareRoles({Roles.DEPOSIT_ALL_VIEW, Roles.DEPOSIT_MODIFY})
@PermitAll
public class RolesManagerBean {

    @Inject
    private SecurityContext sc;

    @Inject
    private Logger log;

    public String getUserName() {
        if (sc == null) {
            return "null";
        }
        log.info("principal={}", sc.getCallerPrincipal());
        return sc.getCallerPrincipal() == null ? "null" : sc.getCallerPrincipal().getName();
    }

    public String getRoles() {
        if (sc == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (String role : Arrays.asList("user-inmem", "admin-inmem", "USER", "ADMIN", Roles.DEPOSIT_ALL_VIEW, Roles.DEPOSIT_MODIFY, Roles.DEPOSIT_VIEW, Roles.STEIN)) {
            sb.append(role).append("=").append(sc.isCallerInRole(role)).append("|");
        }
        return sb.toString();
    }

}
