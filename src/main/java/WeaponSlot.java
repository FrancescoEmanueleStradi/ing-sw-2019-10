public class WeaponSlot {
    private int n;     //ci sono 3 WS, indica il numero della SW a cui ci stiamo riferendo (1 alto, 2 destra, 3 sinistra)
    private Card card1;
    private Card card2;
    private Card card3;

    public WeaponSlot(int n) {
        card1 = new Card();
        card2 = new Card();
        card3 = new Card();
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public Card getCard3() {
        return card3;
    }

    public void setCard3(Card card3) {
        this.card3 = card3;
    }
}

