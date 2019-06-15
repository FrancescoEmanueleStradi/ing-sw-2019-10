package model.player;

import model.Colour;

public class DamageToken {

    private Colour c;

    public DamageToken(Colour c) {
        this.c = c;
    }

    public Colour getC() {
        return c;
    }
}