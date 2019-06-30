package model.player;

import model.*;
import model.player.damagetrack.DamageTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * The player's personal board containing their damage track, received marks and point value indicator.
 */
public class PlayerBoard {

    private DamageTrack damage;
    private PointsPlayerBoard points;
    private ArrayList<DamageToken> marks;

    /**
     * Creates a new player board.
     */
    PlayerBoard() {
        this.damage = new DamageTrack();
        this.points = new PointsPlayerBoard();
        this.marks = new ArrayList<>();
    }

    /**
     * Gets player's received damage.
     *
     * @return damage
     */
    public DamageTrack getDamage() {
        return damage;
    }

    /**
     * Sets damage track.
     *
     * @param damage damage track.
     */
    public void setDamage(DamageTrack damage) {
        this.damage = damage;
    }

    /**
     * Gets player's point values.
     *
     * @return point values
     */
    public PointsPlayerBoard getPoints() {
        return points;
    }

    /**
     * Sets player's point values.
     *
     * @param points the points
     */
    void setPoints(PointsPlayerBoard points) {
        this.points = points;
    }

    /**
     * Determines whether or not player has no marks.
     *
     * @return boolean
     */
    boolean mIsEmpty() {
        return this.marks.isEmpty();
    }

    /**
     * Gets player's received marks.
     * Note that marks are represented as an enemy's damage tokens.
     *
     * @return marks
     */
    public List<DamageToken> getMarks() {
        return marks;
    }

    /**
     * Adds single mark to player board. The maximum amount of marks an enemy player may keep on a player is 3, making
     * it necessary to check occurrences of the colour of a damage token.
     *
     * @param d damage token
     */
    public void addMark(DamageToken d) {
        int i = 0;
        for(DamageToken dT : this.marks) {
            if(d.getC().equals(dT.getC()))
                i++;
            if(i == 3)
                return;
        }
        this.marks.add(d);
    }

    /**
     * Clears player's received marks of some colour.
     *
     * @param c colour
     */
    public void clearMark(Colour c) {
        for(int i = this.marks.size() - 1; i >= 0 ; i--) {
            if(this.marks.get(i).getC() == c)
                this.marks.remove(i);
        }
    }
}