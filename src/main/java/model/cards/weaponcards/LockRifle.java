package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

public class LockRifle extends WeaponCard {

    public LockRifle() {
        super();
        this.cardName = "Lock Rifle";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE)};
        this.description = "basic effect: Deal 2 damage and 1 mark to 1 target you can see.\n" +
                             "with second lock: Deal 1 mark to a different target you can see.\n";
    }

    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException { //player p attacks p1 (visible), giving him 2 damage and 1 mark
        grid.damage(p, p1, 2);
        grid.addMark(p, p1);
    }

    public void applySpecialEffect(Grid grid, Player p, Player p2) throws RemoteException { //Second Lock: player p attacks p2 (visible), who is different from the p1 selected for the primary effect: the controller will check this!
        grid.addMark(p, p2);
    }
}