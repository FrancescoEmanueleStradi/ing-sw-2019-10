package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Shockwave extends WeaponCard {

    private String alternateFireMode = "Tsunami Mode";

    public Shockwave() throws InvalidColourException {
        super();
        this.cardName = "Shockwave";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW)};
        this.numOptionalEffect = 0;
        super.alternateFireMode = true;
        String description = "basic mode: Choose up to 3 targets on\n" +
                "different squares, each exactly 1 move away.\n" +
                "Deal 1 damage to each target.\n" +
                "in tsunami mode: Deal 1 damage to all\n" +
                "targets that are exactly 1 move away.";
    }

    public String getAlternateFireMode() {
        return alternateFireMode;
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }
}
