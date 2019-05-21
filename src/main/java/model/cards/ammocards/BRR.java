package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class BRR extends AmmoCard {

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
