package view.gui.rmi.powerups;

import network.ServerInterface;
import view.gui.rmi.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

/**
 * Panel prompting choice of powerup card.
 */
public class UsePUCPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private String nickName;
    private java.util.Timer timer;
    private int turn;
    private List<JButton> l = new LinkedList<>();

    /**
     * Creates a new UsePUCPanel.
     *
     * @param gui gui
     * @param server server
     * @param parent parent frame
     * @param game game
     * @param nickName nickname
     * @param timer timer
     * @param turn turn
     * @throws RemoteException RMI exeption
     */
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

        for (int i = 0; i < this.server.messageGetPlayerPowerUpCard(game, nickName).size(); i++) {
            add(new JLabel(this.server.messageGetPlayerPowerUpCard(game, nickName).get(i) + "coloured" + this.server.messageGetPlayerPowerUpCardColour(game, nickName).get(i))).doLayout();
            l.add(new JButton(Integer.toString(i)));
            this.add(l.get(l.size()-1)).doLayout();
            l.get(l.size()-1).addActionListener(this);
            add(new JLabel(server.messageGetPlayerDescriptionPUC(game, this.server.messageGetPlayerPowerUpCard(game, nickName).get(i), this.server.messageGetPlayerPowerUpCardColour(game, nickName).get(i), this.nickName))).doLayout();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();
            switch(this.server.messageGetPlayerPowerUpCard(game, nickName).get(Integer.parseInt(action.getText()))){
                case "Tagback Grenade":
                    gui.TGPUC(timer, turn, this.server.messageGetPlayerPowerUpCardColour(game, nickName).get(Integer.parseInt(action.getText())));
                    break;

                case "Targeting Scope":
                    gui.TSPUC(timer, turn, this.server.messageGetPlayerPowerUpCardColour(game, nickName).get(Integer.parseInt(action.getText())));
                    break;

                case "Newton":
                    gui.NPUC(timer, turn, this.server.messageGetPlayerPowerUpCardColour(game, nickName).get(Integer.parseInt(action.getText())));
                    break;

                case "Teleporter":
                    gui.TPUC(timer, turn, this.server.messageGetPlayerPowerUpCardColour(game, nickName).get(Integer.parseInt(action.getText())));
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
