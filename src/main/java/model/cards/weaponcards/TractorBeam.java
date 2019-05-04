package model.cards.weaponcards;

import model.*;
import model.board.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.util.List;

public class TractorBeam extends WeaponCard {

    private String alternativeEffect = "Punisher Mode";

    public TractorBeam() {
        super();
        this.cardName = "Tractor Beam";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = true;
        this.description = "basic mode: Move a target 0, 1, or 2 squares to a square you can see, and give it 1 damage.\n" +
                             "in punisher mode: Choose a target 0, 1, or 2 moves away from you. Move the target to your square and deal 3 damage to it.\n" +
                             "Notes: You can move a target even if you can't see it. The target ends up in a place where you can see and damage it.\n" +
                             "The moves do not have to be in the same direction.\n";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    //before: let the player p choose which player p1 he wants to move and damage, letting him choose the visible cell he wants to move the enemy in

    public void applyEffect(Grid grid, Player p, Player p1, List<Integer> directions) {    //enemy is moved to cell and damaged
        for(Integer i : directions)
            grid.move(p1, i);
        grid.damage(p, p1, 1);
    }

    //before: let the player choose which player p1 he wants to move and damage: controller checks if the distance is 0, 1 or 2, but player p don't necessarily have to see the enemy

    public void applySpecialEffect(Grid grid, Player p, Player p1) {    //enemy is moved to p and damaged
        p1.changeCell(p.getCell());
        grid.damage(p, p1, 3);
    }
}