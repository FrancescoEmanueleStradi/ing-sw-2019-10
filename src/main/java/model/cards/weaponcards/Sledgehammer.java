package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Sledgehammer.
 */
public class Sledgehammer extends WeaponCard {

    /**
     * Instantiates a new Sledgehammer.
     */
    public Sledgehammer() {
        super();
        this.cardName = "Sledgehammer";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW)};
        this.description = "basic mode: Deal 2 damage to 1 target on your square.\n" +
                             "in pulverize mode: Deal 3 damage to 1 target on your square, then move that target 0, 1, or 2 squares in one direction.\n" +
                             "Notes: Remember that moves go through doors, but not walls.\n";
    }

    //prior to effect: let player p choose a target p1 on his cell.

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //player p deals 2 damages to the chosen p1
        grid.damage(p, p1, 2);
    }

    //prior to effect: let player p choose a target p1 on his cell.

    /**
     * Apply special effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1) throws RemoteException {    //Pulverize Mode (First Part): player p deals 3 damages to the chosen p1
        grid.damage(p, p1, 3);
    }

    //then (optional): if player p wants, he can move the attacked p1 0, 1, 2 cells in one direction. Player p will click the cell. ALTERNATIVE: numMoves and direction as parameters and if.

    /**
     * Move enemy.
     *
     * @param p1        the p 1
     * @param grid      the grid
     * @param moves     the moves
     * @param direction the direction
     * @throws RemoteException the remote exception
     */
    public void moveEnemy(Player p1, Grid grid, int moves, int direction) throws RemoteException {   //Pulverize Mode (Second Part): enemy p1 is moved where player p has decided
        for(int i = 0; i < moves; i++)
            grid.move(p1, direction);
    }
}