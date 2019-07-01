package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Shockwave weapon card.
 */
public class Shockwave extends WeaponCard {

    /**
     * Creates a new Shockwave.
     */
    public Shockwave() {
        super();
        this.cardName = "Shockwave";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW)};
        this.description = "basic mode: Choose up to 3 targets on different squares, each exactly 1 move away. Deal 1 damage to each target.\n" +
                "in tsunami mode: Deal 1 damage to all targets that are exactly 1 move away.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose up to three targets p1, p2, p3 on different cells, each exactly one cell
     * away from p.
     * Player p deals 1 damage to p1 and, if p2/p3 is selected, deals 1 damage to him/them too.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @param p2   opponent 2
     * @param p3   opponent 3
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1, Player p2, Player p3) throws RemoteException {
        grid.damage(p, p1, 1);
        if(p2 != null)
            grid.damage(p, p2, 1);
        if(p3 != null)
            grid.damage(p, p3, 1);
    }

    /**
     * Applies the card's special effect.
     * Tsunami Mode: player p deals 1 damage to every enemy who is in a cell exactly one move away from him.
     *
     * @param grid grid
     * @param p    player (self)
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p) throws RemoteException {
        for(Player enemy : grid.getPlayers()) {
            if((enemy.getCell().getPos().getX() == p.getCell().getPos().getX()+1) ||
                    enemy.getCell().getPos().getX() == p.getCell().getPos().getX()-1 ||
                    enemy.getCell().getPos().getY() == p.getCell().getPos().getY()+1 ||
                    enemy.getCell().getPos().getY() == p.getCell().getPos().getY()-1)
                grid.damage(p, enemy, 1);
        }
    }
}