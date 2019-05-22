package model.board;

import model.*;
import model.cards.AmmoCard;

public class Cell {
    private Position pos;
    private int status;   //1 is a spawn point, 0 it is not, -1 cell does not exist
    private Colour c;
    private int[] posWall;
    private int[] posDoor;              //1 up, 2 right, 3 down, 4 left
    private AmmoCard a;

    public Cell(int r, Position pos) {   //r = -1
        this.status = r;
        this.pos = pos;
    }

    public Cell(int r, Colour c1, int[] pm,  int[] pp, Position pos) {
        this.pos = pos;
        this.status = r;
        this.c = c1;
        this.posWall = new int[pm.length];
        System.arraycopy(pm, 0, this.posWall,0, pm.length);
        this.posDoor = new int[pp.length];
        System.arraycopy(pp, 0, this.posDoor,0, pp.length);
    }

    public int getStatus() {
        return status;
    }

    public Colour getC() {
        return c;
    }

    public Position getPos() {
        return pos;
    }

    public int[] getPosWall() {
        return posWall;
    }

    public int[] getPosDoor() {
        return posDoor;
    }

    public AmmoCard getA() {
        return a;
    }

    public void setA(AmmoCard a) {
        if(this.status ==0)
            this.a = a;
    }
}
