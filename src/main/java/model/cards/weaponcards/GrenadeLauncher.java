package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Grenade launcher.
 */
public class GrenadeLauncher extends WeaponCard {

    /**
     * Instantiates a new Grenade launcher.
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

    //prior to effect: let player p choose a target p1 he can see.

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException { //player p deals 1 damage to p1
        grid.damage(p, p1, 1);
    }

    //after primary effect: ask player p if he wants to move the attacked player p1 one cell, and in which direction (click on cell and from that we get the direction?)

    /**
     * Move enemy.
     *
     * @param grid      the grid
     * @param p1        the p 1
     * @param direction the direction
     * @throws RemoteException the remote exception
     */
    public void moveEnemy(Grid grid, Player p1, int direction) throws RemoteException {    //right after the primary effect
        grid.move(p1, direction);
    }

    //prior to effect: let the player p choose a Cell cell he can see. PLAYER CAN DO THIS BEFORE OR AFTER THE BASIC EFFECT!

    /**
     * Apply special effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param x    the x
     * @param y    the y
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, String x, String y) throws RemoteException {  //Extra Grenade: player p deals 1 damage to every enemy on the selected Cell cell
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().getPos().getX() == Integer.parseInt(x) && enemy.getCell().getPos().getY() == Integer.parseInt(y) && enemy != p)
                grid.damage(p, enemy, 1);
        }
    }
}