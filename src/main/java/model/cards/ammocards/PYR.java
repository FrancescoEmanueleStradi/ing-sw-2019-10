package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 powerup card, 1 yellow ammo cube and 1 red ammo cube.
 */
public class PYR extends AmmoCard {

    /**
     * Creates a new PYR card.
     */
    public PYR() {
        super();
        this.ammoCardName = "PYR";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.RED));

        this.pC = true;
    }
}