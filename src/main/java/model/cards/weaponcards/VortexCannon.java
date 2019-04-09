package model.cards.weaponcards;

import model.*;
import model.board.*;
import model.cards.WeaponCard;

public class VortexCannon extends WeaponCard {

    private String optionalEffect1 = "Black Hole";

    public VortexCannon() throws InvalidColourException {
        super();
        this.cardName = "Vortex Cannon";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 1;
        super.alternateFireMode = false;
        String description = "basic effect: Choose a square you can see, but not your square. Call it \"the vortex\".\n" +
                             "Choose a target on the vortex or 1 move away from it. Move it onto the vortex and give it 2 damage.\n" +
                             "with black hole: Choose up to 2 other targets on the vortex or 1 move away from it. Move them onto the vortex and give them each 1 damage.\n" +
                             "Notes: The 3 targets must be different, but some might start on the same square.\n" +
                             "It is legal to choose targets on your square, on the vortex, or even on squares you can't see. They all end up on the vortex.\n";
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    //before: controller let the player p choose a cell for the vortex, and a player p1 to move into the vortex (p1 must be on the vortex or 1 move away from it) and to damage it

    public void applyEffect(Grid grid, Player p, Player p1, Cell vortex) {  //p1 is moved into the vortex and damaged
        p1.setCell(vortex);
        grid.damage(p, p1, 2);
    }

    //before: controller let the player p choose up to 2 other targets on the vortex or 1 move away from it

    public void applySpecialEffect(Grid grid, Player p, Player p1, Player p2, Cell vortex) { //p2 can be null. p1 and p2 are moved into the vortex and damaged
        p1.setCell(vortex);
        if(!(p2.equals(null))) {
            p2.setCell(vortex);
            grid.damage(p, p2, 1);
        }
        grid.damage(p, p1, 1);
    }
}