package model.cards;

import model.AmmoCube;
import model.Colour;

public abstract class PowerUpCard implements Card {

    protected String cardName;
    protected Colour c;
    protected AmmoCube value;
    protected String description;

    public String getCardName() {
        return cardName;
    }

    public AmmoCube getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public Colour getC() {
        return c;
    }
}