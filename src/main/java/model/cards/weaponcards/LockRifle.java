package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class LockRifle extends WeaponCard {

    private Player firstPlayerAttacked; //useful? controller could save the player's choice and use that

    private String optionalEffect = "Second Lock";

    public LockRifle() throws InvalidColourException {
        super();
        this.cardName = "Lock Rifle";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 1;
        this.alternateFireMode = false;
        this.description = "basic effect: Deal 2 damage and 1 mark to 1 target you can see.\n" +
                             "with second lock: Deal 1 mark to a different target you can see.\n";
    }

    public Player getFirstPlayerAttacked() {
        return firstPlayerAttacked;
    }

    public String getOptionalEffect() {
        return optionalEffect;
    }

    public void applyEffect(Grid grid, Player p, Player p1) { //player p attacks p1 (visible), giving him 2 damage and 1 mark
        grid.damage(p, p1, 2);
        grid.addMark(p, p1);
        this.firstPlayerAttacked = p1; //useful? See above
    }

    public void applySpecialEffect(Grid grid, Player p, Player p2) { //Second Lock: player p attacks p2 (visible), who is different from the p1 selected for the primary effect: the controller will check this!
        grid.damage(p, p2, 1);
    }
}