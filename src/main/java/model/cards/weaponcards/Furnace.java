package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Furnace extends WeaponCard {

    public Furnace() throws InvalidColourException {
        super();
        this.cardName = "Furnace";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE)};
        this.numSpecialEffect = 0;                                  //has alternate fire mode
        String description = "basic mode: Choose a room you can see, but not the room\n" +
                "you are in. Deal 1 damage to everyone in that room.\n" +
                "in cozy fire mode: Choose a square exactly one move\n" +
                "away. Deal 1 damage and 1 mark to everyone on that\n" +
                "square.";
    }

    @Override
    public void applyEffect(Grid grid, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }

}
