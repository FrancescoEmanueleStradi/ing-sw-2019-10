package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class PYR extends AmmoCard{

    public PYR() throws InvalidColourException {
        super();
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.RED));

        this.pC = true;
    }
}
