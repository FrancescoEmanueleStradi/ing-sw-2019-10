package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class THOR extends WeaponCard {

    public THOR() throws InvalidColourException {
        super();
        this.cardName = "T.H.O.R.";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.RED)};
        this.numSpecialEffect = 2;
        String description = "basic effect: Deal 2 damage to 1 target you can see.\n" +
                "with chain reaction: Deal 1 damage to a second target that\n" +
                "your first target can see.\n" +
                "with high voltage: Deal 2 damage to a third target that\n" +
                "your second target can see. You cannot use this effect\n" +
                "unless you first use the chain reaction.\n" +
                "Notes: This card constrains the order in which you can use\n" +
                "its effects. (Most cards don't.) Also note that each target\n" +
                "must be a different player.";
        }

    @Override
    public void applyEffect(Grid grid, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }
}
