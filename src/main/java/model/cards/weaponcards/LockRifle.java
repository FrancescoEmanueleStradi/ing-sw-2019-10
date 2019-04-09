package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class LockRifle extends WeaponCard {

    private String optionalEffect1 = "Second Lock";

    public LockRifle() throws InvalidColourException {
        super();
        this.cardName = "Lock Rifle";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 1;
        super.alternateFireMode = false;
        String description = "basic effect: Deal 2 damage and 1 mark to 1 target\n" +
                "you can see.\n" +
                "with second lock: Deal 1 mark to a different target\n" +
                "you can see.";
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) { //player p attacks p1, giving him 2 damage and 1 mark
        grid.damage(p1, 2);
        grid.addMark(p, p1);
    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) { //Second Lock: player p attacks p1, who is different from the p1 selected for the primary effect
        grid.damage(p1, 1);
    }
}