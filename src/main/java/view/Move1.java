package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class Move1 extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private int identifier;
    private String nickName;
    private JButton b;
    private JTextField txt1;
    private JTextField txt2;
    private JTextField txt3;

    public Move1(GUI gui, ServerInterface server, int game, int identifier, String nickName) {
        super();
        this.gui = gui;
        this.server = server;
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        b = new JButton("Confirm");
        txt1 = new JTextField("Write here", 25);
        txt2 = new JTextField("Write here", 25);
        txt3 = new JTextField("Write here", 25);
        b.addActionListener(this);
        add(new Label(("Enter your first direction:")));
        add(txt1);
        add(new Label("Enter your second direction:"));
        add(txt2);
        add(new Label("Enter your third direction:"));
        add(txt3);
        add(b);
    }

    public synchronized void actionPerformed(ActionEvent e) {
        try {
            List<Integer> l = new LinkedList<>();
            l.add(Integer.parseInt(txt1.getText()));
            l.add(Integer.parseInt(txt2.getText()));
            l.add(Integer.parseInt(txt3.getText()));
            while (!server.messageIsValidFirstActionMove(game, nickName, l))
                gui.moveFirstAction();
            server.messageFirstActionMove(game, nickName, l);
            notifyAll();
        } catch (RemoteException r) {

        } catch (InterruptedException i) {

        }


    }
}


