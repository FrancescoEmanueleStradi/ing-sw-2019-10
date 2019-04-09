package model.cards.weaponcards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.WeaponCard;

public class Electroscythe extends WeaponCard {

    public Electroscythe() throws InvalidColourException {
        super();
        this.cardName = "Electroscythe";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.numSpecialEffect = 0;                                  //has alternate fire mode
        String description = "basic mode: Deal 1 damage to every other player\n" +
                "on your square.\n" +
                "in reaper mode: Deal 2 damage to every other player\n" +
                "on your square.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
