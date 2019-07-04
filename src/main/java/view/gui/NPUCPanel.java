package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * Panel for the Newton powerup requesting parameters before use.
 */
public class NPUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private String nickName;
    private JFrame parent;
    private JTextField txt1;
    private JTextField txt2;
    private JTextField txt3;
    private JTextField txt4;
    private JButton b;
    private Timer timer;
    private int turn;
    private String c;
    private List<String> l = new LinkedList<>();

    /**
     * Creates a new NPUCPanel.
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
    public NPUCPanel(GUI gui, ServerInterface server, JFrame parent, int game, String nickName, Timer timer, int turn, String c) {
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.turn = turn;
        this.c = c;
        this.timer = timer;

        add(new JLabel("Enter the nickname of a player you can see and that gave you damage:")).doLayout();
        txt1 = new JTextField("Write here", 25);
        add(txt1).doLayout();
        add(new JLabel("Enter the direction(s) in which you want the enemy to go")).doLayout();
        txt2 = new JTextField("", 25);
        add(txt2).doLayout();
        txt3 = new JTextField("", 25);
        add(txt3).doLayout();
        txt4 = new JTextField("", 25);
        add(txt4).doLayout();
        b = new JButton("Confirm");
        b.addActionListener(this);
        add(b);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            this.timer.cancel();
            l.add(txt1.getText());
            if (!txt2.getText().equals(""))
                l.add(txt2.getText());
            if (!txt3.getText().equals(""))
                l.add(txt3.getText());
            if (!txt4.getText().equals(""))
                l.add(txt4.getText());
            if (this.server.messageIsValidUsePowerUpCard(game, nickName, "Newton", c, l, null)) {
                this.server.messageUsePowerUpCard(game, nickName, "Newton", c, l, null);
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

        } catch (InterruptedException exc) {

        }
    }
}


