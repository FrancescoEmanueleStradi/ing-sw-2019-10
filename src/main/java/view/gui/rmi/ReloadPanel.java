package view.gui.rmi;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

/**
 * Panel prompting player to reload one or more weapon cards.
 */
public class ReloadPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private String nickName;
    private List<JButton> l = new LinkedList<>();

    /**
     * Creates a new ReloadPanel.
     *
     * @param gui gui
     * @param server server
     * @param parent parent frame
     * @param game game
     * @param nickName nickname
     * @throws RemoteException RMI exception
     */
    public ReloadPanel(GUI gui, ServerInterface server, JFrame parent, int game, String nickName) throws RemoteException {
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        add(new JLabel("Click on the name of the card you want to load, or on exit if you have finished"));
        add(new JButton("exit"));

        for (String s : this.server.messageGetPlayerWeaponCardUnloaded(game, this.nickName)) {

            l.add(new JButton(s));
            this.add(l.get(l.size()-1)).doLayout();
            l.get(l.size()-1).addActionListener(this);

        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();

            if(action.getText().equals("exit")) {
                gui.reload();
            }
            else{
                if(this.server.messageIsValidReload(game, this.nickName, action.getText()))
                    this.server.messageReload(game, this.nickName, action.getText());
                gui.reload();
            }

            parent.setVisible(false);
            parent.dispose();
        }catch(RemoteException ex) {

        }catch (InterruptedException exc){

        }
    }
}
