package view.cli;

import model.Colour;
import network.ServerInterface;
import view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLI extends UnicastRemoteObject implements View {

    private int game;
    private int identifier;
    private int type = 0;
    private transient ServerInterface server;
    private String nickName;
    private Colour colour;
    private static CLIWeaponPrompt wPrompt = new CLIWeaponPrompt();
    private static final String ERRORRETRY = "Error: please retry";
    private static final String COLOURED = " coloured ";
    private static final String DIRECTIONS = "1 = north, 2 = east, 3 = south, 4 = west";
    static final String EXITSTRING = "Do you want to go back and change action?";
    static final String YESPROMPT = "(Yes/yes/y)";

    public CLI(int game, ServerInterface server) throws RemoteException {
        super();
        this.game = game;
        this.server = server;
    }

    @Override
    public View getView() {
        return this;
    }

    public int getGame() {              //for the test
        return game;
    }

    @Override
    public void setGame(int game) {
        this.game = game;
    }

    @Override
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void setInformation(int identifier) throws RemoteException {
        this.nickName = server.getSuspendedName(game, identifier);
        this.colour = server.getSuspendedColour(game, this.nickName);
        this.identifier = identifier;
    }

    @Override
    public void disconnected(int disconnected) {
        System.out.println("Player with identifier " + disconnected + " has disconnected from the game.");
    }

    @Override
    public void askNameAndColour() throws RemoteException {
        String yourName = "Enter your name:";
        String yourColour = "Enter your colour in all caps (choose between BLACK, BLUE, GREEN, PURPLE or YELLOW):";
        Scanner in = new Scanner(System.in);

        if (this.server.messageGameIsNotStarted(game) && this.identifier == 1) {
            System.out.println("\n---------- NAME AND COLOUR SELECTION ----------\n");
            System.out.println(yourName);
            this.nickName = in.nextLine();
            server.setNickName(this.game, this.identifier, this.nickName);
            System.out.println(yourColour);
            String s1 = in.nextLine();
            this.colour = Colour.valueOf(s1);
            this.server.messageGameStart(game, nickName, colour);
            System.out.println("Choose the type of arena (1, 2, 3, 4):");
            int typeInput = in.nextInt();
            while (!this.server.messageIsValidReceiveType(game, typeInput)){
                System.out.println(ERRORRETRY);
                System.out.println("Choose the type of arena (1, 2, 3, 4):");
                typeInput = in.nextInt();
            }
            this.server.messageReceiveType(game, typeInput);
            System.out.println("\n---------- GENERATING ARENA... ----------\n");
            this.setType(server.getType(game));
            return;
        }

        if(server.getType(game) != 0)
            this.setType(server.getType(game));

        System.out.println("\n---------- NAME AND COLOUR SELECTION ----------\n");
        System.out.println(yourName);
        this.nickName = in.nextLine();
        System.out.println(yourColour);
        String s2 = in.nextLine();
        server.setNickName(this.game, this.identifier, this.nickName);
        this.colour = Colour.valueOf(s2);
        while (!this.server.messageIsValidAddPlayer(game, this.nickName, this.colour)) {
            System.out.println(ERRORRETRY);
            System.out.println(yourName);
            this.nickName = in.nextLine();
            server.setNickName(this.game, this.identifier, this.nickName);
            System.out.println(yourColour);
            s2 = in.nextLine();
            this.colour = Colour.valueOf(s2);
        }
        this.server.messageAddPlayer(game, this.nickName, this.colour);
    }

    @Override
    public void selectSpawnPoint() throws RemoteException {
        Scanner in = new Scanner(System.in);
        this.server.messageGiveTwoPUCard(game, this.nickName);
        String p;
        String c;
        System.out.println("The following are " + this.nickName +"'s starting PowerUpCards:");
        System.out.println(this.server.messageGetPowerUpCard(game, this.nickName).get(0) + COLOURED + this.server.messageGetPowerUpCardColour(game, this.nickName).get(0));
        System.out.println(this.server.messageGetPowerUpCard(game, this.nickName).get(1) + COLOURED + this.server.messageGetPowerUpCardColour(game, this.nickName).get(1));

        System.out.println("\n---------- SPAWN POINT SELECTION ----------\n");
        while(true) {
            System.out.println("Enter the name of the PowerUp card you want to keep.\n" +
                            "You will discard the other one, and its colour will be the colour of your spawn point.");
            p = in.nextLine();
            System.out.println("Enter the colour of the chosen PowerUp card:");
            c = in.nextLine();
            if (this.server.messageIsValidPickAndDiscard(game, this.nickName, p, c))
                break;
            else
                System.out.println(ERRORRETRY);
        }

        if(this.server.messageGetPowerUpCard(game, this.nickName).get(0).equals(p) && this.server.messageGetPowerUpCardColour(game, this.nickName).get(0).equals(c)) {
            String spawnColour = this.server.messageGetPowerUpCardColour(game, this.nickName).get(1);
            this.server.messagePickAndDiscardCard(game, this.nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(0), this.server.messageGetPowerUpCardColour(game, this.nickName).get(0));
            System.out.println("Your spawn point is " + spawnColour + "\n");
        }
        else {
            String spawnColour = this.server.messageGetPowerUpCardColour(game, this.nickName).get(0);
            this.server.messagePickAndDiscardCard(game, this.nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(1), this.server.messageGetPowerUpCardColour(game, this.nickName).get(1));
            System.out.println("Your spawn point is " + spawnColour + "\n");
        }

        if(server.getType(game) != 0)
            this.setType(server.getType(game));     //in case it has not been set during AskNameAndColour
    }

    @Override
    public void action1() throws RemoteException {
        Scanner in = new Scanner(System.in);
        String action;
        System.out.println("Your status:\n" + this.server.messageCheckYourStatus(game, nickName));

        System.out.println("\n---------- START OF " + this.nickName + "'s FIRST ACTION ----------\n");
        while(true) {
            System.out.println("Choose the first action you want to do (Move, Shoot, Grab):");
            action = in.nextLine();
            if((action.equals("Move") || action.equals("Shoot") || action.equals("Grab")
                    || action.equals("move") || action.equals("shoot") || action.equals("grab")))
                break;
            else
                System.out.println(ERRORRETRY);
        }

        if(action.equals("Move") || action.equals("move"))
            this.moveFirstAction();
        else if(action.equals("Shoot") || action.equals("shoot"))
            this.shootFirstAction();
        else if(action.equals("Grab") || action.equals("grab"))
            this.grabFirstAction();
    }

    private void moveFirstAction() throws RemoteException {
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        boolean x;
        List<Integer> l = new LinkedList<>();
        System.out.println("The AmmoCards on the Board are as below:\n" + this.server.messageShowCardsOnBoard(game));

        System.out.println("Enter the sequence of movements you want to do, one integer at a time, up to 3\n" +
                DIRECTIONS + "\n" +
                "Enter 0 to finish");
        while(true) {
            System.out.println("Next direction (integer):");
            int n = intScan.nextInt();
            if(n == 0 && this.server.messageIsValidFirstActionMove(game, nickName, l)) {
                break;
            }
            else if(n == 0 && !this.server.messageIsValidFirstActionMove(game, nickName, l)) {
                System.out.println(ERRORRETRY);
                l.clear();
                x = exitHandler(in);
                if (x) {
                    action1();
                    return;
                }
            }
            else {
                l.add(n);
            }
        }
        this.server.messageFirstActionMove(game, this.nickName, l);
    }

    private void shootFirstAction() throws RemoteException {
            String inputReminder = "Below are the relevant strings you must enter for this card, with respect to any possible order of effects as " +
                    "described in the manual.\nIn brackets is the additional ammo cost for certain effects and firing modes.\n";
        Scanner in = new Scanner(System.in);
        String s = "";

        while(true) {
            System.out.println("Choose one of these cards to shoot:");
            this.server.messageGetWeaponCardLoaded(game, this.nickName).forEach(System.out::println);
            s = in.nextLine();
            if(this.server.messageIsValidCard(game, nickName, s))
                break;
            else
                System.out.println(ERRORRETRY);
        }
        System.out.println(this.server.messageGetReloadCost(game, s, nickName));
        System.out.println(this.server.messageGetDescriptionWC(game, s, nickName));

        switch(s) {
            case "Cyberblade":
                System.out.println(inputReminder +
                        "basic effect: target in your cell\nshadowstep: direction you want to move to\n" +
                        "slice and dice: different target in your cell [1 yellow]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Electroscythe":
                System.out.println(inputReminder +
                        "basic mode: none\nreaper mode: none [1 red 1 blue]");
                wPrompt.shootToUser2(game, server, nickName, s);
                break;

            case "Flamethrower":
                System.out.println(inputReminder +
                        "basic mode: target 1 move away, and possibly another target 1 more move away in the same direction\n" +
                        "barbecue mode: coordinates of cell of target(s) 1 move away, and possibly those of another cell 1 more more away in the same direction [2 yellow]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Furnace":
                System.out.println(inputReminder +
                        "basic mode: colour of room you can see that isn't your room\ncozy fire mode: coordinates of cell 1 move away");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;

            case "Grenade Launcher":
                System.out.println(inputReminder +
                        "basic effect: target you can see, and possibly the direction you wish to move him in\n" +
                        "extra grenade: coordinates of cell you can see [1 red]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Heatseeker":
                System.out.println(inputReminder +
                        "effect: target you canNOT see");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;

            case "Hellion":
                System.out.println(inputReminder +
                        "basic mode: target you can see at least 1 move away\nnano-tracer mode: as with basic mode [1 red]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Lock Rifle":
                System.out.println(inputReminder +
                        "basic effect: target you can see\nsecond lock: different target you can see [1 red]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Machine Gun":
                System.out.println(inputReminder +
                        "basic effect: 1 or 2 targets you can see\nfocus shot: one of those targets [1 yellow]\n" +
                        "turret tripod: the other of those targets and/or a different target you can see [1 blue]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Plasma Gun":
                System.out.println(inputReminder +
                        "basic effect: target you can see\nphase glide: number of cells you want to move (1 or 2) and the direction(s)\n" +
                        "charged shot: none [1 blue]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Power Glove":
                System.out.println(inputReminder +
                        "basic mode: target 1 move away\n" +
                        "rocket fist mode: coordinates of cell 1 move away, and possibly a target on that cell" +
                        "(you may repeat this once with a cell in the same direction just 1 square away, plus a target on that cell [1 blue]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Railgun":
                System.out.println(inputReminder +
                        "basic mode: target in a cardinal direction\npiercing mode: 1 or 2 targets in a cardinal direction" +
                        "(keep in mind this attack ignores walls)");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;

            case "Rocket Launcher":
                System.out.println(inputReminder +
                        "basic effect: target you can see but not in your cell, and possibly the direction in which to move them\n" +
                        "rocket jump: number of cells you want to move (1 or 2) and the direction(s) [1 blue]" +
                        "fragmenting warhead: none [1 yellow]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Shockwave":
                System.out.println(inputReminder +
                        "basic mode: up to 3 targets in different cells, each 1 move away\ntsunami mode: none [1 yellow]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Shotgun":
                System.out.println(inputReminder +
                        "basic mode: target in your cell, and possibly the direction you want to move them in\n" +
                        "long barrel mode: target 1 move away");
                wPrompt.shootToUser3(game, server, nickName, s);
               break;

            case "Sledgehammer":
                System.out.println(inputReminder +
                        "basic mode: target in your cell\npulverize mode: target in your cell, and possibly the number of squares (1 or 2) you want to move them " +
                        "and the direction(s) [1 red]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "T.H.O.R.":
                System.out.println(inputReminder +
                        "basic effect: target you can see\nchain reaction: target your first target can see [1 blue]\n" +
                        "high voltage: target your second target can see [1 blue]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Tractor Beam":
                System.out.println(inputReminder +
                        "basic mode: target you may or may not see, coordinates of a cell you can see up to 2 squares away from you\n" +
                        "punisher mode: target up to 2 moves away [1 red 1 yellow]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Vortex Cannon":
                System.out.println(inputReminder +
                        "basic effect: target up to 1 move away from the 'vortex', coordinates of the cell the vortex is to be placed in " +
                        "(must not be your cell)\nblack hole: 1 or 2 targets on the vortex or 1 move away from it [1 red]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Whisper":
                System.out.println(inputReminder +
                        "effect: target you can see at least 2 moves away)");
                wPrompt.shootToUser4(game, server, nickName, s);
                break;

            case "ZX-2":
                System.out.println(inputReminder +
                        "basic mode: target you can see\nscanner mode: up to 3 targets you can see");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;
            default: break;
        }
        action1();
    }

    private void grabFirstAction() throws RemoteException{
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        boolean x;
        List<Integer> lD = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();
        String wCard = "";
        String weaponSlot = "";
        String confirm;

        System.out.println("The AmmoCards on the Board are as below:\n" + this.server.messageShowCardsOnBoard(game));

        while(true) {
            System.out.println("If you wish to grab whatever is in your cell (Ammo Card or Weapon Slot), enter 0.\n" +
                    "Otherwise, enter the sequence of movements you want to do, one integer at a time: only one is permitted " +
                    "if you haven't unlocked the Adrenaline move, up to two otherwise\n" +
                    DIRECTIONS + "\n" +
                    "Then, enter 5 to finish.");
            while(intScan.hasNextInt()) {
                int d = intScan.nextInt();
                if (d == 5)
                    break;
                else
                    lD.add(d);
            }

            System.out.println("Would you like to check the WeaponCards of a WeaponSlot? " + YESPROMPT);
            confirm = in.nextLine();
            while(confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y")) {
                System.out.println("Enter the number of the WeaponSlot you want to check (1 up, 2 right, 3 left):");
                int n = intScan.nextInt();
                List<String> lWS = this.server.messageCheckWeaponSlotContents(game, n);

                System.out.println("Below are the cards available in WeaponSlot " + n + ", together with their reload costs:\n");
                for(String s : lWS)
                    System.out.println(s);

                System.out.println("Check some other WeaponSlot? " + YESPROMPT);
                confirm = in.nextLine();
            }

            System.out.println("Do you want to buy a WeaponCard? " + YESPROMPT);
            confirm = in.nextLine();
            if(confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y")) {
                System.out.println("Enter the name of the WeaponCard you wish to buy:");
                wCard = in.nextLine();
                System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                weaponSlot = in.nextLine();

                System.out.println("Enter the colour(s), in order and in all caps, of the required AmmoCube(s) to buy the card; 0 to finish");
                while(true) {
                    String a = in.nextLine();
                    if (a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }

                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn; 0 to finish");
                while(true) {
                    String p = in.nextLine();
                    if (p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }

                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn; 0 to finish");
                while(true) {
                    String c = in.nextLine();
                    if (c.equals("0"))
                        break;
                    else
                        lPC.add(c);
                }
            }

            if(this.server.messageIsValidFirstActionGrab(game, nickName, lD, wCard, weaponSlot, lC, lP, lPC))
                break;

            else {
                System.out.println(ERRORRETRY);
                lD.clear();
                lC.clear();
                lP.clear();
                lPC.clear();
                x = exitHandler(in);
                if(x) {
                    action1();
                    return;
                }
            }
        }
        this.server.messageFirstActionGrab(game, nickName, lD, wCard, lC, lP, lPC);

        if(this.server.messageIsDiscard(game)) {
            System.out.println("Enter the WeaponCard you want to discard");
            String wCDiscard = in.nextLine();
            this.server.messageDiscardWeaponCard(game, nickName, weaponSlot, wCDiscard);
        }
    }

    @Override
    public void action2() throws RemoteException {
        String action;
        Scanner in = new Scanner(System.in);
        System.out.println("Your status:\n" + this.server.messageCheckYourStatus(game, nickName));

        System.out.println("---------- START OF " + this.nickName + "'s SECOND ACTION ----------");
        while (true) {
            System.out.println("Choose the second action you want to do (Move, Shoot, Grab):");
            action = in.nextLine();
            if ((action.equals("Move") || action.equals("Shoot") || action.equals("Grab")
                    || action.equals("move") || action.equals("shoot") || action.equals("grab")))
                break;
            else
                System.out.println(ERRORRETRY);
        }

        if(action.equals("Move") || action.equals("move"))
            this.moveSecondAction();
        if(action.equals("Shoot") || action.equals("shoot"))
            this.shootSecondAction() ;
        if(action.equals("Grab") || action.equals("grab"))
            this.grabSecondAction();
    }

    private void moveSecondAction() throws RemoteException {
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        boolean x;
        List<Integer> l = new LinkedList<>();
        System.out.println("The AmmoCards on the Board are as below:\n" + this.server.messageShowCardsOnBoard(game));

        System.out.println("Enter the sequence of movements you want to do, one integer at a time, up to 3\n" +
                DIRECTIONS + "\n" +
                "Enter 0 to finish");
        while(true) {
            System.out.println("Next direction (integer):");
            int n = intScan.nextInt();
            if(n == 0 && this.server.messageIsValidSecondActionMove(game, nickName, l)) {
                break;
            }
            else if(n == 0 && !this.server.messageIsValidSecondActionMove(game, nickName, l)) {
                System.out.println(ERRORRETRY);
                l.clear();
                x = exitHandler(in);
                if (x) {
                    action2();
                    return;
                }
            }
            else {
                l.add(n);
            }
        }
        this.server.messageSecondActionMove(game, this.nickName, l);
    }

    private void shootSecondAction() throws RemoteException {
        String inputReminder = "Below are the relevant strings you must enter for this card, with respect to any possible order of effects as " +
                "described in the manual.\nIn brackets is the additional ammo cost for certain effects and firing modes.\n";
        Scanner in = new Scanner(System.in);
        String s = "";

        while(true) {
            System.out.println("Choose one of these cards to shoot:");
            this.server.messageGetWeaponCardLoaded(game, this.nickName).forEach(System.out::println);
            s = in.nextLine();
            if(this.server.messageIsValidCard(game, nickName, s))
                break;
            else
                System.out.println(ERRORRETRY);
        }
        System.out.println(this.server.messageGetReloadCost(game, s, nickName));
        System.out.println(this.server.messageGetDescriptionWC(game, s, nickName));

        switch(s) {
            case "Cyberblade":
                System.out.println(inputReminder +
                        "basic effect: target in your cell\nshadowstep: direction you want to move to\n" +
                        "slice and dice: different target in your cell [1 yellow]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Electroscythe":
                System.out.println(inputReminder +
                        "basic mode: none\nreaper mode: none [1 red 1 blue]");
                wPrompt.shoot2ToUser2(game, server, nickName, s);
                break;

            case "Flamethrower":
                System.out.println(inputReminder +
                        "basic mode: target 1 move away, and possibly another target 1 more move away in the same direction\n" +
                        "barbecue mode: coordinates of cell of target(s) 1 move away, and possibly those of another cell 1 more more away in the same direction [2 yellow]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Furnace":
                System.out.println(inputReminder +
                        "basic mode: colour of room you can see that isn't your room\ncozy fire mode: coordinates of cell 1 move away");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;

            case "Grenade Launcher":
                System.out.println(inputReminder +
                        "basic effect: target you can see, and possibly the direction you wish to move him in\n" +
                        "extra grenade: coordinates of cell you can see [1 red]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Heatseeker":
                System.out.println(inputReminder +
                        "effect: target you canNOT see");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;

            case "Hellion":
                System.out.println(inputReminder +
                        "basic mode: target you can see at least 1 move away\nnano-tracer mode: as with basic mode [1 red]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Lock Rifle":
                System.out.println(inputReminder +
                        "basic effect: target you can see\nsecond lock: different target you can see [1 red]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Machine Gun":
                System.out.println(inputReminder +
                        "basic effect: 1 or 2 targets you can see\nfocus shot: one of those targets [1 yellow]\n" +
                        "turret tripod: the other of those targets and/or a different target you can see [1 blue]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Plasma Gun":
                System.out.println(inputReminder +
                        "basic effect: target you can see\nphase glide: number of cells you want to move (1 or 2) and the direction(s)\n" +
                        "charged shot: none [1 blue]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Power Glove":
                System.out.println(inputReminder +
                        "basic mode: target 1 move away\n" +
                        "rocket fist mode: coordinates of cell 1 move away, and possibly a target on that cell" +
                        "(you may repeat this once with a cell in the same direction just 1 square away, plus a target on that cell [1 blue]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Railgun":
                System.out.println(inputReminder +
                        "basic mode: target in a cardinal direction\npiercing mode: 1 or 2 targets in a cardinal direction" +
                        "(keep in mind this attack ignores walls)");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;

            case "Rocket Launcher":
                System.out.println(inputReminder +
                        "basic effect: target you can see but not in your cell, and possibly the direction in which to move them\n" +
                        "rocket jump: number of cells you want to move (1 or 2) and the direction(s) [1 blue]" +
                        "fragmenting warhead: none [1 yellow]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Shockwave":
                System.out.println(inputReminder +
                        "basic mode: up to 3 targets in different cells, each 1 move away\ntsunami mode: none [1 yellow]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Shotgun":
                System.out.println(inputReminder +
                        "basic mode: target in your cell, and possibly the direction you want to move them in\n" +
                        "long barrel mode: target 1 move away");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;

            case "Sledgehammer":
                System.out.println(inputReminder +
                        "basic mode: target in your cell\npulverize mode: target in your cell, and possibly the number of squares (1 or 2) you want to move them " +
                        "and the direction(s) [1 red]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "T.H.O.R.":
                System.out.println(inputReminder +
                        "basic effect: target you can see\nchain reaction: target your first target can see [1 blue]\n" +
                        "high voltage: target your second target can see [1 blue]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Tractor Beam":
                System.out.println(inputReminder +
                        "basic mode: target you may or may not see, coordinates of a cell you can see up to 2 squares away from you\n" +
                        "punisher mode: target up to 2 moves away [1 red 1 yellow]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Vortex Cannon":
                System.out.println(inputReminder +
                        "basic effect: target up to 1 move away from the 'vortex', coordinates of the cell the vortex is to be placed in " +
                        "(must not be your cell)\nblack hole: 1 or 2 targets on the vortex or 1 move away from it [1 red]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Whisper":
                System.out.println(inputReminder +
                        "effect: target you can see at least 2 moves away)");
                wPrompt.shoot2ToUser4(game, server, nickName, s);
                break;

            case "ZX-2":
                System.out.println(inputReminder +
                        "basic mode: target you can see\nscanner mode: up to 3 targets you can see");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;
            default: break;
        }
        action2();
    }

    private void grabSecondAction() throws RemoteException {
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        boolean x;
        List<Integer> lD = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();
        String wCard = "";
        String weaponSlot = "";
        String confirm;

        System.out.println("The AmmoCards on the Board are as below:\n" + this.server.messageShowCardsOnBoard(game));

        while(true) {
            System.out.println("If you wish to grab whatever is in your cell (Ammo Card or Weapon Slot), enter 0.\n" +
                    "Otherwise, enter the sequence of movements you want to do, one integer at a time: only one is permitted " +
                    "if you haven't unlocked the Adrenaline move, up to two otherwise\n" +
                    DIRECTIONS + "\n" +
                    "Then, enter 5 to finish");
            while(intScan.hasNextInt()) {
                int d = intScan.nextInt();
                if (d == 5)
                    break;
                else
                    lD.add(d);
            }

            System.out.println("Would you like to check the WeaponCards of a WeaponSlot? " + YESPROMPT);
            confirm = in.nextLine();
            while(confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y")) {
                System.out.println("Enter the number of the WeaponSlot you want to check (1 up, 2 right, 3 left):");
                int n = intScan.nextInt();
                List<String> lWS = this.server.messageCheckWeaponSlotContents(game, n);

                System.out.println("Below are the cards available in WeaponSlot " + n + ", together with their reload costs:\n");
                for(String s : lWS)
                    System.out.println(s);

                System.out.println("Check some other WeaponSlot? " + YESPROMPT);
                confirm = in.nextLine();
            }

            System.out.println("Do you want to buy a WeaponCard? " + YESPROMPT);
            confirm = in.nextLine();
            if(confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y")) {
                System.out.println("Enter the name of the WeaponCard you wish to buy:");
                wCard = in.nextLine();
                System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                weaponSlot = in.nextLine();

                System.out.println("Enter the colour(s), in order and in all caps, of the required AmmoCube(s) to buy the card; 0 to finish");
                while(true) {
                    String a = in.nextLine();
                    if (a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }

                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn; 0 to finish");
                while(true) {
                    String p = in.nextLine();
                    if (p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }

                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn; 0 to finish");
                while(true) {
                    String c = in.nextLine();
                    if (c.equals("0"))
                        break;
                    else
                        lPC.add(c);
                }
            }

            if(this.server.messageIsValidSecondActionGrab(game, nickName, lD, wCard, weaponSlot, lC, lP, lPC))
                break;

            else {
                System.out.println(ERRORRETRY);
                lD.clear();
                lC.clear();
                lP.clear();
                lPC.clear();
                x = exitHandler(in);
                if(x) {
                    action2();
                    return;
                }
            }
        }
        this.server.messageSecondActionGrab(game, nickName, lD, wCard, lC, lP, lPC);

        if(this.server.messageIsDiscard(game)) {
            System.out.println("Enter the WeaponCard you want to discard");
            String wCDiscard = in.nextLine();
            this.server.messageDiscardWeaponCard(game, nickName, weaponSlot, wCDiscard);
        }
    }

    @Override
    public boolean doYouWantToUsePUC() throws RemoteException {
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to use a PowerUpCard now?");
        String confirm = in.next();
        return (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y"));
    }

    @Override
    public void usePowerUpCard() throws RemoteException {
        Scanner in = new Scanner(System.in);
        boolean x;
        String namePC;
        String colourPC;
        List<String> lS = new LinkedList<>();

        System.out.println("Enter which PowerUpCard you want to use. You have the following:");
        for(int i = 0; i < this.server.messageGetPowerUpCard(game, nickName).size(); i++) {
            System.out.println(this.server.messageGetPowerUpCard(game, nickName).get(i) + COLOURED + this.server.messageGetPowerUpCardColour(game, nickName).get(i));
        }

        namePC = in.nextLine();

        System.out.println("Enter the colour of the chosen PowerUpCard:");
        colourPC = in.nextLine();
        this.server.messageGetDescriptionPUC(game, namePC, colourPC, nickName);

        switch(namePC) {
            case "Tagback Grenade":
                while(true) {
                    System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                    lS.add(in.nextLine());

                    if(this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, null))
                        break;
                    else {
                        System.out.println(ERRORRETRY);
                        lS.clear();
                        x = exitHandler(in);
                        if (x)
                            return;
                    }
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;

            case "Targeting Scope":
                Colour c = null;

                while(true) {
                    System.out.println("Enter the nickname of one or more players you have damaged; 0 to finish");
                    while(true) {
                        String p = in.nextLine();
                        if (p.equals("0"))
                            break;
                        else
                            lS.add(p);
                    }

                    System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                    c = Colour.valueOf(in.nextLine());

                    if(this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, c))
                        break;
                    else {
                        System.out.println(ERRORRETRY);
                        lS.clear();
                        x = exitHandler(in);
                        if(x)
                            return;
                    }
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, c);
                break;

            case "Newton":
                while(true) {
                    System.out.println("Enter the nickname of a player:");
                    lS.add(in.nextLine());

                    System.out.println("Enter the direction(s) in which you want the enemy to go; 0 to finish");
                    while(true) {
                        String s = in.next();
                        if(s.equals("0"))
                            break;
                        else
                            lS.add(in.next());
                    }

                    if(this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, null)) {
                        break;
                    }
                    else {
                        System.out.println(ERRORRETRY);
                        lS.clear();
                        x = exitHandler(in);
                        if(x)
                            return;
                    }
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;

            case "Teleporter":
                while(true) {
                    System.out.println("Enter the coordinates of the cell you want to move to:");
                    lS.add(in.next());
                    lS.add(in.next());

                    if(this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, null))
                        break;
                    else {
                        System.out.println(ERRORRETRY);
                        lS.clear();
                        x = exitHandler(in);
                        if(x)
                            return;
                    }
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;
        }

    }
    
    @Override
    public void reload() throws RemoteException {
        Scanner in = new Scanner(System.in);
        this.server.messageGetWeaponCardUnloaded(game, this.nickName).forEach(System.out::println);
        int i = 0;

        while(i == 0) {
            System.out.println("Choose the weapon card you want to reload, or enter 'end' if you don't need/want to");
            String s = in.nextLine();
            if(s.equals("end"))
                break;

            System.out.println("Enter 0 if you want to reload another card, otherwise 1");
            i = in.nextInt();
            if(this.server.messageIsValidReload(game, this.nickName, s))
                this.server.messageReload(game, this.nickName, s, i);
            else
                System.out.println("You can't reload now");
        }
    }

    @Override
    public void scoring() throws RemoteException {
        if(this.server.messageIsValidScoring(game)) {
            System.out.println("Scoring this turn...");
            this.server.messageScoring(game);
        }
        else
            System.out.println("No scoring yet");
    }

    @Override
    public void newSpawnPoint() throws RemoteException {
        Scanner in = new Scanner(System.in);

        if(this.server.messageGetDeadList(game).contains(this.nickName)) {
            String p = "";
            String c = "";

            while(true) {
                System.out.println("Enter the PowerUp card you want to discard; its colour will be your new spawn point:");
                p = in.nextLine();

                System.out.println("Enter the colour of the chosen PowerUp card:");
                c = in.nextLine();

                if(this.server.messageIsValidDiscardCardForSpawnPoint(game, this.nickName, p, c))
                    break;
                else
                    System.out.println(ERRORRETRY);

            }
            this.server.messageDiscardCardForSpawnPoint(game, this.nickName, p, c);
        }
    }

    @Override
    public void replace() throws RemoteException {
        if(this.server.messageIsValidToReplace(game)) {
            System.out.println("Replacing...");
            this.server.messageReplace(game);
            System.out.println("Your turn has ended. Wait for other players to play their turn.");
        }
        else {
            System.out.println("It's not time to replace yet.");
            System.out.println("Your turn has ended. Wait for other players to play their turn.");
        }
    }


    @Override
    public void finalFrenzyTurn()throws RemoteException{
        Scanner in = new Scanner(System.in);
        List<String> l = new LinkedList<>();
        System.out.println("This is the final turn. Final frenzy mode activated.\n" +
                "Choose the action(s) you want to do according to the fact you are before or after the player who started the game.");
        while (in.hasNext())
            l.add(in.next());
        while(!this.server.messageIsValidFinalFrenzyAction(game, nickName, l)){
            System.out.println(ERRORRETRY);
            System.out.println("This is the final turn. Final frenzy mode activated.\n" +
                    "Choose the moves you want to do according to the fact you are before or after the player who started the game.");
            while (in.hasNext())
                l.add(in.next());
        }
        for(String s : l){
            switch (s) {
                case "1":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    System.out.println("Write the direction you want to move:");
                    int i = in.nextInt();
                    System.out.println("Write the card(s) you want to reload: "+this.server.messageGetWeaponCardUnloaded(game, nickName));
                    List<String> lW = new LinkedList<>();
                    while (in.hasNext())
                        lW.add(in.next());
                    System.out.println("Write the card you want to use: "+this.server.messageGetPlayerWeaponCard(game, nickName));
                    String wC = in.next();
                    List<Integer> lI = new LinkedList<>();
                    List<String> lS = new LinkedList<>();
                    List<Colour> lC = new LinkedList<>();
                    List<String> lP = new LinkedList<>();
                    List<String> lPC = new LinkedList<>();
                    System.out.println("Enter the number of the effect you want to use:");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("Enter the relevant strings for the card:");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect:");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    this.server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
                    System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                    while (in.hasNext())
                        lPC.add(in.next());
                    while(!this.server.messageIsValidFinalFrenzyAction1(game, nickName, i, wC, lI, lS, lC, lP, lPC)){
                        System.out.println(ERRORRETRY);
                        System.out.println("Write the direction you want to move:");
                        i = in.nextInt();
                        System.out.println("Write the card(s) you want to reload: "+this.server.messageGetWeaponCardUnloaded(game, nickName));
                        while (in.hasNext())
                            lW.add(in.next());
                        System.out.println("Write the card you want to use: "+this.server.messageGetPlayerWeaponCard(game, nickName));
                        wC = in.next();
                        System.out.println("Enter the number of the effect you want to use:");
                        while (in.hasNext())
                            lI.add(in.nextInt());
                        System.out.println("Enter the relevant strings for the card:");
                        while (in.hasNext())
                            lS.add(in.next());
                        System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect:");
                        while (in.hasNext())
                            lC.add(Colour.valueOf(in.next()));
                        server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                        while (in.hasNext())
                            lP.add(in.next());
                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                        while (in.hasNext())
                            lPC.add(in.next());
                    }
                    this.server.messageFinalFrenzyAction1(game, nickName, i, lW, wC, lI, lS, lC, lP, lPC);
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                case "2":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    System.out.println("Write the direction(s) you want to move:");
                    List<Integer> list = new LinkedList<>();
                    while(in.hasNext())
                        list.add(in.nextInt());
                     while(!this.server.messageIsValidFinalFrenzyAction2(game, nickName, list)) {
                         System.out.println(ERRORRETRY);
                         System.out.println("Write the direction(s) you want to move:");
                         while (in.hasNext())
                             list.add(in.nextInt());
                     }
                    this.server.messageFinalFrenzyAction2(game, nickName, list);
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                case "3":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    List<Integer> list2 = new LinkedList<>();
                    List<Colour> lC2 = new LinkedList<>();
                    List<String> lP2 = new LinkedList<>();
                    List<String> lPC2 = new LinkedList<>();
                    String wCard;
                    String weaponSlot = null;
                    System.out.println("Write the direction(s) you want to move:");
                    while(in.hasNext())
                        list2.add(in.nextInt());
                    System.out.println("If you want, enter a WeaponCard to buy:");
                    wCard = in.next();
                    if(!wCard.equals("")) {
                        System.out.println("Enter the number of the WeaponSlot from which you want to buy the card (1 up, 2 right, 3 left):");
                        weaponSlot = in.next();
                        System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                        while ((in.hasNext()))
                            lC2.add(Colour.valueOf(in.next()));
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                        while ((in.hasNext()))
                            lP2.add(in.next());
                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                        while ((in.hasNext()))
                            lPC2.add(in.next());
                    }
                    while(!this.server.messageIsValidFinalFrenzyAction3(game, nickName, list2, wCard, weaponSlot, lC2, lP2, lPC2)){
                        System.out.println(ERRORRETRY);
                        System.out.println("Write the direction(s) you want to move:");
                        while(in.hasNext())
                            list2.add(in.nextInt());
                        System.out.println("If you want, enter a WeaponCard to buy:");
                        wCard = in.next();
                        if(!wCard.equals("")) {
                            System.out.println("Enter the number of the WeaponSlot from which you want to buy the card (1 up, 2 right, 3 left):");
                            weaponSlot = in.next();
                            System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                            while ((in.hasNext()))
                                lC2.add(Colour.valueOf(in.next()));
                            System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                            while ((in.hasNext()))
                                lP2.add(in.next());
                            System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                            while ((in.hasNext()))
                                lPC2.add(in.next());
                        }
                    }
                    this.server.messageFinalFrenzyAction3(game, nickName, list2, wCard, lC2, lP2, lPC2);
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                case "4":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    System.out.println("Write the direction(s) you want to move:");
                    List<Integer> list3 = new LinkedList<>();
                    while(in.hasNext())
                        list3.add(in.nextInt());
                    System.out.println("Write the card(s) you want to reload: "+this.server.messageGetWeaponCardUnloaded(game, nickName));
                    List<String> lW2 = new LinkedList<>();
                    while (in.hasNext())
                        lW2.add(in.next());
                    System.out.println("Write the card you want to use: "+this.server.messageGetPlayerWeaponCard(game, nickName));
                    String wC2 = in.next();
                    List<Integer> lI2 = new LinkedList<>();
                    List<String> lS2 = new LinkedList<>();
                    List<Colour> lC3 = new LinkedList<>();
                    List<String> lP3 = new LinkedList<>();
                    List<String> lPC3 = new LinkedList<>();
                    System.out.println("Enter the number of the effect(s) you want to use:");
                    while (in.hasNext())
                        lI2.add(in.nextInt());
                    System.out.println("Enter the relevant strings for the card:");
                    while (in.hasNext())
                        lS2.add(in.next());
                    System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect, if necessary:");
                    while (in.hasNext())
                        lC3.add(Colour.valueOf(in.next()));
                    server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
                    System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                    while (in.hasNext())
                        lP3.add(in.next());
                    System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                    while ((in.hasNext()))
                        lPC3.add(in.next());
                    while(!this.server.messageIsValidFinalFrenzyAction4(game, nickName, list3, wC2, lI2, lS2, lC3, lP3, lPC3)) {
                        System.out.println(ERRORRETRY);
                        System.out.println("Write the direction(s) you want to move:");
                        while (in.hasNext())
                            list3.add(in.nextInt());
                        System.out.println("Write the card(s) you want to reload: " + this.server.messageGetWeaponCardUnloaded(game, nickName));
                        while (in.hasNext())
                            lW2.add(in.next());
                        System.out.println("Write the card you want to use: " + this.server.messageGetPlayerWeaponCard(game, nickName));
                        wC2 = in.next();
                        System.out.println("Enter the number of the effect(s) you want to use:");
                        while (in.hasNext())
                            lI2.add(in.nextInt());
                        System.out.println("Enter the relevant strings for the card:");
                        while (in.hasNext())
                            lS2.add(in.next());
                        System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect, if necessary:");
                        while (in.hasNext())
                            lC3.add(Colour.valueOf(in.next()));
                        server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                        while (in.hasNext())
                            lP3.add(in.next());
                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                        while ((in.hasNext()))
                            lPC3.add(in.next());
                    }
                    this.server.messageFinalFrenzyAction4(game, nickName, list3, lW2, wC2, lI2, lS2, lC3, lP3, lPC3);
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                case "5":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    weaponSlot = null;
                    List<Colour> lC4= new LinkedList<>();
                    List<String> lP4= new LinkedList<>();
                    List<String> lPC4 = new LinkedList<>();
                    System.out.println("Write the direction(s) you want to move:");
                    List<Integer> list4 = new LinkedList<>();
                    while(in.hasNext())
                        list4.add(in.nextInt());
                    System.out.println("Enter the WeaponCard you want to buy, if you want:");
                    wCard = in.next();
                    if(!wCard.equals("")) {
                        System.out.println("Enter the number of the WeaponSlot from which you want to buy the card (1 up, 2 right, 3 left):");
                        weaponSlot = in.next();
                        System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                        while ((in.hasNext()))
                            lC4.add(Colour.valueOf(in.next()));
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                        while ((in.hasNext()))
                            lP4.add(in.next());
                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                        while ((in.hasNext()))
                            lPC4.add(in.next());
                    }
                    while(!this.server.messageIsValidFinalFrenzyAction5(game, nickName, list4, wCard, weaponSlot, lC4, lP4, lPC4)){
                        System.out.println(ERRORRETRY);
                        System.out.println("Write the direction(s) you want to move:");
                        while(in.hasNext())
                            list4.add(in.nextInt());
                        System.out.println("Enter the WeaponCard you want to buy, if you want:");
                        wCard = in.next();
                        if(!wCard.equals("")) {
                            System.out.println("Enter the number of the WeaponSlot from which you want to buy the card (1 up, 2 right, 3 left):");
                            weaponSlot = in.next();
                            System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                            while ((in.hasNext()))
                                lC4.add(Colour.valueOf(in.next()));
                            System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                            while ((in.hasNext()))
                                lP4.add(in.next());
                            System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                            while ((in.hasNext()))
                                lPC4.add(in.next());
                        }
                    }
                    this.server.messageFinalFrenzyAction5(game, nickName, list4, wCard, lC4, lP4, lPC4);
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;
                default: break;
            }
        }
        this.server.messageFinalFrenzyTurnScoring(game);
    }

    @Override
    public void endFinalFrenzy()throws RemoteException {
        this.server.messageEndTurnFinalFrenzy(game);
        System.out.println("Final Frenzy finished: calculating the result...");
    }

    @Override
    public void finalScoring()throws RemoteException {
        this.server.messageFinalScoring(game);
        System.out.println("\n\n---------- FINAL SCOREBOARD ----------\n");
        for(int i = 0; i < this.server.messageGetPlayers(game).size(); i++) {
            System.out.println(this.server.messageGetPlayers(game).get(i) + "           " + this.server.messageGetScore(game).get(i));
        }
        System.out.println("The game is over. Thanks for playing!");
    }


    @Override
    public void printPlayer(List<String> information) {
        System.out.println("Player " + information.get(0) + " (identifier " + information.get(2)+ ") whose colour is " + information.get(1) + " is now a player of this game.");
    }

    @Override
    public void printScore(List<String> information) {
        System.out.println("Player " + information.get(0) + "'s current score is " + information.get(1));
    }

    @Override
    public void printPosition(List<String> information) {
        System.out.println("Player " + information.get(0) + " is now in cell " + information.get(1) + " " + information.get(2));
    }

    @Override
    public void printMark(List<String> information) {
        System.out.println("Player " + information.get(0) + "has given a new mark to player " + information.get(1));
    }

    @Override
    public void printDamage(List<String> information) {
        System.out.println("Player " + information.get(0) + " has dealt " + information.get(1) + " damage to player " + information.get(2));
    }

    @Override
    public void printType() {
        System.out.println("The type of the arena is: " + type);
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    public boolean exitHandler(Scanner in) {
        System.out.println(EXITSTRING + YESPROMPT);
        String exitInput = in.next();
        return (exitInput.equals("Yes") || exitInput.equals("yes") || exitInput.equals("y"));
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}