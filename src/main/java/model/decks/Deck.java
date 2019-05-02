package model.decks;

import java.util.Collections;
import java.util.List;

public interface Deck {

    public default void shuffleDeck(List deck) {
        Collections.shuffle(deck);
    }

}
