package model.cards;

import model.player.AmmoCube;

import java.util.ArrayList;
import java.util.List;

public abstract class AmmoCard {

    protected ArrayList<AmmoCube> aC;
    protected boolean pC;   //if true the AmmoCard has the PowerUpCard

    public List<AmmoCube> getaC() {
        return aC;
    }

    public boolean ispC() {
        return pC;
    }
}