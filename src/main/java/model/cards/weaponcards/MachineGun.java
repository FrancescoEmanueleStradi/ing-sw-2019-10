package model.cards.weaponcards;

import model.*;
import model.AmmoCube;
import model.cards.WeaponCard;

public class MachineGun extends WeaponCard {

    private String optionalEffect1 = "Focus Shot";
    private String optionalEffect2 = "Turret Tripod";

    public MachineGun() throws InvalidColourException {
        super();
        this.cardName = "Machine Gun";                                                                                  //here?
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 2;
        this.alternateFireMode = false;
        String description = "basic effect: Choose 1 or 2 targets you can see and deal 1 damage to each.\n" +
                "with focus shot: Deal 1 additional damage to one of those targets.\n" +                                                                            //write cost
                "with turret tripod: Deal 1 additional damage to the other of those targets and/or deal 1 damage to a different target you can see.\n" +
                "Notes: If you deal both additional points of damage,\n" +
                "they must be dealt to 2 different targets. If you see only\n" +
                "2 targets, you deal 2 to each if you use both optional effects. If you use the basic effect on only 1 target, you can still use the the turret tripod to give it 1 additional damage.";
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    public String getOptionalEffect2() {
        return optionalEffect2;
    }

//before applying effects: let player p selecting player(s) to attack, checking if they are visible

    public void applyEffect(Grid grid, Player p, Player p1) { //primary attack if only 1 target is selected: player p attacks p1
        grid.damage(p, p1, 1);
    }

    public void applyEffect(Grid grid, Player p, Player p1, Player p2) { //primary attack if 2 targets are selected: player attacks p1 and p2
        grid.damage(p, p1, 1);
        grid.damage(p, p2, 1);
    }

    public void applySpecialEffect(Grid grid, Player p, Player p1) { //Focus Shot: player damages p1 (controller)
        grid.damage(p, p1, 1);
    }

    public void applySpecialEffect2(Grid grid, Player p, Player p1) { //Turret Tripod: player p damages the "other" player (not the one selected in Focus Shot)
        grid.damage(p, p1, 1);
    }

    public void applySpecialEffect2bis(Grid grid, Player p, Player p1, Player p2) { //Turret Tripod: player damages the "other" player and a different target
        grid.damage(p, p1, 1);
        grid.damage(p, p2, 1);
    }

    public void applySpecialEffectSpecialCase(Grid grid, Player p, Player p1, Player p2) { //Special case: if player uses both special effect but sees only 2 players (so he cannot attack a different target)
        grid.damage(p, p2, 2);
        grid.damage(p, p2, 2);
    }
}