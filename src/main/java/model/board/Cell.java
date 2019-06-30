package model.board;

import model.*;
import model.cards.AmmoCard;

/**
 * Represents a cell in the game arena.
 */
public class Cell {

    private Position pos;

    /**
     * 1 spawn point, 0 any cell other than a spawn point, -1 cell does not exist
     */
    private int status;

    private Colour c;

    /**
     * 1 up, 2 right, 3 down, 4 left
     */
    private int[] posWall;

    /**
     * 1 up, 2 right, 3 down, 4 left
     */
    private int[] posDoor;

    private AmmoCard a;

    /**
     * Creates a new cell without regard for the walls and doors surrounding it.
     *
     * @param r   int (0, 1, -1)
     * @param pos position
     */
    public Cell(int r, Position pos) {
        this.status = r;
        this.pos = pos;
    }

    /**
     * Creates a new cell referencing room colour and the position of surrounding walls and doors.
     *
     * @param r   int (0, 1, -1)
     * @param c1  room colour
     * @param pm  wall positions
     * @param pp  door positions
     * @param pos position
     */
    public Cell(int r, Colour c1, int[] pm,  int[] pp, Position pos) {
        this.pos = pos;
        this.status = r;
        this.c = c1;
        this.posWall = new int[pm.length];
        System.arraycopy(pm, 0, this.posWall,0, pm.length);
        this.posDoor = new int[pp.length];
        System.arraycopy(pp, 0, this.posDoor,0, pp.length);
    }

    /**
     * Gets cell status.
     *
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets cell colour corresponding to the colour of the room the cell is in.
     *
     * @return colour
     */
    public Colour getC() {
        return c;
    }

    /**
     * Gets cell position.
     *
     * @return position
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Get wall positions.
     *
     * @return wall positions
     */
    public int[] getPosWall() {
        return posWall;
    }

    /**
     * Gets door positions.
     *
     * @return door positions
     */
    public int[] getPosDoor() {
        return posDoor;
    }

    /**
     * Gets ammo card on cell.
     *
     * @return ammo card
     */
    public AmmoCard getA() {
        return a;
    }

    /**
     * Sets ammo card on cell.
     *
     * @param a ammo card
     */
    public void setA(AmmoCard a) {
        if(this.status == 0)
            this.a = a;
    }
}