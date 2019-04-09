package model.decks;

import model.Colour;
import model.InvalidColourException;
import model.cards.PowerUpCard;
import model.cards.powerupcards.Newton;
import model.cards.powerupcards.TagbackGrenade;
import model.cards.powerupcards.TargetingScope;
import model.cards.powerupcards.Teleporter;

import java.util.ArrayList;

public class PowerUpDeck extends Deck {

    private ArrayList<PowerUpCard> powerUpDeck = new ArrayList<>();

    public PowerUpDeck() throws InvalidColourException {
        TargetingScope c1 = new TargetingScope(Colour.RED);
            powerUpDeck.add(c1);
        TargetingScope c2 = new TargetingScope(Colour.RED);
            powerUpDeck.add(c2);
        TargetingScope c3 = new TargetingScope(Colour.BLUE);
            powerUpDeck.add(c3);
        TargetingScope c4 = new TargetingScope(Colour.BLUE);
            powerUpDeck.add(c4);
        TargetingScope c5 = new TargetingScope(Colour.YELLOW);
            powerUpDeck.add(c5);
        TargetingScope c6 = new TargetingScope(Colour.YELLOW);
            powerUpDeck.add(c6);

        Newton c7 = new Newton(Colour.RED);
            powerUpDeck.add(c7);
        Newton c8 = new Newton(Colour.RED);
            powerUpDeck.add(c8);
        Newton c9 = new Newton(Colour.BLUE);
            powerUpDeck.add(c9);
        Newton c10 = new Newton(Colour.BLUE);
            powerUpDeck.add(c10);
        Newton c11 = new Newton(Colour.YELLOW);
            powerUpDeck.add(c11);
        Newton c12 = new Newton(Colour.YELLOW);
            powerUpDeck.add(c12);

        TagbackGrenade c13 = new TagbackGrenade(Colour.RED);
            powerUpDeck.add(c13);
        TagbackGrenade c14 = new TagbackGrenade(Colour.RED);
            powerUpDeck.add(c14);
        TagbackGrenade c15 = new TagbackGrenade(Colour.BLUE);
            powerUpDeck.add(c15);
        TagbackGrenade c16 = new TagbackGrenade(Colour.BLUE);
            powerUpDeck.add(c16);
        TagbackGrenade c17 = new TagbackGrenade(Colour.YELLOW);
            powerUpDeck.add(c17);
        TagbackGrenade c18 = new TagbackGrenade(Colour.YELLOW);
            powerUpDeck.add(c18);

        Teleporter c19 = new Teleporter(Colour.RED);
            powerUpDeck.add(c19);
        Teleporter c20 = new Teleporter(Colour.RED);
            powerUpDeck.add(c20);
        Teleporter c21 = new Teleporter(Colour.BLUE);
            powerUpDeck.add(c21);
        Teleporter c22 = new Teleporter(Colour.BLUE);
            powerUpDeck.add(c22);
        Teleporter c23 = new Teleporter(Colour.YELLOW);
            powerUpDeck.add(c23);
        Teleporter c24 = new Teleporter(Colour.YELLOW);
            powerUpDeck.add(c24);
    }

    public void startingShuffle() {
        shuffleDeck(powerUpDeck);
    }

    public ArrayList<PowerUpCard> getPowerUpDeck() {
        return powerUpDeck;
    }

    public void addCard(PowerUpCard c){
        this.getPowerUpDeck().add(c);
    }

    public PowerUpCard getTopOfDeck() {
        return powerUpDeck.get(0);
    }

    public void drawFromDeck() {
    }
}