package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

public class Railgun extends WeaponCard {

    private String alternativeEffect = "Piercing Mode";

    public Railgun() {
        super();
        this.cardName = "Railgun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = true;
        this.description = "basic mode: Choose a cardinal direction and 1 target in that direction. Deal 3 damage to it.\n" +
                "in piercing mode: Choose a cardinal direction and 1 or 2 targets in that direction. Deal 2 damage to each.\n" +
                "Notes: Basically, you're shooting in a straight line and ignoring walls.\n" +
                "You don't have to pick a target on the other side of a wall – it could even be someone on your own square – but shooting through walls sure is fun.\n" +
                "There are only 4 cardinal directions. You imagine facing one wall or door, square-on, and firing in that direction. Anyone on a square in that direction (including yours) is a valid target.\n" +
                "In piercing mode, the 2 targets can be on the same square or on different squares.\n";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    //before: let player p choose a cardinal direction (1, 2, 3, 4) and a player p1 in that direction, even if there is a wall. LET HIM CHOOSE ENEMIES ON HIS SAME CELL TOO!!

    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //player p deals 3 damages to p1
        grid.damage(p, p1, 3);
    }

    //before: let player p choose a cardinal direction (1, 2, 3, 4) and one or two players p1, p2 in that direction, even if there is a wall. LET HIM CHOOSE ENEMIES ON HIS SAME CELL TOO!! P1 AND P2 CAN BE ON THE SAME CELL!

    public void applySpecialEffect(Grid grid, Player p, Player p1, Player p2) throws RemoteException{ //player p deals 2 damage to p1 and, if selected, 2 damages to p2 too
        grid.damage(p, p1, 2);
        if(p2 != null)
            grid.damage(p, p2, 2);
    }
}