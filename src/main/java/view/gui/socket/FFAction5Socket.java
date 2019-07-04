package view.gui.socket;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

public class FFAction5Socket extends JPanel {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private JFrame parent;
    private int game;
    private String nickName;
    private java.util.Timer timer;

    private List<Integer> directions = new LinkedList<>();
    private JButton leftArrow;
    private JButton rightArrow;
    private JButton upArrow;
    private JButton downArrow;
    private JButton reset;
    private int dirCount;
    private JButton b;

    public FFAction5Socket(GUISocket gui, Socket socket, JFrame parent, int game, String nickName, Timer timer) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.timer = timer;

        add(new JLabel("Final Frenzy action 5.\n" +
                "You may move up to 3 squares and grab something."));
    }
}