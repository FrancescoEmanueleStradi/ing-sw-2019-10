package model.decks;

import java.util.Collections;
import java.util.List;

public interface Deck {

    default void shuffleDeck(List deck) {
        Collections.shuffle(deck);
    }
}