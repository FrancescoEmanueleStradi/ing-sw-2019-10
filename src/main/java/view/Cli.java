package view;

import controller.Game;
import model.Colour;
import model.InvalidColourException;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Cli extends View{

    private Game game;
    private String nickName;                //TODO maybe nickName and colour should go in the client and not hear beacuse View must be the RMI registry
    private Colour colour;

    @Override
    public void askNameAndColour() throws InvalidColourException {
        Scanner in = new Scanner(System.in);
        if (this.game.gameIsNotStarted()) {
            System.out.println("Enter your name: ");
            this.nickName = in.nextLine();

            System.out.println("Enter your colour (YELLOW, BLUE, GREEN, PURPLE, BLACK): ");
            String stringColour = in.nextLine();
            this.colour = Colour.valueOf(stringColour);

            this.game.gameStart(nickName, colour);

            System.out.println("Choose the type of Arena (1, 2, 3, 4): ");
            int type = in.nextInt();
            while (!this.game.isValidReceiveType(type)){
                System.out.println("Choose the type of Arena (1, 2, 3, 4): ");
                type = in.nextInt();
            }
            this.game.receiveType(type);
            return;
        }

        System.out.println("Enter your name: ");
        this.nickName = in.nextLine();

        System.out.println("Enter your colour: ");
        String stringColour = in.nextLine();
        this.colour = Colour.valueOf(stringColour);

        while(!this.game.isValidAddPlayer(this.nickName, this.colour)){
            System.out.println("Enter your name: ");
            this.nickName = in.nextLine();

            System.out.println("Enter your colour: ");
            String stringColour1 = in.nextLine();
            this.colour = Colour.valueOf(stringColour1);
        }

        this.game.addPlayer(this.nickName, this.colour);
    }

    @Override
    public void selectSpawnPoint() {
        Scanner in = new Scanner(System.in);
        List<PowerUpCard> l = new LinkedList<>();
        for(PowerUpCard p : this.game.giveTwoPUCard(this.nickName)){
            System.out.println(p.getCardName());
            l.add(p);
        }
        System.out.println("Enter the name of the card you want to choose; you will discard the other one");
        String p1 = in.nextLine();
        while(this.game.isValidPickAndDiscard(this.nickName)) {
            System.out.println("Enter the name of the card you want to choose; you will discard the other one");
            p1 = in.nextLine();
        }
            if(l.get(0).getCardName().equals(p1))
                this.game.pickAndDiscardCard(this.nickName, l.get(0), l.get(1));
            else
                this.game.pickAndDiscardCard(this.nickName, l.get(1), l.get(0));
    }

    @Override
    public void action1() throws InvalidColourException{
        Scanner in = new Scanner(System.in);
        System.out.println("Choose the action you want to do (Move, Shoot, Grab): ");
        String action = in.nextLine();
        while (!(action.equals("Move") || action.equals("Shoot") || action.equals("Grab"))){
            System.out.println("Choose the action you want to do (Move, Shoot, Grab): ");
            action = in.nextLine();
        }
        if(action.equals("Move"))
            this.moveFirstAction();
        if(action.equals("Shoot"))
            this.shootFirstAction();
        if(action.equals("Grab"))
            this.grabFirstAction();
    }

    private void moveFirstAction() {
        Scanner in = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        while(!this.game.isValidFirstActionMove(l)) {
            System.out.println("Choose the sequence of movement you want to do");
            while (in.hasNext())
                l.add(in.nextInt());
        }
        this.game.firstActionMove(this.nickName, l);
    }

    private void shootFirstAction() throws InvalidColourException{
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot: ");
        this.game.getWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
        String s = in.next();
        while(this.game.isValidCard(nickName, s)){
            System.out.println("Error : choose one of these cards to shoot: ");
            this.game.getWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
            s = in.next();
        }
        System.out.println(this.game.getReloadCost(s, nickName));
        System.out.println(this.game.getDescriptionWC(s,nickName));
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        int i;
        switch(s){
            case "Cyberblade":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                System.out.println("Error repeat: don't be silly ;)");
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Electroscythe":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);

               break;
            case "Flamethrower":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);

                break;
            case "Furnace":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Grenade Launcher":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);

                break;
            case "Heatseeker":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Hellion":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Lock Rifle":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Machine Gun":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
               break;
            case "Plasma Gun":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);

                break;
            case "Power Glove":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);

                break;
            case "Railgun":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Rocket Launcher":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);

                break;
            case "Shockwave":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Shotgun":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
               break;
            case "Sledgehammer":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "T.H.O.R.":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Tractor Beam":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
               break;
            case "Vortex Cannon":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                while (in.hasNext())
                    lC.add(Colour.valueOf(in.next()));
                this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                System.out.println("write the name of the power up cards that are useful for the card");
                while (in.hasNext())
                    lP.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("write the Colour of Ammo Cube you want to use that you nedd for the card");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("write the name of the power up cards that are useful for the card");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "Whisper":
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
                break;
            case "ZX-2":
                System.out.println("write the number of the effect you want to use");
                while (in.hasNext())
                    lI.add(in.nextInt());
                System.out.println("write the String that are useful for the card");
                while (in.hasNext())
                    lS.add(in.next());
                System.out.println("If you are adrenalin enter the direction of the move");
                i = in.nextInt() ;                       //TODO nextint is a problem
                while(!this.game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
                    System.out.println("Error repeat: don't be silly ;)");
                    System.out.println("write the number of the effect you want to use");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("write the String that are useful for the card");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("If you are adrenalin enter the direction of the move");
                    i = in.nextInt() ;
                }
                this.game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);

                break;
        }



    }

    private void grabFirstAction() {
        //TODO
    }

    @Override
    public void action2() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose the action you want to do (Move, Shoot, Grab): ");
        String action = in.nextLine();
        while (!(action.equals("Move") || action.equals("Shoot") || action.equals("Grab"))){
            System.out.println("Choose the action you want to do (Move, Shoot, Grab): ");
            action = in.nextLine();
        }
        if(action.equals("Move"))
            this.moveSecondAction();
        if(action.equals("Shoot"))
            this.shootSecondAction();
        if(action.equals("Grab"))
            this.grabSecondAction();
    }

    private void moveSecondAction() {
        Scanner in = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        while(!this.game.isValidSecondActionMove(l)) {
            System.out.println("Choose the sequence of movement you want to do");
            while (in.hasNext())
                l.add(in.nextInt());
        }
        this.game.firstActionMove(this.nickName, l);
    }

    private void shootSecondAction(){

    }

    private void grabSecondAction(){

    }

    @Override
    public void reload() {               //the player knows everything!
        Scanner in = new Scanner(System.in);
        this.game.getWeaponCardUnloaded(this.nickName).stream().forEach(System.out::println);
        int i = 0;
        while(i == 0){
            System.out.println("Choose the weapon card you want to reload");
            String s = in.nextLine();
            System.out.println("Enter 0 if you want to reload another card, otherwise 1");
            i = in.nextInt();
            if(this.game.isValidReload())
                this.game.reload(this.nickName, s, i);
            else
                System.out.println("You can't reload now: pay attention to the rules! (you can find the manual inside the box)");
        }
    }

    @Override
    public void scoring() {
        if(this.game.isValidScoring())
            this.game.scoring();
        else
            System.out.println("It is not time for scoring: pay attention to the rule! (you can find the rule book near the board you bought)");
    }


    @Override
    public void newSpawnPoint() {

        if(this.game.getDeadList().contains(this.nickName)) {
            System.out.println("Enter the PowerUp card you want to discard: ");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            while(!this.game.isValidDiscardCardForSpawnPoint()){
                System.out.println("Enter the PowerUp card you want to discard: ");
                s = in.nextLine();
            }
                    this.game.discardCardForSpawnPoint(this.nickName, s);
        }
        else
            System.out.println("What are you doing, man?");
    }


    @Override
    public void replace() {
        if(this.game.isValidToReplace())
            this.game.replace();
        else
            System.out.println("Time for replacing has not come yet: pay attention to the rule! (you can find the rule book near the board you bought)");
    }
}
