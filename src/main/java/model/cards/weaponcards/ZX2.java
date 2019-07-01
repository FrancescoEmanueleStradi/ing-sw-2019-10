package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * ZX-2 weapon card.
 */
public class ZX2 extends WeaponCard {

    /**
     * Creates a new ZX-2.
     */
    public ZX2() {
        super();
        this.cardName = "ZX-2";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.RED)};
        this.description = "basic mode: Deal 1 damage and 2 marks to 1 target you can see.\n" +
                             "in scanner mode: Choose up to 3 targets you can see and deal 1 mark to each.\n" +
                             "Notes: Remember that the 3 targets can be in 3 different rooms.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a target p1 he can see.
     * Player p deals 1 damage to p1 and gives him 2 marks.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 1);
        grid.addMark(p, p1);
        grid.addMark(p, p1);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let player p choose up to three targets he can see: p1, p2, p3. They can be in three different
     * rooms.
     * Scanner Mode: player p deals 1 damage to p1, p2 and p3: p2 and/or p3 can be null.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @param p2   opponent 2
     * @param p3   opponent 3
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1, Player p2, Player p3) throws RemoteException {
        grid.damage(p, p1, 1);
        if(p2 != null)
            grid.damage(p, p2, 1);
        if(p3 != null)
            grid.damage(p, p3, 1);
    }
}