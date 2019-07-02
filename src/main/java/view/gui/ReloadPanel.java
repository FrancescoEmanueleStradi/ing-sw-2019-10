package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class ReloadPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private String nickName;

    public ReloadPanel(GUI gui, ServerInterface server, JFrame parent, int game, String nickName) throws RemoteException {
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        add(new JLabel("Click on the name of the card you want to load, or on exit if you have finished"));
        add(new JButton("exit"));

        for (String s : this.server.messageGetWeaponCardUnloaded(game, this.nickName)) {

            this.add(new JButton(s));
        }

    }



    public void actionPerformed(ActionEvent e) {
        try {
            String action = e.getSource().toString();

            if(action.equals("exit")) {
                gui.reload();
            }

            else{
                if(this.server.messageIsValidReload(game, this.nickName, action))
                    this.server.messageReload(game, this.nickName, action);
                gui.reload();
            }

            parent.setVisible(false);
            parent.dispose();


        }catch(RemoteException ex) {

        }catch (InterruptedException exc){

        }
    }
}
