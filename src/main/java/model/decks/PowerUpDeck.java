package model.decks;

import model.Colour;
import model.InvalidColourException;
import model.cards.PowerUpCard;
import model.cards.powerupcards.Newton;
import model.cards.powerupcards.TagbackGrenade;
import model.cards.powerupcards.TargetingScope;
import model.cards.powerupcards.Teleporter;

import java.util.ArrayList;
import java.util.List;

public class PowerUpDeck extends Deck {

    private ArrayList<PowerUpCard> deck = new ArrayList<>();

    public PowerUpDeck() throws InvalidColourException {
        TargetingScope c1 = new TargetingScope(Colour.RED);
            deck.add(c1);
        TargetingScope c2 = new TargetingScope(Colour.RED);
            deck.add(c2);
        TargetingScope c3 = new TargetingScope(Colour.BLUE);
            deck.add(c3);
        TargetingScope c4 = new TargetingScope(Colour.BLUE);
            deck.add(c4);
        TargetingScope c5 = new TargetingScope(Colour.YELLOW);
            deck.add(c5);
        TargetingScope c6 = new TargetingScope(Colour.YELLOW);
            deck.add(c6);

        Newton c7 = new Newton(Colour.RED);
            deck.add(c7);
        Newton c8 = new Newton(Colour.RED);
            deck.add(c8);
        Newton c9 = new Newton(Colour.BLUE);
            deck.add(c9);
        Newton c10 = new Newton(Colour.BLUE);
            deck.add(c10);
        Newton c11 = new Newton(Colour.YELLOW);
            deck.add(c11);
        Newton c12 = new Newton(Colour.YELLOW);
            deck.add(c12);

        TagbackGrenade c13 = new TagbackGrenade(Colour.RED);
            deck.add(c13);
        TagbackGrenade c14 = new TagbackGrenade(Colour.RED);
            deck.add(c14);
        TagbackGrenade c15 = new TagbackGrenade(Colour.BLUE);
            deck.add(c15);
        TagbackGrenade c16 = new TagbackGrenade(Colour.BLUE);
            deck.add(c16);
        TagbackGrenade c17 = new TagbackGrenade(Colour.YELLOW);
            deck.add(c17);
        TagbackGrenade c18 = new TagbackGrenade(Colour.YELLOW);
            deck.add(c18);

        Teleporter c19 = new Teleporter(Colour.RED);
            deck.add(c19);
        Teleporter c20 = new Teleporter(Colour.RED);
            deck.add(c20);
        Teleporter c21 = new Teleporter(Colour.BLUE);
            deck.add(c21);
        Teleporter c22 = new Teleporter(Colour.BLUE);
            deck.add(c22);
        Teleporter c23 = new Teleporter(Colour.YELLOW);
            deck.add(c23);
        Teleporter c24 = new Teleporter(Colour.YELLOW);
            deck.add(c24);
    }

    public void startingShuffle() {
        shuffleDeck(deck);
    }

    public List<PowerUpCard> getDeck() {
        return deck;
    }

    public void addCard(PowerUpCard c){
        this.getDeck().add(c);
    }

    public PowerUpCard getTopOfDeck() {
        return deck.get(0);
    }

    public void drawFromDeck() {
        //TODO
    }
}