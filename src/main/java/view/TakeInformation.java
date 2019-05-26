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
     private JTextField txt1;
     private JTextField txt2;
     private JTextField txt3;
     private boolean error = false;


     public TakeInformation(GUI gui, ServerInterface server, int game, int identifier) {
         super();
         this.gui = gui;
         this.server = server;
         this.game = game;
         this.identifier = identifier;
         b = new JButton("Confirm");
         txt1 = new JTextField("Write here", 25);
         txt2 = new JTextField("Write here", 25);
         txt3 = new JTextField("Write here", 25);
         b.addActionListener(this);
         add(new Label(("Enter your name:")));
         add(txt1);
         add(new Label("Enter your Colour:"));
         add(txt2);
         add(new Label("Enter the type of arena:"));
         add(txt3);
         add(b);
     }

     public synchronized void actionPerformed(ActionEvent e){
         try {
             if(identifier == 1)
                getInformation();
             else
                 getLessInformation();
         }catch(RemoteException ex){
             //TODO?
         }
     }

     private synchronized void getInformation()  throws RemoteException {
         if(!error){
             gui.setNickName(txt1.getText());
             server.setNickName(this.game, this.identifier, txt1.getText());
             gui.setColour(Colour.valueOf(txt2.getText()));
             this.server.messageGameStart(game, txt1.getText(), Colour.valueOf(txt2.getText()));
             gui.setType(Integer.valueOf(txt3.getText()));
         }
             while (!this.server.messageIsValidReceiveType(game, Integer.valueOf(txt3.getText()))) {
                 add(new Label("Error, write a valid type"));
             }

         this.server.messageReceiveType(game, Integer.valueOf(txt3.getText()));
         add(new Label("\n---------GENERATING ARENA...---------\n"));
         gui.setType(server.getType(game));
         notifyAll();
     }

     private synchronized void getLessInformation() throws RemoteException{
         gui.setType(server.getType(game));
         add(new Label("\n---------WAITING FOR PLAYERS TO JOIN---------\n"));
         gui.setNickName(txt1.getText());
         gui.setColour(Colour.valueOf(txt2.getText()));
         server.setNickName(this.game, this.identifier, txt1.getText());
         while (!this.server.messageIsValidAddPlayer(game, txt1.getText(), Colour.valueOf(txt2.getText()))) {
             add(new Label("Error retry"));
             gui.setNickName(txt1.getText());
             gui.setColour(Colour.valueOf(txt2.getText()));
             server.setNickName(this.game, this.identifier, txt1.getText());
         }
         this.server.messageAddPlayer(game, txt1.getText(), Colour.valueOf(txt2.getText()));
         notifyAll();
     }
 }
