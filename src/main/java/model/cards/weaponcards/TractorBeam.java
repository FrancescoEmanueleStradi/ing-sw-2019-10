package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class TractorBeam extends WeaponCard {

    private String alternateFireMode1 = "Punisher Mode";

    public TractorBeam() throws InvalidColourException {
        super();
        this.cardName = "Tractor Beam";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        this.numAlternateFireMode = 1;
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

    public String getAlternateFireMode1() {
        return alternateFireMode1;
    }

    //TODO
    //before the primary attack, player p moves a target 0, 1 or 2 squares to a square he can sees

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) { //after having moved the target, player p gives him 1 damage
        grid.damage(p1, 1);
    }

    //before the alternate fire mode, player p choose a target 0, 1 or 2 squares away from him, then moves him to his square

    @Override
    public void applySpecialEffect(Grid grid, Player p1) { //after having moved the target, player p gives him 3 damages
        grid.damage(p1, 3);
    }

    public void applySpecialEffect2(Grid grid, Player p) {
        
    }

}