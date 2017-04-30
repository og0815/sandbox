/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.one;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class HelloBean {

    private String name;

    public String sayHello() {
        if (name != null && !name.trim().equals("")) {
            return "Hello, " + name + "!";
        } else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
