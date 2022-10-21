/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package de.ltux.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author oliver.guenther
 */
public class RegisterServiceOne {

    public static class ServiceOneImpl implements ServiceOne {

        @Override
        public boolean messageOne(String message) throws RemoteException {
            System.out.println("ServiceOne: " + message);
            return true;
        }
        
    }
    
    public static void main(String[] args) throws RemoteException, InterruptedException, AlreadyBoundException, NotBoundException {
        new RmiConnection(1098).bind(ServiceOne.class, new ServiceOneImpl());
        TimeUnit.MINUTES.sleep(5);
    }
}
