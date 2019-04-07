package model.cards.ammoCards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.AmmoCard;
import model.cards.PowerUpCard;

import java.util.ArrayList;
import java.util.Arrays;

public class RBB extends AmmoCard{

    public RBB() throws InvalidColourException {
        super();
        this.aC = new ArrayList<AmmoCube>(Arrays.asList(new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)));
        //this.pC = new ArrayList<PowerUpCard>
    }
}