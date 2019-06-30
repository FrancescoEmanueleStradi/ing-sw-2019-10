package model.player;

import model.Colour;

/**
 * Representation of ammo cubes from the game characterized solely by their colour: red, blue or yellow.
 * It is assumed their is no limit to the number of ammo cubes that can be instantiated.
 */
public class AmmoCube {

    private Colour c;

    /**
     * Creates a new ammo cube of one of three colours.
     *
     * @param c colour
     */
    public AmmoCube(Colour c) {
        this.c = c;
    }

    /**
     * Gets colour of ammo cube.
     *
     * @return colour
     */
    public Colour getC() {
        return c;
    }
}