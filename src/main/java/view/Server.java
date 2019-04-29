package view;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public class Server extends UnicastRemoteObject implements ServerInterface {

    /*private String someMessage = "Hello there";

    public String getSomeMessage() {
        return someMessage;
    }*/

    public Server() throws RemoteException {
        super();
    }

    public String echo(String input) throws RemoteException {
        return "From server: " + input;
    }

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        System.out.println("Generating server...");
        Server centralServer = new Server();

        System.out.println("Binding server to registry...");
        //Registry registry = LocateRegistry.getRegistry();
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("central_server", centralServer);

        System.out.println("Client may now invoke methods");
    }
}
