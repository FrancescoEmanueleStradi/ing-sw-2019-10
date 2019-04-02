package Model.Board;

import Model.WeaponCard;

public class WeaponSlot {
    private int n;     //there are 3 WS, it refers to the WS position (1 high, 2 right, 3 left)
    private WeaponCard card1;
    private WeaponCard card2;
    private WeaponCard card3;

    public WeaponSlot(int n, WeaponCard w1, WeaponCard w2,WeaponCard w3) {
        this.card1 = w1;
        this.card2 = w2;
        this.card3 = w3;
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public WeaponCard getCard1() {
        return card1;
    }

    public void setCard1(WeaponCard card1) {
        this.card1 = card1;
    }

    public WeaponCard getCard2() {
        return card2;
    }

    public void setCard2(WeaponCard card2) {
        this.card2 = card2;
    }

    public WeaponCard getCard3() {
        return card3;
    }

    public void setCard3(WeaponCard card3) {
        this.card3 = card3;
    }
}

