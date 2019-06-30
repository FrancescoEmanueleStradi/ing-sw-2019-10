package model.cards;

import model.player.AmmoCube;

import java.util.ArrayList;
import java.util.List;

/**
 * All ammo cards in the game extend this class.
 */
public abstract class AmmoCard {

    protected String ammoCardName;
    protected ArrayList<AmmoCube> aC;

    /**
     * If true, the AmmoCard has a PowerUpCard
     */
    protected boolean pC;

    /**
     * Gets ammo card name.
     *
     * @return the ammo card name
     */
    public String getAmmoCardName() {
        return ammoCardName;
    }

    /**
     * Gets the list of ammo cubes represented within the ammo card.
     *
     * @return ammo cube list
     */
    public List<AmmoCube> getaC() {
        return aC;
    }

    /**
     *
     *
     * @return the boolean
     */
    public boolean haspC() {
        return pC;
    }
}