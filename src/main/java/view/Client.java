package view;

import controller.ServerInterface;

import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import javax.naming.*;

public class Client {

    public static void main(String[] args) throws NamingException, RemoteException, AlreadyBoundException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        ServerInterface centralServer = (ServerInterface)registry.lookup("rmi://localhost:5099/central");
        System.out.println(centralServer.getSomeMessage());
    }

}
