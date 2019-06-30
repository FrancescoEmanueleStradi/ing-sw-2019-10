package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Cyberblade.
 */
public class Cyberblade extends WeaponCard {

    /**
     * Instantiates a new Cyberblade.
     */
    public Cyberblade() {
        super();
        this.cardName = "Cyberblade";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.RED)};
        this.description = "basic effect: Deal 2 damage to 1 target on your square.\n" +
                             "with shadowstep: Move 1 square before or after the basic effect.\n" +
                             "with slice and dice: Deal 2 damage to a different target on your square.\n" +
                             "The shadowstep may be used before or after this effect.\n" +
                             "Notes: Combining all effects allows you to move onto a square and whack 2 people; or whack somebody, move, and whack somebody else; or whack 2 people and then move.\n";
    }

    //prior to effect: let player p choose a player p1 on his cell.

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //player p deals 1 damage to p1
        grid.damage(p, p1, 2);
    }

    //prior to effect: This Special Effect can be done before or after the other effects. Let player p choose a cell he wants to go to.

    /**
     * Apply special effect.
     *
     * @param grid      the grid
     * @param p         the p
     * @param direction the direction
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, String direction) throws RemoteException {   //Shadowstep: player p moves in the chosen cell
        grid.move(p, Integer.parseInt(direction));
    }

    //prior to effect: let player p choose a different target p2 than p1. p2 must be on the same cell of p.

    /**
     * Apply special effect 2.
     *
     * @param grid the grid
     * @param p    the p
     * @param p2   the p 2
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect2(Grid grid, Player p, Player p2) throws RemoteException {   //Slice and dice: player p deals 2 damage to the chosen p2
        grid.damage(p, p2, 2);
    }
}