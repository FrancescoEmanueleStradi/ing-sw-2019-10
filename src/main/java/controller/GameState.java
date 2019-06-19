package controller;

public enum GameState {

    START("S"),
    INITIALIZED("I"),
    STARTTURN("ST"),
    ACTION1("A1"),
    ACTION2("A2"),
    ENDTURN("ET"),
    RELOADED("R"),
    ENDGAME("EG"),
    ENDALLTURN("EAT");

    private String abbreviation;

    GameState(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}