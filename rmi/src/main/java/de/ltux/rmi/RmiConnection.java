/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rmi;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * Simplifyed class for rmi usage Lessons learned:
 * <ul>
 * <li>Die VM, die createRegistry aufruft, enthält auch den Registry thread. Wenn die Applkation beendet wird, ist auf die Registry weg.</li <li>Je Applikation
 * eine eigene Registry verwenden</li <li>Applikationen, die beendet werden, aber einen Service registiert haben müssen den unbinden. Sonst bleibt er in der
 * Registry</li </ul> @author oliver.guenther
 */
public class RmiConnection {

    private final int port;

    private Remote remote;
    
    public RmiConnection(int port) {
        this.port = port;
    }

    private Registry optionallyCreateRegistry() throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(port);
            // TODO (OG): The list is slow (1s) if no registry exists. Could be optimised on startup by other ways to check for a service on port 1099.
            registry.list();
        } catch (ConnectException ex) {
            System.out.println("Info: Could not connect to registry, assuming no registry exists, creating one");
            registry = LocateRegistry.createRegistry(port);
        }
        return registry;
    }

    public List<String> list() {
        try {
            String[] list = LocateRegistry.getRegistry(port).list();
            if (list == null || list.length == 0) return Collections.emptyList();
            return Arrays.asList(list);
        } catch (RemoteException ex) {
            return Collections.emptyList();
        }
    }

    public <T extends Remote> void bind(Class<T> clazz, T instance) throws RemoteException, NotBoundException {        
        remote = UnicastRemoteObject.exportObject(requireNonNull(instance, "instance must not be null"), 0);
        Registry reg = optionallyCreateRegistry();
        reg.rebind(requireNonNull(clazz, "clazz must not be null").getName(),
                 remote);
        System.out.println("Info: Bound " + clazz.getName());
    }

    public <T> T lookup( Class<T> clazz) throws RemoteException, NotBoundException {
        return (T)LocateRegistry.getRegistry(port).lookup(requireNonNull(clazz, "clazz must not be null").getName());
    }
    
}
