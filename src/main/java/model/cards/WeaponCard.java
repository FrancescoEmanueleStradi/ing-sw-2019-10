package model.cards;

import model.player.AmmoCube;

import java.util.ArrayList;

public abstract class WeaponCard implements Card {

    protected String cardName;
    protected AmmoCube[] reloadCost;      //first cube is the default one
    protected String description;
    protected int numOptionalEffect;      //number of optional effects
    protected boolean alternateFireMode;   //presence of alternate fire mode: true if present --> if true, it's unnecessary to check how many optional effect are present
    protected ArrayList<String> effect = new ArrayList<>();
    boolean reloaded;   //reload the card when player buys it (and when he reloads at the end of the turn, of course)


    public String getCardName() {
        return this.cardName;
    }

    public AmmoCube[] getReloadCost() {
        return this.reloadCost;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReloaded() {
        return reloaded;
    }

    public void reload(){
        this.reloaded = true;
    }

    public void unload(){
        this.reloaded = false;
    }

    public int getNumOptionalEffect() {
        return numOptionalEffect;
    }

    public boolean hasAlternateFireMode() {
        return alternateFireMode;
    }

    //public abstract void applySpecialEffect(Grid grid, Player p1); (useful? almost every card has a different signature)

}
