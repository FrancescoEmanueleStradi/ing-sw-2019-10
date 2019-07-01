package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Sledgehammer weapon card.
 */
public class Sledgehammer extends WeaponCard {

    /**
     * Creates a new Sledgehammer.
     */
    public Sledgehammer() {
        super();
        this.cardName = "Sledgehammer";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW)};
        this.description = "basic mode: Deal 2 damage to 1 target on your square.\n" +
                             "in pulverize mode: Deal 3 damage to 1 target on your square, then move that target 0, 1, or 2 squares in one direction.\n" +
                             "Notes: Remember that moves go through doors, but not walls.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a target p1 in his cell.
     * Player p deals 2 damage to the chosen p1.
     *
     * @param grid grid
     * @param p    player (self).
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 2);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let player p choose a target p1 in his cell.
     * Pulverize Mode (first part): player p deals 3 damage to the chosen p1.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 3);
    }

    /**
     * Moves enemy.
     * Then (optional): if player p wants, he can move the attacked p1 0, 1, 2 cells in one direction.
     * Pulverize Mode (second part): enemy p1 is moved where player p has decided.
     *
     * @param p1        opponent 1
     * @param grid      grid
     * @param moves     move count
     * @param direction direction
     * @throws RemoteException RMI exception
     */
    public void moveEnemy(Player p1, Grid grid, int moves, int direction) throws RemoteException {
        for(int i = 0; i < moves; i++)
            grid.move(p1, direction);
    }
}