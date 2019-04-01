import java.util.Scanner;

public class MachineGun extends WeaponCard {

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


    @Override
    public void applyEffect() {
        //if ((player.munition).equals(this.reloadCost)){

        //}

        //to implement in another method(Controller)

        //else
        //System.out.println("You don't have the right ammo :(");

        //}
        System.out.println("Choose 1 or 2 targets you can see to attack");
        Scanner in = new Scanner(System.in);
        String p = in.next();
        Grid.damage(p);
        String p2 = in.next();
        if ((p2 != null))
            Grid.damage(p2);

    }


    @Override
    public void applySpecialEffect() {
                                                                    //TODO ask the professor about the override
    }
}