package view.gui;

import view.gui.socket.GUISocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Prompts choice of using a powerup card or not. Use this after the end of the second
 * action.
 */
public class WantUsePUCPanel3 extends JPanel implements ActionListener {

    private GUI gui;
    private GUISocket guiSocket;
    private JFrame parent;
    private JButton firstButton;
    private JButton secondButton;

    /**
     * Creates a new WantUsePUCPanel3.
     *
     * @param gui gui
     * @param guiSocket socket gui
     * @param parent parent frame
     */
    public WantUsePUCPanel3(GUI gui, GUISocket guiSocket, JFrame parent) {
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
    public void actionPerformed(ActionEvent e)  {
        try {
            JButton action = (JButton)e.getSource();
            if(action == firstButton) {
                parent.setVisible(false);
                parent.dispose();
                if(gui != null)
                    gui.usePowerUpCard3();
                else
                    guiSocket.usePowerUpCard3();
            }
            else if(action == secondButton) {
                parent.setVisible(false);
                parent.dispose();
                if(gui != null)
                    gui.reload();
                else
                    guiSocket.reload();
            }

        }catch (InterruptedException i) {

        }catch (RemoteException r){

        }
    }
}