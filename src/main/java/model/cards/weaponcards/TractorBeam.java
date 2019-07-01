package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Tractor beam weapon card.
 */
public class TractorBeam extends WeaponCard {

    /**
     * Creates a new Tractor Beam.
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

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose which player p1 he wants to move and damage, letting him choose the visible
     * cell he wants to move the enemy in.
     * Enemy is moved to cell and damaged.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @param x    x coordinate
     * @param y    y coordinate
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1, int x, int y) throws RemoteException {
        p1.changeCell(grid.getBoard().getArena()[x][y]);
        grid.damage(p, p1, 1);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let the player choose which player p1 he wants to move and damage. p don't necessarily have to
     * see the enemy.
     * Enemy is moved to p's cell and damaged.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1) throws RemoteException {
        p1.changeCell(p.getCell());
        grid.damage(p, p1, 3);
    }
}