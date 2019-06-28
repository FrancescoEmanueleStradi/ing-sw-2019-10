package model;

/**
 * A position is merely one of the cell's properties expressed in x and y coordinates.
 * x designates the rows, and y the columns.
 */
public class Position {

    private int x;
    private int y;

    /**
     * Creates a new position
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x coordinate.
     *
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y coordinate
     *
     * @return y coordinate
     */
    public int getY() {
        return y;
    }
}