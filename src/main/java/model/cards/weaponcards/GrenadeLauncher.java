package model.cards.weaponcards;

import model.*;
import model.board.Cell;
import model.cards.WeaponCard;

public class GrenadeLauncher extends WeaponCard {

    private String optionalEffect1 = "Extra Grenade";

    public GrenadeLauncher() throws InvalidColourException {
        super();
        this.cardName = "Grenade Launcher";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 1;
        this.alternateFireMode = false;
        String description = "basic effect: Deal 1 damage to 1 target you can see. Then you may move the target 1 square.\n" +
                             "with extra grenade: Deal 1 damage to every player on a square you can see. You can use this before or after the basic effect's move.\n" +
                             "Notes: For example, you can shoot a target, move it onto a square with other targets, then damage everyone including the first target.\n" +
                             "Or you can deal 2 to a main target, 1 to everyone else on that square, then move the main target.\n" +
                             "Or you can deal 1 to an isolated target and 1 to everyone on a different square.\n" +
                             "If you target your own square, you will not be moved or damaged.\n";
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    //before: let player p choose a target p1 he can see.

    public void applyEffect(Grid grid, Player p, Player p1) { //player p deals 1 damage to p1
        grid.damage(p, p1, 1);
    }

    //after primary effect: ask player p if he wants to move the attacked player p1 one cell, and in which direction (click on cell and from that we get the direction?)

    public void moveEnemy(Grid grid, Player p1, int direction) {    //right after the primary effect
        grid.move(p1, direction);
    }

    //before: let the player p choose a Cell cell he can see. PLAYER CAN DO THIS BEFORE OR AFTER THE BASIC EFFECT!

    public void applySpecialEffect(Grid grid, Player p, Cell cell) {  //Extra Grenade: player p deals 1 damage to every enemy on the selected Cell cell
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().equals(cell) && enemy != p)
                grid.damage(p, enemy, 1);
        }
    }
}