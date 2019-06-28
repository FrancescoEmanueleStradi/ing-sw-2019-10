package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 red and 2 yellow ammo cubes.
 */
public class RYY extends AmmoCard {

    /**
     * Creates a new RYY card.
     */
    public RYY() {
        super();
        this.ammoCardName = "RYY";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.YELLOW));

        this.pC = false;
    }
}