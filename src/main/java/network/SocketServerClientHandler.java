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
            Scanner inScanner = new Scanner(socket.getInputStream());
            PrintWriter outScanner = new PrintWriter(socket.getOutputStream());

            inScanner.close();
            outScanner.close();
        } catch (IOException e ) {
            System.out.println("I/O Exception");
        }
    }
}