package model.cards.weaponcards;

import model.*;
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
    public void applyEffect(Grid grid, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }

}
