package model.cards.weaponCards;

import model.*;
import model.AmmoCube;
import model.cards.WeaponCard;

import java.util.Scanner;

public class MachineGun extends WeaponCard {

    private String target1;
    private String target2;

    private String specialEffect1 = "Focus shot";
    private String specialEffect2 = "Turret tripod";

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

        System.out.println("Enter the name of the target player");
        Scanner in = new Scanner(System.in);
        this.target1 = in.next();

        System.out.println("Target another player?");
        String response = in.next();
        if(response == ("y")) {
            System.out.println("Enter the name of the target player");
            this.target2 = in.next();

            //Player p1 = Grid.choosePlayer(numPlayer);
            //Grid.damage(p1, 1);
        }

    }


    @Override
    public void applySpecialEffect() {
        //ask professor about override
        Scanner in = new Scanner(System.in);
        System.out.println("Apply effect '" + getSpecialEffect1() + "'? (y/n)");
        String response = in.next();
        if (response == ("y")) {
            System.out.println("Which of the previous players do you want to target? "
                               + this.target1 + " or " + this.target2 +"? Enter 1 or 2");
            String selection = in.next();
            if (selection == "1") {

            }



            int numPlayer = Integer.parseInt(this.target1);
            //Player p1 = Grid.choosePlayer(numPlayer);
            //Grid.damage(p1, 1);
        }
        System.out.println("Enter the name of another player");

        String playerName = in.next();

    }
}