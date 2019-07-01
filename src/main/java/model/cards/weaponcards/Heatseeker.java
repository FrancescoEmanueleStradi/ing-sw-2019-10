package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Heatseeker weapon card.
 */
public class Heatseeker extends WeaponCard {

    /**
     * Creates a new Heatseeker.
     */
    public Heatseeker() {
        super();
        this.cardName = "Heatseeker";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.RED), new AmmoCube(Colour.YELLOW)};
        this.description = "effect: Choose 1 target you cannot see and deal 3 damage to it.\n" +
                             "Notes: Yes, this can only hit targets you cannot see.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose 1 target he cannot see.
     * Player p deals 3 damages to the chosen player p1.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 3);
    }
}