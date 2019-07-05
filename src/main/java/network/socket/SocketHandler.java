package network.socket;

import network.ServerMethods;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Purpose of this class is to abstract socket creation from anything explicity RMI-related.
 */
public class SocketHandler {

    private int port;
    private ServerMethods server;

    /**
     * Creates a SocketHandler.
     *
     * @param port   port number
     * @param server server
     */
    public SocketHandler(int port, ServerMethods server) {
        this.port = port;
        this.server = server;
    }

    /**
     * Does *not* start a new server but launches a socket as a new thread linked to the existing server.
     */
    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;

        try {
            System.out.println("\nGenerating Adrenaline Socket Server...");
            serverSocket = new ServerSocket(port);
        } catch (IOException | NullPointerException e) {
            System.out.println("Port not available!");
            return;
        }

        System.out.println("Adrenaline Socket Server ready.\nClient may now invoke methods.");

        while(true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new SocketServerClientHandler(socket, server));
            } catch (IOException | NullPointerException e) {
                break;
            }
        }
        executor.shutdown();
    }
}