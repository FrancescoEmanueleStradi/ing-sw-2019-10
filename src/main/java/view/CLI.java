package view;

import model.Colour;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLI extends UnicastRemoteObject implements View {

    private int game;
    private int identifier;
    private ServerInterface server;
    private String nickName;
    private Colour colour;
    private CLIWeaponPrompt wPrompt = new CLIWeaponPrompt();
    private String errorRetry = "Error: please retry";
    private String directions = "1 = north, 2 = east, 3 = south, 4 = west";

    CLI(int game, ServerInterface server) throws RemoteException{
        super();
        this.game = game;
        this.server = server;
    }

    @Override
    public View  getView() {
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
    public void setIdentifier(int identifier) throws RemoteException{
        this.identifier = identifier;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void setInformation(int identifier) throws RemoteException{
        this.nickName = server.getSuspendedName(game, identifier);
        this.colour = server.getSuspendedColour(game, this.nickName);
        this.identifier = identifier;
    }

    @Override
    public void disconnected(int disconnected) throws RemoteException, InterruptedException{
        System.out.println("Player number " + disconnected + " is disconnected");
    }

    @Override
    public void askNameAndColour() throws RemoteException {
        String yourName = "Enter your name:";
        String yourColour = "Enter your colour in all caps (YELLOW, BLUE, GREEN, PURPLE, BLACK):";
        Scanner in = new Scanner(System.in);
        if (this.server.messageGameIsNotStarted(game)) {
            System.out.println(yourName);
            this.nickName = in.nextLine();
            server.setNickName(this.game, this.identifier, this.nickName);
            System.out.println(yourColour);
            String s1 = in.nextLine();
            this.colour = Colour.valueOf(s1);
            this.server.messageGameStart(game, nickName, colour);
            System.out.println("Choose the type of arena (1, 2, 3, 4):");
            int type = in.nextInt();
            while (!this.server.messageIsValidReceiveType(game, type)){
                System.out.println(errorRetry);
                System.out.println("Choose the type of arena (1, 2, 3, 4):");
                type = in.nextInt();
            }
            this.server.messageReceiveType(game, type);
            System.out.println("\n---------GENERATING ARENA...---------\n");
            return;
        }
        System.out.println("\n---------WAITING FOR PLAYERS TO JOIN---------\n");
        System.out.println(yourName);
        this.nickName = in.nextLine();
        System.out.println(yourColour);
        String s2 = in.nextLine();
        server.setNickName(this.game, this.identifier, this.nickName);
        this.colour = Colour.valueOf(s2);
        while (!this.server.messageIsValidAddPlayer(game, this.nickName, this.colour)) {
            System.out.println(errorRetry);
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
    public void selectSpawnPoint() throws RemoteException{
        Scanner in = new Scanner(System.in);
        this.server.messageGiveTwoPUCard(game, this.nickName);
        String p;
        String c;
        System.out.println("The following are " + this.nickName +"'s starting PowerUpCards");
        System.out.println(this.server.messageGetPowerUpCard(game, this.nickName).get(0) + " coloured " + this.server.messageGetPowerUpCardColour(game, this.nickName).get(0));
        System.out.println(this.server.messageGetPowerUpCard(game, this.nickName).get(1) + " coloured " + this.server.messageGetPowerUpCardColour(game, this.nickName).get(1));
        System.out.println("\n---------SPAWN POINT SELECT---------\n");
        while (true) {
            System.out.println("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                    "colour of your spawn point");
            p = in.nextLine();
            System.out.println("Enter the colour of that card:");
            c = in.nextLine();
            if (this.server.messageIsValidPickAndDiscard(game, this.nickName, p, c))
                break;
            else
                System.out.println(errorRetry);
        }
            if (this.server.messageGetPowerUpCard(game, this.nickName).get(0).equals(p) && this.server.messageGetPowerUpCardColour(game, this.nickName).get(0).equals(c))
                this.server.messagePickAndDiscardCard(game, this.nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(0), this.server.messageGetPowerUpCardColour(game, this.nickName).get(0));
            else
                this.server.messagePickAndDiscardCard(game, this.nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(1), this.server.messageGetPowerUpCardColour(game, this.nickName).get(1));
    }

    @Override
    public void action1() throws RemoteException{
        Scanner in = new Scanner(System.in);
        String action;
        System.out.println("\n---------START OF " + this.nickName + "'s FIRST ACTION---------\n");
        while (true) {
            System.out.println("Choose the first action you want to do (Move, Shoot, Grab):");
            action = in.nextLine();
            if ((action.equals("Move") || action.equals("Shoot") || action.equals("Grab")
                    || action.equals("move") || action.equals("shoot") || action.equals("grab")))
                break;
            else
                System.out.println(errorRetry);
        }
        if (action.equals("Move") || action.equals("move"))
            this.moveFirstAction();
        if (action.equals("Shoot") || action.equals("shoot"))
            this.shootFirstAction();
        if (action.equals("Grab") || action.equals("grab"))
            this.grabFirstAction();
    }

    private void moveFirstAction() throws RemoteException{
        Scanner in = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        System.out.println("Enter the sequence of movements you want to do, one integer at a time, up to 3\n" +
                directions + "\n" +
                "Press 0 to finish");
        while(true) {
            System.out.println("Next int:");
            int n = in.nextInt();
            if (n == 0 && this.server.messageIsValidFirstActionMove(game, nickName, l)) {
                break;
            }
            else if (n == 0 && !this.server.messageIsValidFirstActionMove(game, nickName, l)) {
                System.out.println(errorRetry);
                l.clear();
            }
            else {
                l.add(n);
            }
        }
        this.server.messageFirstActionMove(game, this.nickName, l);
    }

    private void shootFirstAction() throws RemoteException {
        String inputReminder = "Below are the relevant strings you must enter for this card, in order of effects as shown in the manual.\n" +
                "In brackets is the additional ammo cost for certain effects and modes.\n";
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot:");
        this.server.messageGetWeaponCardLoaded(game, this.nickName).forEach(System.out::println);
        String s = in.nextLine();
        while (!this.server.messageIsValidCard(game, nickName, s)) {
            System.out.println("Error: choose one of these cards to shoot:");
            this.server.messageGetWeaponCardLoaded(game, this.nickName).forEach(System.out::println);
            s = in.nextLine();
        }
        System.out.println(this.server.messageGetReloadCost(game, s, nickName));
        System.out.println(this.server.messageGetDescriptionWC(game, s, nickName));

        switch(s){
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
    }

    private void grabFirstAction() throws RemoteException{
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        List<Integer> lD = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();
        String wCard = "";
        String weaponSlot = "";
        String confirm;
        while (true) {
            System.out.println("If you wish to grab whatever is in your cell, enter 0\n" +
                    "Otherwise, enter the sequence of movements you want to do, one integer at a time: only one is permitted " +
                    "if you haven't unlocked the Adrenaline move, up to two otherwise\n" +
                    directions + "\n" +
                    "Enter 5 to finish");
            while (intScan.hasNextInt()) {
                int d = intScan.nextInt();
                if (d == 5)
                    break;
                else
                    lD.add(d);
            }
            System.out.println("Would you like to check the WeaponCards of a WeaponSlot? (Yes/yes/y)");
            confirm = in.nextLine();
            while (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y")) {
                System.out.println("Enter the number of the WeaponSlot you want to check:");
                int n = intScan.nextInt();
                List<String> lWS = this.server.messageCheckWeaponSlotContents(game, n);
                System.out.println("The cards available in WeaponSlot " + n + " are:\n" + lWS.get(0) + "\n" + lWS.get(1) + "\n" + lWS.get(2) +
                        "\nCheck some other WeaponSlot? (Yes/yes/y)");
                confirm = in.nextLine();
            }
            in.nextLine();
            System.out.println("Do you want to buy a WeaponCard instead of grabbing ammo? (Yes/yes/y)");
            confirm = in.nextLine();
            if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y")) {
                System.out.println("Enter the name of the WeaponCard you wish to buy:");
                wCard = in.nextLine();
                System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                weaponSlot = in.nextLine();
                System.out.println("Enter the colour(s), in order and in all caps, of the required AmmoCube(s) to buy the card,\n" +
                        "or 0 if not necessary");
                while (in.hasNext()) {
                    String a = in.nextLine();
                    if (a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }
                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn, or 0 if not necessary");
                while (in.hasNext()) {
                    String p = in.nextLine();
                    if (p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }
                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn, or 0 if not necessary");
                while (in.hasNext()) {
                    String c = in.nextLine();
                    if (c.equals("0"))
                        break;
                    else
                        lPC.add(c);
                }
            }
            if (this.server.messageIsValidFirstActionGrab(game, nickName, lD, wCard, weaponSlot, lC, lP, lPC))
                break;
            else {
                System.out.println(errorRetry);
                lD.clear();
                lC.clear();
                lP.clear();
                lPC.clear();
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
    public void action2() throws RemoteException{
        System.out.println("---------START OF " + this.nickName + "'s SECOND ACTION---------");
        String action;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Choose the second action you want to do (Move, Shoot, Grab):");
            action = in.nextLine();
            if ((action.equals("Move") || action.equals("Shoot") || action.equals("Grab")
                    || action.equals("move") || action.equals("shoot") || action.equals("grab")))
                break;
            else
                System.out.println(errorRetry);
        }
        if(action.equals("Move") || action.equals("move"))
            this.moveSecondAction();
        if(action.equals("Shoot") || action.equals("shoot"))
            this.shootSecondAction() ;
        if(action.equals("Grab") || action.equals("grab"))
            this.grabSecondAction();
    }

    private void moveSecondAction() throws RemoteException{
        Scanner in = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        System.out.println("Enter the sequence of movements you want to do, one integer at a time, up to 3\n" +
                directions + "\n" +
                "Press 0 to finish");
        while (true) {
            System.out.println("Next int:");
            int n = in.nextInt();
            if (n == 0 && this.server.messageIsValidSecondActionMove(game, nickName, l)) {
                break;
            }
            else if (n == 0 && !this.server.messageIsValidSecondActionMove(game, nickName, l)) {
                System.out.println(errorRetry);
                l.clear();
            }
            else {
                l.add(n);
            }
        }
        this.server.messageSecondActionMove(game, this.nickName, l);
    }

    private void shootSecondAction() throws RemoteException{
        String inputReminder = "Below are the relevant strings you must enter for this card, in order of effects as shown in the manual.\n" +
                "In brackets is the additional ammo cost for certain effects and modes.\n";
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot: ");
        this.server.messageGetWeaponCardLoaded(game, this.nickName).forEach(System.out::println);
        String s = in.next();
        while(!this.server.messageIsValidCard(game, nickName, s)){
            System.out.println("Error: choose one of these cards to shoot: ");
            this.server.messageGetWeaponCardLoaded(game, this.nickName).forEach(System.out::println);
            s = in.next();
        }
        System.out.println(this.server.messageGetReloadCost(game, s, nickName));
        System.out.println(this.server.messageGetDescriptionWC(game, s,nickName));

        switch(s){
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
    }

    private void grabSecondAction() throws RemoteException{
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        List<Integer> lD = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();
        String wCard = "";
        String weaponSlot = "";
        String confirm;
        while (true) {
            System.out.println("If you wish to grab whatever is in your cell, enter 0\n" +
                    "Otherwise, enter the sequence of movements you want to do, one integer at a time: only one is permitted " +
                    "if you haven't unlocked the Adrenaline move, up to two otherwise\n" +
                    "1 = north, 2 = east, 3 = south, 4 = west\n" +
                    "Enter 5 to finish");
            while (intScan.hasNextInt()) {
                int d = intScan.nextInt();
                if (d == 5)
                    break;
                else
                    lD.add(d);
            }
            System.out.println("Would you like to check the WeaponCards of a WeaponSlot? (Yes/yes/y)");
            confirm = in.nextLine();
            while (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y")) {
                System.out.println("Enter the number of the WeaponSlot you want to check:");
                int n = intScan.nextInt();
                List<String> lWS = this.server.messageCheckWeaponSlotContents(game, n);
                System.out.println("The cards available in WeaponSlot " + n + " are:\n" + lWS.get(0) + "\n" + lWS.get(1) + "\n" + lWS.get(2) +
                        "\nCheck some other WeaponSlot? (Yes/yes/y)");
                confirm = in.nextLine();
            }
            in.nextLine();
            System.out.println("Do you want to buy a WeaponCard instead of grabbing ammo? (Yes/yes/y)");
            confirm = in.nextLine();
            if (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y")) {
                System.out.println("Enter the name of the WeaponCard you wish to buy:");
                wCard = in.nextLine();
                System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                weaponSlot = in.nextLine();
                System.out.println("Enter the colour(s), in order and in all caps, of the required AmmoCube(s) to buy the card,\n" +
                        "or 0 if not necessary");
                while (in.hasNext()) {
                    String a = in.nextLine();
                    if (a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }
                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn, or 0 if not necessary");
                while (in.hasNext()) {
                    String p = in.nextLine();
                    if (p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }
                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn, or 0 if not necessary");
                while (in.hasNext()) {
                    String c = in.nextLine();
                    if (c.equals("0"))
                        break;
                    else
                        lPC.add(c);
                }
            }
            if (this.server.messageIsValidSecondActionGrab(game, nickName, lD, wCard, weaponSlot, lC, lP, lPC))
                break;
            else {
                System.out.println(errorRetry);
                lD.clear();
                lC.clear();
                lP.clear();
                lPC.clear();
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
    public boolean doYouWantToUsePUC() throws RemoteException{
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to use the PowerUpCard now?");
        String confirm = in.next();
        return (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y"));
    }

    @Override
    public void usePowerUpCard() throws RemoteException{
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        String namePC;
        String colourPC;
        List<String> lS = new LinkedList<>();
        System.out.println("Enter which PowerUpCard you want to use:");
        this.server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
        namePC = in.nextLine();
        System.out.println("Enter the colour of the PowerUpCard:");
        colourPC = in.nextLine();
        this.server.messageGetDescriptionPUC(game, namePC, colourPC, nickName);
        switch (namePC){
            case "Tagback Grenade":
                System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                lS.add(in.nextLine());
                while(!this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, null)){
                    System.out.println(errorRetry);
                    System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                    lS.add(in.nextLine());
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;

            case "Targeting Scope":
                System.out.println("Enter the nickname of one or more players you have damaged:");
                while(in.hasNext())
                    lS.add(in.nextLine());
                System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                Colour c = Colour.valueOf(in.nextLine());
                while(!this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, c)){
                    System.out.println(errorRetry);
                    System.out.println("Enter the nickname of one or more players you have damaged:");
                    while(in.hasNext())
                        lS.add(in.nextLine());
                    System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                    c = Colour.valueOf(in.nextLine());
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, c);
                break;

            case "Newton":
                System.out.println("Enter the nickname of a player:");
                lS.add(in.nextLine());
                System.out.println("Enter the direction(s) in which you want the enemy to go:");
                while(in.hasNext())
                    lS.add(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, null)){
                    System.out.println("Error: please retry");
                    System.out.println("Enter the nickname of a player:");
                    lS.add(in.next());
                    System.out.println("Enter the direction(s) in which you want the enemy to go:");
                    while(in.hasNext())
                        lS.add(in.next());
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;

            case "Teleporter":
                System.out.println("Enter the coordinates of the cell you want to move to (x y):");
                lS.add(in.next());
                lS.add(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, null)) {
                    System.out.println(errorRetry);
                    System.out.println("Enter the coordinates of the cell you want to move:");
                    lS.add(in.next());
                    lS.add(in.next());
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;
        }

    }
    
    @Override
    public void reload() throws RemoteException{
        Scanner in = new Scanner(System.in);
        this.server.messageGetWeaponCardUnloaded(game, this.nickName).forEach(System.out::println);
        int i = 0;
        while (i == 0) {
            System.out.println("Choose the weapon card you want to reload, or 'end' if you don't need/want to");
            String s = in.nextLine();
            if (s.equals("end"))
                break;
            System.out.println("Enter 0 if you want to reload another card, otherwise 1");
            i = in.nextInt();
            if (this.server.messageIsValidReload(game, this.nickName, s))
                this.server.messageReload(game, this.nickName, s, i);
            else
                System.out.println("You can't reload now: pay attention to the rules! (you can find the manual inside the box)");
        }
    }

    @Override
    public void scoring() throws RemoteException{
        if(this.server.messageIsValidScoring(game))
            this.server.messageScoring(game);
        else
            System.out.println("It is not time for scoring: pay attention to the rule! (you can find the rule book near the board you bought)");
    }

    @Override
    public void newSpawnPoint() throws RemoteException{
        if(this.server.messageGetDeadList(game).contains(this.nickName)) {
            System.out.println("Enter the PowerUp card you want to discard:");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            System.out.println("Enter the colour of the PowerUp:");
            String c = in.nextLine();
            while(!this.server.messageIsValidDiscardCardForSpawnPoint(game, this.nickName, s, c)){
                System.out.println("Enter the PowerUp card you want to discard:");
                s = in.nextLine();
                System.out.println("Enter the colour of the PowerUp:");
                c = in.nextLine();
            }
            this.server.messageDiscardCardForSpawnPoint(game, this.nickName, s, c);
        }
    }

    @Override
    public void replace() throws RemoteException{
        if(this.server.messageIsValidToReplace(game))
            this.server.messageReplace(game);
        else
            System.out.println("Time for replacing has not come yet: pay attention to the rule! (you can find the rule book near the board you bought)");
    }


    @Override
    public void finalFrenzyTurn()throws RemoteException{
        Scanner in = new Scanner(System.in);
        List<String> l = new LinkedList<>();
        System.out.println("This is the final turn. final frenzy mode:\nchoose the moves you want to do according to the fact you are before or after the player who started the game");
        while (in.hasNext())
            l.add(in.next());
        while(!this.server.messageIsValidFinalFrenzyAction(game, nickName, l)){
            System.out.println(errorRetry);
            System.out.println("This is the final turn. final frenzy mode:\nchoose the moves you want to do according to the fact you are before or after the player who started the game");
            while (in.hasNext())
                l.add(in.next());
        }
        for(String s : l){
            switch (s) {
                case "1":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    System.out.println("write the direction you want to move");
                    int i = in.nextInt();
                    System.out.println("Write the card(s) you want to reload:"+this.server.messageGetWeaponCardUnloaded(game, nickName));
                    List<String> lW = new LinkedList<>();
                    while (in.hasNext())
                        lW.add(in.next());
                    System.out.println("Write the card you want to use:"+this.server.messageGetWeaponCard(game, nickName));
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
                        System.out.println(errorRetry);
                        System.out.println("write the direction you want to move");
                        i = in.nextInt();
                        System.out.println("Write the card(s) you want to reload:"+this.server.messageGetWeaponCardUnloaded(game, nickName));
                        while (in.hasNext())
                            lW.add(in.next());
                        System.out.println("Write the card you want to use:"+this.server.messageGetWeaponCard(game, nickName));
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
                    System.out.println("write the direction(s) you want to move");
                    List<Integer> list = new LinkedList<>();
                    while(in.hasNext())
                        list.add(in.nextInt());
                     while(!this.server.messageIsValidFinalFrenzyAction2(game, nickName, list)) {
                         System.out.println(errorRetry);
                         System.out.println("write the direction(s) you want to move");
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
                    System.out.println("write the direction(s) you want to move");
                    while(in.hasNext())
                        list2.add(in.nextInt());
                    System.out.println("Enter the WeaponCard you want to buy, if you want:");
                    wCard = in.next();
                    if(!wCard.equals("")) {
                        System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                        weaponSlot = in.next();
                        System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                        while ((in.hasNext()))
                            lC2.add(Colour.valueOf(in.next()));
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                        while ((in.hasNext()))
                            lP2.add(in.next());
                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                        while ((in.hasNext()))
                            lPC2.add(in.next());
                    }
                    while(!this.server.messageIsValidFinalFrenzyAction3(game, nickName, list2, wCard, weaponSlot, lC2, lP2, lPC2)){
                        System.out.println(errorRetry);
                        System.out.println("write the direction(s) you want to move");
                        while(in.hasNext())
                            list2.add(in.nextInt());
                        System.out.println("Enter the WeaponCard you want to buy, if you want:");
                        wCard = in.next();
                        if(!wCard.equals("")) {
                            System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                            weaponSlot = in.next();
                            System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                            while ((in.hasNext()))
                                lC2.add(Colour.valueOf(in.next()));
                            System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                            while ((in.hasNext()))
                                lP2.add(in.next());
                            System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
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
                    System.out.println("write the direction(s) you want to move");
                    List<Integer> list3 = new LinkedList<>();
                    while(in.hasNext())
                        list3.add(in.nextInt());
                    System.out.println("Write the card(s) you want to reload:"+this.server.messageGetWeaponCardUnloaded(game, nickName));
                    List<String> lW2 = new LinkedList<>();
                    while (in.hasNext())
                        lW2.add(in.next());
                    System.out.println("Write the card you want to use:"+this.server.messageGetWeaponCard(game, nickName));
                    String wC2 = in.next();
                    List<Integer> lI2 = new LinkedList<>();
                    List<String> lS2 = new LinkedList<>();
                    List<Colour> lC3 = new LinkedList<>();
                    List<String> lP3 = new LinkedList<>();
                    List<String> lPC3 = new LinkedList<>();
                    System.out.println("Enter the number of the effect you want to use:");
                    while (in.hasNext())
                        lI2.add(in.nextInt());
                    System.out.println("Enter the relevant strings for the card:");
                    while (in.hasNext())
                        lS2.add(in.next());
                    System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect:");
                    while (in.hasNext())
                        lC3.add(Colour.valueOf(in.next()));
                    server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
                    System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                    while (in.hasNext())
                        lP3.add(in.next());
                    System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                    while ((in.hasNext()))
                        lPC3.add(in.next());
                    while(!this.server.messageIsValidFinalFrenzyAction4(game, nickName, list3, wC2, lI2, lS2, lC3, lP3, lPC3)) {
                        System.out.println("Error: repeat");
                        System.out.println("write the direction(s) you want to move");
                        while (in.hasNext())
                            list3.add(in.nextInt());
                        System.out.println("Write the card(s) you want to reload:" + this.server.messageGetWeaponCardUnloaded(game, nickName));
                        while (in.hasNext())
                            lW2.add(in.next());
                        System.out.println("Write the card you want to use:" + this.server.messageGetWeaponCard(game, nickName));
                        wC2 = in.next();
                        System.out.println("Enter the number of the effect you want to use:");
                        while (in.hasNext())
                            lI2.add(in.nextInt());
                        System.out.println("Enter the relevant strings for the card:");
                        while (in.hasNext())
                            lS2.add(in.next());
                        System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect:");
                        while (in.hasNext())
                            lC3.add(Colour.valueOf(in.next()));
                        server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                        while (in.hasNext())
                            lP3.add(in.next());
                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
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
                    System.out.println("write the direction(s) you want to move");
                    List<Integer> list4 = new LinkedList<>();
                    while(in.hasNext())
                        list4.add(in.nextInt());
                    System.out.println("Enter the WeaponCard you want to buy, if you want:");
                    wCard = in.next();
                    if(!wCard.equals("")) {
                        System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                        weaponSlot = in.next();
                        System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                        while ((in.hasNext()))
                            lC4.add(Colour.valueOf(in.next()));
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                        while ((in.hasNext()))
                            lP4.add(in.next());
                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                        while ((in.hasNext()))
                            lPC4.add(in.next());
                    }
                    while(!this.server.messageIsValidFinalFrenzyAction5(game, nickName, list4, wCard, weaponSlot, lC4, lP4, lPC4)){
                        System.out.println("Error: repeat");
                        System.out.println("write the direction(s) you want to move");
                        while(in.hasNext())
                            list4.add(in.nextInt());
                        System.out.println("Enter the WeaponCard you want to buy, if you want:");
                        wCard = in.next();
                        if(!wCard.equals("")) {
                            System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                            weaponSlot = in.next();
                            System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                            while ((in.hasNext()))
                                lC4.add(Colour.valueOf(in.next()));
                            System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                            while ((in.hasNext()))
                                lP4.add(in.next());
                            System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
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
    public void endFinalFrenzy()throws RemoteException{
        this.server.messageEndTurnFinalFrenzy(game);
        System.out.println("We are calculating the result");
    }

    @Override
    public void finalScoring()throws RemoteException{
        this.server.messageFinalScoring(game);
        System.out.println("FINAL SCORE");
        this.server.messageGetPlayers(game).forEach(System.out::print);
        System.out.println();
        this.server.messageGetScore(game).forEach(System.out::print);
        System.out.println();
        System.out.println("END GAME");
    }


    @Override
    public void printScore(List<String> information) throws RemoteException{
        System.out.println("Player: " + information.get(0) + " has now this score: " + information.get(1));
    }

    @Override
    public void printPosition(List<String> information) throws RemoteException{
        System.out.println("Now Player: " + information.get(0) + " is in the cell " + information.get(1) + " " + information.get(2));
    }

    @Override
    public void printMark(List<String> information) throws RemoteException{
        System.out.println("Player: " + information.get(0) + "give a new Mark to Player" + information.get(1));
    }

    @Override
    public void printDamage(List<String> information) throws RemoteException{
        System.out.println("Player: " + information.get(0) + " give " + information.get(1) + " damages to Player: " + information.get(2));
    }

    @Override
    public void printType(int type) throws RemoteException{
        System.out.println("Type of the grid is: " + type);
    }
}