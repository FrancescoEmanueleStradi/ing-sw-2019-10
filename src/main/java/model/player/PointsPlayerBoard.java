package model.player;

import java.util.ArrayList;
import java.util.List;

public class PointsPlayerBoard {

    private ArrayList<Integer> points = new ArrayList<>();

    public PointsPlayerBoard(){
        points.add(1);
        points.add(8);
        points.add(6);
        points.add(4);
        points.add(2);
        points.add(1);
        points.add(1);
    }

    public List<Integer> getPoints() {
        return points;
    }

    public int getInt(int index){
        return this.points.get(index);
    }


    public void remove(){
        this.points.remove(1);
    }


}
