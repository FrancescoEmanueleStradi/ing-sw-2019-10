package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

public class PlasmaGun extends WeaponCard {

    public PlasmaGun() {
        super();
        this.cardName = "Plasma Gun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.YELLOW)};
        this.description = "basic effect: Deal 2 damage to 1 target you can see.\n" +
                "with phase glide: Move 1 or 2 squares. This effect can be used either before or after the basic effect.\n" +
                "with charged shot: Deal 1 additional damage to your target.\n" +
                "Notes: The two moves have no ammo cost. You don't have to be able to see your target when you play the card.\n" +
                "For example, you can move 2 squares and shoot a target you now see. You cannot use 1 move before shooting and 1 move after.\n";
    }

    //before: let the player p choose one target he can see

    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //player p damages the chosen player p1
        grid.damage(p, p1, 2);
    }

    //before: ask the player p how many cells he wants to move (1 or 2) (alternative: let him click the cell he wants to go to). PLAYER CAN USE THIS BEFORE OR AFTER THE PRIMARY EFFECT

    public void applySpecialEffect(Grid grid, Player p, int moves, int direction1, int direction2) throws RemoteException {   //Phase Glide: player p moves 1 or 2 cells: he can change direction (i.e. one move up and one right)
        if(moves == 1)
            grid.move(p, direction1);
        else if(moves == 2) {
            grid.move(p, direction1);
            grid.move(p, direction2);
        }
    }

    public void applySpecialEffect2(Grid grid, Player p, Player p1) throws RemoteException {   //Charged Shots: player p deals 1 additional damage to the same player p1 attacked with the primary effect
        grid.damage(p, p1, 1);
    }
}