package view.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GridGraphic extends JPanel implements Serializable {

    private Image img1;
    private Image img2;

    public GridGraphic(String s1, String s2) {
        img1 = Toolkit.getDefaultToolkit().createImage(s1);
        img2 = Toolkit.getDefaultToolkit().createImage(s2);
        loadImage(img1);
        loadImage(img2);
    }

    protected void paintComponent(Graphics g) {
        setOpaque(false);
        g.drawImage(img1, 0, 0, null);
        g.drawImage(img1, 500, 0, null);
        super.paintComponent(g);
    }

    private void loadImage(Image img) {
        try {
            MediaTracker track = new MediaTracker(this);
            track.addImage(img1, 0);
            track.waitForID(0);
            track.addImage(img2, 0);
            track.waitForID(0);
        } catch (InterruptedException e) {

        }
    }



}
