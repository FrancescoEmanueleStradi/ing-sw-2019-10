package view.gui;

import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class WantUsePUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private JFrame parent;
    private JButton firstButton;
    private JButton secondButton;

    public WantUsePUCPanel(GUI gui, JFrame parent) {
        this.gui = gui;
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
                gui.usePowerUpCard();
            }
            else if(action == secondButton) {
                parent.setVisible(false);
                parent.dispose();
                gui.action1();
            }

        }catch (InterruptedException i) {

        }catch (RemoteException ex){

        }
    }
}
