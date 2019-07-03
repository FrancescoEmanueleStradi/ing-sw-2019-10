package view.gui;

import view.gui.socket.GUISocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class WantUsePUCPanel2 extends JPanel implements ActionListener {

    private GUI gui;
    private GUISocket guiSocket;
    private JFrame parent;
    private JButton firstButton;
    private JButton secondButton;

    public WantUsePUCPanel2(GUI gui, GUISocket guiSocket, JFrame parent) {

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
                    gui.usePowerUpCard2();
                else
                    guiSocket.usePowerUpCard2();
            }
            else if(action == secondButton) {
                parent.setVisible(false);
                parent.dispose();
                if(gui != null)
                    gui.action2();
                else
                    guiSocket.action2();
            }

        }catch (InterruptedException i) {

        }catch (RemoteException ex){

        }
    }
}
