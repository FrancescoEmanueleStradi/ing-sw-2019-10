package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 red and 2 blue ammo cubes.
 */
public class RBB extends AmmoCard {

    /**
     * Creates a new RBB card.
     */
    public RBB() {
        super();
        this.ammoCardName = "RBB";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = false;
    }
}