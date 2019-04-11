package model.cards.ammocards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class PYY extends AmmoCard{

    public PYY() throws InvalidColourException {
        super();
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.YELLOW));

        this.pC = true;
    }
}