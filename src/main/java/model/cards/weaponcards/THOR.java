package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * T.H.O.R. weapon card.
 */
public class THOR extends WeaponCard {

    /**
     * Creates a new T.H.O.R..
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

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose which player p1 (visible) he wants to attack.
     * Player p deals 2 damage to p1.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 2);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let player p choose which player p2 (visible by p1) he wants to attack.
     * Chain Reaction: player p deals 1 damage to p2, who is a second target that the first target p1 can see.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p2   opponent 2
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p2) throws RemoteException {
        grid.damage(p, p2, 1);
    }

    /**
     * Applies the card's second special effect.
     * Prior to effect: check if player p has used applySpecialEffect. At this point, player p can choose which player
     * p3 (visible by p2) he wants to attack.
     * High Voltage: player p deals 2 damage to player p3, who is a third target that the second target p2 can see.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p3   opponent 3
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect2(Grid grid, Player p, Player p3) throws RemoteException {
        grid.damage(p, p3, 2);
    }
}