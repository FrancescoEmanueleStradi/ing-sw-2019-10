package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Electroscythe.
 */
public class Electroscythe extends WeaponCard {

    /**
     * Instantiates a new Electroscythe.
     */
    public Electroscythe() {
        super();
        this.cardName = "Electroscythe";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.description = "basic mode: Deal 1 damage to every other player on your square.\n" +
                             "in reaper mode: Deal 2 damage to every other player on your square.\n";
    }

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p) throws RemoteException { //player p damages (1) every enemy on the same square as player p
        for(Player enemy : grid.getPlayers()) {
            if(grid.whereAmI(enemy).equals(grid.whereAmI(p)) && !(enemy.equals(p)))
                grid.damage(p, enemy, 1);
        }
    }

    /**
     * Apply special effect.
     *
     * @param grid the grid
     * @param p    the p
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p) throws RemoteException { //Reaper Mode: player p damages (2) every enemy on the same square as player p
        for(Player enemy : grid.getPlayers()) {
            if(grid.whereAmI(enemy).equals(grid.whereAmI(p)) && !(enemy.equals(p)))
                grid.damage(p, enemy, 2);
        }
    }
}