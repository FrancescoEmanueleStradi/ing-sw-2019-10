package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 powerup card and 2 blue ammo cubes.
 */
public class PBB extends AmmoCard {

    /**
     * Creates a new PBB card.
     */
    public PBB() {
        super();
        this.ammoCardName = "PBB";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = true;
    }
}