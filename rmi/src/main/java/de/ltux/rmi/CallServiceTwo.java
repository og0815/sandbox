/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author oliver.guenther
 */
public class CallServiceTwo {
    public static void main(String[] args) throws RemoteException, NotBoundException {        
        new RmiConnection(1099).lookup(ServiceTwo.class).messageTwo("Hallo");
        System.out.println("ServiceTwo called");
    }
    
}
