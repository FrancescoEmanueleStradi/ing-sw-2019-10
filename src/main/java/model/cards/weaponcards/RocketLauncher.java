package model.cards.weaponcards;

import model.*;
import model.board.Cell;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

/**
 * The type Rocket launcher.
 */
public class RocketLauncher extends WeaponCard {

    private Player firstEnemy;
    private Cell firstEnemyOriginalCell;

    /**
     * Instantiates a new Rocket launcher.
     */
    public RocketLauncher() {
        super();
        this.cardName = "Rocket Launcher";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.RED)};
        this.description = "basic effect: Deal 2 damage to 1 target you can see that is not on your square. Then you may move the target 1 square.\n" +
                             "with rocket jump: Move 1 or 2 squares. This effect can be used either before or after the basic effect.\n" +
                             "with fragmenting warhead: During the basic effect, deal 1 damage to every player on your target's original square – including the target, even if you move it.\n" +
                             "Notes: If you use the rocket jump before the basic effect, you consider only your new square when determining if a target is legal.\n" +
                             "You can even move off a square so you can shoot someone on it.\n" +
                             "If you use the fragmenting warhead, you deal damage to everyone on the target's square before you move the target – your target will take 3 damage total.\n";
    }

    //prior to effect: let player p choose one visible player p1 who is not on the same cell as p.

    /**
     * Apply effect.
     *
     * @param grid the grid
     * @param p    the p
     * @param p1   the p 1
     * @throws RemoteException the remote exception
     */
    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //player p deals 2 damages to p1. We save the original cell of p1, in case p uses Fragmenting Warhead
        grid.damage(p, p1, 2);
        firstEnemy = p1;
        firstEnemyOriginalCell = p1.getCell();
    }

    //after primary effect: ask player p if he wants to move p1 one cell, and in which direction (click on cell and from that we get the direction?).

    /**
     * Move player.
     *
     * @param grid      the grid
     * @param p1        the p 1
     * @param direction the direction
     * @throws RemoteException the remote exception
     */
    public void movePlayer(Grid grid, Player p1, int direction) throws RemoteException {   //right after the primary effect
        grid.move(p1, direction);
    }

    //prior to effect: ask player p if he wants to move one or two cells, and in which direction (alternative: let him select the cell he wants to go to). PLAYER CAN USE THIS BEFORE OR AFTER THE PRIMARY EFFECT

    /**
     * Apply special effect.
     *
     * @param grid       the grid
     * @param p          the p
     * @param moves      the moves
     * @param direction1 the direction 1
     * @param direction2 the direction 2
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect(Grid grid, Player p, int moves, int direction1, int direction2) throws RemoteException {    //Rocket Jump: p moves one or two cells, according to what he has chosen
        if(moves == 1)
            grid.move(p, direction1);
        else if(moves == 2) {
            grid.move(p, direction1);
            grid.move(p, direction2);
        }
    }

    /**
     * Apply special effect 2.
     *
     * @param grid the grid
     * @param p    the p
     * @throws RemoteException the remote exception
     */
    public void applySpecialEffect2(Grid grid, Player p) throws RemoteException {  //player p deals 1 damage to every player enemy in the original cell of p1 selected for the primary effect, including p1 even if he was moved
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().equals(firstEnemyOriginalCell) && enemy != firstEnemy)
                grid.damage(p, enemy, 1);
        }
        grid.damage(p, firstEnemy, 1);
    }
}