package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 yellow and 2 red ammo cubes.
 */
public class YRR extends AmmoCard {

    /**
     * Creates a new YRR card.
     */
    public YRR() {
        super();
        this.ammoCardName = "YRR";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.RED));

        this.pC = false;
    }
}