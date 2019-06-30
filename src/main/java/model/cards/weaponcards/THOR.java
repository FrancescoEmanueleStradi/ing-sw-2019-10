package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Thor.
 */
public class THOR extends WeaponCard {

    /**
     * Instantiates a new Thor.
     */
    public THOR() {
        super();
        this.cardName = "T.H.O.R.";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.RED)};
        this.description = "basic effect: Deal 2 damage to 1 target you can see.\n" +
                "with chain reaction: Deal 1 damage to a second target that your first target can see.\n" +
                "with high voltage: Deal 2 damage to a third target that your second target can see.\n" +
                "You cannot use this effect unless you first use the chain reaction.\n" +
                "Notes: This card constrains the order in which you can use its effects (most cards don't).\n" +
                "Also note that each target must be a different player.\n";
    }

    //prior to effect: let the player p choose which player p1 (visible) he wants to attack

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException { //player p gives 2 damages to p1
        grid.damage(p, p1, 2);
    }

    //prior to effect: let the player p choose which player p2 (visible by p1) he wants to attack

    /**
     * Apply special effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p2   the p 2
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p2) throws RemoteException { //Chain Reaction: player p gives 1 damage to p2, who is a second target that the first target p1 can see
        grid.damage(p, p2, 1);
    }

    //prior to effect: check if player p has used applySpecialEffect. At this point, player p can choose which player p3 (visible by p2) he wants to attack

    /**
     * Apply special effect 2.
     *
     * @param grid the grid
     * @param p    the p
     * @param p3   the p 3
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect2(Grid grid, Player p, Player p3) throws RemoteException { //High Voltage: player p gives 2 damage to player p3, who is a third target that the second target p2 can see
        grid.damage(p, p3, 2);
    }

    //Each target must be a different player!
}