package model.cards.powerupcards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.PowerUpCard;

public class Newton extends PowerUpCard {

    public Newton(Colour c) throws InvalidColourException {
        super();
        this.cardName = "Newton";
        this.value = new AmmoCube(c);
        String description = "You may play this card on your turn before or\n" +
                "after any action. Choose any other player's\n" +
                "figure and move it 1 or 2 squares in one\n" +
                "direction. (You can't use this to move a figure\n" +
                "after it respawns at the end of your turn. That\n" +
                "would be too late.)";
    }

    @Override
    public void applyEffect() {

    }
}
