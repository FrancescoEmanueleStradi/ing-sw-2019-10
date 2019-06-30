package model.cards;

import model.Colour;

/**
 * All powereup cards in the game extend this class.
 */
public abstract class PowerUpCard {

    protected String cardName;
    protected Colour c;
    protected String description;

    /**
     * Gets powerup card name.
     *
     * @return card name
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Gets powerup description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets powerup card colour
     *
     * @return colour
     */
    public Colour getC() {
        return c;
    }
}