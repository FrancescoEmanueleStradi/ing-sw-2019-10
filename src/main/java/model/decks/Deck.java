package model.decks;

import java.util.Collections;
import java.util.List;

public abstract class Deck {

    public void shuffleDeck(List deck) {
        Collections.shuffle(deck);
    }

    public void showDeck(List deck) {
        System.out.println(deck);
    }

}
