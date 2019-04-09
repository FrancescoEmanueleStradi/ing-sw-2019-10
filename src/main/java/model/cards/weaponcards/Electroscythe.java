package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Electroscythe extends WeaponCard {

    private String alternateFireMode = "Reaper Mode";

    public Electroscythe() throws InvalidColourException {
        super();
        this.cardName = "Electroscythe";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        super.alternateFireMode = true;
        String description = "basic mode: Deal 1 damage to every other player\n" +
                "on your square.\n" +
                "in reaper mode: Deal 2 damage to every other player\n" +
                "on your square.";
    }

    public String getAlternateFireMode() {
        return alternateFireMode;
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) { //player p damages p1: call this method for every p1 on the same square as player p
        grid.damage(p1, 1);
    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) { //Reaper Mode: player p damages p1: call this method for every p1 on the same square as player p
        grid.damage(p1, 1);
    }

}