package view;

import javax.swing.*;

public class CardLink {
     private String card;
     private ImageIcon image;

    public ImageIcon getImage() {
        return image;
    }

    public String getCard() {
        return card;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public void setCard(String card) {
        this.card = card;
    }

}
