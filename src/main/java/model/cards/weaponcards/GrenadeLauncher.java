package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class GrenadeLauncher extends WeaponCard {

    private String optionalEffect1 = "Extra Grenade";

    public GrenadeLauncher() throws InvalidColourException {
        super();
        this.cardName = "Grenade Launcher";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 1;
        super.alternateFireMode = false;
        String description = "basic effect: Deal 1 damage to 1 target you can see. Then you may move\n" +
                "the target 1 square.\n" +
                "with extra grenade: Deal 1 damage to every player on a square you can\n" +
                "see. You can use this before or after the basic effect's move.\n" +
                "Notes: For example, you can shoot a target, move it onto a square with\n" +
                "other targets, then damage everyone including the first target.\n" +
                "Or you can deal 2 to a main target, 1 to everyone else on that square,\n" +
                "then move the main target. Or you can deal 1 to an isolated target and\n" +
                "1 to everyone on a different square. If you target your own square,\n" +
                "you will not be moved or damaged.";
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }
}
