package view.gui;

import model.Colour;
import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

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
    /*private JTextField txt2;
    private JTextField txt3;*/
    private boolean error = false;

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
        //txt2 = new JTextField("Write here", 25);
        //txt3 = new JTextField("Write here", 25);
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

    public synchronized void actionPerformed(ActionEvent e) {
        try {
            b.setEnabled(false);
            if(identifier == 1)
                getInformation();
            else
                getLessInformation();
        } catch (RemoteException | InterruptedException ex) {
            //TODO?
        }

    }

    private synchronized void getInformation()  throws RemoteException, InterruptedException {
        String colour = (String)colourList.getSelectedItem();
        Integer type = (Integer)arenaList.getSelectedItem();

        gui.setNickName(txt1.getText());
        server.setNickName(this.game, this.identifier, txt1.getText());
        gui.setColour(Colour.valueOf(colour));
        this.server.messageGameStart(game, txt1.getText(), Colour.valueOf(colour));
        gui.setType(type);
        this.server.messageReceiveType(game, type);
        //add(new JLabel("\nGENERATING ARENA . . .\n")).doLayout();
        //revalidate();
        gui.setFlag(true);
        parent.setVisible(false);
        parent.dispose();
    }

    private synchronized void getLessInformation() throws RemoteException, InterruptedException {
        String colour = (String)colourList.getSelectedItem();
        JLabel errorRetry = new JLabel("Error: retry");
        gui.setType(server.getType(game));
        if(!this.server.messageIsValidAddPlayer(game, txt1.getText(), Colour.valueOf(colour))) {
            if(!errorRetry.isVisible()) {
                errorRetry.doLayout();
                parent.add(errorRetry);
                errorRetry.setVisible(true);
                revalidate();
            }
            b.setEnabled(true);
        }
        else {
            add(new JLabel("\nWAITING FOR PLAYERS TO JOIN . . .\n")).doLayout();
            if(errorRetry.isVisible())
                errorRetry.setVisible(false);
            revalidate();
            gui.setNickName(txt1.getText());
            gui.setColour(Colour.valueOf(colour));
            server.setNickName(this.game, this.identifier, txt1.getText());
            this.server.messageAddPlayer(game, txt1.getText(), Colour.valueOf(colour));
            gui.setFlag(true);
            parent.setVisible(false);
            parent.dispose();
        }
    }
}
