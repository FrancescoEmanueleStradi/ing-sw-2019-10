package view.gui.rmi.powerups;

import network.ServerInterface;
import view.gui.rmi.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

/**
 * Panel for the Teleporter powerup requesting parameters before use.
 */
public class TPUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private String nickName;
    private JFrame parent;
    private JTextField txt1;
    private JTextField txt2;
    private JButton b;
    private java.util.Timer timer;
    private int turn;
    private String c;
    private List<String> l = new LinkedList<>();

    /**
     * Creates a new TPUCPanel.
     *
     * @param gui gui
     * @param server server
     * @param parent parent frame
     * @param game game
     * @param nickName nickname
     * @param timer timer
     * @param turn turn
     * @param c ammo cube colour
     */
    public TPUCPanel(GUI gui, ServerInterface server, JFrame parent , int game, String nickName, java.util.Timer timer, int turn, String c){
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.turn = turn;
        this.c = c;
        this.timer = timer;

        add(new JLabel("Enter the coordinates of the cell you want to move to:")).doLayout();
        txt1 = new JTextField("Write here", 25);
        add(txt1).doLayout();
        txt2 = new JTextField("Write here", 25);
        add(txt2).doLayout();
        b = new JButton("Confirm");
        b.addActionListener(this);
        add(b);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            this.timer.cancel();
            l.add(txt1.getText());
            l.add(txt2.getText());
            if (this.server.messageIsValidUsePowerUpCard(game, nickName, "Teleporter", c, l, null)) {
                this.server.messageUsePowerUpCard(game, nickName, "Teleporter", c, l, null);
                if (turn == 1)
                    gui.action1();
                if (turn == 2)
                    gui.action2();
                if (turn == 3)
                    gui.reload();
            } else {
                gui.usePowerUpCard();
            }
            parent.setVisible(false);
            parent.dispose();
        } catch (RemoteException ex) {

        } catch (InterruptedException exc){

        }
    }
}
