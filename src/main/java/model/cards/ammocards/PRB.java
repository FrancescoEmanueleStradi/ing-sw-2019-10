package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

/**
 * Ammo card containing 1 powerup card, 1 red ammo cube and 1 blue ammo cube.
 */
public class PRB extends AmmoCard {

    /**
     * Creates a new PRB card.
     */
    public PRB() {
        super();
        this.ammoCardName = "PRB";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = true;
    }
}