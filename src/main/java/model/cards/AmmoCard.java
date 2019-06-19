package model.cards;

import model.player.AmmoCube;

import java.util.ArrayList;
import java.util.List;

public abstract class AmmoCard {

    protected String ammoCardName;
    protected ArrayList<AmmoCube> aC;

    //if true the AmmoCard has the PowerUpCard
    protected boolean pC;

    public String getAmmoCardName() {
        return ammoCardName;
    }

    public List<AmmoCube> getaC() {
        return aC;
    }

    public boolean ispC() {
        return pC;
    }
}