package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

public class FFAction5 extends JPanel {

    private GUI gui;
    private ServerInterface server;
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

    public FFAction5(GUI gui, ServerInterface server, JFrame parent, int game, String nickName, Timer timer) {
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.timer = timer;

        add(new JLabel("Final Frenzy action 5.\n" +
                "You may move up to 3 squares and grab something."));
    }
}
