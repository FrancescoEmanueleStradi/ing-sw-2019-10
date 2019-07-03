package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class UsePUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private String nickName;
    private java.util.Timer timer;
    private int turn;
    private List<JButton> l = new LinkedList<>();

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
        add(new JLabel("Press the button near the card you want to use")).doLayout();

        for (int i = 0; i < this.server.messageGetPowerUpCard(game, nickName).size(); i++) {
            add(new JLabel(this.server.messageGetPowerUpCard(game, nickName).get(i) + "coloured" + this.server.messageGetPowerUpCardColour(game, nickName).get(i))).doLayout();
            l.add(new JButton(Integer.toString(i)));
            this.add(l.get(l.size()-1)).doLayout();
            l.get(l.size()-1).addActionListener(this);
            add(new JLabel(server.messageGetDescriptionPUC(game, this.server.messageGetPowerUpCard(game, nickName).get(i), this.server.messageGetPowerUpCardColour(game, nickName).get(i), this.nickName))).doLayout();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String action = e.getSource().toString();
            switch(this.server.messageGetPowerUpCard(game, nickName).get(Integer.parseInt(action))){
                case "Tagback Grenade":
                    gui.TGPUC(timer, turn, this.server.messageGetPowerUpCardColour(game, nickName).get(Integer.parseInt(action)));
                    break;

                case "Targeting Scope":
                    gui.TSPUC(timer, turn, this.server.messageGetPowerUpCardColour(game, nickName).get(Integer.parseInt(action)));
                    break;

                case "Newton":
                    gui.NPUC(timer, turn, this.server.messageGetPowerUpCardColour(game, nickName).get(Integer.parseInt(action)));
                    break;

                case "Teleporter":
                    gui.TPUC(timer, turn, this.server.messageGetPowerUpCardColour(game, nickName).get(Integer.parseInt(action)));
                    break;
                default: break;
            }
            parent.setVisible(false);
            parent.dispose();

       }
         catch (RemoteException r) {

        } catch (InterruptedException i) {

        }
    }

}
