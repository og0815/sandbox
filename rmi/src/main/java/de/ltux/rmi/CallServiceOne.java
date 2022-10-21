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
public class CallServiceOne {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        new RmiConnection(1098).lookup(ServiceOne.class).messageOne("Hallo");
        System.out.println("ServiceOne called");

    }

}
