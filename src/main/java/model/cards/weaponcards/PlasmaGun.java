package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Plasma Gun weapon card.
 */
public class PlasmaGun extends WeaponCard {

    /**
     * Creates a new Plasma Gun.
     */
    public PlasmaGun() {
        super();
        this.cardName = "Plasma Gun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.YELLOW)};
        this.description = "basic effect: Deal 2 damage to 1 target you can see.\n" +
                "with phase glide: Move 1 or 2 squares. This effect can be used either before or after the basic effect.\n" +
                "with charged shot: Deal 1 additional damage to your target.\n" +
                "Notes: The two moves have no ammo cost. You don't have to be able to see your target when you play the card.\n" +
                "For example, you can move 2 squares and shoot a target you now see. You cannot use 1 move before shooting and 1 move after.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose one target he can see.
     * Player p damages the chosen player p1.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 2);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: ask player p how many cells he wants to move (1 or 2).
     * Phase Glide: player p moves 1 or 2 cells: he can change direction (i.e. one move up and one right)
     * PLAYER CAN USE THIS BEFORE OR AFTER THE PRIMARY EFFECT.
     *
     * @param grid       grid
     * @param p          player (self)
     * @param moves      move count
     * @param direction1 direction 1
     * @param direction2 the direction 2
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, int moves, int direction1, int direction2) throws RemoteException {
        if(moves == 1)
            grid.move(p, direction1);
        else if(moves == 2) {
            grid.move(p, direction1);
            grid.move(p, direction2);
        }
    }

    /**
     * Applies the card's second special effect.
     * Charged Shots: player p deals 1 additional damage to the same player p1 attacked with the primary effect.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect2(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 1);
    }
}