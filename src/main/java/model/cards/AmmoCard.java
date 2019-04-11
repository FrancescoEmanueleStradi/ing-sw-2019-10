package model.cards;

import model.AmmoCube;

import java.util.ArrayList;
import java.util.List;

public abstract class AmmoCard {
    protected ArrayList<AmmoCube> aC;
    protected boolean pC;   //if true the AmmoCard has the PowerUpCard

    /*public abstract void AmmoCard();*/            //for each AmmoCard implement the arrays


    public List<AmmoCube> getaC() {
        return aC;
    }

    public boolean ispC() {
        return pC;
    }
}