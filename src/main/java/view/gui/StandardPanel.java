package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.*;

public class StandardPanel extends JPanel {

    private int game;
    private ServerInterface server;
    private int disconnected;

    public StandardPanel(int game, ServerInterface server){
        super();
        this.game = game;
        this.server = server;
    }

    public void setDisconnected(int disconnected) {
        this.disconnected = disconnected;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(40,20, 140,40);
        g.setColor(Color.black);
        g.drawString("Player number" + disconnected +"is disconnected",50,60);
    }
}
