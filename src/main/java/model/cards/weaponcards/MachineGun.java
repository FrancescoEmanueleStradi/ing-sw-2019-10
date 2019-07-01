package model.cards.weaponcards;

import model.*;
import model.player.AmmoCube;
import model.cards.WeaponCard;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Machine Gun weapon card.
 */
public class MachineGun extends WeaponCard {

    /**
     * Creates a new Machine Gun.
     */
    public MachineGun() {
        super();
        this.cardName = "Machine Gun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.RED)};
        this.description = "basic effect: Choose 1 or 2 targets you can see and deal 1 damage to each.\n" +
                "with focus shot: Deal 1 additional damage to one of those targets.\n" +                                                                            //write cost
                "with turret tripod: Deal 1 additional damage to the other of those targets and/or deal 1 damage to a different target you can see.\n" +
                "Notes: If you deal both additional points of damage, they must be dealt to 2 different targets. If you see only\n" +
                "2 targets, you deal 2 to each if you use both optional effects. If you use the basic effect on only 1 target, you can still use the the turret tripod to give it 1 additional damage.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p select player(s) to attack, checking if they are visible.
     * Primary attack if 2 visible targets are selected: player p attacks p1 and p2.
     *
     * @param grid grid
     * @param p    the p
     * @param p1   opponent 1
     * @param p2   opponent 2
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1, Player p2) throws RemoteException {
        grid.damage(p, p1, 1);
        if(p2 != null)
            grid.damage(p, p2, 1);
    }

    /**
     * Applies the card's special effect.
     * Focus Shot: player damages p1.
     *
     * @param grid grid
     * @param p    the p
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 1);
    }
}