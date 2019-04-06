package model.cards.weaponCards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.WeaponCard;

public class Heatseeker extends WeaponCard {

    public Heatseeker() throws InvalidColourException {
        super();
        this.cardName = "Heatseeker";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.RED), new AmmoCube(Colour.YELLOW)};
        this.numSpecialEffect = 0;
        String description = "effect: Choose 1 target you cannot see and deal 3 damage\n" +
                "to it.\n" +
                "Notes: Yes, this can only hit targets you cannot see.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
