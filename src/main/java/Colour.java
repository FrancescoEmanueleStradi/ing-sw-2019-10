public enum Colour {

    VIOLA("VI"), GIALLO("G"), ROSSO("R"), BIANCO("B"), AZZURRO("A"), VERDE("VE"), NERO("N");
    private String abbreviation;

    private Colour(String abbreviation){     //chiedere al prof riguardo private
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
