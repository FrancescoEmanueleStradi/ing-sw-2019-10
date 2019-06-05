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
            PrintWriter outScanner = new PrintWriter(socket.getOutputStream(), true);

            Server server = new Server();

            while(true) {
                switch (inScanner.nextLine()) {
                    case "Get Games":
                        outScanner.println(server.getGames());
                        break;
                    case "Is A Suspended Identifier":
                        if(server.isASuspendedIdentifier(Integer.parseInt(inScanner.nextLine()), Integer.parseInt(inScanner.nextLine())))
                            outScanner.println("true");
                        else
                            outScanner.println("false");
                        break;
                }
                if(inScanner.nextLine().equals("QUIT"))
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