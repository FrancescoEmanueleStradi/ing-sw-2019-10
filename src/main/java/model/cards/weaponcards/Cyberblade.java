package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Cyberblade weapon card.
 */
public class Cyberblade extends WeaponCard {

    /**
     * Creates a new Cyberblade.
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

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a player p1 in his cell.
     * Player p deals 1 damage to p1.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //
        grid.damage(p, p1, 2);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: This effect can be used before or after the other effects. Let player p choose a cell he wants
     * to go to.
     * Shadowstep: Player p moves in the chosen cell.
     *
     * @param grid      grid
     * @param p         player (self)
     * @param direction the direction
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, String direction) throws RemoteException {
        grid.move(p, Integer.parseInt(direction));
    }

    /**
     * Applies the card's second special effect.
     * Prior to effect: let player p choose a different target p2. p2 must be in the same cell as p.
     * Slice and dice: player p deals 2 damage to the chosen p2.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p2   opponent 2
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect2(Grid grid, Player p, Player p2) throws RemoteException {
        grid.damage(p, p2, 2);
    }
}