package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class UsePUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private String nickName;
    private java.util.Timer timer;
    private int turn;

    public UsePUCPanel(GUI gui, ServerInterface server, JFrame parent, int game, String nickName, java.util.Timer timer, int turn) throws RemoteException {
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.timer = timer;
        this.turn = turn;
        add(new JLabel("The following are " + this.nickName + "'s PowerUpCards")).setBounds(0, 0, 5, 5);
        add(new JLabel("Enter the name of the card you want to use")).doLayout();

        for (int i = 0; i < this.server.messageGetPowerUpCard(game, nickName).size(); i++) {
            add(new JButton(this.server.messageGetPowerUpCard(game, nickName).get(i) + "coloured" + this.server.messageGetPowerUpCardColour(game, nickName).get(i))).doLayout();
            add(new JLabel(server.messageGetDescriptionPUC(game, this.server.messageGetPowerUpCard(game, nickName).get(i), this.server.messageGetPowerUpCardColour(game, nickName).get(i), this.nickName))).doLayout();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //try {
            String action = e.getSource().toString();
            switch(action){
                /*case "Tagback Grenade coloured BLUE ":                //TODO
                    gui.TGPUC(timer, turn, BLUE);
                    break;

                case "Targeting Scope":
                    gui.TSPUCPanel(timer, turn);
                    break;

                case "Newton":
                    gui.NPUC(timer, turn);
                    break;

                case "Teleporter":
                    gui.TPUC(timer, turn);
                    break;
                default: break;*/
            }
            parent.setVisible(false);
            parent.dispose();

       //}
         /*catch (RemoteException r) {

        } catch (InterruptedException i) {

        }*/
    }

}
