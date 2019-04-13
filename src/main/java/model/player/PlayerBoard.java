package model.player;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {

    private Actions actions;
    private DamageTrack damages;
    private PointsPlayerBoard points;
    private ArrayList<DamageToken> marks;

    public PlayerBoard() {
        this.actions = new Actions();
        this.damages = new DamageTrack();
        this.points = new PointsPlayerBoard();
        this.marks = new ArrayList<>();
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

    public boolean mIsEmpty(){
        return this.marks.isEmpty();
    }

    public List<DamageToken> getMarks() {
        return marks;
    }

    public void addMark(DamageToken d){
        int i = 0;
        for(DamageToken dT : this.marks) {
            if (d.getC().equals(dT.getC()))
                i++;
            if(i == 3)
                return;
        }
        this.marks.add(d);
    }

    public void clearMark(Colour c){
        for(int i = this.marks.size() - 1; i >= 0 ; i--){
            if(this.marks.get(i).getC() == c)
                this.marks.remove(i);
        }
    }
}