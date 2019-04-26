package model.cards.weaponcards;

import model.*;
import model.board.Cell;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

public class Cyberblade extends WeaponCard {

    private String optionalEffect1 = "Shadowstep";
    private String optionalEffect2 = "Slice and Dice";

    public Cyberblade() throws InvalidColourException {
        super();
        this.cardName = "Cyberblade";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 2;
        this.alternateFireMode = false;
        this.description = "basic effect: Deal 2 damage to 1 target on your square.\n" +
                             "with shadowstep: Move 1 square before or after the basic effect.\n" +
                             "with slice and dice: Deal 2 damage to a different target on your square.\n" +
                             "The shadowstep may be used before or after this effect.\n" +
                             "Notes: Combining all effects allows you to move onto a square and whack 2 people; or whack somebody, move, and whack somebody else; or whack 2 people and then move.\n";
    }

    public String getOptionalEffect2() {
        return optionalEffect2;
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    //before: let player p choose a player p1 on his cell.

    public void applyEffect(Grid grid, Player p, Player p1) {   //player p deals 1 damage to p1
        grid.damage(p, p1, 2);
    }

    //before: This Special Effect can be done before or after the other effects. Let player p choose a cell he wants to go to.

    public void applySpecialEffect(Grid grid, Player p, String x, String y) {   //Shadowstep: player p moves in the chosen cell
        grid.move(p, Integer.parseInt(x), Integer.parseInt(y));
    }

    //before: let player p choose a different target p2 than p1. p2 must be on the same cell of p.

    public void applySpecialEffect2(Grid grid, Player p, Player p2) {   //Slice and dice: player p deals 2 damage to the chosen p2
        grid.damage(p, p2, 2);
    }
}