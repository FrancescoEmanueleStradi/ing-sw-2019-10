package model.cards.powerupcards;

import model.*;
import model.player.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.board.Cell;
import model.cards.PowerUpCard;
import model.player.Player;

public class Newton extends PowerUpCard {

    public Newton(Colour c) throws InvalidColourException {
        super();
        this.cardName = "Newton";
        this.c = c;
        this.value = new AmmoCube(c);
        this.description = "You may play this card on your turn before or after any action.\n" +
                            "Choose any other player's figure and move it 1 or 2 squares in one direction.\n" +
                            "(You can't use this to move a figure after it respawns at the end of your turn. That would be too late.)\n";
    }

    //before: let player p choose a player p1 at any time of his turn, except for when p1 respawns at the end of p's turn.
    //        also let player p select a Cell cell one or two cells away from p1.

    public void applyEffect(Grid grid, Player p1, Cell cell) {  //p1 is moved in cell
        p1.setCell(cell);
    }
}