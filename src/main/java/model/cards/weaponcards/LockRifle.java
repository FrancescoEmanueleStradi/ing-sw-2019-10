package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Lock Rifle weapon card.
 */
public class LockRifle extends WeaponCard {

    /**
     * Creates a new Lock Rifle.
     */
    public LockRifle() {
        super();
        this.cardName = "Lock Rifle";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE)};
        this.description = "basic effect: Deal 2 damage and 1 mark to 1 target you can see.\n" +
                             "with second lock: Deal 1 mark to a different target you can see.\n";
    }

    /**
     * Applies the card's effect.
     * Player p attacks p1 (visible), giving him 2 damage and 1 mark.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 2);
        grid.addMark(p, p1);
    }

    /**
     * Applies the card's special effect.
     * Second Lock: player p attacks p2 (visible), who is different from the p1 selected for the primary effect.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p2   opponent 2
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p2) throws RemoteException {
        grid.addMark(p, p2);
    }
}