package model.cards.powerupcards;

import model.*;
import model.cards.PowerUpCard;
import model.player.Player;

import java.rmi.RemoteException;

public class TagbackGrenade extends PowerUpCard {

    public TagbackGrenade(Colour c) {
        super();
        this.cardName = "Tagback Grenade";
        this.c = c;
        this.description = "You may play this card when you receive damage from a player you can see.\n" +
                            "Give that player 1 mark.\n";
    }

    //before: let player p use this card only when he is receiving damage from a player he can see.

    public void applyEffect(Grid grid, Player p, Player p1) throws RemoteException {   //p gives 1 mark to p1
        grid.addMark(p, p1);
    }
}