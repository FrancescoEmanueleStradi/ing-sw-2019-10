package view.gui;

import network.ServerInterface;

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


    public DiscardPUC(GUI gui, ServerInterface server, int game, String nickName, String n1, String n2, String c1, String c2) throws RemoteException{
        super();
        this.gui = gui;
        this.server = server;
        this.game = game;
        this.nickName = nickName;
        this.n1 = n1;
        this.n2 = n2;
        this.c1 = c1;
        this.c2 = c2;
        CardLinkList l = new CardLinkList();
        add(new JLabel("The following are " + this.nickName +"'s starting PowerUpCards"));
        add(new JLabel(l.getImageIconFromName(this.server.messageGetPowerUpCard(game, this.nickName).get(0), this.server.messageGetPowerUpCardColour(game, this.nickName).get(0))));
        add(new JLabel(l.getImageIconFromName(this.server.messageGetPowerUpCard(game, this.nickName).get(1), this.server.messageGetPowerUpCardColour(game, this.nickName).get(1))));
        add(new JLabel("\nSPAWN POINT SELECT\n"));
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
            if(action == firstButton) {
                while(!this.server.messageIsValidPickAndDiscard(game, nickName, n1, c1))
                    gui.selectSpawnPoint();
                server.messagePickAndDiscardCard(game, nickName, n1, c1);
                gui.printType();
            }
            else if(action == secondButton) {
                while(!this.server.messageIsValidPickAndDiscard(game, nickName, n2, c2))
                    gui.selectSpawnPoint();
                server.messagePickAndDiscardCard(game, nickName, n2, c2);
                gui.printType();
            }
        }catch (RemoteException r) {

        }catch (InterruptedException i) {

        }
    }

}
