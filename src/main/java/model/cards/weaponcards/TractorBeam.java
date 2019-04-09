package model.cards.weaponcards;

import model.*;
import model.board.*;
import model.cards.WeaponCard;

public class TractorBeam extends WeaponCard {

    private String alternativeEffect = "Punisher Mode";

    public TractorBeam() throws InvalidColourException {
        super();
        this.cardName = "Tractor Beam";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = true;
        String description = "basic mode: Move a target 0, 1, or 2 squares to a square you can see, and give it 1 damage.\n" +
                             "in punisher mode: Choose a target 0, 1, or 2 moves away from you. Move the target to your square and deal 3 damage to it.\n" +
                             "Notes: You can move a target even if you can't see it. The target ends up in a place where you can see and damage it.\n" +
                             "The moves do not have to be in the same direction.\n";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    //before the attack, controller let the player p choose which player p1 he wants to move and damage, letting him choose the visible cell he wants to move the enemy in

    public void applyEffect(Grid grid, Player p, Player p1, Cell destCell) {    //enemy is moved to cell and damaged
        p1.setCell(destCell);
        grid.damage(p, p1, 1);
    }

    //before the alternate fire mode, controller let the player choose which player p1 he wants to move and damage: controller checks if the distance is 0, 1 or 2, but player p don't necessarily have to see the enemy

    public void applySpecialEffect(Grid grid, Player p, Player p1) {    //enemy is moved to p and damaged
        p1.setCell(p.getCell());
        grid.damage(p, p1, 3);
    }
}