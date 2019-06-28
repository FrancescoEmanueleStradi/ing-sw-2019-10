package network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Purpose of this class is to abstract binding to RMI server from anything socket-related.
 */
public class RMIHandler {

    /**
     * Creates an RMIHandler, binding the server to the registry with fixed port number.
     *
     * @param methods server methods
     * @throws RemoteException RMI exception
     */
    public RMIHandler(ServerMethods methods) throws RemoteException {
        System.out.println("\nGenerating Adrenaline RMI Server...");

        System.out.println("Binding server to registry...");
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("central_server", methods);

        System.out.println("Adrenaline RMI Server ready.\nClient may now invoke methods.");
    }
}