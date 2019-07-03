package network;

import network.rmi.RMIProcesses;
import network.socket.SocketProcesses;

import java.io.IOException;
import java.net.Socket;
import java.rmi.*;
import java.util.Scanner;

/**
 * The Client class is self-explanatory. An arbitrary amount of clients may be opened, but up to 5 are permitted
 * per game.
 * Users may use either a socket or an RMI connection.
 */
public class Client {

    /**
     * The main method from which the client is launched.
     *
     * @param args input arguments
     * @throws NotBoundException    Failure to bind to RMI socket
     * @throws InterruptedException Thread interruption
     * @throws IOException          I/O exception of some sort
     */
    public static void main(String[] args) throws NotBoundException, InterruptedException, IOException {

        Scanner in = new Scanner(System.in);

        System.out.println("\n                    Welcome to");
        System.out.println("           A  D  R  E  N  A  L  I  N  E\n");

        System.out.println("Enter the Server IP address:");
        String serverIP = in.nextLine();

        System.out.println("Choose the type of connection between Socket and RMI:");

        boolean rmiSocket = false;
        do {
            switch(in.next()) {
                case "SOCKET":
                case "Socket":
                case "socket":
                    rmiSocket = true;
                    System.out.println("Creating socket connection...");
                    Socket socket = new Socket(serverIP, 9876);
                    System.out.println("Socket connection created successfully!\n");
                    SocketProcesses.socketProcesses(socket);
                    break;
                case "Rmi":
                case "RMI":
                case "rmi":
                    rmiSocket = true;
                    System.out.println("Creating RMI connection, please wait...");
                    ServerInterface centralServer = (ServerInterface) Naming.lookup("rmi://" + serverIP + ":5099/central_server");
                    System.out.println("RMI connection created successfully!\n");
                    RMIProcesses.rmiProcesses(centralServer);
                    break;
                default: break;
            }
        } while(!rmiSocket);
    }
}