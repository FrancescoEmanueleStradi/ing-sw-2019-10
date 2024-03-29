package view.gui.rmi.finalfrenzy;

import network.ServerInterface;
import view.gui.rmi.GUI;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * First Final Frenzy action panel.
 */
public class FFAction1 extends JPanel {

    private GUI gui;
    private ServerInterface server;
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
     * Creates a new FFAction1.
     *
     * @param gui gui
     * @param server server
     * @param parent parent
     * @param game game
     * @param nickName nickname
     */
    public FFAction1(GUI gui, ServerInterface server, JFrame parent, int game, String nickName) {
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        add(new JLabel("Final Frenzy action 1.\n" +
                "You may move one square, reload (optional), then shoot."));




    }

}
