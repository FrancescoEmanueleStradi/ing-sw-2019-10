package Model.WeaponCards;

import Model.AmmoCube;
import Model.Colour;
import Model.InvalidColourException;
import Model.WeaponCard;

public class ZX2 extends WeaponCard {

    public ZX2() throws InvalidColourException {
        super();
        this.cardName = "ZX-2";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.RED)};
        this.numSpecialEffect = 0;                                    //has alternate fire mode
        String description = "basic mode: Deal 1 damage and 2 marks to\n" +
                "1 target you can see.\n" +
                "in scanner mode: Choose up to 3 targets you\n" +
                "can see and deal 1 mark to each.\n" +
                "Notes: Remember that the 3 targets can be\n" +
                "in 3 different rooms.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
