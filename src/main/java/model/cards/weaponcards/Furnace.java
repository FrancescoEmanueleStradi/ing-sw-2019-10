package model.cards.weaponcards;

import model.*;
import model.board.*;
import model.cards.WeaponCard;

public class Furnace extends WeaponCard {

    private String alternativeEffect = "Cozy Fire Mode";

    public Furnace() throws InvalidColourException {
        super();
        this.cardName = "Furnace";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = true;
        String description = "basic mode: Choose a room you can see, but not the room\n" +
                             "you are in. Deal 1 damage to everyone in that room.\n" +
                             "in cozy fire mode: Choose a square exactly one move\n" +
                             "away. Deal 1 damage and 1 mark to everyone on that\n" +
                             "square.";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    //controller let the player p choose a room he can see (excluding the room the player is in)

    public void applyEffect(Grid grid, Player p, Colour c) {
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().getC().equals(c))
                grid.damage(p, enemy, 1);
        }
    }

    //controller let the player choose a cell one move away from him (check this)

    public void applySpecialEffect(Grid grid, Player p, Cell cell) {
        for(Player enemy : grid.getPlayers()) {
            if(enemy.getCell().equals(cell)) {
                grid.damage(p, enemy, 1);
                grid.addMark(p, enemy);
            }
        }
    }
}
