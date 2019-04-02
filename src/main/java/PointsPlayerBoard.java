import java.util.ArrayList;
public class PointsPlayerBoard {

    private ArrayList<Integer> points = new ArrayList<Integer>();

    public PointsPlayerBoard(){
        points.add(1);
        points.add(8);
        points.add(6);
        points.add(4);
        points.add(2);
        points.add(1);
        points.add(1);
    }

    public int getInt(int index){
        return this.points.get(index);
    }


    public void remove(){
        this.points.remove(1);
    }


}
