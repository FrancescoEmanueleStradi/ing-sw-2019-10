package model.cards.ammocards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.AmmoCard;

import java.util.ArrayList;
import java.util.Arrays;

public class YRR extends AmmoCard{

    public YRR() throws InvalidColourException {
        super();
        this.aC = new ArrayList<AmmoCube>(Arrays.asList(new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)));
        //this.pC = new ArrayList<PowerUpCard>
    }
}