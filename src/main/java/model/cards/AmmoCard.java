package model.cards;

import model.AmmoCube;
import model.cards.PowerUpCard;

import java.util.ArrayList;

public abstract class AmmoCard {
    protected ArrayList<AmmoCube> aC;
    protected boolean pC;   //if true the AmmoCard has the PowerUpCard

    /*public abstract void AmmoCard();*/            //for each AmmoCard implement the arrays


    public ArrayList<AmmoCube> getaC() {
        return aC;
    }

    public boolean ispC() {
        return pC;
    }
}
