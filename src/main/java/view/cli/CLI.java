package view.cli;

import model.Colour;
import network.ServerInterface;
import view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * RMI view class for CLI.
 */
public class CLI extends UnicastRemoteObject implements View {

    private int game;
    private int identifier;
    private int type = 0;
    private transient ServerInterface server;
    private String nickName;
    private Colour colour;
    private static CLIWeaponPrompt wPrompt = new CLIWeaponPrompt();
    private static final String inputReminder = "Below are the relevant strings (marked by capital letters) you must enter for this card,\nwith respect to any possible order of effects as " +
            "described in the manual. The number of the effect is in brackets (). The order of the sub-effects MUST be respected.\nIn brackets is the additional ammo cost for certain effects and firing modes.\n" +
            "Also in brackets is the OPTIONAL tag for certain sub-effects, which MUST receive an empty string,\nor 0 in case of a direction, " +
            "should they not be used.\n";
    private static final String ERRORRETRY = "Error: please retry";
    private static final String COLOURED = " coloured ";
    private static final String DIRECTIONS = "1 = north, 2 = east, 3 = south, 4 = west";
    static final String EXITSTRING = "Do you want to go back and change action?";
    static final String YESPROMPT = "(Yes/yes/y)";

    /**
     * Creates a new CLI.
     *
     * @param game   game
     * @param server server
     * @throws RemoteException RMI exception
     */
    public CLI(int game, ServerInterface server) throws RemoteException {
        super();
        this.game = game;
        this.server = server;
    }

    public View getView() {
        return this;
    }

