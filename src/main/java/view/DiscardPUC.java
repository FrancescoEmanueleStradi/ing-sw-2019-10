package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class DiscardPUC extends JOptionPane implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private JButton firstButton;
    private JButton secondButton;
    private String nickName;
    private String n1;
    private String n2;
    private String c1;
    private String c2;


    public DiscardPUC( GUI gui, ServerInterface server, int game, String nickName, String n1, String n2, String c1, String c2){
        super();
        this.gui = gui;
        this.server = server;
        this.game = game;
        this.nickName = nickName;
        this.n1 = n1;
        this.c1 = c1;
        this.c2 = c2;
        add(new JLabel("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                "colour of your spawn point"));
        firstButton = new JButton(this.n1 + " coloured " + this.c1);
        add(firstButton).doLayout();
        firstButton.addActionListener(this);
        secondButton = new JButton(this.n2 + " coloured " + this.c2);
        add(secondButton).doLayout();
        secondButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();
            if (action == firstButton){
                while(!this.server.messageIsValidPickAndDiscard(game, nickName, n1, c1))
                    gui.selectSpawnPoint();
                server.messagePickAndDiscardCard(game, nickName, n1, c1);
            }
            if (action == secondButton) {
                while (!this.server.messageIsValidPickAndDiscard(game, nickName, n2, c2))
                    gui.selectSpawnPoint();
                server.messagePickAndDiscardCard(game, nickName, n2, c2);
            }
            notifyAll();
        }catch (RemoteException r){

        }catch (InterruptedException i){

        }
    }

}
