package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketHandler {

    private int port;
    private ServerMethods server;

    public SocketHandler(int port, ServerMethods server) {
        this.port = port;
        this.server = server;
    }

    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException | NullPointerException e) {
            System.out.println("Port not available!");
        }
        System.out.println("\nAdrenaline Socket Server ready.\nClient may now invoke methods.");
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