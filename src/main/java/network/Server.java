package network;

import controller.Game;
import model.Colour;
import view.View;

import java.rmi.*;
import java.rmi.server.*;
import java.util.LinkedList;
import java.util.List;


public class Server extends UnicastRemoteObject {

    public Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException {

        ServerMethods methods = new ServerMethods();

        RMIHandler rmiHandler = new RMIHandler(methods);
        SocketHandler socketHandler = new SocketHandler(9876, methods);
        socketHandler.startServer();
    }
}