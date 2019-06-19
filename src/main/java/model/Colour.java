package model;

import java.io.Serializable;

public enum Colour implements Serializable {

    PURPLE("PURPLE"),
    YELLOW("YELLOW"),
    RED("RED"),
    WHITE("WHITE"),
    BLUE("BLUE"),
    GREEN("GREEN"),
    BLACK("BLACK");

    private String colourId;

    Colour(String colourId) {
        this.colourId = colourId;
    }

    public String getColourId() {
        return colourId;
    }
}