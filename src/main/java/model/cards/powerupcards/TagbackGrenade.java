package model.cards.powerupcards;

import model.*;
import model.cards.PowerUpCard;

public class TagbackGrenade extends PowerUpCard {

    public TagbackGrenade(Colour c) throws InvalidColourException {
        super();
        this.cardName = "Tagback Grenade";
        this.value = new AmmoCube(c);
        String description = "You may play this card\n" +
                "when you receive damage\n" +
                "from a player you can see.\n" +
                "Give that player 1 mark.";
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) {

    }
}
