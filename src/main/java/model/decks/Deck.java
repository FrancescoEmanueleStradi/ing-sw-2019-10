package model.decks;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Deck {

    public void shuffleDeck(ArrayList deck) {
        Collections.shuffle(deck);
    }

    public void showDeck(ArrayList deck) {
        System.out.println(deck);
    }

}
