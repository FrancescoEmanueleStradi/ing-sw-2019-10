package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * Power Glove weapon card.
 */
public class PowerGlove extends WeaponCard {

    /**
     * Creates a new Power Glove.
     */
    public PowerGlove() {
        super();
        this.cardName = "Power Glove";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)};
        this.description = "basic mode: Choose 1 target on any square exactly 1 move away. Move onto that square and give the target 1 damage and 2 marks.\n" +
                "in rocket fist mode: Choose a square exactly 1 move away. Move onto that square. You may deal 2 damage to 1 target there.\n" +
                "If you want, you may move 1 more square in that same direction (but only if it is a legal move). You may deal 2 damage to 1 target there, as well.\n" +
                "Notes: In rocket fist mode, you're flying 2 squares in a straight line, punching 1 person per square.\n";
    }

    /**
     * Applies the card's effect.
     * Prior to effect: let player p choose a target p1 in a cell exactly one move away from him.
     * Player p moves in the same cell as the selected p1, deals him 1 damage and 2 marks.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {
        p.changeCell(p1.getCell());
        grid.damage(p, p1, 1);
        grid.addMark(p, p1);
        grid.addMark(p, p1);
    }

    /**
     * Applies the card's special effect, step 1. Later steps are optional.
     * Before Rocket Fist Mode: Player p chooses one cell to go to.
     * Player p moves in the first cell selected.
     *
     * @param p    player (self)
     * @param grid grid
     * @param x    x coordinate
     * @param y    y coordinate
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffectPart1(Player p, Grid grid, String x, String y) throws RemoteException {
        grid.move(p, Integer.parseInt(x), Integer.parseInt(y));
    }

    /**
     * Applies the card's special effect, step 2.
     * Player p deals 2 damage to a target in his cell.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p1   opponent 1
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffectPart2(Grid grid, Player p, Player p1) throws RemoteException {
        grid.damage(p, p1, 2);
    }

    /**
     * Applies card's special effect, step 3.
     * If he wants, he can move again in the same direction.
     * Player p moves in the second cell selected.
     *
     * @param p    player (self)
     * @param grid grid
     * @param x2   x coordinate
     * @param y2   y coordinate
     * @throws RemoteException RMI exception
     */
        public void applySpecialEffectPart3(Player p, Grid grid, String x2, String y2) throws RemoteException {
            grid.move(p, Integer.parseInt(x2), Integer.parseInt(y2));
    }

    /**
     * Applies card;s special effect, step 4.
     * Finally, he can attack a player p2 there.
     * Player p deals 2 damage to a target in his cell.
     *
     * @param grid grid
     * @param p    player (self)
     * @param p2   opponent 2
     * @throws RemoteException RMI exception
     */
    public void applySpecialEffectPart4(Grid grid, Player p, Player p2) throws RemoteException {
        grid.damage(p, p2, 2);
    }
}