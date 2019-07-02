package controller;

/**
 * Game states are necessary to separate illegal actions from legal ones throughout the course of the game. Note
 * that the state is set when the corresponding event has just ended.
 */
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

    /**
     * Sets game state's abbreviation.
     * @param abbreviation abbreviation
     */
    GameState(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Gets abbreviation.
     *
     * @return abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }
}