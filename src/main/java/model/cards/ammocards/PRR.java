package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class PRR extends AmmoCard {

    public PRR() {
        super();
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.RED));

        this.pC = true;
    }
}