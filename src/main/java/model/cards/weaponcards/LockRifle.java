package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class LockRifle extends WeaponCard {

    public LockRifle() throws InvalidColourException {
        super();
        this.cardName = "Lock Rifle";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE)};
        this.numSpecialEffect = 1;
        String description = "basic effect: Deal 2 damage and 1 mark to 1 target\n" +
                "you can see.\n" +
                "with second lock: Deal 1 mark to a different target\n" +
                "you can see.";
    }

    @Override
    public void applyEffect(Grid grid, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }
}
