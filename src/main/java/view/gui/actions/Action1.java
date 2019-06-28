package view.gui.actions;

import view.gui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class Action1 extends JPanel implements ActionListener {

    private GUI gui;
    JFrame parent;
    JButton moveButton;
    JButton grabButton;
    JButton shootButton;

    public Action1(GUI gui, JFrame parent) {
        super();
        this.gui = gui;
        this.parent = parent;
        add(new JLabel("Choose the first action you want to do"));
        moveButton = new JButton("Move");
        add(moveButton).doLayout();
        moveButton.addActionListener(this);
        grabButton = new JButton("Grab");
        add(moveButton).doLayout();
        grabButton.addActionListener(this);
        shootButton = new JButton("Shoot");
        add(moveButton).doLayout();
        shootButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();
            if(action == moveButton)
                gui.moveFirstAction();
            else if(action == grabButton)
                gui.grabFirstAction();
            else if(action == shootButton)
                gui.shootFirstAction();
            action.setEnabled(false);
            parent.dispose();
        }catch (InterruptedException | RemoteException i) {
            //TODO
        }
    }
}
