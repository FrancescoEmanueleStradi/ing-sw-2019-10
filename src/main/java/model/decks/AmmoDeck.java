package model.decks;

import model.InvalidColourException;
import model.cards.AmmoCard;
import model.cards.ammocards.*;

import java.util.ArrayList;

public class AmmoDeck extends Deck {

    private ArrayList<AmmoCard> ammoDeck = new ArrayList<>();

    public AmmoDeck () throws InvalidColourException {
        BRR c1 = new BRR();
        ammoDeck.add(c1);
        BRR c2 = new BRR();
        ammoDeck.add(c2);
        BRR c3 = new BRR();
        ammoDeck.add(c3);

        BYY c4 = new BYY();
        ammoDeck.add(c4);
        BYY c5 = new BYY();
        ammoDeck.add(c5);
        BYY c6 = new BYY();
        ammoDeck.add(c6);

        PBB c7 = new PBB();
        ammoDeck.add(c7);
        PBB c8 = new PBB();
        ammoDeck.add(c8);

        PRB c9 = new PRB();
        ammoDeck.add(c9);
        PRB c10 = new PRB();
        ammoDeck.add(c10);
        PRB c11 = new PRB();
        ammoDeck.add(c11);
        PRB c12 = new PRB();
        ammoDeck.add(c12);

        PRR c13 = new PRR();
        ammoDeck.add(c13);
        PRR c14 = new PRR();
        ammoDeck.add(c14);

        PYB c15 = new PYB();
        ammoDeck.add(c15);
        PYB c16 = new PYB();
        ammoDeck.add(c16);
        PYB c17 = new PYB();
        ammoDeck.add(c17);
        PYB c18 = new PYB();
        ammoDeck.add(c18);

        PYR c19 = new PYR();
        ammoDeck.add(c19);
        PYR c20 = new PYR();
        ammoDeck.add(c20);
        PYR c21 = new PYR();
        ammoDeck.add(c21);
        PYR c22 = new PYR();
        ammoDeck.add(c22);

        PYY c23 = new PYY();
        ammoDeck.add(c23);
        PYY c24 = new PYY();
        ammoDeck.add(c24);

        RBB c25 = new RBB();
        ammoDeck.add(c25);
        RBB c26 = new RBB();
        ammoDeck.add(c26);
        RBB c27 = new RBB();
        ammoDeck.add(c27);

        RYY c28 = new RYY();
        ammoDeck.add(c28);
        RYY c29 = new RYY();
        ammoDeck.add(c29);
        RYY c30 = new RYY();
        ammoDeck.add(c30);

        YBB c31 = new YBB();
        ammoDeck.add(c31);
        YBB c32 = new YBB();
        ammoDeck.add(c32);
        YBB c33 = new YBB();
        ammoDeck.add(c33);

        YRR c34 = new YRR();
        ammoDeck.add(c34);
        YRR c35 = new YRR();
        ammoDeck.add(c35);
        YRR c36 = new YRR();
        ammoDeck.add(c36);
    }

}
