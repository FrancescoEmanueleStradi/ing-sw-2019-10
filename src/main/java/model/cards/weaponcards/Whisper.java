package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Whisper.
 */
public class Whisper extends WeaponCard {

    /**
     * Instantiates a new Whisper.
     */
    public Whisper () {
        super();
        this.cardName = "Whisper";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE), new AmmoCube(Colour.YELLOW)};
        this.description = "effect: Deal 3 damage and 1 mark to 1 target you can see. Your target must be at least 2 moves away from you.\n" +
                             "Notes: For example, in the 2-by-2 room, you cannot shoot a target on an adjacent square, but you can shoot a target on the diagonal.\n" +
                             "If you are beside a door, you can't shoot a target on the other side of the door, but you can shoot a target on a different square of that room.\n";
    }

    //prior to effect: let the player p choose 1 target p1 he can see: it has to be at least 2 moves away from player p, so check this.

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //Player p deals 3 damages and adds 1 mark to player p1
        grid.damage(p, p1, 3);
        grid.addMark(p, p1);
    }
}
