package model.cards.weaponCards;

import model.*;
import model.AmmoCube;
import model.cards.WeaponCard;

import java.util.Scanner;

public class MachineGun extends WeaponCard {

    private String target1;
    private String target2;

    private String specialEffect1 = "Focus Shot";
    private String specialEffect2 = "Turret Tripod";

    public MachineGun() throws InvalidColourException {
        super();
        this.cardName = "Machine Gun";                                                                                  //here?
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.RED)};
        this.numSpecialEffect = 2;
        String description = "basic effect: Choose 1 or 2 targets you can see and deal 1 damage to each.\n" +
                "with focus shot: Deal 1 additional damage to one of those targets.\n" +                                                                            //write cost
                "with turret tripod: Deal 1 additional damage to the other of those targets and/or deal 1 damage to a different target you can see.\n" +
                "Notes: If you deal both additional points of damage,\n" +
                "they must be dealt to 2 different targets. If you see only\n" +
                "2 targets, you deal 2 to each if you use both optional effects. If you use the basic effect on only 1 target, you can still use the the turret tripod to give it 1 additional damage.";
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }

    public String getSpecialEffect1() {
        return specialEffect1;
    }

    public String getSpecialEffect2() {
        return specialEffect2;
    }

    @Override
    public void applyEffect() {

        //to implement in another method(Controller)
        /*
        if ((player.munition).equals(this.reloadCost)){
        }
        else
        System.out.println("You don't have the right ammo :(");
        }
        */

        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Enter the name of a player you can see");
            this.target1 = in.next();
            if(Grid.getPlayerObject(this.target1) != null)
                Grid.damage(Grid.getPlayerObject(this.target1), 1);
        } while(Grid.getPlayerObject(this.target1) == null);

        System.out.println("Target another player you can see?");
        String response = in.next();
        if(response.equals("y")) {
            do {
                System.out.println("Enter the name of the player");
                this.target2 = in.next();
                if(Grid.getPlayerObject(this.target2) != null)
                    Grid.damage(Grid.getPlayerObject(this.target2), 1);
            } while (Grid.getPlayerObject(this.target2) == null);
        }

    }

    //ask professor about override
    //too procedural, not enough methods??
    @Override
    public void applySpecialEffect() {

        Scanner in = new Scanner(System.in);

        System.out.println("Apply effect '" + getSpecialEffect1() + "'? (y/n)");
        String response = in.next();

        if (response.equals("y")) {
            if (this.target2 == null) {
                System.out.println("Do you want to target " + this.target1 + " or another player you " +
                        "can see? Enter 1 or 2");
                String selection = in.next();
                if (selection.equals("1"))
                    Grid.damage(Grid.getPlayerObject(this.target1), 1);
                if (selection.equals("2")) {
                    do {
                        System.out.println("Enter the name of the player");
                        this.target2 = in.next();
                        Grid.damage(Grid.getPlayerObject(this.target2), 1);
                    } while (Grid.getPlayerObject(this.target2) == null);
                }
            } else {
                System.out.println("Do you want to target " + this.target1 + " or " + this.target2 + "? Enter 1 or 2");
                String selection = in.next();
                if (selection.equals("1"))
                    Grid.damage(Grid.getPlayerObject(this.target1), 1);
                if (selection.equals("2"))
                    Grid.damage(Grid.getPlayerObject(this.target2), 1);
            }
        }
        if (response.equals("n")) {
            System.out.println("Apply effect '" + getSpecialEffect2() + "'? (y/n)");
            String response2 = in.next();
            if (response2.equals("y")) {
                System.out.println("If you applied Focus Shot on one of the previous players, the next target has to " +
                                   "be the other of the two, or another player you can see\n");
                System.out.println("");


            }
        }
    }
}