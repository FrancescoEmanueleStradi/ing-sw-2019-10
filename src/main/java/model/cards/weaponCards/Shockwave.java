package model.cards.weaponCards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.WeaponCard;

public class Shockwave extends WeaponCard {

    public Shockwave() throws InvalidColourException {
        super();
        this.cardName = "model.cards.weaponCards.Shockwave";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW)};
        this.numSpecialEffect = 0;                                    //has alternate fire mode
        String description = "basic mode: Choose up to 3 targets on\n" +
                "different squares, each exactly 1 move away.\n" +
                "Deal 1 damage to each target.\n" +
                "in tsunami mode: Deal 1 damage to all\n" +
                "targets that are exactly 1 move away.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
