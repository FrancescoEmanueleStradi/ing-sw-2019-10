package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.Player;

public class ZX2 extends WeaponCard {

    private String alternativeEffect = "Scanner Mode";

    public ZX2() {
        super();
        this.cardName = "ZX-2";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = true;
        this.description = "basic mode: Deal 1 damage and 2 marks to 1 target you can see.\n" +
                             "in scanner mode: Choose up to 3 targets you can see and deal 1 mark to each.\n" +
                             "Notes: Remember that the 3 targets can be in 3 different rooms.\n";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    //before: let player p choose one target he can see: p1.

    public void applyEffect(Grid grid, Player p, Player p1) {   //player p deals 1 damage to p1 and adds 2 marks to him
        grid.damage(p, p1, 1);
        grid.addMark(p, p1);
        grid.addMark(p, p1);
    }

    //before: let player p choose up to three target he can see: p1, p2, p3. They can be in three different rooms.

    public void applySpecialEffect(Grid grid, Player p, Player p1, Player p2, Player p3) { //Scanner Mode: player p deals 1 damage to p1, p2 and p3: p2 and/or p3 can be null
        grid.damage(p, p1, 1);
        if(p2 != null)
            grid.damage(p, p2, 1);
        if(p3 != null)
            grid.damage(p, p3, 1);
    }
}