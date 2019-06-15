package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.TimerTask;

public class MyTaskSocket extends TimerTask {
    private int game;
    private int identifier;
    private String nickName;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;

    MyTaskSocket(int game, int identifier, String nickName, Socket socket) throws IOException {
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
    }

    public void run() {
        socketOut.println("Manage Disconnection");
        socketOut.println(game);
        socketOut.println(identifier);
        socketOut.println(nickName);

        socketOut.println("Finish Turn");
        socketOut.println(game);

        System.exit(0);
    }
}