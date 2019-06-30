package model.decks;

import java.util.Collections;
import java.util.List;

/**
 * Deck interface implemented by the 3 card decks which currently has more of a symbolic value.
 */
public interface Deck {

    /**
     * Shuffles deck.
     *
     * @param deck deck
     */
    default void shuffleDeck(List deck) {
        Collections.shuffle(deck);
    }
}