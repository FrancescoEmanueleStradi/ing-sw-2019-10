package model.cards.ammocards;

import model.player.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.AmmoCard;

import java.util.ArrayList;

public class PBB extends AmmoCard{

    public PBB() throws InvalidColourException {
        super();
        this.aC = new ArrayList<>();
        this.aC.add(new AmmoCube(Colour.BLUE));
        this.aC.add(new AmmoCube(Colour.BLUE));

        this.pC = true;
    }
}