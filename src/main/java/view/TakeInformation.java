package view;

import model.Colour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class TakeInformation extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private int identifier;
    private JButton b;
    private JComboBox colourList;
    private JComboBox arenaList;
    private JTextField txt1;
    /*private JTextField txt2;
    private JTextField txt3;*/
    private boolean error = false;

    public TakeInformation(GUI gui, ServerInterface server, int game, int identifier) {
        super();
        this.gui = gui;
        this.server = server;
        this.game = game;
        this.identifier = identifier;
        b = new JButton("Confirm");
        Object[] colours = {"YELLOW", "BLUE", "GREEN", "PURPLE", "BLACK"};
        Object[] arenas = {1, 2, 3, 4};
        txt1 = new JTextField("Write here", 25);
        colourList = new JComboBox(colours);
        arenaList = new JComboBox(arenas);
        //txt2 = new JTextField("Write here", 25);
        //txt3 = new JTextField("Write here", 25);
        b.addActionListener(this);
        add(new JLabel(("Enter your name:")));
        add(txt1).doLayout();
        add(new JLabel("Select your Colour:"));
        add(colourList).doLayout();
        if(identifier == 1) {
            add(new JLabel("Select the type of arena:"));
            add(arenaList).doLayout();
        }
        add(b);
    }

    public synchronized void actionPerformed(ActionEvent e) {
        if(e.getSource() == b) { try {
            b.setEnabled(false);
            if(identifier == 1)
                getInformation();
            else
                getLessInformation();
        } catch(RemoteException ex){
            //TODO?
        } catch (InterruptedException i){
            //TODO?
        }
        }
    }

    private synchronized void getInformation()  throws RemoteException, InterruptedException {
        String colour = (String)colourList.getSelectedItem();
        Integer type = (Integer)arenaList.getSelectedItem();
        if(!error) {
            gui.setNickName(txt1.getText());
            server.setNickName(this.game, this.identifier, txt1.getText());
            gui.setColour(Colour.valueOf(colour));
            this.server.messageGameStart(game, txt1.getText(), Colour.valueOf(colour));
            gui.setType(type);
        }
        /*while(!this.server.messageIsValidReceiveType(game, type)) {
            add(new JLabel("Error, write a valid type"));
            gui.askNameAndColour();
        }*/

        this.server.messageReceiveType(game, type);
        add(new JLabel("\nGENERATING ARENA . . .\n"));
        gui.setType(server.getType(game));
        notifyAll();
    }

    private synchronized void getLessInformation() throws RemoteException, InterruptedException {
        String colour = (String)colourList.getSelectedItem();
        gui.setType(server.getType(game));
        add(new JLabel("\nWAITING FOR PLAYERS TO JOIN . . .\n"));
        gui.setNickName(txt1.getText());
        gui.setColour(Colour.valueOf(colour));
        server.setNickName(this.game, this.identifier, txt1.getText());
        while(!this.server.messageIsValidAddPlayer(game, txt1.getText(), Colour.valueOf(colour))) {
            add(new JLabel("Error retry"));
            gui.askNameAndColour();
            gui.setNickName(txt1.getText());
            gui.setColour(Colour.valueOf(colour));
            server.setNickName(this.game, this.identifier, txt1.getText());
        }
        this.server.messageAddPlayer(game, txt1.getText(), Colour.valueOf(colour));
        notifyAll();
    }
}
