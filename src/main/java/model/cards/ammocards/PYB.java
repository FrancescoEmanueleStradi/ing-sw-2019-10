package model.cards.ammocards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class PYB extends AmmoCard {     //PYB stands for "Powerup Yellow Blue", referring to the items on the card
                                        //all other cards will have the same naming scheme
    public PYB() throws InvalidColourException {
        super();
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.YELLOW));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = true;
    }
}
