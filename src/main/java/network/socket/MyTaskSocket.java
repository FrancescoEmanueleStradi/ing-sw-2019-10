package network.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.TimerTask;

/**
 * Functions similarly to MyTask but with the necessary socket-specific additions.
 */
public class MyTaskSocket extends TimerTask {

    private int game;
    private int identifier;
    private String nickName;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;

    /**
     * Instantiates a new My task socket.
     *
     * @param game       game number
     * @param identifier player identifier
     * @param nickName   player nickname
     * @param socket     socket
     * @throws IOException some I/O exception
     */
    public MyTaskSocket(int game, int identifier, String nickName, Socket socket) throws IOException {
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
    }

    /**
     * Launches thread to carry out the task. It aims to safely terminate the program should a player disconnect
     * (and the conditions in manageDisconnection are met).
     */
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