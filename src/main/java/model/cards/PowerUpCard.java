package model.cards;

import model.player.AmmoCube;
import model.Colour;

import java.io.Serializable;

public abstract class PowerUpCard implements Card, Serializable {

    protected String cardName;
    protected Colour c;         //useful? Maybe AmmoCube value is enough
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