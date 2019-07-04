package view.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Panel containing the layout out of the grid components.
 */
public class GridGraphic extends JPanel implements Serializable {

    private Image image1;
    private JScrollPane jScrollPane;
    private TextArea textArea;

    /**
     * Creates a new GridGraphic. Includes an area which lets the user scroll through
     * messages that appear during the game.
     *
     * @param s1 graphic filename
     */
    public GridGraphic(String s1) {
        image1 = Toolkit.getDefaultToolkit().createImage(s1);
        loadImage(image1);
        textArea = new TextArea();
        textArea.setBackground(Color.darkGray);
        textArea.setForeground(Color.WHITE);
        textArea.setSize(200, 200);
        jScrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setLayout(new ScrollPaneLayout());
        jScrollPane.setLocation(0, 0);
        add(textArea);
    }

    /**
     * Allows grid graphic to be rendered.
     *
     * @param g graphic
     */
    protected void paintComponent(Graphics g) {
        setOpaque(false);
        g.drawImage(image1, 0, 0, null);
        super.paintComponent(g);
    }

    /**
     * Loads image.
     *
     * @param img image
     */
    private void loadImage(Image img) {
        try {
            MediaTracker track = new MediaTracker(this);
            track.addImage(img, 0);
            track.waitForID(0);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Refreshes scroll pane text.
     *
     * @param text
     */
    public void changeText(String text){
        //jScrollPane.setToolTipText(text);
        jScrollPane.setToolTipText(text);
        this.revalidate();
    }
}
