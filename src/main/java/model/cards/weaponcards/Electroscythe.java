package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Electroscythe extends WeaponCard {

    private String alternativeEffect = "Reaper Mode";

    public Electroscythe() throws InvalidColourException {
        super();
        super.cardName = "Electroscythe";
        super.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        super.alternateFireMode = true;
        String description = "basic mode: Deal 1 damage to every other player\n" +
                             "on your square.\n" +
                             "in reaper mode: Deal 2 damage to every other player\n" +
                             "on your square.";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    public void applyEffect(Grid grid, Player p) { //player p damages every enemy on the same square as player p
        for(Player enemy : grid.getPlayers()) {
            if (grid.whereAmI(enemy).equals(grid.whereAmI(p)) && !(enemy.equals(p)))
                grid.damage(p, enemy, 1);
        }
    }

    public void applySpecialEffect(Grid grid, Player p) { //Reaper Mode: player p damages p1: call this method for every p1 on the same square as player p
        for(Player enemy : grid.getPlayers()) {
            if (grid.whereAmI(enemy).equals(grid.whereAmI(p)) && !(enemy.equals(p)))
                grid.damage(p, enemy, 2);
        }
    }

}