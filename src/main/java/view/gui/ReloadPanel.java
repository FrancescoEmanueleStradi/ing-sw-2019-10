package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class ReloadPanel extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private String nickName;
    private List<JButton> l = new LinkedList<>();

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

            l.add(new JButton(s));
            this.add(l.get(l.size()-1)).doLayout();
            l.get(l.size()-1).addActionListener(this);

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
