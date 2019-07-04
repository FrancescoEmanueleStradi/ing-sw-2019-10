package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Panel prompting to discard a powerup card for new spawn point. Simpler
 * implementation comapred to DiscardPUC.
 */
public class NewSpawnPointPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private String nickName;
    private JButton b;
    private JTextField txt1;
    private JTextField txt2;

    /**
     * Creates a new SpawnPointPanel
     *
     * @param gui gui
     * @param server server
     * @param parent parent frame
     * @param game game
     * @param nickName nickname
     * @throws RemoteException RMI exception
     */
    public NewSpawnPointPanel(GUI gui, ServerInterface server, JFrame parent, int game, String nickName) throws RemoteException{
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        add(new JLabel("The following are " + this.nickName +"'s starting PowerUpCards")).setBounds(0,0, 5, 5);
        add(new JLabel("Enter the PowerUp card you want to discard; its colour will be your new spawn point:")).doLayout();
        for (int i = 0; i < this.server.messageGetPlayerPowerUpCard(game, nickName).size(); i++)
            add(new JLabel(this.server.messageGetPlayerPowerUpCard(game, nickName).get(i) + "coloured" + this.server.messageGetPlayerPowerUpCardColour(game, nickName).get(i))).doLayout();

        b = new JButton("Confirm");
        txt1 = new JTextField("Write here the name", 25);
        txt2 = new JTextField("Write here the colour", 25);
        b.addActionListener(this);
        add(new JLabel(("Enter the name:"))).doLayout();
        add(txt1).doLayout();
        add(new JLabel("Enter the colour:")).doLayout();
        add(txt2).doLayout();
        add(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(this.server.messageIsValidDiscardCardForSpawnPoint(game, this.nickName, txt1.getText(), txt2.getText()))
                this.server.messageDiscardCardForSpawnPoint(game, this.nickName, txt1.getText(), txt2.getText());
            else
                gui.newSpawnPoint();

            parent.setVisible(false);
            parent.dispose();
        }

        catch (RemoteException r) {

        }

    }
}
