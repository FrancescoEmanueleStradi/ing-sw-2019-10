package network;

import java.net.ServerSocket;
import java.util.concurrent.*;

public class MultiThreadedServer {

    private int portNum;

    public MultiThreadedServer(int port) {
        portNum = port;
    }

    public void startServer() {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket socket;
    }
}
