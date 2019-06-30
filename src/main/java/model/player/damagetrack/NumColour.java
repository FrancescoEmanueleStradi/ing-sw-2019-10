package model.player.damagetrack;

import model.Colour;

import java.util.LinkedList;
import java.util.List;

/**
 * This class handles the occurrences of a given colour in the killtrack and damage track while monitoring ties
 * between colours.
 */
public class NumColour {

    private Colour c;
    private int num;
    private boolean  tie;
    private List<Colour> cTie;

    /**
     * Creates a new NumColour.
     *
     * @param c damage token colour
     */
    public NumColour(Colour c) {
        this.c = c;
        this.num = 0;
        this.tie = false;
        this.cTie = new LinkedList<>();
    }

    /**
     * Gets colour.
     *
     * @return colour
     */
    public Colour getC() {
        return c;
    }

    /**
     * Gets number of occurrences of colour.
     *
     * @return number
     */
    public int getNum() {
        return num;
    }

    /**
     * Determines whether or not the colours are in equal amount, resulting in a tie.
     *
     * @return boolean
     */
    boolean isTie() {
        return tie;
    }

    /**
     * Gets list of colours that result in a tie.
     *
     * @return colour tie list
     */
    public List<Colour> getcTie() {
        return cTie;
    }

    /**
     * Sets colour.
     *
     * @param c the c
     */
    public void setC(Colour c) {
        this.c = c;
    }

    /**
     * Sets number of occcurrences of colour.
     *
     * @param num number
     */
    void setNum(int num) {
        this.num = num;
    }

    /**
     * Increases number of occurrences of colour.
     */
    public void addNum() {
        this.num++;
    }

    /**
     * Sets tie.
     *
     * @param tie tie
     */
    public void setTie(boolean tie) {
        this.tie = tie;
    }

    /**
     * Sets list of colour ties.
     *
     * @param cTie colour tie list
     */
    void setcTie(List<Colour> cTie) {
        this.cTie = cTie;
    }

    /**
     * Calculates difference in occurrences of colours.
     *
     * @param n NumColour
     * @return difference
     */
    int colourDifference(NumColour n) {
        if(this.getNum() >= n.getNum())
            return this.getNum();
        else return n.getNum();
    }
}