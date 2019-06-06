package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerClientHandler implements Runnable {

    private Socket socket;
    private ServerInterface server;

    public SocketServerClientHandler(Socket socket, ServerInterface server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            Scanner inScanner = new Scanner(socket.getInputStream());
            PrintWriter outScanner = new PrintWriter(socket.getOutputStream(), true);

            while(true) {
                boolean exit = false;
                switch (inScanner.nextLine()) {
                    case "Get Games":
                        outScanner.println(server.getGames());
                        break;
                    case "Is A Suspended Identifier":
                        outScanner.println(server.isASuspendedIdentifier(Integer.parseInt(inScanner.nextLine()), Integer.parseInt(inScanner.nextLine())));
                        break;
                }
                if(exit)
                    break;
            }

            inScanner.close();
            outScanner.close();
            socket.close();
        } catch (IOException e ) {
            System.out.println("I/O Exception");
        }
    }
}