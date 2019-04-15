package view;

import controller.ServerInterface;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import javax.naming.*;

public class Client {

    public static void main(String[] args) throws NamingException, RemoteException, AlreadyBoundException, NotBoundException, MalformedURLException {
        //Registry registry = LocateRegistry.getRegistry();
        ServerInterface centralServer = (ServerInterface)Naming.lookup("rmi://localhost:5099/central_server");
        System.out.println("Client --> " + centralServer.echo("Hello there!"));
    }

}
