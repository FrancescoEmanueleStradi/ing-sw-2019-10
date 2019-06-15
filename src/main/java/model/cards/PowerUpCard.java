package model.cards;
import model.Colour;

public abstract class PowerUpCard implements Card {

    protected String cardName;
    protected Colour c;         //useful? Maybe AmmoCube value is enough
    protected String description;

    public String getCardName() {
        return cardName;
    }

    public String getDescription() {
        return description;
    }

    public Colour getC() {
        return c;
    }
}