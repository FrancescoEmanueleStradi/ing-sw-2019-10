package Model.WeaponCards;

import Model.AmmoCube;
import Model.Colour;
import Model.InvalidColourException;
import Model.WeaponCard;

public class TractorBeam extends WeaponCard {

    public TractorBeam() throws InvalidColourException {
        super();
        this.cardName = "Tractor Beam";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.numSpecialEffect = 0;                                  //has alternate fire mode
        String description = "basic mode: Move a target 0, 1, or 2 squares to a square\n" +
                "you can see, and give it 1 damage.\n" +
                "in punisher mode: Choose a target 0, 1, or 2 moves away\n" +
                "from you. Move the target to your square\n" +
                "and deal 3 damage to it.\n" +
                "Notes: You can move a target even if you can't see it.\n" +
                "The target ends up in a place where you can see and\n" +
                "damage it. The moves do not have to be in the same\n" +
                "direction.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }

}
