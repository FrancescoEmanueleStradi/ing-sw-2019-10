package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

public class Heatseeker extends WeaponCard {

    public Heatseeker() {
        super();
        this.cardName = "Heatseeker";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.RED), new AmmoCube(Colour.YELLOW)};
        this.description = "effect: Choose 1 target you cannot see and deal 3 damage to it.\n" +
                             "Notes: Yes, this can only hit targets you cannot see.\n";
    }

    //before: let the player p choose 1 target he cannot see (it checks that condition)

    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //Player p deals 3 damages to the chosen player p1
        grid.damage(p, p1, 3);
    }
}