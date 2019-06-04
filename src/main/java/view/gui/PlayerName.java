package view.gui;

import model.Colour;

import javax.swing.*;
import java.awt.*;

public class PlayerName extends JTextField {

    private String nickName;
    private Color c;
    private String identifier;
    private JLabel jname;
    private JLabel jidentifier;

    public PlayerName(String nickName, String c, String identifier){
        this.nickName = nickName;
        this.c = Color.getColor(c);
        jname = new JLabel(this.nickName);
        jname.setBackground(this.c);
        jidentifier = new  JLabel(this.identifier);
        jidentifier.setBackground(this.c);
        add(jname);
        add(jidentifier);
    }


}
