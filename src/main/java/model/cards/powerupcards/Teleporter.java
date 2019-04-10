package model.cards.powerupcards;

import model.*;
import model.cards.PowerUpCard;

public class Teleporter extends PowerUpCard {

    public Teleporter(Colour c) throws InvalidColourException {
        super();
        this.cardName = "Teleporter";
        this.value = new AmmoCube(c);
        String description = "You may play this card on your turn before\n" +
                "or after any action. Pick up your figure and\n" +
                "set it down on any square of the board. (You\n" +
                "can't use this after you see where someone\n" +
                "respawns at the end of your turn. By then it is\n" +
                "too late.)";
    }

    public void applyEffect(Grid grid, Player p, Player p1) {

    }
}
