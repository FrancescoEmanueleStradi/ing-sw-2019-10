package network;

import network.rmi.RMIHandler;
import network.socket.SocketHandler;

import java.rmi.*;
import java.rmi.server.*;

/**
 * The class from which a single server, supporting multiple games, is created.
 */
public class Server extends UnicastRemoteObject {

    /**
     * Calls to super() per the standard RMI implementation.
     *
     * @throws RemoteException RMI exception
     */
    public Server() throws RemoteException {
        super();
    }

    /**
     * The entry point of the application, creating both an RMIHandler and a SocketHandler, without need for
     * further abstractions,
     *
     * @param args input arguments
     * @throws RemoteException RMI exception
     */
    public static void main(String[] args) throws RemoteException {

        ServerMethods methods = new ServerMethods();

        RMIHandler rmiHandler = new RMIHandler(methods);
        SocketHandler socketHandler = new SocketHandler(9876, methods);
        socketHandler.startServer();
    }
}