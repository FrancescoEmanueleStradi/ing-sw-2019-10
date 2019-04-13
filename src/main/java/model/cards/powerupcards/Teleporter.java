package model.cards.powerupcards;

import model.*;
import model.board.Cell;
import model.cards.PowerUpCard;
import model.player.AmmoCube;
import model.player.Player;

public class Teleporter extends PowerUpCard {

    public Teleporter(Colour c) throws InvalidColourException {
        super();
        this.cardName = "Teleporter";
        this.c = c;
        this.value = new AmmoCube(c);
        this.description = "You may play this card on your turn before or after any action.\n" +
                            "Pick up your figure and set it down on any square of the board.\n" +
                            "(You can't use this after you see where someone respawns at the end of your turn. By then it is too late.)\n";
    }

    //before: let player p choose a Cell cell he wants to go in. He has to use this card before any player respawns at the end of p's turn.

    public void applyEffect(Player p, Cell cell) {
        p.setCell(cell);
    }
}