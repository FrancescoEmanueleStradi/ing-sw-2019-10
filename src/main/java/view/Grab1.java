package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grab1 extends JPanel {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private int identifier;
    private String nickName;
    private JButton weaponConfirm;
    private JButton ammoConfirm;
    private JFrame weaponFrame;
    private JFrame ammoFrame;

    public Grab1(GUI gui, ServerInterface server, int game, int identifier, String nickName) {
        super();
        this.gui = gui;
        this.server = server;
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        add(new Label("Grab AmmoCard or WeaponCard?"));
        weaponConfirm = new JButton("WeaponCard");
        weaponConfirm.addActionListener(new CardConfirmed());
        ammoConfirm = new JButton("AmmoCard");
        ammoConfirm.addActionListener(new CardConfirmed());
    }

    private class CardConfirmed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton cardConfirm = (JButton)e.getSource();
            if (cardConfirm == weaponConfirm)
                weaponGrab();
            if (cardConfirm == ammoConfirm)
                ammoGrab();
                //notifyAll();
        }
    }

    private class SomethingConfirmed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    public void weaponGrab() {

    }

    public void ammoGrab() {

    }
}
