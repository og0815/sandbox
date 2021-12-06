/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author oliver.guenther
 */
@RequestScoped
@Named
public class IndexController {

    public String hello() {
        return toString();
    }

}
