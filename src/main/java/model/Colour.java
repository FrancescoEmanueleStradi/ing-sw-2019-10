package model;

import java.io.Serializable;

public enum Colour implements Serializable {

    PURPLE("PURPLE"), YELLOW("YELLOW"), RED("RED"), WHITE("WHITE"), BLUE("BLUE"), GREEN("GREEN"), BLACK("BLACK");
    private String abbreviation;

    private Colour(String abbreviation){     //Ask professor about the private
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
