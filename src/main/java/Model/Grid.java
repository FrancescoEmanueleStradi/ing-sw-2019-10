package Model;

import Model.Board.Board;

import java.util.ArrayList;

public class Grid {
    Board board;
    ArrayList<PlayerBoard> pB;
    ArrayList<Figure> fig;
    Deck weaponDeck;
    Deck powerupDeck;
    Deck ammoDeck;

    public Grid(int aType) throws InvalidColourException {
        this.weaponDeck = new WeaponDeck();
        this. powerupDeck = new PowerUpDeck();
        this.ammoDeck = new AmmoDeck();
        this.board = new Board(aType);
        this.pB = new ArrayList<PlayerBoard>();
        this.fig = new ArrayList<Figure>();
    }

    public void addPB(PlayerBoard pb) {
        this.pB.add(pb);
    }

    public void removePB(PlayerBoard pb) {
        this.pB.remove(pb);
    }

    public void addFIG(Figure fig) {
        this.fig.add(fig);
    }

    public void removeFIG(Figure fig) {
        this.fig.remove(fig);
    }
}
