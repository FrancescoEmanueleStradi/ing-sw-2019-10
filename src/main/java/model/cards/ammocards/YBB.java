package model.cards.ammocards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class YBB extends AmmoCard{

    public YBB() throws InvalidColourException {
        super();
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = false;
    }
}