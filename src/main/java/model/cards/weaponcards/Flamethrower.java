package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

import java.rmi.RemoteException;

public class Flamethrower extends WeaponCard {

    public Flamethrower() {
        super();
        this.cardName = "Flamethrower";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED)};
        this.description = "basic mode: Choose a square 1 move away and possibly a second square 1 more move away in the same direction.\n" +
                             "On each square, you may choose 1 target and give it 1 damage.\n" +
                             "in barbecue mode: Choose 2 squares as above. Deal 2 damage to everyone on the first square and 1 damage to everyone on the second square.\n" +
                             "Notes: This weapon cannot damage anyone in your square. However, it can sometimes damage a target you can't see – the flame won't go through walls, but it will go through doors. Think of it as a straight-line\n" +
                             "blast of flame that can travel 2 squares in a cardinal direction.\n";
    }

    //before: let the player p choose a cell one move away from him, and possibly a second cell one more move away in the same direction (not through walls but ok if through doors). Let him choose one player for each cell he has selected (p1 and p2 respectively). It can be useful to implement a method here.

    public void applyEffect(Grid grid, Player p, Player p1, Player p2) throws RemoteException {    //player p deals 1 damage to p1 and p2. p2 can be null
        grid.damage(p, p1, 1);
        if(p2 != null)
            grid.damage(p, p2, 1);
    }

    //before: let the player p choose one or two cells as above. This time, however, he does not select the player(s)

    public void applySpecialEffect(Grid grid, Player p, String x1, String y1, String x2, String y2) throws RemoteException { //Barbecue Mode: player p deals 2 damage to every enemy in the first cell c1, and 1 damage to every enemy in the second cell c2. c2 can be null
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().getPos().getX() == Integer.parseInt(x1) && enemy.getCell().getPos().getY() == Integer.parseInt(y1))
                grid.damage(p, enemy, 2);
        }

        if(x2 != null && y2 != null) {
            for(Player enemy : grid.getPlayers()) {
                if(enemy.getCell().getPos().getX() == Integer.parseInt(x2) && enemy.getCell().getPos().getY() == Integer.parseInt(y2))
                    grid.damage(p, enemy, 1);
            }
        }
    }
}