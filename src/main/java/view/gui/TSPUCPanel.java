package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TSPUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private String nickName;
    private JFrame parent;
    private JTextField txt1;
    private JButton b;
    private java.util.Timer timer;
    private int turn;
    private String c;

    public TSPUCPanel(GUI gui, ServerInterface server, JFrame parent , int game, String nickName, java.util.Timer timer, int turn, String c){
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.turn = turn;
        this.c = c;

        add(new JLabel("Enter the nickname of a player you can see and that gave you damage:")).doLayout();
        txt1 = new JTextField("Write here", 25);
        add(txt1).doLayout();
        b = new JButton("Confirm");
        b.addActionListener(this);
        add(b);
    }

    public void actionPerformed(ActionEvent e) {
        //TODO
    }
}
