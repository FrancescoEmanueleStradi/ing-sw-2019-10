package view.gui.common;

import javax.swing.*;

/**
 * Represents a member of CardLinkList containing the card (among other components),
 * its colour (must be null for some elements) and associated image.
 */
public class CardLink {

    private String card;
    private String colour;
    private ImageIcon image;

    /**
     * Create a new CardLink.
     * @param cardName name
     * @param cardColour colour
     * @param cardImage image
     */
    public CardLink(String cardName, String cardColour, ImageIcon cardImage) {
        this.card = cardName;
        this.colour = cardColour;
        this.image = cardImage;
    }

    /**
     * Gets image.
     *
     * @return image
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * Gets colour.
     *
     * @return colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * Gets card name (or other graphical component).
     *
     * @return card
     */
    public String getCard() {
        return card;
    }

    /**
     * Sets image.
     *
     * @param image image
     */
    public void setImage(ImageIcon image) {
        this.image = image;
    }

    /**
     * Sets card name.
     *
     * @param card name
     */
    public void setCard(String card) {
        this.card = card;
    }

    /**
     * Sets colour.
     *
     * @param colour colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }
}