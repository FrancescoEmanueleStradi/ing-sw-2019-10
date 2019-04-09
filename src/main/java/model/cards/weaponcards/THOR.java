package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class THOR extends WeaponCard {

    private String optionalEffect1 = "Chain Reaction";
    private String optionalEffect2 = "High Voltage";

    public THOR() throws InvalidColourException {
        super();
        this.cardName = "T.H.O.R.";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 2;
        super.alternateFireMode = true;
        String description = "basic effect: Deal 2 damage to 1 target you can see.\n" +
                "with chain reaction: Deal 1 damage to a second target that\n" +
                "your first target can see.\n" +
                "with high voltage: Deal 2 damage to a third target that\n" +
                "your second target can see. You cannot use this effect\n" +
                "unless you first use the chain reaction.\n" +
                "Notes: This card constrains the order in which you can use\n" +
                "its effects. (Most cards don't.) Also note that each target\n" +
                "must be a different player.";
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    public String getOptionalEffect2() {
        return optionalEffect2;
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) { //player p gives 2 damages to p1 (a target he can sees)
        grid.damage(p1, 2);
    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) { //player p gives 1 damage to p1, who is a second target that the first target can see
        grid.damage(p1, 1);
    }


}
