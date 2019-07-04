package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * Fourth Final Frenzy action panel.
 */
public class FFAction4 extends JPanel {

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
     * Creates a new FFAction4.
     *
     * @param gui gui
     * @param server server
     * @param parent parent
     * @param game game
     * @param nickName nickname
     */
    public FFAction4(GUI gui, ServerInterface server, JFrame parent, int game, String nickName) {
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        add(new JLabel("Final Frenzy action 4.\n" +
                "You may move up to 2 squares, reload (optional), then shoot."));
    }
}
