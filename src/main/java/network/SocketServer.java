package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

    private int port;

    public SocketServer(int port) {
        this.port = port;
    }

    public static void main(String args[]){
        //create the socket server object
        SocketServer server = new SocketServer(9876);
        server.startServer();
    }

    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Port not available!");
        }
        System.out.println("Adrenaline Server Socket ready");
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new SocketServerClientHandler(socket));
            } catch (IOException e) {
                break;
            }
        }
        executor.shutdown();
    }
}