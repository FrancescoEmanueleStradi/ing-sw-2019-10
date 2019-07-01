package model.cards.powerupcards;

import model.*;
import model.cards.PowerUpCard;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The Tagback Grenade powerup card.
 */
public class TagbackGrenade extends PowerUpCard {

    /**
     * Creates a new Tagback Grenade card.
     *
     * @param c card colour
     */
    public TagbackGrenade(Colour c) {
        super();
        this.cardName = "Tagback Grenade";
        this.c = c;
        this.description = "You may play this card when you receive damage from a player you can see.\n" +
                            "Give that player 1 mark.\n";
    }

    /**
     * Applies the card's effect.
     * p gives 1 mark to p1.
     * Prior to effect: let player p use this card only when he is receiving damage from a player he can see.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.addMark(p, p1);
    }
}