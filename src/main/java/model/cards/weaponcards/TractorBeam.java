package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Tractor beam.
 */
public class TractorBeam extends WeaponCard {

    /**
     * Instantiates a new Tractor beam.
     */
    public TractorBeam() {
        super();
        this.cardName = "Tractor Beam";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.description = "basic mode: Move a target 0, 1, or 2 squares to a square you can see, and give it 1 damage.\n" +
                             "in punisher mode: Choose a target 0, 1, or 2 moves away from you. Move the target to your square and deal 3 damage to it.\n" +
                             "Notes: You can move a target even if you can't see it. The target ends up in a place where you can see and damage it.\n" +
                             "The moves do not have to be in the same direction.\n";
    }

    //prior to effect: let the player p choose which player p1 he wants to move and damage, letting him choose the visible cell he wants to move the enemy in

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @param x    the x
     * @param y    the y
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1, int x, int y) throws RemoteException {    //enemy is moved to cell and damaged
        p1.changeCell(grid.getBoard().getArena()[x][y]);
        grid.damage(p, p1, 1);
    }

    //prior to effect: let the player choose which player p1 he wants to move and damage: controller checks if the distance is 0, 1 or 2, but player p don't necessarily have to see the enemy

    /**
     * Apply special effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1) throws RemoteException {    //enemy is moved to p and damaged
        p1.changeCell(p.getCell());
        grid.damage(p, p1, 3);
    }
}