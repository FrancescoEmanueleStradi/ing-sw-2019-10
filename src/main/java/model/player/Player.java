package model.player;

import model.*;
import model.board.*;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;

import java.util.*;

/**
 * The player has an identifying name and colour (and at any point may have a number of ammo cubes, weapon cards
 * and powerup cards on their playboard. They also have a number of "statuses" (dead, in adrenaline, and so forth),
 * among other attributes.
 */
public class Player {

    private String nickName;

    /**
     * BLACK, BLUE, GREEN, PURPLE, YELLOW are the colours of the available playable characters (whose figurines are
     * not represented)
     */
    private Colour c;

    private PlayerBoard playerBoard;

    /**
     * True means first player
     */
    private boolean firstPlayerCard;

    private int score;

    /**
     * Max 3 ammo cubes of each colour: index 0-2 = red, 3-5 = blue, 6-8 = yellow. These positions never change
     */
    private AmmoCube[] ammoCubes;

    private LinkedList<WeaponCard> weaponCards;
    private LinkedList<PowerUpCard> powerUpCards;
    private Cell cell;

    /**
     * 0 if your Final Frenzy turn is before the first player, 1 if you are the first player, 2 if you play after
     */
    private int turnFinalFrenzy;

    /**
     * Creates a new player.
     *
     * @param name nickname
     * @param c player colour
     * @param f    the f
     */
    public Player(String name, Colour c, boolean f)  {

        this.nickName = name;
        this.c = c;
        this.playerBoard = new PlayerBoard();
        this.firstPlayerCard = f;
        this.score = 0;
        this.ammoCubes = new AmmoCube[]{new AmmoCube(Colour.RED), null, null, new AmmoCube(Colour.BLUE), null, null, new AmmoCube(Colour.YELLOW), null, null};
        weaponCards = new LinkedList<>();
        powerUpCards = new LinkedList<>();
    }

    /**
     * Gets nickname.
     *
     * @return nickname
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Gets player colour
     *
     * @return colour
     */
    public Colour getC() {
        return c;
    }

