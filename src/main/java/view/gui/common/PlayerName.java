package view.gui.common;

import javax.swing.*;
import java.awt.*;

/**
 * Part of the grid graphic displaying the player's names in formatted text.
 */
public class PlayerName extends JTextField {

    private String nickName;
    private Color c;
    private String identifier;
    private JLabel jname;
    private JLabel jidentifier;

    /**
     * Creates a new PlayerName.
     *
     * @param nickName nickname
     * @param c colour
     * @param identifier identifier
     */
    public PlayerName(String nickName, String c, String identifier) {
        this.nickName = nickName;
        this.c = Color.getColor(c);
        this.identifier = identifier;
        jname = new JLabel(this.nickName);
        jname.setBackground(this.c);
        jidentifier = new  JLabel(this.identifier);
        jidentifier.setBackground(this.c);
        add(jname);
        add(jidentifier);
    }


}
