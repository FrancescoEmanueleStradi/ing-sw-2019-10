package model.cards.weaponcards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.WeaponCard;

public class PlasmaGun extends WeaponCard {

    public PlasmaGun() throws InvalidColourException {
        super();
        this.cardName = "Plasma Gun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.YELLOW)};
        this.numSpecialEffect = 2;
        String description = "basic effect: Deal 2 damage to 1 target you can see.\n" +
                "with phase glide: Move 1 or 2 squares. This effect can be\n" +
                "used either before or after the basic effect.\n" +
                "with charged shot: Deal 1 additional damage to your\n" +
                "target.\n" +
                "Notes: The two moves have no ammo cost. You don't have\n" +
                "to be able to see your target when you play the card.\n" +
                "For example, you can move 2 squares and shoot a target\n" +
                "you now see. You cannot use 1 move before shooting and\n" +
                "1 move after.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
