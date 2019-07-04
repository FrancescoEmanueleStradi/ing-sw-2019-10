package view.gui.common;

import view.gui.rmi.GUI;
import view.gui.socket.GUISocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Prompts choice of using a powerup card. Use this before any action.
 */
public class WantUsePUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private GUISocket guiSocket;
    private JFrame parent;
    private JButton firstButton;
    private JButton secondButton;

    /**
     * Creates a new WantUsePUCPanel.
     *
     * @param gui gui
     * @param guiSocket socket gui
     * @param parent parent frame
     */
    public WantUsePUCPanel(GUI gui, GUISocket guiSocket, JFrame parent) {
        if(gui != null)
            this.gui = gui;
        else
            this.guiSocket = guiSocket;

        this.parent = parent;
        add(new JLabel("Do you want to use Power Up card?"));
        firstButton = new JButton("Yes");
        add(firstButton).doLayout();
        firstButton.addActionListener(this);
        secondButton = new JButton("No");
        add(secondButton).doLayout();
        secondButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();
            if(action == firstButton) {
                parent.setVisible(false);
                parent.dispose();
                if(gui != null)
                    gui.usePowerUpCard();
                else
                    guiSocket.usePowerUpCard();
            }
            else if(action == secondButton) {
                parent.setVisible(false);
                parent.dispose();
                if(gui != null)
                    gui.action1();
                else
                    guiSocket.action1();
            }

        }catch (InterruptedException i) {

        }catch (RemoteException ex){

        }
    }
}