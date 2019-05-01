package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class RBB extends AmmoCard {

    public RBB() {
        super();
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.RED));
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = false;
    }
}