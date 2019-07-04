package view.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GridGraphic extends JPanel implements Serializable {

    private Image image1;

    public GridGraphic(String s1) {
        image1 = Toolkit.getDefaultToolkit().createImage(s1);
        loadImage(image1);
    }

    protected void paintComponent(Graphics g) {
        setOpaque(false);
        g.drawImage(image1, 0, 0, null);
        super.paintComponent(g);
    }

    private void loadImage(Image img) {
        try {
            MediaTracker track = new MediaTracker(this);
            track.addImage(img, 0);
            track.waitForID(0);
        } catch (InterruptedException e) {

        }
    }



}
