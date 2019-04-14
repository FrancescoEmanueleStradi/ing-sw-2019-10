package model.player.damagetrack;

import model.Colour;

import java.util.LinkedList;
import java.util.List;

public class NumColour {
    private Colour c;
    private int num;
    private boolean  tie;
    private List<Colour> cTie;

    public NumColour(Colour c){
        this.c = c;
        this.num = 0;
        this.tie = false;
        this.cTie = new LinkedList<>();
    }

    public Colour getC() {
        return c;
    }

    public int getNum() {
        return num;
    }

    public boolean isTie() {
        return tie;
    }

    public List<Colour> getcTie() {
        return cTie;
    }

    public void setC(Colour c) {
        this.c = c;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void addNum(){
        this.num++;
    }

    public void setTie(boolean tie) {
        this.tie = tie;
    }

    public void setcTie(List<Colour> cTie) {
        this.cTie = cTie;
    }

    public int colourDifference(NumColour n){
        if(this.getNum() >= n.getNum())
            return this.getNum();
        else return n.getNum();
    }
}
