package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Grenade Launcher weapon card.
 */
public class GrenadeLauncher extends WeaponCard {

    /**
     * Creates a new Grenade launcher.
     */
    public GrenadeLauncher() {
        super();
        this.cardName = "Grenade Launcher";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED)};
        this.description = "basic effect: Deal 1 damage to 1 target you can see. Then you may move the target 1 square.\n" +
                             "with extra grenade: Deal 1 damage to every player on a square you can see. You can use this before or after the basic effect's move.\n" +
                             "Notes: For example, you can shoot a target, move it onto a square with other targets, then damage everyone including the first target.\n" +
                             "Or you can deal 2 to a main target, 1 to everyone else on that square, then move the main target.\n" +
                             "Or you can deal 1 to an isolated target and 1 to everyone on a different square.\n" +
                             "If you target your own square, you will not be moved or damaged.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a target p1 he can see.
     * player p deals 1 damage to p1.
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
     * Move enemy.
     * After primary effect: ask player p if he wants to move the attacked player p1 one cell, and in which direction.
     *
     * @param grid      grid
     * @param p1        opponent 1
     * @param direction direction
     * @throws RemoteException RMI exception
     */
    public void moveEnemy(Grid grid, Player p1, int direction) throws RemoteException {
        grid.move(p1, direction);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let player p choose a Cell cell he can see. PLAYER CAN DO THIS BEFORE OR AFTER THE BASIC EFFECT!
     * Extra Grenade: player p deals 1 damage to every enemy in the selected cell.
     *
     * @param grid grid
     * @param p    player (self)
     * @param x    x coordinate
     * @param y    y coordinate
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, String x, String y) throws RemoteException {
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().getPos().getX() == Integer.parseInt(x) && enemy.getCell().getPos().getY() == Integer.parseInt(y) && enemy != p)
                grid.damage(p, enemy, 1);
        }
    }
}