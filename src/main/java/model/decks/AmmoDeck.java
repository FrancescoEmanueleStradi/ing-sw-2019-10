package model.decks;

import model.cards.AmmoCard;
import model.cards.ammocards.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The deck of ammo cards on the game board.
 */
public class AmmoDeck implements Deck {

    private ArrayList<AmmoCard> deck = new ArrayList<>();

    /**
     * Creates a new ammo deck, instantiating 2-4 of each ammo card.
     */
    public AmmoDeck () {
        BRR c1 = new BRR();
        deck.add(c1);
        BRR c2 = new BRR();
        deck.add(c2);
        BRR c3 = new BRR();
        deck.add(c3);

        BYY c4 = new BYY();
        deck.add(c4);
        BYY c5 = new BYY();
        deck.add(c5);
        BYY c6 = new BYY();
        deck.add(c6);

        PBB c7 = new PBB();
        deck.add(c7);
        PBB c8 = new PBB();
        deck.add(c8);

        PRB c9 = new PRB();
        deck.add(c9);
        PRB c10 = new PRB();
        deck.add(c10);
        PRB c11 = new PRB();
        deck.add(c11);
        PRB c12 = new PRB();
        deck.add(c12);

        PRR c13 = new PRR();
        deck.add(c13);
        PRR c14 = new PRR();
        deck.add(c14);

        PYB c15 = new PYB();
        deck.add(c15);
        PYB c16 = new PYB();
        deck.add(c16);
        PYB c17 = new PYB();
        deck.add(c17);
        PYB c18 = new PYB();
        deck.add(c18);

        PYR c19 = new PYR();
        deck.add(c19);
        PYR c20 = new PYR();
        deck.add(c20);
        PYR c21 = new PYR();
        deck.add(c21);
        PYR c22 = new PYR();
        deck.add(c22);

        PYY c23 = new PYY();
        deck.add(c23);
        PYY c24 = new PYY();
        deck.add(c24);

        RBB c25 = new RBB();
        deck.add(c25);
        RBB c26 = new RBB();
        deck.add(c26);
        RBB c27 = new RBB();
        deck.add(c27);

        RYY c28 = new RYY();
        deck.add(c28);
        RYY c29 = new RYY();
        deck.add(c29);
        RYY c30 = new RYY();
        deck.add(c30);

        YBB c31 = new YBB();
        deck.add(c31);
        YBB c32 = new YBB();
        deck.add(c32);
        YBB c33 = new YBB();
        deck.add(c33);

        YRR c34 = new YRR();
        deck.add(c34);
        YRR c35 = new YRR();
        deck.add(c35);
        YRR c36 = new YRR();
        deck.add(c36);
    }

    /**
     * Shuffle done at the start of the game.
     */
    public void startingShuffle() {
        shuffleDeck(deck);
    }

    /**
     * Gets ammo deck.
     *
     * @return ammo deck
     */
    public List<AmmoCard> getDeck() {
        return deck;
    }

    /**
     * Removes the top (first card) of the deck.
     *
     * @return ammo card, null (default)
     */
    public AmmoCard getTopOfDeck() {
        if(!this.deck.isEmpty()) {
            AmmoCard a = this.deck.get(0);
            this.deck.remove(0);
            return a;
        }
        return null;
    }
}