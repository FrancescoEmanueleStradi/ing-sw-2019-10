package model.cards.powerupcards;

import model.*;
import model.cards.PowerUpCard;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The Targeting Scope powerup card.
 */
public class TargetingScope extends PowerUpCard {

    /**
     * Creates a new Targeting Scope powerup card.
     *
     * @param c card colour
     */
    public TargetingScope(Colour c) {
        super();
        this.cardName = "Targeting Scope";
        this.c = c;
        this.description = "You may play this card when you are dealing damage to one or more targets.\n" +
                            "Pay 1 ammo cube of any color. Choose 1 of those targets and give it an extra point of damage.\n" +
                            "Note: You cannot use this to do 1 damage to a target that is receiving only marks.\n";
    }

    /**
     * Applies the card's effect.
     * p deals 1 additional damage to p1.
     * Prior to effect: let player p choose one player p1 who is being attacked by p. p1 can't solely receive marks from
     * the attack that p is using against him.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 1);
    }
}