package controller;

public enum GameState {

    START("S"), ASSIGNSPAWNPOINT("ASP"), DRAWPOWERUPCARD("DPC"), STARTTURN("ST"), MOVE("M"), GRABWEAPON("GW"), GRABAMMO("GA"),
    SHOOT("SH"), ENDTURN("ET"), RELOAD("R"), DEATH("D"), RESPAWN("RW");
    private String abbreviation;

    private GameState(String abbreviation){     //Ask professor about the private
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
