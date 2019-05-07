package model.player;

import model.Colour;

import java.io.Serializable;

public class AmmoCube {

    private Colour c;

    public AmmoCube(Colour c) {
        this.c = c;
    }

    public Colour getC() {
        return c;
    }

}
