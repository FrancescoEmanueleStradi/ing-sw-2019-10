package network;

import view.gui.GUI;
import view.View;
import view.cli.CLI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.*;
import java.util.Scanner;
import java.util.Timer;
import javax.naming.*;

public class Client {

    private static View view;
    private static int game;
    private static int identifier;

    private static Socket socket;

    public static void main(String[] args) throws NamingException, RemoteException, AlreadyBoundException, NotBoundException, MalformedURLException, InterruptedException, UnknownHostException, IOException, ClassNotFoundException {

        System.out.println("\n                    Welcome to");
        System.out.println("           A  D  R  E  N  A  L  I  N  E\n");

        System.out.println("Choose the type of connection between Socket and RMI:");
        Scanner in = new Scanner(System.in);

        boolean rmiSocket = false;
        do {
            switch(in.next()) {
                case "SOCKET":
                case "Socket":
                case "socket":
                    rmiSocket = true;
                    socket = new Socket("localhost", 9876);
                    SocketProcesses.socketProcesses(socket);
                    break;
                case "Rmi":
                case "RMI":
                case "rmi":
                    rmiSocket = true;
                    ServerInterface centralServer = (ServerInterface) Naming.lookup("rmi://localhost:5099/central_server");
                    RMIProcesses.rmiProcesses(centralServer);
                    break;
            }
        } while(!rmiSocket);
    }
}