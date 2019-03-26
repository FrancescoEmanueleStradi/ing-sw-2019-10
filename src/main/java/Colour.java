public enum Colour {

    PURPLE("P"), YELLOW("Y"), RED("R"), WHITE("W"), BLUE("B"), GREEN("G"), BLACK("BK");
    private String abbreviation;

    private Colour(String abbreviation){     //Ask professor about the private
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
