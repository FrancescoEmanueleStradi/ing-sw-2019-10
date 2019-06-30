package model.board;

import model.cards.WeaponCard;

/**
 * Represents a weapon slot from the game board containing three weapon cards.
 * */
public class WeaponSlot {

    /**
     * Number referring to the weapon slots' respective positions (1 high, 2 right, 3 left)
     */
    private int n;

    private WeaponCard card1;
    private WeaponCard card2;
    private WeaponCard card3;

    /**
     * Creates a new weapon slot.
     *
     * @param n  num weapon slot
     * @param w1 weapon card 1
     * @param w2 weapon card 2
     * @param w3 weapon card 3
     */
    public WeaponSlot(int n, WeaponCard w1, WeaponCard w2, WeaponCard w3) {
        this.card1 = w1;
        this.card2 = w2;
        this.card3 = w3;
        this.n = n;
    }

    /**
     * Gets weapon slot number.
     *
     * @return num weapon slot
     */
    int getWeaponSlot() {
        return n;
    }

    /**
     * Gets weapon card 1.
     *
     * @return card 1
     */
    public WeaponCard getCard1() {
        return card1;
    }

    /**
     * Sets weapon card 1.
     *
     * @param card1 card 1
     */
    public void setCard1(WeaponCard card1) {
        this.card1 = card1;
    }

    /**
     * Gets weapon card 2.
     *
     * @return card 2
     */
    public WeaponCard getCard2() {
        return card2;
    }

    /**
     * Sets weapon card 2.
     *
     * @param card2 card 2
     */
    public void setCard2(WeaponCard card2) {
        this.card2 = card2;
    }

    /**
     * Gets weapon card 3.
     *
     * @return card 3
     */
    public WeaponCard getCard3() {
        return card3;
    }

    /**
     * Sets weapon card 3.
     *
     * @param card3 card 3
     */
    public void setCard3(WeaponCard card3) {
        this.card3 = card3;
    }
}