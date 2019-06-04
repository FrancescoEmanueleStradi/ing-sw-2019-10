package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerClientHandler implements Runnable {

    private Socket socket;

    public SocketServerClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            in.close();
            out.close();
        } catch (IOException e ) {
            System.out.println("I/O Exception");
        }
    }
}