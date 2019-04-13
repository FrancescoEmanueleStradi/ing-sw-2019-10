package model.player;

import model.Colour;
import model.InvalidColourException;

public class AmmoCube {

    private Colour c;

    public AmmoCube(Colour c) throws InvalidColourException {
        if(c.equals(Colour.BLUE)||c.equals(Colour.YELLOW)||c.equals(Colour.RED))
            this.c = c;
        else throw new InvalidColourException();

    }

    public Colour getC() {
        return c;
    }

}
