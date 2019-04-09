package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class LockRifle extends WeaponCard {

    private Player firstPlayerAttacked;

    private String optionalEffect = "Second Lock";

    public LockRifle() throws InvalidColourException {
        super();
        this.cardName = "Lock Rifle";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 1;
        this.alternateFireMode = false;
        String description = "basic effect: Deal 2 damage and 1 mark to 1 target\n" +
                             "you can see.\n" +
                             "with second lock: Deal 1 mark to a different target\n" +
                             "you can see.";
    }

    public Player getFirstPlayerAttacked() {
        return firstPlayerAttacked;
    }

    public String getOptionalEffect() {
        return optionalEffect;
    }

    public void applyEffect(Grid grid, Player p, Player p1) { //player p attacks p1, giving him 2 damage and 1 mark
        grid.damage(p, p1, 2);
        grid.addMark(p, p1);
        this.firstPlayerAttacked = p1;
    }

    public void applySpecialEffect(Grid grid, Player p, Player p2) { //Second Lock: player p attacks p2, who is different from the p1 selected for the primary effect: the controller will check this!
        grid.damage(p, p2, 1);
    }
}