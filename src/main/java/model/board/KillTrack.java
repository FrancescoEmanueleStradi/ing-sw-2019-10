package model.board;

import model.Colour;
import model.player.damagetrack.NumColour;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the sole killshot track on the game board from which skulls are removed and to which damage tokens are
 * added as players die.
 * Only the standard rules whereby the game goes into Final Frenzy on the 8th and final skull are considered.
 */
public class KillTrack {

    /**
     * 0 skull, 1 damage, 2 double damage, 3 empty
     */
    private int[] skulls;

    private Colour[] c;
    private List<NumColour> l;

    /**
     * Creates a new killtrack filled with skulls.
     * It also creates an empty array of Colour: every time a player is killed, the colour of the killer is added
     * in the array, at the same position as the removed skull.
     */
    KillTrack() {
        skulls = new int[]{0,0,0,0,0,0,0,0};
        c = new Colour[8];
    }

    /**
     * Gets colours of damage token(s) on killtrack, representing the colour of the killers.
     *
     * @return colours
     */
    public Colour[] getC() {
        return c;
    }

    /**
     * Gets skulls array.
     *
     * @return skulls
     */
    public int[] getSkulls() {
        return skulls;
    }

    /**
     * Sets colours.
     *
     * @param c the c
     */
    public void setC(Colour[] c) {
        this.c = c;
    }

    /**
     * Sets skulls on killtrack.
     *
     * @param skulls skulls
     */
    public void setSkulls(int[] skulls) {
        this.skulls = skulls;
    }

    /**
     * Returns list containing the distinct colour of damage tokens on the killtrack.
     *
     * @return colour list
     */
    private List<Colour> colours() {
        LinkedList<Colour> lC = new LinkedList<>();
        for(Colour colour : this.getC()) {
            if(colour != null && !lC.contains(colour))
                lC.add(colour);
        }
        return lC;
    }

    /**
     * Creates a NumColour list and adds a new instance of NumColour for every member of colours().
     */
    private void initializeListNumColour() {
        this.l = new LinkedList<>();
        for(Colour colour : this.colours()) {
            if(colour != null) {
                NumColour num = new NumColour(colour);
                this.l.add(num);
            }
        }
    }

    /**
     * Calls initializeListNumColour() and adds occurrences of each colour in the killtrack to the list.
     * This is the final step before calling tie().
     */
    private void listNumColour() {
        this.initializeListNumColour();
        for(int i = 0; i < this.getC().length; i++) {
            if(this.getC()[i] != null)
                giveNumColour(this.getC()[i]).addNum();
            if(this.getSkulls()[i] == 2)
                giveNumColour(this.getC()[i]).addNum();
        }
        this.tie();
    }

    /**
     * Player's scoreboard, calculated at the end of the game and based on the killtrack.
     *
     * @return the scoreboard list
     */
    public List<Colour> scoreBoard() {
        this.listNumColour();
        LinkedList<Colour> colour = new LinkedList<>();
        for(NumColour n : getOrderedNumColour())
            colour.add(n.getC());
        return colour;
    }

    /**
     * Gets colour position in scoreboard.
     *
     * @param n index
     * @return position
     */
    public Colour getColourPosition(int n) {
        return scoreBoard().get(n);
    }

    /**
     * Sorts NumColour list in ascending order based on the difference between colour occurrences.
     *
     * @return NumColour list
     */
    private List<NumColour> getOrderedNumColour() {
        l.sort((numColour1, numColour2) -> numColour2.getNum() - numColour1.getNum());
        return l;
    }

    /**
     * Returns a specific NumColour member from the list.
     *
     * @param c colour
     * @return NumColour, nullColour (default)
     */
    private NumColour giveNumColour(Colour c) {
        NumColour nullColour = new NumColour(null);
        for(NumColour n : this.l) {
            if(n.getC().equals(c))
                return n;
        }
        return nullColour;
    }

    /**
     * Searches numColour list for a tie based on number of colours and setTie is naturally set to true if a tie
     * is found.
     */
    private void tie() {
        for(int i = 0; i < this.l.size()-1; i++) {
            for(int j = i+1; j < this.l.size() ; j++) {
                if(this.l.get(i).getNum() == this.l.get(j).getNum()) {
                    this.l.get(i).setTie(true);
                    if(!(this.l.get(i).getcTie().contains(this.l.get(j).getC())))
                        this.l.get(i).getcTie().add(this.l.get(j).getC());
                    this.l.get(j).setTie(true);
                    if(!(this.l.get(j).getcTie().contains(this.l.get(i).getC())))
                        this.l.get(j).getcTie().add(this.l.get(i).getC());
                }
            }
        }
    }

    /**
     * Cleans NumColour list.
     */
    public void cleanL() {
        this.l.clear();
    }
}