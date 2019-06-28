package view.gui.actions;

import view.gui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Action2 extends JOptionPane implements ActionListener {

    private GUI gui;
    private JFrame parent;
    JButton moveButton;
    JButton grabButton;
    JButton shootButton;

    public Action2(GUI gui, JFrame parent) {
        super();
        this.gui = gui;
        this.parent = parent;
        add(new JLabel("Choose the second action you want to do"));
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
                gui.moveSecondAction();
            else if(action == grabButton)
                gui.grabSecondAction();
            else if(action == shootButton)
                gui.shootSecondAction();
            action.setEnabled(false);
            parent.dispose();
        }catch (InterruptedException i) {

        }
    }
}

