package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

public class Shockwave extends WeaponCard {

    private String alternativeEffect = "Tsunami Mode";

    public Shockwave() {
        super();
        this.cardName = "Shockwave";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = true;
        this.description = "basic mode: Choose up to 3 targets on different squares, each exactly 1 move away. Deal 1 damage to each target.\n" +
                "in tsunami mode: Deal 1 damage to all targets that are exactly 1 move away.\n";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    //before: let player p choose up to three targets p1, p2, p3 on different cells, each exactly one cell away from p.

    public void applyEffect(Grid grid, Player p, Player p1, Player p2, Player p3) throws RemoteException { //player p deals 1 damage to p1 and, if p2/p3 is selected, he deals 1 damage to him/them too.
        grid.damage(p, p1, 1);
        if(p2 != null)
            grid.damage(p, p2, 1);
        if(p3 != null)
            grid.damage(p, p3, 1);
    }

    public void applySpecialEffect(Grid grid, Player p) throws RemoteException{   //Tsunami Mode: player p deals 1 damage to every enemy who is in a cell exactly one move away from him
        for(Player enemy : grid.getPlayers()) {
            if((enemy.getCell().getP().getX() == p.getCell().getP().getX()+1) ||
                    enemy.getCell().getP().getX() == p.getCell().getP().getX()-1 ||
                    enemy.getCell().getP().getY() == p.getCell().getP().getY()+1 ||
                    enemy.getCell().getP().getY() == p.getCell().getP().getY()-1)
                grid.damage(p, enemy, 1);
        }
    }
}