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
@RolesAllowed(Roles.DEPOSIT_VIEW)
public class UserAllowedController {

    private String modification = "None";

    private String viewing = "None";

    public String getModification() {
        return modification;
    }

    public String getViewing() {
        return viewing;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

    public void setViewing(String viewing) {
        this.viewing = viewing;
    }

    @RolesAllowed(Roles.DEPOSIT_MODIFY)
    public void modify() {
        modification = "Changing";
    }

    public void view() {
        viewing = "Viewing";
    }
}
