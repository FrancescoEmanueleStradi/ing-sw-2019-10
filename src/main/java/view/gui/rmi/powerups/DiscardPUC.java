package view.gui.rmi.powerups;

import network.ServerInterface;
import view.gui.common.CardLinkList;
import view.gui.rmi.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Panel prompting to discard a powerup card for the spawn point.
 */
public class DiscardPUC extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private JButton firstButton;
    private JButton secondButton;
    private String nickName;
    private String n1;
    private String n2;
    private String c1;
    private String c2;

    /**
     * Creates a new DiscardPUC.
     *
     * @param gui gui
     * @param server server
     * @param game game
     * @param nickName nickname
     * @param n1 powerup 1
     * @param n2 powerup 2
     * @param c1 powerup colour 1
     * @param c2 powerup colour 2
     * @param parent parent frame
     * @throws RemoteException
     */
    public DiscardPUC(GUI gui, ServerInterface server, int game, String nickName, String n1, String n2, String c1, String c2, JFrame parent) throws RemoteException{
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.n1 = n1;
        this.n2 = n2;
        this.c1 = c1;
        this.c2 = c2;
        CardLinkList l = new CardLinkList();
        add(new JLabel("The following are " + this.nickName +"'s starting PowerUpCards")).setBounds(0,0, 5, 5);
        add(new JLabel("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                "colour of your spawn point")).doLayout();
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
                parent.setVisible(false);
                parent.dispose();
                gui.printType();
            }
            else if(action == secondButton) {
                while(!this.server.messageIsValidPickAndDiscard(game, nickName, n2, c2))
                    gui.selectSpawnPoint();
                server.messagePickAndDiscardCard(game, nickName, n2, c2);
                parent.setVisible(false);
                parent.dispose();
                gui.printType();
            }
        }catch (RemoteException r) {

        }catch (InterruptedException i) {

        }
    }

}
