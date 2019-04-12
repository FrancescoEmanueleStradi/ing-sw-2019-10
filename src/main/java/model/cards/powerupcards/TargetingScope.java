package model.cards.powerupcards;

import model.*;
import model.cards.PowerUpCard;

public class TargetingScope extends PowerUpCard {

    public TargetingScope(Colour c) throws InvalidColourException {
        super();
        this.cardName = "Targeting Scope";
        this.c = c;
        this.value = new AmmoCube(c);
        this.description = "You may play this card when you are dealing damage to one or more targets.\n" +
                            "Pay 1 ammo cube of any color. Choose 1 of those targets and give it an extra point of damage.\n" +
                            "Note: You cannot use this to do 1 damage to a target that is receiving only marks.\n";
    }

    //before: let player p choose one player p1 who is being attacked by p. p1 can't receive only marks from the attack that p is using against him.

    public void applyEffect(Grid grid, Player p, Player p1) {   //p deals 1 additional damage to p1
        grid.damage(p, p1, 1);
    }
}