package model.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the player's point value indicator on their player board.
 */
public class PointsPlayerBoard {

    private ArrayList<Integer> points = new ArrayList<>();

    /**
     * Creates a new PointsPlayerBoard.
     */
    PointsPlayerBoard() {
        points.add(1);
        points.add(8);
        points.add(6);
        points.add(4);
        points.add(2);
        points.add(1);
        points.add(1);
    }

    /**
     * Gets point values.
     *
     * @return point values
     */
    public List<Integer> getPoints() {
        return points;
    }

    /**
     * Gets index of point values.
     *
     * @param index index
     * @return point value
     */
    public int getInt(int index) {
        return this.points.get(index);
    }

    /**
     * Removes second member of points array.
     * Note that 1 refers to the first blood extra point, which is always valid no matter how many deaths the player
     * has suffered.
     */
    public void remove() {
        this.points.remove(1);
    }
}