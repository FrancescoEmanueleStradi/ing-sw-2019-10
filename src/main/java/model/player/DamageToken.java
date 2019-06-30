package model.player;

import model.Colour;

/**
 * Representation of damage tokens from the game characterized solely by their colour, corresponding to the player's
 * colour.
 */
public class DamageToken {

    private Colour c;

    /**
     * Creates a new damage token of one of the 5 player colours.
     *
     * @param c colour
     */
    public DamageToken(Colour c) {
        this.c = c;
    }

    /**
     * Gets damage token colour.
     *
     * @return colour
     */
    public Colour getC() {
        return c;
    }
}