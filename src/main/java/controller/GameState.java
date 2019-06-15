package controller;

public enum GameState {

    START("S"), INITIALIZED("I"),
    ASSIGNSPAWNPOINT("ASP"), DRAWPOWERUPCARD("DPC"),
    STARTTURN("ST"), ACTION1("A1"), ACTION2("A2"),
    GRABAMMO("GA"), SHOOT("SH"),
    ENDTURN("ET"), RELOADED("R"),
    DEATH("D"), RESPAWN("RW"),
    ENDGAME("EG"), ENDALLTURN("EAT");

    private String abbreviation;

    GameState(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}