package view.gui;

import javax.swing.*;

public class CardLink {
    private String card;
    private String colour;
    private ImageIcon image;

    public CardLink(String cardName, String cardColour, ImageIcon cardImage) {
        this.card = cardName;
        this.colour = cardColour;
        this.image = cardImage;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getColour() {
        return colour;
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

    public void setColour(String colour) {
        this.colour = colour;
    }
}