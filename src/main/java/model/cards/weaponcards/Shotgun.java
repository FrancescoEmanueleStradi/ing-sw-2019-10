package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Shotgun weapon class.
 */
public class Shotgun extends WeaponCard {

    /**
     * Creates a new Shotgun.
     */
    public Shotgun() {
        super();
        this.cardName = "Shotgun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW)};
        this.description = "basic mode: Deal 3 damage to 1 target on your square. If you want, you may then move the target 1 square.\n" +
                             "in long barrel mode: Deal 2 damage to 1 target on any square exactly one move away.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: ask player p which player p1 (who must be on the same cell as p) he wants to attack.
     * Player p deals 1 damage to player p1.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 1);
    }

    /**
     * Moves player.
     * After primary effect: ask player p if he wants to move the attacked player p1 one cell, and in which direction.
     *
     * @param grid      grid
     * @param p1        opponent 1
     * @param direction direction
     * @throws RemoteException RMI exception
     */
    public void movePlayer(Grid grid, Player p1, int direction) throws RemoteException {
        grid.move(p1, direction);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let player p choose one player p1 exactly one cell away.
     * Player p deals 2 damage to the selected p1.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 2);
    }
}