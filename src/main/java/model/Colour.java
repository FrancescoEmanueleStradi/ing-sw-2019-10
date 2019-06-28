package model;

import java.io.Serializable;

/**
 * Enumeration of the various colours pertaining to certain objects in Adrenaline.
 */
public enum Colour implements Serializable {

    PURPLE("PURPLE"),
    YELLOW("YELLOW"),
    RED("RED"),
    WHITE("WHITE"),
    BLUE("BLUE"),
    GREEN("GREEN"),
    BLACK("BLACK");

    private String colourId;

    /**
     * Sets name of colour.
     */
    Colour(String colourId) {
        this.colourId = colourId;
    }

    /**
     * Gets name of colour.
     *
     * @return colour name
     */
    public String getColourId() {
        return colourId;
    }
}