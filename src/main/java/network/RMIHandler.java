package network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIHandler {

    public RMIHandler(ServerMethods methods) throws RemoteException {
        System.out.println("\nGenerating Adrenaline RMI Server...");

        System.out.println("Binding server to registry...");
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("central_server", methods);

        System.out.println("Adrenaline RMI Server ready.\nClient may now invoke methods.");
    }
}