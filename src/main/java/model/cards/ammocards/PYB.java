package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 powerup card, 1 yellow ammo cube and 1 blue ammo cube.
 */
public class PYB extends AmmoCard {

    /**
     * Creates a new PYB card.
     */
    public PYB() {
        super();
        this.ammoCardName = "PYB";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = true;
    }
}