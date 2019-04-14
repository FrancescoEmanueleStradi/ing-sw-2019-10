package controller;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements ServerInterface {

    public Server() throws RemoteException {

    }

    public static void main(String args[]) throws AlreadyBoundException, RemoteException {
        System.out.println("Generating server...");

        Server central = new Server();

    }
}
