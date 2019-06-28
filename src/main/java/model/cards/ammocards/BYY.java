package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 blue and 2 yellow ammo cubes.
 */
public class BYY extends AmmoCard {

    /**
     * Creates a new BYY card.
     */
    public BYY() {
        super();
        this.ammoCardName = "BYY";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.YELLOW));

        this.pC = false;
    }
}