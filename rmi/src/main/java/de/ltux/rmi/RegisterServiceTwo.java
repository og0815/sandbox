/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package de.ltux.rmi;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author oliver.guenther
 */
public class RegisterServiceTwo {

    public static class ServiceTwoImpl implements ServiceTwo {

        @Override
        public boolean messageTwo(String message) throws RemoteException {
            System.out.println("ServiceTwo: " + message);
            return true;
        }

    }

    public static void main(String[] args) throws RemoteException, InterruptedException, AlreadyBoundException, AccessException, NotBoundException {
        RmiConnection rmi = new RmiConnection(1099);
        rmi.bind(ServiceTwo.class, new ServiceTwoImpl());
        System.out.println("Registry:" + rmi.list());
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Shutdown");

        System.exit(0); // Only simple way to shutdown the created rmi threads.
    }
}
