/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.security;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author oliver.guenther
 */
@Named
@Stateless
@RolesAllowed(Roles.DEPOSIT_MODIFY)
public class AdminAllowedController {

    public String modify() {
        return "Changing";
    }

}
