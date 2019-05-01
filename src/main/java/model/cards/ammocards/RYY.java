package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class RYY extends AmmoCard {

    public RYY() {
        super();
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.YELLOW));

        this.pC = false;
    }
}