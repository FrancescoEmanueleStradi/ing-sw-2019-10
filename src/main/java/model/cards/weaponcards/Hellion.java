package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Hellion weapon card.
 */
public class Hellion extends WeaponCard {

    /**
     * Creates a new Hellion.
     */
    public Hellion() {
        super();
        this.cardName = "Hellion";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.YELLOW)};
        this.description = "basic mode: Deal 1 damage to 1 target you can see at least 1 move away. Then give 1 mark to that target and everyone else on that square.\n" +
                             "in nano-tracer mode: Deal 1 damage to 1 target you can see at least 1 move away. Then give 2 marks to that target and everyone else on that square.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose 1 visible target p1 at least 1 move away.
     * Player p deals 1 damage to p1 and adds a mark to p1 and to every enemy in his cell.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 1);
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().equals(p1.getCell()))
                grid.addMark(p, enemy);
        }
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let player p choose 1 visible target p1 at least 1 move away.
     * Player p deals 1 damage to p1 and adds 2 marks to p1 and to every enemy in his cell.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 1);
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().equals(p1.getCell())) {
                grid.addMark(p, enemy);
                grid.addMark(p, enemy);
            }
        }
    }
}