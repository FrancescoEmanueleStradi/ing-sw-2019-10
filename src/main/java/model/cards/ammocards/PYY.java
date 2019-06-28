package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 powerup card and 2 yellow ammo cubes.
 */
public class PYY extends AmmoCard {

    /**
     * Creates a new PYY card.
     */
    public PYY() {
        super();
        this.ammoCardName = "PYY";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.YELLOW));

        this.pC = true;
    }
}