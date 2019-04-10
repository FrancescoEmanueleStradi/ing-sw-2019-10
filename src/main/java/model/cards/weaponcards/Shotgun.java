package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Shotgun extends WeaponCard {

    private String alternativeEffect = "Long Barrel Mode";

    public Shotgun() throws InvalidColourException {
        super();
        this.cardName = "Shotgun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = true;
        String description = "basic mode: Deal 3 damage to 1 target on your square. If you want, you may then move the target 1 square.\n" +
                             "in long barrel mode: Deal 2 damage to 1 target on any square exactly one move away.\n";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    //before: ask player p which player p1 (who must be on the same cell as p) he wants to attack.

    public void applyEffect(Grid grid, Player p, Player p1) {   //player p deals 1 damage to player p1
        grid.damage(p, p1, 1);
    }

    //after primary effect: ask player p if he wants to move the attacked player p1 one cell, and in which direction (click on cell and from that we get the direction?).

    public void movePlayer(Grid grid, Player p1, int direction) {   //right after the primary effect
        grid.move(p1, direction);
    }

    //before: let player p choose one player p1 exactly one cell away.

    public void applySpecialEffect(Grid grid, Player p, Player p1) {    //player p deals 2 damages to the selected p1
        grid.damage(p, p1, 2);
    }
}