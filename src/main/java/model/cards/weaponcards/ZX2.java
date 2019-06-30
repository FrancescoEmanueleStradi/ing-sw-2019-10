package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Zx 2.
 */
public class ZX2 extends WeaponCard {

    /**
     * Instantiates a new Zx 2.
     */
    public ZX2() {
        super();
        this.cardName = "ZX-2";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.RED)};
        this.description = "basic mode: Deal 1 damage and 2 marks to 1 target you can see.\n" +
                             "in scanner mode: Choose up to 3 targets you can see and deal 1 mark to each.\n" +
                             "Notes: Remember that the 3 targets can be in 3 different rooms.\n";
    }

    //prior to effect: let player p choose one target he can see: p1.

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //player p deals 1 damage to p1 and adds 2 marks to him
        grid.damage(p, p1, 1);
        grid.addMark(p, p1);
        grid.addMark(p, p1);
    }

    //prior to effect: let player p choose up to three target he can see: p1, p2, p3. They can be in three different rooms.

    /**
     * Apply special effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @param p2   the p 2
     * @param p3   the p 3
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1, Player p2, Player p3) throws RemoteException { //Scanner Mode: player p deals 1 damage to p1, p2 and p3: p2 and/or p3 can be null
        grid.damage(p, p1, 1);
        if(p2 != null)
            grid.damage(p, p2, 1);
        if(p3 != null)
            grid.damage(p, p3, 1);
    }
}