    /**
     * Gets player board.
     *
     * @return player board
     */
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Determines whether or not the player is the first player (in the game the first player keeps the "first
     * player card" throughout the game, not represented here).
     *
     * @return boolean
     */
    public boolean hasFirstPlayerCard() {
        return firstPlayerCard;
    }

    /**
     * Gets player's score.
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds points to player's total score.
     *
     * @param score additional points
     */
    public void addScore(int score) {
        this.score = this.score + score;
    }

    /**
     * Gets player's ammo cubes.
     *
     * @return ammo cubes
     */
    public AmmoCube[] getAmmoCubes() {
        return ammoCubes;
    }

    /**
     * Gets player's final frenzy turn.
     *
     * @return the turn final frenzy
     */
    public int getTurnFinalFrenzy() {
        return turnFinalFrenzy;
    }

    /**
     * Sets player's Final Frenzy turn
     *
     * @param turnFinalFrenzy Final Frenzy turn
     */
    public void setTurnFinalFrenzy(int turnFinalFrenzy) {
        this.turnFinalFrenzy = turnFinalFrenzy;
    }

    /**
     * Checks whether or not the player has the required ammo cubes for some action.
     *
     * @param a ammo cubes
     * @return boolean
     */
    public boolean checkAmmoCube(AmmoCube[] a) {
        List<AmmoCube> l2 = new ArrayList<>(Arrays.asList(a));

        if(l2.isEmpty())
            return true;

        List<Colour> lInput = new LinkedList<>();
        for(AmmoCube aCPlayer : this.ammoCubes) {
            if(aCPlayer != null)
                lInput.add(aCPlayer.getC());
        }

        List<Colour> lCost = new LinkedList<>();
        for(AmmoCube aCost : l2)
            lCost.add(aCost.getC());

        for(Colour colour : lCost) {
            if(Collections.frequency(lInput, colour) < Collections.frequency(lCost, colour))
                return false;
        }

        return lInput.containsAll(lCost);
    }

    /**
     * Adds new ammo cube to player's hand of some colour. Calls one of three specific methods for each colour.
     *
     * @param ac ammo cube
     */
    public void addNewAC(AmmoCube ac) {
        if(ac.getC() == Colour.RED)
            this.addNewACRED(ac);
        else if(ac.getC() == Colour.BLUE)
            this.addNewACBLUE(ac);
        else if(ac.getC() == Colour.YELLOW)
            this.addNewACYELLOW(ac);
    }

    /**
     * Adds red ammo cube.
     *
     * @param ac ammo cube
     */
    private void addNewACRED(AmmoCube ac) {
        for(int i = 0; i < 3; i++) {
            if(this.ammoCubes[i] == null) {
                this.ammoCubes[i] = ac;
                break;
            }
        }
    }

    /**
     * Adds blue ammo cube.
     *
     * @param ac ammo cube
     */
    private void addNewACBLUE(AmmoCube ac) {
        for(int i = 3; i < 6; i++) {
            if(this.ammoCubes[i] == null) {
                this.ammoCubes[i] = ac;
                break;
            }
        }
    }

    /**
     * Adds yellow ammo cube.
     *
     * @param ac ammo cube
     */
    private void addNewACYELLOW(AmmoCube ac) {
        for(int i = 6; i < 9; i++) {
            if(this.ammoCubes[i] == null) {
                this.ammoCubes[i] = ac;
                break;
            }
        }
    }

    /**
     * Removes some ammo cube from player's hand.
     *
     * @param a ammo cube
     */
    private void removeAC(AmmoCube a) {
        int i = 0;
        for(AmmoCube a1 : this.getAmmoCubes()) {
            if((a1 != null) && a1.getC().equals(a.getC())) {
                this.ammoCubes[i] = null;
                return;
            }
            i++;
        }
    }

    /**
     * Removes all ammo cubes from players hand
     *
     * @param a ammo cubes
     */
    public void removeArrayAC(AmmoCube[] a) {
        for(AmmoCube a1 : a)
            removeAC(a1);
    }

    /**
     * Player pays some ammo cost using either their ammo cubes or powerup cards.
     *
     * @param lA ammo cube list
     * @param lP powerup list
     */
    public void payCard(List<AmmoCube> lA, List<PowerUpCard> lP) {
        if(!lA.isEmpty()) {
            for(AmmoCube a : lA)
                this.removeAC(a);
        }
        if(!lP.isEmpty()) {
            for(PowerUpCard p : lP)
                this.powerUpCards.remove(p);
        }
    }

    /**
     * Gets player's weapon cards.
     *
     * @return weapon card list
     */
    public List<WeaponCard> getWeaponCards() {
        return weaponCards;
    }

    /**
     * Adds weapon card to player's hand.
     *
     * @param w weapon card
     */
    public void addWeaponCard(WeaponCard w) {
        this.weaponCards.add(w);
    }

    /**
     * Removes weapon card from player's hand.
     *
     * @param w weapon card
     */
    public void removeWeaponCard(WeaponCard w) {
        this.weaponCards.remove(w);
    }

    /**
     * Gets weapon card as an object from its name
     *
     * @param s weapon name
     * @return weapon card object
     */
    public WeaponCard getWeaponCardObject(String s) {
        for(WeaponCard w: this.weaponCards) {
            if(w.getCardName().equals(s))
                return w;
        }
        return null;
    }

    /**
     * Gets player's powerup cards.
     *
     * @return powerup list
     */
    public List<PowerUpCard> getPowerUpCards() {
        return powerUpCards;
    }

    /**
     * Adds powerup card to player's hand.
     *
     * @param p powerup card
     */
    public void addPowerUpCard(PowerUpCard p) {
        this.powerUpCards.add(p);
    }

    /**
     * Removes powerup card from player's hand.
     *
     * @param p powerup card
     */
    public void removePowerUpCard(PowerUpCard p) {
        this.powerUpCards.remove(p);
    }

    /**
     * Gets powerup card as an object from its name.
     *
     * @param s      powerup name
     * @param colour powerup colour
     * @return power up card object
     */
    public PowerUpCard getPowerUpCardObject(String s, Colour colour) {
        for(PowerUpCard p: this.powerUpCards) {
            if(p.getCardName().equals(s) && p.getC().equals(colour))
                return p;
        }
        return null;
    }

    /**
     * Changes player's current cell.
     *
     * @param c cell
     */
    public void changeCell(Cell c) {
        if(c != null && c.getStatus() != -1)
            this.cell = c;
        else if(c == null)
            this.cell = null;
    }

    /**
     * Gets cell player is in.
     *
     * @return cell
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * Determines whether or not player has unlocked the first adrenaline action.
     *
     * @return boolean
     */
    public boolean isAdrenaline1() {
        return (this.playerBoard.getDamage().getDamageTokens()[2]!=null);
    }

    /**
     * Determines whether or not player has unlocked the second adrenaline action.
     *
     * @return boolean
     */
    public boolean isAdrenaline2() {
        return (this.playerBoard.getDamage().getDamageTokens()[5]!=null);
    }

    /**
     * Determines whether or not player is dead.
     *
     * @return boolean
     */
    public boolean isDead() {
        return (this.playerBoard.getDamage().getDamageTokens()[10]!=null);
    }

    /**
     * Determines whether or not player is overkilled.
     *
     * @return boolean
     */
    public boolean isOverkilled() {
        return (this.playerBoard.getDamage().getDamageTokens()[11]!=null);
    }
}