package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Electroscythe weapon card,
 */
public class Electroscythe extends WeaponCard {

    /**
     * Creates a new Electroscythe.
     */
    public Electroscythe() {
        super();
        this.cardName = "Electroscythe";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.description = "basic mode: Deal 1 damage to every other player on your square.\n" +
                             "in reaper mode: Deal 2 damage to every other player on your square.\n";
    }

    /**
     * Applies the card's effect.
     * Player p damages (1) every enemy on the same square as player p.
     *
     * @param grid grid
     * @param p    player (self)
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p) throws RemoteException {
        for(Player enemy : grid.getPlayers()) {
            if(grid.whereAmI(enemy).equals(grid.whereAmI(p)) && !(enemy.equals(p)))
                grid.damage(p, enemy, 1);
        }
    }

    /**
     * Applies the card's special effect.
     * Reaper Mode: player p damages (2) every enemy on the same square as player p.
     *
     * @param grid grid
     * @param p    player (self)
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p) throws RemoteException {
        for(Player enemy : grid.getPlayers()) {
            if(grid.whereAmI(enemy).equals(grid.whereAmI(p)) && !(enemy.equals(p)))
                grid.damage(p, enemy, 2);
        }
    }
}