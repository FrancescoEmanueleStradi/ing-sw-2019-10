package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class VortexCannon extends WeaponCard {

    private String optionalEffect1 = "Black Hole";

    public VortexCannon() throws InvalidColourException {
        super();
        this.cardName = "Vortex Cannon";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 1;
        super.alternateFireMode = false;
        String description = "basic effect: Choose a square you can see, but not your\n" +
                "square. Call it \"the vortex\". Choose a target on the vortex\n" +
                "or 1 move away from it. Move it onto the vortex and give it\n" +
                "2 damage.\n" +
                "with black hole: Choose up to 2 other targets on the\n" +
                "vortex or 1 move away from it. Move them onto the vortex\n" +
                "and give them each 1 damage.\n" +
                "Notes: The 3 targets must be different, but some might\n" +
                "start on the same square. It is legal to choose targets on\n" +
                "your square, on the vortex, or even on squares you can't\n" +
                "see. They all end up on the vortex.";
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }

}
