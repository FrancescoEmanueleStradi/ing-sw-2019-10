package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class PBB extends AmmoCard {

    public PBB() {
        super();
        this.ammoCardName = "PBB";
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = true;
    }
}