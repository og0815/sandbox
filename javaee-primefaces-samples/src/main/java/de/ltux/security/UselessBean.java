/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.security;

import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;

/**
 *
 * @author oliver.guenther
 */
@Stateless
@DeclareRoles(Roles.DEPOSIT_CREATE)
public class UselessBean {

    public void doNothing() {

    }

}
