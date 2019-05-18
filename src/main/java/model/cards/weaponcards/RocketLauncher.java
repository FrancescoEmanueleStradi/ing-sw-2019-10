package model.cards.weaponcards;

import model.*;
import model.board.Cell;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

public class RocketLauncher extends WeaponCard {

    private String optionalEffect1 = "Rocket Jump";
    private String optionalEffect2 = "Fragmenting Warhead";

    private Player firstEnemy;
    private Cell firstEnemyOriginalCell;

    public RocketLauncher() {
        super();
        this.cardName = "Rocket Launcher";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 2;
        this.alternateFireMode = false;
        this.description = "basic effect: Deal 2 damage to 1 target you can see that is not on your square. Then you may move the target 1 square.\n" +
                             "with rocket jump: Move 1 or 2 squares. This effect can be used either before or after the basic effect.\n" +
                             "with fragmenting warhead: During the basic effect, deal 1 damage to every player on your target's original square – including the target, even if you move it.\n" +
                             "Notes: If you use the rocket jump before the basic effect, you consider only your new square when determining if a target is legal.\n" +
                             "You can even move off a square so you can shoot someone on it.\n" +
                             "If you use the fragmenting warhead, you deal damage to everyone on the target's square before you move the target – your target will take 3 damage total.\n";
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    public String getOptionalEffect2() {
        return optionalEffect2;
    }

    //before: let player p choose one visible player p1 who is not on the same cell as p.

    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //player p deals 2 damages to p1. We save the original cell of p1, in case p uses Fragmenting Warhead
        grid.damage(p, p1, 2);
        firstEnemyOriginalCell = p1.getCell();
    }

    //after primary effect: ask player p if he wants to move p1 one cell, and in which direction (click on cell and from that we get the direction?).

    public void movePlayer(Grid grid, Player p1, int direction) throws RemoteException{   //right after the primary effect
        grid.move(p1, direction);
    }

    //before: ask player p if he wants to move one or two cells, and in which direction (alternative: let him select the cell he wants to go to). PLAYER CAN USE THIS BEFORE OR AFTER THE PRIMARY EFFECT

    public void applySpecialEffect(Grid grid, Player p, int moves, int direction1, int direction2) throws RemoteException{    //Rocket Jump: p moves one or two cells, according to what he has chosen
        if(moves == 1) {
            if(direction1 == 1)
                grid.move(p, 1);
            else if(direction1 == 2)
                grid.move(p, 2);
            else if(direction1 == 3)
                grid.move(p, 3);
            else if(direction1 == 4)
                grid.move(p, 4);
        }
        else if(moves == 2) {
            if(direction1 == 1) {
                grid.move(p, 1);
                if(direction2 == 1)
                    grid.move(p, 1);
                else if(direction2 == 2)
                    grid.move(p, 2);
                else if(direction2 == 3)
                    grid.move(p, 3);
                else if(direction2 == 4)
                    grid.move(p, 4);
            }
            else if(direction1 == 2) {
                grid.move(p, 2);
                if(direction2 == 1)
                    grid.move(p, 1);
                else if(direction2 == 2)
                    grid.move(p, 2);
                else if(direction2 == 3)
                    grid.move(p, 3);
                else if(direction2 == 4)
                    grid.move(p, 4);
            }
            else if(direction1 == 3) {
                grid.move(p, 3);
                if(direction2 == 1)
                    grid.move(p, 1);
                else if(direction2 == 2)
                    grid.move(p, 2);
                else if(direction2 == 3)
                    grid.move(p, 3);
                else if(direction2 == 4)
                    grid.move(p, 4);
            }
            else if(direction1 == 4) {
                grid.move(p, 4);
                if(direction2 == 1)
                    grid.move(p, 1);
                else if(direction2 == 2)
                    grid.move(p, 2);
                else if(direction2 == 3)
                    grid.move(p, 3);
                else if(direction2 == 4)
                    grid.move(p, 4);
            }
        }
    }

    public void applySpecialEffect2(Grid grid, Player p) throws RemoteException{  //player p deals 1 damage to every player enemy in the original cell of p1 selected for the primary effect, including p1 even if he was moved
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().equals(firstEnemyOriginalCell) && enemy != firstEnemy)
                grid.damage(p, enemy, 1);
        }
        grid.damage(p, firstEnemy, 1);
    }
}