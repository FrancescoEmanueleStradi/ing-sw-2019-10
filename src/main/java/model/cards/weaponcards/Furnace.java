package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Furnace weapon card.
 */
public class Furnace extends WeaponCard {

    /**
     * Creates a new Furnace.
     */
    public Furnace() {
        super();
        this.cardName = "Furnace";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE)};
        this.description = "basic mode: Choose a room you can see, but not the room you are in. Deal 1 damage to everyone in that room.\n" +
                             "in cozy fire mode: Choose a square exactly one move away. Deal 1 damage and 1 mark to everyone on that square.";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a room he can see (excluding the room the player is in).
     * p damages (1) all players in his cell.
     *
     * @param grid grid
     * @param p    player (self)
     * @param c    colour
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Colour c) throws RemoteException {
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().getC().equals(c))
                grid.damage(p, enemy, 1);
        }
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let the player choose a cell one move away from him.
     * Cozy Fire Mode: player p gives 1 damage and 1 mark to every enemy in that cell.
     *
     * @param grid grid
     * @param p    player (self)
     * @param x    x coordinate
     * @param y    x coordinate
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, String x, String y) throws RemoteException {
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().getPos().getX() == Integer.parseInt(x) && enemy.getCell().getPos().getY() == Integer.parseInt(y)) {
                grid.damage(p, enemy, 1);
                grid.addMark(p, enemy);
            }
        }
    }
}
