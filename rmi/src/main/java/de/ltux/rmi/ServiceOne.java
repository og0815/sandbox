/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.ltux.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author oliver.guenther
 */
public interface ServiceOne extends Remote{
    
    boolean messageOne(String  message) throws RemoteException;
}
