package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Whisper weapon card.
 */
public class Whisper extends WeaponCard {

    /**
     * Creates a new Whisper.
     */
    public Whisper () {
        super();
        this.cardName = "Whisper";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE), new AmmoCube(Colour.YELLOW)};
        this.description = "effect: Deal 3 damage and 1 mark to 1 target you can see. Your target must be at least 2 moves away from you.\n" +
                             "Notes: For example, in the 2-by-2 room, you cannot shoot a target on an adjacent square, but you can shoot a target on the diagonal.\n" +
                             "If you are beside a door, you can't shoot a target on the other side of the door, but you can shoot a target on a different square of that room.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose 1 target p1 he can see and who must be at least 2 moves away from p.
     * Player p deals 3 damage and adds 1 mark to player p1.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 3);
        grid.addMark(p, p1);
    }
}
