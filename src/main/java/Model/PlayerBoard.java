package Model;

public class PlayerBoard {

    private Actions actions;
    private DamageTrack damages;
    private PointsPlayerBoard points;

    public PlayerBoard() {
        this.actions = new Actions();
        this.damages = new DamageTrack();
        this.points = new PointsPlayerBoard();
    }

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public DamageTrack getDamages() {
        return damages;
    }

    public void setDamages(DamageTrack damages) {
        this.damages = damages;
    }

    public PointsPlayerBoard getPoints() {
        return points;
    }

    public void setPoints(PointsPlayerBoard points) {
        this.points = points;
    }

}
