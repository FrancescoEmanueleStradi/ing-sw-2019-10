package model.cards;

import model.player.AmmoCube;

/**
 * All weapon cards in the game extend this class.
 */
public abstract class WeaponCard {

    protected String cardName;

    /**
     * first cube corresponds to the "free" reload cost
     */
    protected AmmoCube[] reloadCost;

    protected String description;
    private boolean reloaded = false;

    /**
     * Gets weapon card name.
     *
     * @return card name
     */
    public String getCardName() {
        return this.cardName;
    }

    /**
     * Get reload cost in ammo cubes.
     *
     * @return reload cost
     */
    public AmmoCube[] getReloadCost() {
        return this.reloadCost;
    }

    /**
     * Gets weapon description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Determines whether or not the weapon is reloaded.
     *
     * @return boolean
     */
    public boolean isReloaded() {
        return reloaded;
    }

    /**
     * Sets reloaded to true.
     */
    public void reload() {
        this.reloaded = true;
    }

    /**
     * Sets reloaded to false.
     */
    public void unload() {
        this.reloaded = false;
    }
}