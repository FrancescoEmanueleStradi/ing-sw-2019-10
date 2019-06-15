package model;

import java.io.Serializable;

public enum Colour implements Serializable {

    PURPLE("PURPLE"), YELLOW("YELLOW"), RED("RED"), WHITE("WHITE"),
    BLUE("BLUE"), GREEN("GREEN"), BLACK("BLACK");

    private String abbreviation;

    Colour(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}