    public int getGame() {              //for the test
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getNickName() {
        return nickName;
    }

    public void setInformation(int identifier) throws RemoteException {
        this.nickName = server.getSuspendedName(game, identifier);
        this.colour = server.getSuspendedColour(game, this.nickName);
        this.identifier = identifier;
    }

    public void disconnected(int disconnected) {
        System.out.println("Player with identifier " + disconnected + " has disconnected from the game.");
    }

    public void askNameAndColour() throws RemoteException {
        String yourName = "Enter your name:";
        String yourColour = "Enter your colour in all caps (choose between BLACK, BLUE, GREEN, PURPLE or YELLOW):";
        Scanner in = new Scanner(System.in);

        if(this.server.messageGameIsNotStarted(game) && this.identifier == 1) {
            System.out.println("\n---------- NAME AND COLOUR SELECTION ----------\n");

            System.out.println(yourName);
            this.nickName = in.nextLine();
            server.setNickName(this.game, this.identifier, this.nickName);

            String s1;

            int counterColour = 0;
            do {
                if(counterColour > 0)
                    System.out.println("Invalid colour, please retry.");

                System.out.println(yourColour);
                s1 = in.nextLine();

                counterColour++;
            }while(!(s1.equals("BLUE") || s1.equals("BLACK") || s1.equals("YELLOW") || s1.equals("PURPLE") || s1.equals("GREEN")));

            this.server.messageGameStart(game, nickName, colour);

            int typeInput;
            int counterType = 0;

            do {
                if(counterType > 0)
                    System.out.println(ERRORRETRY);

                System.out.println("Choose the type of arena (1, 2, 3, 4):");
                typeInput = in.nextInt();
                counterType++;
            } while(!this.server.messageIsValidReceiveType(game, typeInput));

            this.server.messageReceiveType(game, typeInput);

            System.out.println("\n---------- GENERATING ARENA... ----------\n");
            this.setType(server.getType(game));
            return;
        }

        System.out.println("\n---------- NAME AND COLOUR SELECTION ----------\n");

        int isValidAddPlayer;
        String s2;

        do {
            System.out.println(yourName);
            this.nickName = in.nextLine();
            server.setNickName(this.game, this.identifier, this.nickName);

            int counterColour = 0;
            do {
                if(counterColour > 0)
                    System.out.println("Invalid colour, please retry.");

                System.out.println(yourColour);
                s2 = in.nextLine();

                counterColour++;
            }while(!(s2.equals("BLUE") || s2.equals("BLACK") || s2.equals("YELLOW") || s2.equals("PURPLE") || s2.equals("GREEN")));

            this.colour = Colour.valueOf(s2);

            isValidAddPlayer = this.server.messageIsValidAddPlayer(game, this.nickName, this.colour);
            if(isValidAddPlayer == 0)
                System.out.println("The first player have not chosen his name and colour yet. Please wait for it and retry.");
            else if(isValidAddPlayer == 1)
                System.out.println("The nickname you have chosen is already picked. Choose another one and try again.");
            else if(isValidAddPlayer == 2)
                System.out.println("The colour you have chosen is already picked. Choose another one and try again.");

        } while(isValidAddPlayer != 3);

        this.server.messageAddPlayer(game, this.nickName, this.colour);
    }

    public void selectSpawnPoint() throws RemoteException {
        Scanner in = new Scanner(System.in);
        String p;
        String c;

        //To retrieve the type of arena selected from the first player
        if(identifier != 1)
            this.setType(server.getType(game));

        this.server.messageGiveTwoPUCard(game, this.nickName);

        System.out.println("The following are " + this.nickName +"'s starting PowerUpCards:");
        System.out.println(this.server.messageGetPlayerPowerUpCard(game, this.nickName).get(0) + COLOURED + this.server.messageGetPlayerPowerUpCardColour(game, this.nickName).get(0));
        System.out.println(this.server.messageGetPlayerPowerUpCard(game, this.nickName).get(1) + COLOURED + this.server.messageGetPlayerPowerUpCardColour(game, this.nickName).get(1));

        System.out.println("\n---------- SPAWN POINT SELECTION ----------\n");
        while(true) {
            System.out.println("Enter the name of the PowerUp card you want to keep.\n" +
                            "You will discard the other one, and its colour will be the colour of your spawn point.");
            p = in.nextLine();

            int counterPUCCOlour = 0;
            do {
                if(counterPUCCOlour > 0)
                    System.out.println("Invalid colour, please retry.");

                System.out.println("Enter the colour of the chosen PowerUp card:");
                c = in.nextLine();

                counterPUCCOlour++;
            }while(!(c.equals("RED") || c.equals("YELLOW") || c.equals("BLUE")));

            if(this.server.messageIsValidPickAndDiscard(game, this.nickName, p, c))
                break;
            else
                System.out.println(ERRORRETRY);
        }

        if(this.server.messageGetPlayerPowerUpCard(game, this.nickName).get(0).equals(p) && this.server.messageGetPlayerPowerUpCardColour(game, this.nickName).get(0).equals(c)) {
            String spawnColour = this.server.messageGetPlayerPowerUpCardColour(game, this.nickName).get(1);
            this.server.messagePickAndDiscardCard(game, this.nickName, this.server.messageGetPlayerPowerUpCard(game, this.nickName).get(0), this.server.messageGetPlayerPowerUpCardColour(game, this.nickName).get(0));
            System.out.println("Your spawn point is " + spawnColour + "\n");
        }
        else {
            String spawnColour = this.server.messageGetPlayerPowerUpCardColour(game, this.nickName).get(0);
            this.server.messagePickAndDiscardCard(game, this.nickName, this.server.messageGetPlayerPowerUpCard(game, this.nickName).get(1), this.server.messageGetPlayerPowerUpCardColour(game, this.nickName).get(1));
            System.out.println("Your spawn point is " + spawnColour + "\n");
        }
    }

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

    public void moveFirstAction() throws RemoteException {
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
                if(x) {
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

    public void shootFirstAction() throws RemoteException {
        Scanner in = new Scanner(System.in);
        String s;
        System.out.println(this.server.messageCheckOthersStatus(game, this.nickName));

        while(true) {
            System.out.println("Choose one of the cards below to use. Make sure you have the required ammo at least for the basic effect/mode\n");
            this.server.messageGetPlayerWeaponCardLoaded(game, this.nickName).forEach(System.out::println);
            s = in.nextLine();

            if(this.server.messageIsValidCard(game, nickName, s))
                break;
            else
                System.out.println(ERRORRETRY);
        }
        System.out.println(this.server.messageGetPlayerReloadCost(game, s, nickName));
        System.out.println(this.server.messageGetPlayerDescriptionWC(game, s, nickName));

        switch(s) {
            case "Cyberblade":
                System.out.println(inputReminder +
                        "basic effect (1): TARGET in your cell\n" +
                        "shadowstep (2): DIRECTION you want to move to\n" +
                        "slice and dice (3) [1 yellow]: DIFFERENT TARGET in your cell");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Electroscythe":
                System.out.println(inputReminder +
                        "basic mode (1): none\n" +
                        "reaper mode (2) [1 red 1 blue]: none");
                wPrompt.shootToUser2(game, server, nickName, s);
                break;

            case "Flamethrower":
                System.out.println(inputReminder +
                        "basic mode (1): 1. TARGET 1 move away\n " +
                        "            2. [OPTIONAL] ANOTHER TARGET 1 more move away in the same direction\n" +
                        "barbecue mode (2) [2 yellow]: 1. COORDINATES of cell of target(s) 1 move away\n" +
                        "                          2. [OPTIONAL] COORDINATES of another cell 1 more more away in the same direction");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Furnace":
                System.out.println(inputReminder +
                        "basic mode (1): COLOUR of room you can see that isn't your room\n" +
                        "cozy fire mode (2): COORDINATES of cell 1 move away");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;

            case "Grenade Launcher":
                System.out.println(inputReminder +
                        "basic effect (1): 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] DIRECTION you wish to move that target in\n" +
                        "extra grenade (2): COORDINATES of cell you can see [1 red]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Heatseeker":
                System.out.println(inputReminder +
                        "effect (1): TARGET you canNOT see");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;

            case "Hellion":
                System.out.println(inputReminder +
                        "basic mode (1): TARGET you can see at least 1 move away\n" +
                        "nano-tracer mode (2) [1 red]: as with basic mode");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Lock Rifle":
                System.out.println(inputReminder +
                        "basic effect (1): TARGET you can see\n" +
                        "second lock (2) [1 red]: DIFFERENT TARGET you can see");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Machine Gun":
                System.out.println(inputReminder +
                        "basic effect (1): 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] ANOTHER TARGET you can see" +
                        "focus shot (2) [1 yellow]: ONE of those TARGETS\n" +
                        "turret tripod (3) [1 blue]: the OTHER of those TARGETS and/or a DIFFERENT TARGET you can see");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Plasma Gun":
                //TODO
                System.out.println(inputReminder +
                        "basic effect (1): TARGET you can see\n" +
                        "phase glide (2): NUMBER of cells you want to move (1 or 2) and the DIRECTION(s)\n" +
                        "charged shot (3) [1 blue]: none");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Power Glove":
                //TODO
                System.out.println(inputReminder +
                        "basic mode (1): TARGET 1 move away\n" +
                        "rocket fist mode (2) [1 blue]: 1. COORDINATES of cell 1 move away\n" +
                        "                           2. (3) [OPTIONAL] TARGET on that cell\n" +
                        "(You may repeat 2. once with a cell in the same direction just 1 square away (4), plus a target on that cell (5)\n" +
                        "This makes for a total of 4 empty strings if only the first target is chosen.)");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Railgun":
                System.out.println(inputReminder +
                        "basic mode (1): TARGET in a cardinal direction\n" +
                        "piercing mode (2): 1. TARGET in a cardinal direction\n" +
                        "               2. [OPTIONAL] ANOTHER TARGET in that direction\n" +
                        "               (keep in mind this mode IGNORES walls)");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;

            case "Rocket Launcher":
                //TODO
                System.out.println(inputReminder +
                        "basic effect (1): 1. TARGET you can see but not in your cell\n" +
                        "              2. (2) [OPTIONAL] DIRECTION in which you want to move them\n" +
                        "rocket jump (3) [1 blue]: number of cells you want to move (1 or 2) and the direction(s)\n" +
                        "fragmenting warhead (4): none [1 yellow]");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Shockwave":
                System.out.println(inputReminder +
                        "basic mode (1): 1. TARGET in some cell\n" +
                        "            2. [OPTIONAL] TARGET in some other cell\n" +
                        "            3. [OPTIONAL] TARGET in yet another cell\n" +
                        "tsunami mode (2) [1 yellow]: none");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Shotgun":
                System.out.println(inputReminder +
                        "basic mode (1): 1. TARGET in your cell\n" +
                        "            2. (2) [OPTIONAL] DIRECTION you want to move them in\n" +
                        "long barrel mode (3): TARGET 1 move away");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;

            case "Sledgehammer":
                System.out.println(inputReminder +
                        "basic mode (1): TARGET in your cell\n" +
                        "pulverize mode (2): 1. TARGET in your cell\n" +
                        "                2. (3) NUMBER of squares (1 or 2) you want to move them and the DIRECTION(s)\n" +
                        "                   (leave 1 empty string if just 1 direction");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "T.H.O.R.":
                System.out.println(inputReminder +
                        "basic effect (1): TARGET you can see\n" +
                        "chain reaction (2) [1 blue]: TARGET your first target can see\n" +
                        "high voltage (3) [1 blue]: TARGET your second target can see");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Tractor Beam":
                System.out.println(inputReminder +
                        "basic mode (1): 1. TARGET you may or may not see\n" +
                        "            2. COORDINATES of a cell you can see up to 2 squares away from you\n" +
                        "punisher mode (2) [1 red 1 yellow]: TARGET up to 2 moves away");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Vortex Cannon":
                System.out.println(inputReminder +
                        "basic effect (1): 1. TARGET up to 1 move away from the 'vortex'\n" +
                        "              2. COORDINATES of the cell the vortex is to be placed in (must not be your cell)\n" +
                        "black hole (2) [1 red]: 1. TARGET on the vortex or 1 move away from it\n" +
                        "                    2. [OPTIONAL] ANOTHER TARGET on the vortex or 1 move away from it");
                wPrompt.shootToUser1(game, server, nickName, s);
                break;

            case "Whisper":
                System.out.println(inputReminder +
                        "effect (1): TARGET you can see at least 2 moves away)");
                wPrompt.shootToUser4(game, server, nickName, s);
                break;

            case "ZX-2":
                System.out.println(inputReminder +
                        "basic mode (1): TARGET you can see\n" +
                        "scanner mode (2): 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] ANOTHER TARGET you can see\n" +
                        "              3. [OPTIONAL] YET ANOTHER TARGET you can see");
                wPrompt.shootToUser3(game, server, nickName, s);
                break;
            default: break;
        }
        action1();
    }

    public void grabFirstAction() throws RemoteException {
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
                if(d == 5)
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
                    if(a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }

                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn; 0 to finish");
                while(true) {
                    String p = in.nextLine();
                    if(p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }

                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn; 0 to finish");
                while(true) {
                    String c = in.nextLine();
                    if(c.equals("0"))
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

    public void action2() throws RemoteException {
        String action;
        Scanner in = new Scanner(System.in);
        System.out.println("Your status:\n" + this.server.messageCheckYourStatus(game, nickName));

        System.out.println("---------- START OF " + this.nickName + "'s SECOND ACTION ----------");
        while(true) {
            System.out.println("Choose the second action you want to do (Move, Shoot, Grab):");
            action = in.nextLine();
            if((action.equals("Move") || action.equals("Shoot") || action.equals("Grab")
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

    public void moveSecondAction() throws RemoteException {
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
                if(x) {
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

    public void shootSecondAction() throws RemoteException {
        Scanner in = new Scanner(System.in);
        String s;
        System.out.println(this.server.messageCheckOthersStatus(game, this.nickName));

        while(true) {
            System.out.println("Choose one of the cards below to use. Make sure you have the required ammo at least for the basic effect/mode\n");
            this.server.messageGetPlayerWeaponCardLoaded(game, this.nickName).forEach(System.out::println);
            s = in.nextLine();

            if(this.server.messageIsValidCard(game, nickName, s))
                break;
            else
                System.out.println(ERRORRETRY);
        }
        System.out.println(this.server.messageGetPlayerReloadCost(game, s, nickName));
        System.out.println(this.server.messageGetPlayerDescriptionWC(game, s, nickName));

        switch(s) {
            case "Cyberblade":
                System.out.println(inputReminder +
                        "basic effect (1): TARGET in your cell\n" +
                        "shadowstep (2): DIRECTION you want to move to\n" +
                        "slice and dice (3) [1 yellow]: DIFFERENT TARGET in your cell");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Electroscythe":
                System.out.println(inputReminder +
                        "basic mode (1): none\n" +
                        "reaper mode (2) [1 red 1 blue]: none");
                wPrompt.shoot2ToUser2(game, server, nickName, s);
                break;

            case "Flamethrower":
                System.out.println(inputReminder +
                        "basic mode (1): 1. TARGET 1 move away\n " +
                        "            2. [OPTIONAL] ANOTHER TARGET 1 more move away in the same direction\n" +
                        "barbecue mode (2) [2 yellow]: 1. COORDINATES of cell of target(s) 1 move away\n" +
                        "                          2. [OPTIONAL] COORDINATES of another cell 1 more more away in the same direction");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Furnace":
                System.out.println(inputReminder +
                        "basic mode (1): COLOUR of room you can see that isn't your room\n" +
                        "cozy fire mode (2): COORDINATES of cell 1 move away");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;

            case "Grenade Launcher":
                System.out.println(inputReminder +
                        "basic effect (1): 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] DIRECTION you wish to move that target in\n" +
                        "extra grenade (2): COORDINATES of cell you can see [1 red]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Heatseeker":
                System.out.println(inputReminder +
                        "effect (1): TARGET you canNOT see");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;

            case "Hellion":
                System.out.println(inputReminder +
                        "basic mode (1): TARGET you can see at least 1 move away\n" +
                        "nano-tracer mode (2) [1 red]: as with basic mode");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Lock Rifle":
                System.out.println(inputReminder +
                        "basic effect (1): TARGET you can see\n" +
                        "second lock (2) [1 red]: DIFFERENT TARGET you can see");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Machine Gun":
                System.out.println(inputReminder +
                        "basic effect (1): 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] ANOTHER TARGET you can see" +
                        "focus shot (2) [1 yellow]: ONE of those TARGETS\n" +
                        "turret tripod (3) [1 blue]: the OTHER of those TARGETS and/or a DIFFERENT TARGET you can see");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Plasma Gun":
                //TODO
                System.out.println(inputReminder +
                        "basic effect (1): TARGET you can see\n" +
                        "phase glide (2): NUMBER of cells you want to move (1 or 2) and the DIRECTION(s)\n" +
                        "charged shot (3) [1 blue]: none");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Power Glove":
                //TODO
                System.out.println(inputReminder +
                        "basic mode (1): TARGET 1 move away\n" +
                        "rocket fist mode (2) [1 blue]: 1. COORDINATES of cell 1 move away\n" +
                        "                           2. (3) [OPTIONAL] TARGET on that cell\n" +
                        "(You may repeat 2. once with a cell in the same direction just 1 square away (4), plus a target on that cell (5)\n" +
                        "This makes for a total of 4 empty strings if only the first target is chosen.)");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Railgun":
                System.out.println(inputReminder +
                        "basic mode (1): TARGET in a cardinal direction\n" +
                        "piercing mode (2): 1. TARGET in a cardinal direction\n" +
                        "               2. [OPTIONAL] ANOTHER TARGET in that direction\n" +
                        "               (keep in mind this mode IGNORES walls)");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;

            case "Rocket Launcher":
                //TODO
                System.out.println(inputReminder +
                        "basic effect (1): 1. TARGET you can see but not in your cell\n" +
                        "              2. (2) [OPTIONAL] DIRECTION in which you want to move them\n" +
                        "rocket jump (3) [1 blue]: number of cells you want to move (1 or 2) and the direction(s)\n" +
                        "fragmenting warhead (4): none [1 yellow]");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Shockwave":
                System.out.println(inputReminder +
                        "basic mode (1): 1. TARGET in some cell\n" +
                        "            2. [OPTIONAL] TARGET in some other cell\n" +
                        "            3. [OPTIONAL] TARGET in yet another cell\n" +
                        "tsunami mode (2) [1 yellow]: none");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Shotgun":
                System.out.println(inputReminder +
                        "basic mode (1): 1. TARGET in your cell\n" +
                        "            2. (2) [OPTIONAL] DIRECTION you want to move them in\n" +
                        "long barrel mode (3): TARGET 1 move away");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;

            case "Sledgehammer":
                System.out.println(inputReminder +
                        "basic mode (1): TARGET in your cell\n" +
                        "pulverize mode (2): 1. TARGET in your cell\n" +
                        "                2. (3) NUMBER of squares (1 or 2) you want to move them and the DIRECTION(s)\n" +
                        "                   (leave 1 empty string if just 1 direction");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "T.H.O.R.":
                System.out.println(inputReminder +
                        "basic effect (1): TARGET you can see\n" +
                        "chain reaction (2) [1 blue]: TARGET your first target can see\n" +
                        "high voltage (3) [1 blue]: TARGET your second target can see");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Tractor Beam":
                System.out.println(inputReminder +
                        "basic mode (1): 1. TARGET you may or may not see\n" +
                        "            2. COORDINATES of a cell you can see up to 2 squares away from you\n" +
                        "punisher mode (2) [1 red 1 yellow]: TARGET up to 2 moves away");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Vortex Cannon":
                System.out.println(inputReminder +
                        "basic effect (1): 1. TARGET up to 1 move away from the 'vortex'\n" +
                        "              2. COORDINATES of the cell the vortex is to be placed in (must not be your cell)\n" +
                        "black hole (2) [1 red]: 1. TARGET on the vortex or 1 move away from it\n" +
                        "                    2. [OPTIONAL] ANOTHER TARGET on the vortex or 1 move away from it");
                wPrompt.shoot2ToUser1(game, server, nickName, s);
                break;

            case "Whisper":
                System.out.println(inputReminder +
                        "effect (1): TARGET you can see at least 2 moves away)");
                wPrompt.shoot2ToUser4(game, server, nickName, s);
                break;

            case "ZX-2":
                System.out.println(inputReminder +
                        "basic mode (1): TARGET you can see\n" +
                        "scanner mode (2): 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] ANOTHER TARGET you can see\n" +
                        "              3. [OPTIONAL] YET ANOTHER TARGET you can see");
                wPrompt.shoot2ToUser3(game, server, nickName, s);
                break;
            default: break;
        }
    }

    public void grabSecondAction() throws RemoteException {
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
                if(d == 5)
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
                    if(a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }

                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn; 0 to finish");
                while(true) {
                    String p = in.nextLine();
                    if(p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }

                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn; 0 to finish");
                while(true) {
                    String c = in.nextLine();
                    if(c.equals("0"))
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

        System.out.println("Your status:\n" + this.server.messageCheckYourStatus(game, nickName));
    }

    public boolean doYouWantToUsePUC() {
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to use a PowerUpCard now?");
        String confirm = in.next();
        return (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y"));
    }

    public void usePowerUpCard() throws RemoteException {
        Scanner in = new Scanner(System.in);
        boolean x;
        String namePC;
        String colourPC;
        List<String> lS = new LinkedList<>();

        System.out.println("Enter which PowerUpCard you want to use. You have the following:");
        for(int i = 0; i < this.server.messageGetPlayerPowerUpCard(game, nickName).size(); i++) {
            System.out.println(this.server.messageGetPlayerPowerUpCard(game, nickName).get(i) + COLOURED + this.server.messageGetPlayerPowerUpCardColour(game, nickName).get(i));
        }

        namePC = in.nextLine();

        System.out.println("Enter the colour of the chosen PowerUpCard:");
        colourPC = in.nextLine();
        this.server.messageGetPlayerDescriptionPUC(game, namePC, colourPC, nickName);

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
                        if(x)
                            return;
                    }
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;

            case "Targeting Scope":
                Colour c;

                while(true) {
                    System.out.println("Enter the nickname of one or more players you have damaged; 0 to finish");
                    while(true) {
                        String p = in.nextLine();
                        if(p.equals("0"))
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
            default: break;
        }

    }

    public void reload() throws RemoteException {
        Scanner in = new Scanner(System.in);
        this.server.messageGetPlayerWeaponCardUnloaded(game, this.nickName).forEach(System.out::println);

        String reloadChoice;

        while(true) {
            System.out.println("Choose the weapon card you want to reload, or enter 'end' if you don't need/want to");
            reloadChoice = in.nextLine();

            if(reloadChoice.equals("end"))
                break;

            if (this.server.messageIsValidReload(game, this.nickName, reloadChoice))
                this.server.messageReload(game, this.nickName, reloadChoice);
            else
                System.out.println("You don't have enough ammo to reload the chosen weapon\n (or you have selected a weapon you don't have: don't try to cheat!");
        }
    }

    public void scoring() throws RemoteException {
        if(this.server.messageIsValidScoring(game)) {
            System.out.println("Scoring this turn...");
            this.server.messageScoring(game);
        }
        else
            System.out.println("No scoring yet");
    }

    public void newSpawnPoint() throws RemoteException {
        Scanner in = new Scanner(System.in);

        if(this.server.messageGetDeadList(game).contains(this.nickName)) {
            String p;
            String c;

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

    public void replace() throws RemoteException {
        System.out.println("Replacing...");
        this.server.messageReplace(game);
        System.out.println("Your turn has ended. Wait for other players to have their turn.");
    }

    public void finalFrenzyTurn() throws RemoteException {
        Scanner in = new Scanner(System.in);
        List<String> l = new LinkedList<>();

        int counterFinalFrenzyAction = 0;

        do {
            if(counterFinalFrenzyAction > 0)
                System.out.println(ERRORRETRY);

            System.out.println("This is the final turn. Final frenzy mode activated.\n" +
                    "Choose the action(s) you want to do according to the fact you are before or after the player who started the game.");
            while(in.hasNext())
                l.add(in.next());

            counterFinalFrenzyAction++;
        } while(!this.server.messageIsValidFinalFrenzyAction(game, nickName, l));

        for(String s : l) {
            switch (s) {
                case "1":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();

                    int i;
                    String wC;
                    List<String> lW = new LinkedList<>();
                    List<Integer> lI = new LinkedList<>();
                    List<String> lS = new LinkedList<>();
                    List<Colour> lC = new LinkedList<>();
                    List<String> lP = new LinkedList<>();
                    List<String> lPC = new LinkedList<>();

                    int counterFinalFrenzyAction1 = 0;

                    do {
                        if(counterFinalFrenzyAction1 > 0)
                            System.out.println(ERRORRETRY);

                        System.out.println("Write the direction you want to move:");
                        i = in.nextInt();

                        System.out.println("Write the card(s) you want to reload: " + this.server.messageGetPlayerWeaponCardUnloaded(game, nickName));
                        while(in.hasNext())
                            lW.add(in.next());

                        System.out.println("Write the card you want to use: " + this.server.messageGetPlayerWeaponCard(game, nickName));
                        wC = in.next();

                        System.out.println("Enter the number of the effect you want to use:");
                        while(in.hasNext())
                            lI.add(in.nextInt());

                        System.out.println("Enter the relevant strings for the card:");
                        while(in.hasNext())
                            lS.add(in.next());

                        System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect:");
                        while(in.hasNext())
                            lC.add(Colour.valueOf(in.next()));

                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                        this.server.messageGetPlayerPowerUpCard(game, nickName).forEach(System.out::println);
                        while(in.hasNext())
                            lP.add(in.next());

                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                        while(in.hasNext())
                            lPC.add(in.next());

                        counterFinalFrenzyAction1++;
                    } while(!this.server.messageIsValidFinalFrenzyAction1(game, nickName, i, wC, lI, lS, lC, lP, lPC));

                    this.server.messageFinalFrenzyAction1(game, nickName, i, lW, wC, lI, lS, lC, lP, lPC);

                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                case "2":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();

                    List<Integer> list = new LinkedList<>();

                    int counterFinalFrenzyAction2 = 0;

                    do {
                        if(counterFinalFrenzyAction2 > 0)
                            System.out.println(ERRORRETRY);

                        System.out.println("Write the direction(s) you want to move:");
                        while(in.hasNext())
                            list.add(in.nextInt());

                        counterFinalFrenzyAction2++;
                    } while(!this.server.messageIsValidFinalFrenzyAction2(game, nickName, list));

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

                    int counterFinalFrenzyAction3 = 0;

                    do {
                        if(counterFinalFrenzyAction3 > 0)
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
                            while((in.hasNext()))
                                lC2.add(Colour.valueOf(in.next()));
                            System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                            while((in.hasNext()))
                                lP2.add(in.next());
                            System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                            while((in.hasNext()))
                                lPC2.add(in.next());
                        }

                        counterFinalFrenzyAction3++;
                    } while(!this.server.messageIsValidFinalFrenzyAction3(game, nickName, list2, wCard, weaponSlot, lC2, lP2, lPC2));

                    this.server.messageFinalFrenzyAction3(game, nickName, list2, wCard, lC2, lP2, lPC2);

                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                case "4":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();

                    List<String> lW2 = new LinkedList<>();
                    List<Integer> list3 = new LinkedList<>();
                    List<Integer> lI2 = new LinkedList<>();
                    List<String> lS2 = new LinkedList<>();
                    List<Colour> lC3 = new LinkedList<>();
                    List<String> lP3 = new LinkedList<>();
                    List<String> lPC3 = new LinkedList<>();
                    String wC2 = in.next();

                    int counterFinalFrenzyAction4 = 0;

                    do {
                        if(counterFinalFrenzyAction4 > 0)
                            System.out.println(ERRORRETRY);

                        System.out.println("Write the direction(s) you want to move:");
                        while(in.hasNext())
                            list3.add(in.nextInt());

                        System.out.println("Write the card(s) you want to reload: " + this.server.messageGetPlayerWeaponCardUnloaded(game, nickName));
                        while(in.hasNext())
                            lW2.add(in.next());

                        System.out.println("Write the card you want to use: " + this.server.messageGetPlayerWeaponCard(game, nickName));

                        System.out.println("Enter the number of the effect(s) you want to use:");
                        while(in.hasNext())
                            lI2.add(in.nextInt());

                        System.out.println("Enter the relevant strings for the card:");
                        while(in.hasNext())
                            lS2.add(in.next());

                        System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect, if necessary:");
                        while(in.hasNext())
                            lC3.add(Colour.valueOf(in.next()));

                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                        server.messageGetPlayerPowerUpCard(game, nickName).forEach(System.out::println);
                        while(in.hasNext())
                            lP3.add(in.next());

                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                        while((in.hasNext()))
                            lPC3.add(in.next());

                        counterFinalFrenzyAction4++;
                    } while(!this.server.messageIsValidFinalFrenzyAction4(game, nickName, list3, wC2, lI2, lS2, lC3, lP3, lPC3));

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
                    List<Integer> list4 = new LinkedList<>();

                    int counterFinalFrenzyAction5 = 0;

                    do {
                        if(counterFinalFrenzyAction5 > 0)
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
                            while((in.hasNext()))
                                lC4.add(Colour.valueOf(in.next()));
                            System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                            while((in.hasNext()))
                                lP4.add(in.next());
                            System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                            while((in.hasNext()))
                                lPC4.add(in.next());
                        }

                        counterFinalFrenzyAction5++;
                    } while(!this.server.messageIsValidFinalFrenzyAction5(game, nickName, list4, wCard, weaponSlot, lC4, lP4, lPC4));

                    this.server.messageFinalFrenzyAction5(game, nickName, list4, wCard, lC4, lP4, lPC4);

                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                default: break;
            }
        }
        this.server.messageFinalFrenzyTurnScoring(game);
    }

    public void endFinalFrenzy() throws RemoteException {
        this.server.messageEndTurnFinalFrenzy(game);
        System.out.println("\nGame finished: calculating the result...");
    }

    public void finalScoring() throws RemoteException {
        this.server.messageFinalScoring(game);

        System.out.println("\n\n---------- FINAL SCOREBOARD ----------\n");
        for(int i = 0; i < this.server.messageGetPlayers(game).size(); i++) {
            System.out.println(this.server.messageGetPlayers(game).get(i) + "\t" + this.server.messageGetScore(game).get(i));
        }

        System.out.println("\nThe game is over. Thanks for playing!\n");
    }


    public void printPlayer(List<String> information) {
        System.out.println("Player " + information.get(0) + " (identifier " + information.get(2)+ ") whose colour is " + information.get(1) + " is now a player of this game.");
    }

    public void printScore(List<String> information) {
        System.out.println("Player " + information.get(0) + "'s current score is " + information.get(1));
    }

    public void printPosition(List<String> information) {
        System.out.println("Player " + information.get(0) + " is now in cell " + information.get(1) + " " + information.get(2));
    }

    public void printMark(List<String> information) {
        System.out.println("Player " + information.get(0) + "has given a new mark to player " + information.get(1));
    }

    public void printDamage(List<String> information) {
        System.out.println("Player " + information.get(0) + " has dealt " + information.get(1) + " damage to player " + information.get(2));
    }

    public void printType() {
        System.out.println("The type of the arena is: " + type);
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * Handles exiting from a method or input loop.
     *
     * @param in scanner
     * @return boolean
     */
    private boolean exitHandler(Scanner in) {
        System.out.println(EXITSTRING + YESPROMPT);
        String exitInput = in.next();
        return (exitInput.equals("Yes") || exitInput.equals("yes") || exitInput.equals("y"));
    }

    public void exit() {
        System.exit(0);
    }
}