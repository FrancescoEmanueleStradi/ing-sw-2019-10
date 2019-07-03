package view.gui.actions;

import view.gui.*;
import view.gui.socket.GUISocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Action1 extends JPanel implements ActionListener {

    private GUI gui = null;
    private GUISocket guiSocket = null;
    JFrame parent;
    JButton moveButton;
    JButton grabButton;
    JButton shootButton;

    public Action1(GUI gui, GUISocket guiSocket, JFrame parent) {
        super();

        if(gui != null)
            this.gui = gui;
        else
            this.guiSocket = guiSocket;

        this.parent = parent;
        add(new JLabel("Choose the first action you want to do"));
        moveButton = new JButton("Move");
        add(moveButton).doLayout();
        moveButton.addActionListener(this);
        grabButton = new JButton("Grab");
        add(grabButton).doLayout();
        grabButton.addActionListener(this);
        shootButton = new JButton("Shoot");
        add(shootButton).doLayout();
        shootButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();
            if(action == moveButton) {
                if(gui != null)
                    gui.moveFirstAction();
                else
                    guiSocket.moveFirstAction();
            }
            else if(action == grabButton) {
                if(gui != null)
                    gui.grabFirstAction();
                else
                    guiSocket.grabFirstAction();
            }
            else if(action == shootButton) {
                if(gui != null)
                    gui.shootFirstAction();
                else
                    guiSocket.shootFirstAction();
            }

            action.setEnabled(false);
            parent.dispose();
        }catch (InterruptedException | IOException i) {

        }
    }
}