package network;

import view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerClientHandler implements Runnable {

    private Socket socket;
    private ServerMethods server;

    public SocketServerClientHandler(Socket socket, ServerMethods server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            Scanner inScanner = new Scanner(socket.getInputStream());
            PrintWriter outPrinter = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());

            while(true) {
                boolean exit = false;
                switch (inScanner.nextLine()) {
                    case "Get Games":
                        outPrinter.println(server.getGames());
                        break;
                    case "Is A Suspended Identifier":
                        outPrinter.println(server.isASuspendedIdentifier(Integer.parseInt(inScanner.nextLine()), Integer.parseInt(inScanner.nextLine())));
                        break;
                    case "Server Methods":
                        outObject.writeObject(server);
                        break;
                    case "Set View":
                        server.setView(inScanner.nextInt(), inScanner.nextInt(), (View) inObject.readObject());
                        break;
                    case "Get Type":
                        outPrinter.println(server.getType(inScanner.nextInt()));
                        break;
                    case "Manage Reconnection":
                        server.manageReconnection(inScanner.nextInt(), inScanner.nextInt());
                        break;
                }
                if(exit)
                    break;
            }

            inScanner.close();
            outPrinter.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e ) {
            System.out.println("Socket Server Client Handler Exception");
        }
    }
}