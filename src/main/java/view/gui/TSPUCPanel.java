package view.gui;

import model.Colour;
import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class TSPUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private String nickName;
    private JFrame parent;
    private JTextField txt1;
    private JTextField txt2;
    private JButton b;
    private JButton button;
    private java.util.Timer timer;
    private int turn;
    private String c;
    private List<String> l = new LinkedList<>();

    public TSPUCPanel(GUI gui, ServerInterface server, JFrame parent , int game, String nickName, java.util.Timer timer, int turn, String c){
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
        add(new JLabel("Enter the colour of the AmmoCube you want to use to pay:")).doLayout();
        txt2 = new JTextField("Write here", 25);
        add(txt2).doLayout();
        b = new JButton("Confirm end end");
        b.addActionListener(this);
        button = new JButton("Confirm and continue to choose another player");
        button.addActionListener(this);
        add(b);
        add(button);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();
            if(timer != null)
                this.timer.cancel();
            if(action == b) {
                l.add(txt1.getText());
                if (this.server.messageIsValidUsePowerUpCard(game, nickName, "Targeting Scope", c, l, Colour.valueOf(txt2.getText()))) {
                    this.server.messageUsePowerUpCard(game, nickName, "Targeting Scope", c, l, Colour.valueOf(txt2.getText()));
                    if (turn == 1)
                        gui.action1();
                    if (turn == 2)
                        gui.action2();
                    if (turn == 3)
                        gui.reload();
                } else {
                    gui.usePowerUpCard();
                }
            }
            else {
                l.add(txt1.getText());
                if (this.server.messageIsValidUsePowerUpCard(game, nickName, "Targeting Scope", c, l, Colour.valueOf(txt2.getText()))) {
                    this.server.messageUsePowerUpCard(game, nickName, "Targeting Scope", c, l, Colour.valueOf(txt2.getText()));
                    gui.TSPUC(null, turn, c);
                }
                else
                    gui.usePowerUpCard();

            }
            parent.setVisible(false);
            parent.dispose();
        } catch (RemoteException ex) {

        } catch (InterruptedException exc){

        }

    }
}
