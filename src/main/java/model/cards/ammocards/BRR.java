package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 blue and 2 red ammo cubes.
 */
public class BRR extends AmmoCard {

    /**
     * Creates a new BRR card.
     */
    public BRR() {
        super();
        this.ammoCardName = "BRR";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.RED));

        this.pC = false;
    }
}