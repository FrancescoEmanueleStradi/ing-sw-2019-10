package model.cards;

import model.player.AmmoCube;

public abstract class WeaponCard implements Card {

    protected String cardName;
    protected AmmoCube[] reloadCost;      //first cube is the default one
    protected String description;
    private boolean reloaded = false;     //reload the card when player buys it and when he reloads at the end of the turn

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

    public void reload() {
        this.reloaded = true;
    }

    public void unload() {
        this.reloaded = false;
    }
}