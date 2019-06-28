package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 powerup card and 2 red ammo cubes.
 */
public class PRR extends AmmoCard {

    /**
     * Creates a new PRR card.
     */
    public PRR() {
        super();
        this.ammoCardName = "PRR";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.RED));

        this.pC = true;
    }
}