package view.gui.socket.finalfrenzy;

import view.gui.socket.GUISocket;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

/**
 * Fourth Final Frenzy action panel.
 */
public class FFAction4Socket extends JPanel {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private JFrame parent;
    private int game;
    private String nickName;
    private Timer timer;

    private List<Integer> directions = new LinkedList<>();
    private JButton leftArrow;
    private JButton rightArrow;
    private JButton upArrow;
    private JButton downArrow;
    private JButton reset;
    private int dirCount;
    private JButton b;

    /**
     * Creates a new FFAction4Socket.
     *
     * @param gui gui
     * @param socket socket
     * @param parent parent
     * @param game game
     * @param nickName nickname
     * @throws IOException I/O exception of some sort
     */
    public FFAction4Socket(GUISocket gui, Socket socket, JFrame parent, int game, String nickName) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        add(new JLabel("Final Frenzy action 4.\n" +
                "You may move up to 2 squares, reload (optional), then shoot."));
    }
}