package model.cards;

import model.AmmoCube;
import model.*;

import java.util.ArrayList;

public abstract class WeaponCard implements Card {

    protected String cardName;
    protected AmmoCube[] reloadCost;      //first cube is the default one
    protected String description;
    protected int numOptionalEffect;      //number of optional effects
    protected boolean alternateFireMode;   //presence of alternate fire mode: true if present
    protected ArrayList<String> effect = new ArrayList<>();


    public String getCardName() {
        return this.cardName;
    }

    public AmmoCube[] getReloadCost() {
        return this.reloadCost;
    }

    public String getDescription() {
        return description;
    }

    public int getNumOptionalEffect() {
        return numOptionalEffect;
    }

    public boolean hasAlternateFireMode() {
        return alternateFireMode;
    }

    public abstract void applySpecialEffect(Grid grid, Player p1);

}
