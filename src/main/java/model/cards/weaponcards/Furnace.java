package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Furnace.
 */
public class Furnace extends WeaponCard {

    /**
     * Instantiates a new Furnace.
     */
    public Furnace() {
        super();
        this.cardName = "Furnace";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE)};
        this.description = "basic mode: Choose a room you can see, but not the room you are in. Deal 1 damage to everyone in that room.\n" +
                             "in cozy fire mode: Choose a square exactly one move away. Deal 1 damage and 1 mark to everyone on that square.";
    }

    //prior to effect: let the player p choose a room he can see (excluding the room the player is in)

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param c    the c
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Colour c) throws RemoteException {    //damage every enemy in that room
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().getC().equals(c))
                grid.damage(p, enemy, 1);
        }
    }

    //prior to effect: let the player choose a cell one move away from him (it checks this)

    /**
     * Apply special effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param x    the x
     * @param y    the y
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, String x, String y) throws RemoteException {    //Cozy Fire Mode: player p gives 1 damage and 1 mark to every enemy in that cell
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().getPos().getX() == Integer.parseInt(x) && enemy.getCell().getPos().getY() == Integer.parseInt(y)) {
                grid.damage(p, enemy, 1);
                grid.addMark(p, enemy);
            }
        }
    }
}
