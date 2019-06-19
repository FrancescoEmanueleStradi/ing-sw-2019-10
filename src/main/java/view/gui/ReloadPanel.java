package view.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class ReloadPanel extends JPanel implements ActionListener {

    private JButton b;
    private JTextField txt1;
    private JTextField txt2;
    private JTextField txt3;

    public ReloadPanel() {
        super();
        add(new JLabel("Click on the name of the card you want to discard, or on exit if you have finished"));
        add(new JButton("exit"));
    }



    public void addButton(String name) {
        add(new JButton(name));
    }


    public void actionPerformed(ActionEvent e) {
        /*try {


        }catch(RemoteException ex) {
            //TODO?
        }*/
    }
}
