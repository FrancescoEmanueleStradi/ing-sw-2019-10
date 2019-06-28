package model.cards.powerupcards;

import model.*;
import model.Colour;
import model.cards.PowerUpCard;
import model.player.Player;

import java.rmi.RemoteException;
import java.util.List;

/**
 * The Newton powerup card.
 */
public class Newton extends PowerUpCard  {

    /**
     * Creates a new Newton card.
     *
     * @param c card colour
     */
    public Newton(Colour c) {
        super();
        this.cardName = "Newton";
        this.c = c;
        this.description = "You may play this card on your turn before or after any action.\n" +
                            "Choose any other player's figure and move it 1 or 2 squares in one direction.\n" +
                            "(You can't use this to move a figure after it respawns at the end of your turn. That would be too late.)\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a player p1 at any point during his turn, except when p1 respawns at the
     * end of p's turn.
     * Also let player p select a cell one or two cells away from p1.
     *
     * @param grid       grid
     * @param p1         opposing player
     * @param directions direction list
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p1, List<Integer> directions) throws RemoteException {
        for(int i : directions)
            grid.move(p1, i);
    }
}