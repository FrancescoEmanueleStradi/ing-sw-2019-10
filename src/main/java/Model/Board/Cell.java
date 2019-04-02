package Model.Board;

import Model.Colour;
import Model.Position;

public class Cell {
    private int status;   //1 is a spawn point, 0 it is not, -1 cell does not exist
    private Colour c;
    private Position p;
    private int[] posWall;
    private int[] posDoor;              //1 above, 2 right, 3 below, 4 left
    private AmmoCard a;


    public Cell(int r){                     //r = -1
        this.status = r;

    }

    public Cell(int r, Colour c1, /*Model.Position p1,*/  int[] pm,  int[] pp) {

        this.status = r;
        if(this.status ==0)
            this.a = new AmmoCard();
        this.c = c1;
        //this.p = p1;
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

    public Position getP() {
        return p;
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
        this.a = a;
    }
}
