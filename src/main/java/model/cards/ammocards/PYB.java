package model.cards.ammocards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.AmmoCard;

import java.util.ArrayList;
import java.util.Arrays;

public class PYB extends AmmoCard {  //PYB stands for "Powerup Yellow Blue", referring to the items on the card
                                     //all other cards will have the same naming scheme
    public PYB() throws InvalidColourException {
        super();
        this.aC = new ArrayList<AmmoCube>(Arrays.asList(new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)));
        //this.pC = new ArrayList<PowerUpCard>
    }
}