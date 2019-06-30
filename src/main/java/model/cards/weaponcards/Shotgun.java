package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Shotgun.
 */
public class Shotgun extends WeaponCard {

    /**
     * Instantiates a new Shotgun.
     */
    public Shotgun() {
        super();
        this.cardName = "Shotgun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW)};
        this.description = "basic mode: Deal 3 damage to 1 target on your square. If you want, you may then move the target 1 square.\n" +
                             "in long barrel mode: Deal 2 damage to 1 target on any square exactly one move away.\n";
    }

    //prior to effect: ask player p which player p1 (who must be on the same cell as p) he wants to attack.

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //player p deals 1 damage to player p1
        grid.damage(p, p1, 1);
    }

    //after primary effect: ask player p if he wants to move the attacked player p1 one cell, and in which direction (click on cell and from that we get the direction?).

    /**
     * Move player.
     *
     * @param grid      the grid
     * @param p1        the p 1
     * @param direction the direction
     * @throws RemoteException the remote exception
     */
    public void movePlayer(Grid grid, Player p1, int direction) throws RemoteException {   //right after the primary effect
        grid.move(p1, direction);
    }

    //prior to effect: let player p choose one player p1 exactly one cell away.

    /**
     * Apply special effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1) throws RemoteException {    //player p deals 2 damages to the selected p1
        grid.damage(p, p1, 2);
    }
}