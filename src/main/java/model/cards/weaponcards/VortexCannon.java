package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Vortex Cannon weapon card.
 */
public class VortexCannon extends WeaponCard {

    private int xVortex;
    private int yVortex;

    /**
     * Creates a new Vortex Cannon.
     */
    public VortexCannon() {
        super();
        this.cardName = "Vortex Cannon";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE)};
        this.description = "basic effect: Choose a square you can see, but not your square. Call it \"the vortex\".\n" +
                             "Choose a target on the vortex or 1 move away from it. Move it onto the vortex and give it 2 damage.\n" +
                             "with black hole: Choose up to 2 other targets on the vortex or 1 move away from it. Move them onto the vortex and give them each 1 damage.\n" +
                             "Notes: The 3 targets must be different, but some might start on the same square.\n" +
                             "It is legal to choose targets on your square, on the vortex, or even on squares you can't see. They all end up on the vortex.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a cell for the vortex, and a player p1 to move into the vortex
     * (p1 must be on the vortex or 1 move away from it) and to damage.
     * p1 is moved into the vortex and damaged.
     *
     * @param grid    grid
     * @param p       player (self)
     * @param p1      opponent 1
     * @param xVortex x coordinate
     * @param yVortex y coordinate
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1, String xVortex, String yVortex) throws RemoteException {
        grid.move(p1, Integer.parseInt(xVortex), Integer.parseInt(yVortex));
        grid.damage(p, p1, 2);
        this.xVortex = Integer.parseInt(xVortex);
        this.yVortex = Integer.parseInt(yVortex);
    }

    /**
     * Applies the card's special effect.
     * Prior to effect: let player p choose up to 2 other targets on the vortex or 1 move away from it.
     * p2 can be null. p1 and p2 are moved into the vortex and damaged.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @param p2   opponent 2
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffect(Grid grid, Player p, Player p1, Player p2) throws RemoteException {
        grid.move(p1, this.xVortex, this.yVortex);
        if(p2 != null) {
            grid.move(p2, this.xVortex, this.yVortex);
            grid.damage(p, p2, 1);
        }
        grid.damage(p, p1, 1);
    }
}