package view.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GridGraphic extends JPanel implements Serializable {

    private Image image1;
    private JScrollBar jScrollBar;
    private TextArea textArea;

    public GridGraphic(String s1) {
        image1 = Toolkit.getDefaultToolkit().createImage(s1);
        loadImage(image1);
        textArea = new TextArea();
        textArea.setSize(1000, 1000);
        //textArea.setLocation(0,0);
        jScrollBar = new JScrollBar(Adjustable.VERTICAL);
        jScrollBar.add(textArea);
        jScrollBar.setSize(1000, 1000);
        jScrollBar.setMaximum(10000);
        //jScrollBar.setLocation(0,0);
        add(jScrollBar);






        //jScrollPane.setSize(new Dimension(1000, 150));
        //jScrollPane.setLocation(0,0);
        //add(jScrollPane).setLocation(0, 0);

    }

    protected void paintComponent(Graphics g) {
        setOpaque(false);
        g.drawImage(image1, 250, 0, null);
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

    public void changeText(String text){
        //jScrollPane.setToolTipText(text);
        jScrollBar.setToolTipText(text);
        this.revalidate();
    }



}
