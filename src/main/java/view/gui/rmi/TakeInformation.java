package view.gui.rmi;

import model.Colour;
import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Initial panel prompting players to enter their name and colour.
 */
public class TakeInformation extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private int identifier;
    private JFrame parent;
    private JButton b;
    private JComboBox colourList;
    private JComboBox arenaList;
    private JTextField txt1;

    /**
     * Creates a new TakeInformation. Slightly different conditions for the first
     * player who must choose the arena type.
     *
     * @param gui gui
     * @param server server
     * @param game game
     * @param identifier identifier
     * @param parent parent frame
     */
    public TakeInformation(GUI gui, ServerInterface server, int game, int identifier, JFrame parent) {
        super();
        this.gui = gui;
        this.server = server;
        this.game = game;
        this.identifier = identifier;
        this.parent = parent;
        b = new JButton("Confirm");
        Object[] colours = {"YELLOW", "BLUE", "GREEN", "PURPLE", "BLACK"};
        Object[] arenas = {1, 2, 3, 4};
        txt1 = new JTextField("Write here", 25);
        colourList = new JComboBox(colours);
        arenaList = new JComboBox(arenas);
        b.addActionListener(this);
        add(new JLabel(("Enter your name:"))).doLayout();
        add(txt1).doLayout();
        add(new JLabel("Select your Colour:")).doLayout();
        add(colourList).doLayout();
        if(identifier == 1) {
            add(new JLabel("Select the type of arena:")).doLayout();
            add(arenaList).doLayout();
        }
        add(b);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            b.setEnabled(false);
            if(identifier == 1) {
                getInformation();
                gui.selectSpawnPoint();
            }
            else {
                getLessInformation();
                gui.selectSpawnPoint();
            }
        } catch (RemoteException | InterruptedException ex) {

        }
    }

    /**
     * Receives first player's information.
     *
     * @throws RemoteException RMI exception
     */
    private void getInformation()  throws RemoteException {
        String colour = (String)colourList.getSelectedItem();
        Integer type = (Integer)arenaList.getSelectedItem();

        gui.setNickName(txt1.getText());
        server.setNickName(this.game, this.identifier, txt1.getText());
        gui.setColour(Colour.valueOf(colour));
        this.server.messageGameStart(game, txt1.getText(), Colour.valueOf(colour));
        gui.setType(type);
        this.server.messageReceiveType(game, type);
        parent.setVisible(false);
        parent.dispose();
    }

    /**
     * Receives all other players' information.
     *
     * @throws RemoteException RMI exception
     * @throws InterruptedException Thread interruption
     */
    private void getLessInformation() throws RemoteException, InterruptedException {
        String colour = (String)colourList.getSelectedItem();
        gui.setType(server.getType(game));
        if(this.server.messageIsValidAddPlayer(game, txt1.getText(), Colour.valueOf(colour)) != 3) {
            gui.askNameAndColour();
        }
        else {
            gui.setNickName(txt1.getText());
            gui.setColour(Colour.valueOf(colour));
            server.setNickName(this.game, this.identifier, txt1.getText());
            this.server.messageAddPlayer(game, txt1.getText(), Colour.valueOf(colour));
            parent.setVisible(false);
            parent.dispose();
        }
    }
}
