package network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIHandler {

    public RMIHandler() throws RemoteException {
        System.out.println("Generating RMI Adrenaline server...");
        Server centralServer = new Server();

        System.out.println("Binding server to registry...");
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("central_server", centralServer);

        System.out.println("Adrenaline RMI Server ready.\nClient may now invoke methods.");
    }
}