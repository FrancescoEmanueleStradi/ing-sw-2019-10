package model.cards.powerupcards;

import model.*;
import model.cards.PowerUpCard;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The Teleporter powerup card.
 */
public class Teleporter extends PowerUpCard {

    /**
     * Creates a new Teleporter card.
     *
     * @param c card colour
     */
    public Teleporter(Colour c) {
        super();
        this.cardName = "Teleporter";
        this.c = c;
        this.description = "You may play this card on your turn before or after any action.\n" +
                            "Pick up your figure and set it down on any square of the board.\n" +
                            "(You can't use this after you see where someone respawns at the end of your turn. By then it is too late.)\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a cell he wants to go in. He has to use this card before any player
     * respawns at the end of p's turn.
     *
     * @param grid grid
     * @param p    player (self)
     * @param x    x coordinate
     * @param y    y coordinate
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, String x, String y) throws RemoteException {
        grid.move(p, Integer.parseInt(x), Integer.parseInt(y));
    }
}