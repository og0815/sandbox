/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rmi;

import java.rmi.RemoteException;

/**
 *
 * @author oliver.guenther
 */
public class ListRegistries {
    public static void main(String[] args) throws RemoteException {
        System.out.println("Registry 1098:" + new RmiConnection(1098).list());
        System.out.println("Registry 1099:" + new RmiConnection(1099).list());
    }
}
