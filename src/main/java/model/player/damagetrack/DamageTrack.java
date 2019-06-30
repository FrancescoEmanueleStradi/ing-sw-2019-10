package model.player.damagetrack;

import model.Colour;
import model.player.DamageToken;

import java.util.LinkedList;
import java.util.List;

/**
 * The damage track as described by the game manual, with associated methods.
 */
public class DamageTrack {

    /**
     * 0-1 no adrenaline action, 2-4 first action unlocked, 5-9 second action unlocked, 10 death, 11 overkill
     */
    private DamageToken[] damageTokens;

    private List<NumColour> l;

    /**
     * Creates a new damage track as an array of damage tokens.
     */
    public DamageTrack() {
        this.damageTokens = new DamageToken[12];
    }

    /**
     * Gets the damage token array.
     *
     * @return damage token array
     */
    public DamageToken[] getDamageTokens() {
        return damageTokens;
    }

    /**
     * Adds a number of damage tokens of some colour to the damage track.
     *
     * @param numDamage amount of damage
     * @param c         token colour
     */
    public void addDamage(int numDamage, Colour c) {
        for(int i = 0; i < damageTokens.length; i++) {
            if(damageTokens[i] == null && numDamage != 0) {
                damageTokens[i] = new DamageToken(c);
                numDamage--;
               }
            else if(numDamage == 0)
                break;
           }
    }

    /**
     * Gets the damage token from the damage track in the specified index.
     *
     * @param index index of damage token
     * @return damage token
     */
    public DamageToken getDT(int index) {
        return this.damageTokens[index];
    }

    /**
     * Clears the damage track of all damage tokens.
     */
    public void clean() {
        for(int i=0; i<12; i++) {
            damageTokens[i] = null;
        }
    }

    /**
     * Returns list containing the distinct colour of damage tokens on the damage track.
     *
     * @return colour list
     */
    private List<Colour> colours() {
        LinkedList<Colour> lC = new LinkedList<>();
        for(DamageToken d : this.damageTokens) {
            if(d != null && !lC.contains(d.getC()))
                lC.add(d.getC());
        }
        return lC;
    }

    /**
     * Creates a NumColour list and adds a new instance of NumColour for every member of colours().
     */
    private void initializeListNumColour() {
        this.l = new LinkedList<>();
        for(Colour c : this.colours()) {
            NumColour num = new NumColour(c);
            this.l.add(num);
        }
    }

    /**
     * Calls initializeListNumColour() and adds occurrences of each damage token in the damage track to the list.
     * This is the final step before calling tie().
     */
    private void listNumColour() {
        this.initializeListNumColour();
        for(DamageToken d : this.damageTokens) {
            if(d != null)
                giveNumColour(d.getC()).addNum();
        }
        this.tie();
    }

    //TODO
    /**
     * Player's scoreboard.
     *
     * @return the list
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

    //TODO
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
     * Clears NumColour list.
     */
    public void cleanL() {
        this.l.clear();
    }
}