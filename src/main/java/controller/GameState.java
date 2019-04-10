package controller;

public enum GameState {

    START("S"), INITIALIZED("I"), ASSIGNSPAWNPOINT("ASP"), DRAWPOWERUPCARD("DPC"), STARTTURN("ST"), ACTION1("A1"), ACTION2("A2"), GRABAMMO("GA"),
    SHOOT("SH"), ENDTURN("ET"), RELOAD("R"), DEATH("D"), RESPAWN("RW");
    private String abbreviation;

    private GameState(String abbreviation){     //Ask professor about the private
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
