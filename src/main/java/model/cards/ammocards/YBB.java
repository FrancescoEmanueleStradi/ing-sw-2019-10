package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 yellow and 2 blue ammo cubes.
 */
public class YBB extends AmmoCard {

    /**
     * Creates a new YBB card.
     */
    public YBB() {
        super();
        this.ammoCardName = "YBB";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = false;
    }
}