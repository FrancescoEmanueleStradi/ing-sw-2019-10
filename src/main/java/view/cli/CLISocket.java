package view.cli;

import model.Colour;
import view.View;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLISocket extends UnicastRemoteObject implements View {

    private int game;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private int identifier;
    private int type = 0;
    private String nickName;
    private Colour colour;
    private static CLISocketWeaponPrompt wPrompt = new CLISocketWeaponPrompt();
    private static final String ERRORRETRY = "Error: please retry";
    private static final String COLOURED = " coloured ";
    private static final String DIRECTIONS = "1 = north, 2 = east, 3 = south, 4 = west";
    private static final String EXITSTRING = "Do you want to go back and change action?";
    private static final String YESPROMPT = "(Yes/yes/y)";

    public CLISocket(int game, Socket socket) throws IOException {
        super();
        this.game = game;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
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

    public void setInformation(int identifier) {
        socketOut.println("Get Suspended Name");
        socketOut.println(game);
        socketOut.println(identifier);
        this.nickName = socketIn.nextLine();

        socketOut.println("Get Suspended Colour");
        socketOut.println(game);
        socketOut.println(this.nickName);
        this.colour = Colour.valueOf(socketIn.nextLine());
        this.identifier = identifier;
    }

    public void disconnected(int disconnected) {
        System.out.println("Player with identifier " + disconnected + " has disconnected from the game.");
    }

    public void askNameAndColour() {
        String yourName = "Enter your name:";
        String yourColour = "Enter your colour in all caps (choose between BLACK, BLUE, GREEN, PURPLE or YELLOW):";
        Scanner in = new Scanner(System.in);

        socketOut.println("Message Game Is Not Started");
        socketOut.println(game);
        String gameIsNotStarted = socketIn.nextLine();

        if(gameIsNotStarted.equals("true") && this.identifier == 1) {
            System.out.println("\n---------- NAME AND COLOUR SELECTION ----------\n");

            System.out.println(yourName);
            this.nickName = in.nextLine();
            socketOut.println("Set Nickname");
            socketOut.println(game);
            socketOut.println(identifier);
            socketOut.println(nickName);

            System.out.println(yourColour);
            String s1 = in.nextLine();
            this.colour = Colour.valueOf(s1);
            socketOut.println("Message Game Start");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(colour.getColourId());

            String isValidReceiveType;
            int counterType = 0;
            int typeInput;

            do {
                if(counterType > 0)
                    System.out.println(ERRORRETRY);

                System.out.println("Choose the type of arena (1, 2, 3, 4):");
                typeInput = in.nextInt();

                socketOut.println("Message Is Valid Receive Type");
                socketOut.println(game);
                socketOut.println(typeInput);

                isValidReceiveType = socketIn.nextLine();
                counterType++;
            } while(isValidReceiveType.equals("false"));

            socketOut.println("Message Receive Type");
            socketOut.println(game);
            socketOut.println(typeInput);

            System.out.println("\n---------- GENERATING ARENA... ----------\n");

            socketOut.println("Get Type");
            socketOut.println(game);
            int typeReceived = Integer.parseInt(socketIn.nextLine());
            this.setType(typeReceived);
            return;
        }

        System.out.println("\n---------- NAME AND COLOUR SELECTION ----------\n");

        int isValidAddPlayer;
        String s2;

        do {
            System.out.println(yourName);
            this.nickName = in.nextLine();
            socketOut.println("Set Nickname");
            socketOut.println(game);
            socketOut.println(identifier);
            socketOut.println(nickName);

            System.out.println(yourColour);
            s2 = in.nextLine();
            this.colour = Colour.valueOf(s2);

            socketOut.println("Message Is Valid Add Player");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(colour.getColourId());

            isValidAddPlayer = Integer.parseInt(socketIn.nextLine());
            if(isValidAddPlayer == 0)
                System.out.println("The first player have not chosen his name and colour yet. Please wait for it and retry.");
            else if(isValidAddPlayer == 1)
                System.out.println("The nickname you have chosen is already picked. Choose another one and try again.");
            else if(isValidAddPlayer == 2)
                System.out.println("The colour you have chosen is already picked. Choose another one and try again.");

        } while(isValidAddPlayer != 3);

        socketOut.println("Message Add Player");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s2);
    }

    public void selectSpawnPoint() throws RemoteException {
        Scanner in = new Scanner(System.in);
        String p;
        String c;

        //To retrieve the type of arena selected from the first player
        if(identifier != 1) {
            socketOut.println("Get Type");
            socketOut.println(game);
            int typeReceived = Integer.parseInt(socketIn.nextLine());
            this.setType(typeReceived);
        }

        socketOut.println("Message Give Two PU Card");
        socketOut.println(game);
        socketOut.println(nickName);

        socketOut.println("Message Get Initial PowerUp Card");
        socketOut.println(game);
        socketOut.println(nickName);
        String pUCard1 = socketIn.nextLine();
        String pUCard2 = socketIn.nextLine();

        socketOut.println("Message Get Initial PowerUp Card Colour");
        socketOut.println(game);
        socketOut.println(nickName);
        String pUCard1Colour = socketIn.nextLine();
        String pUCard2Colour = socketIn.nextLine();

        System.out.println("The following are " + this.nickName + "'s starting PowerUpCards:");
        System.out.println(pUCard1 + COLOURED + pUCard1Colour);
        System.out.println(pUCard2 + COLOURED + pUCard2Colour);

        System.out.println("\n---------- SPAWN POINT SELECTION ----------\n");

        while(true) {
            System.out.println("Enter the name of the PowerUp card you want to keep.\n" +
                    "You will discard the other one, and its colour will be the colour of your spawn point.");
            p = in.nextLine();

            System.out.println("Enter the colour of the chosen PowerUp card:");
            c = in.nextLine();

            socketOut.println("Message Is Valid Pick And Discard");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(p);
            socketOut.println(c);

            String isValidPickAndDiscard = socketIn.nextLine();

            if(isValidPickAndDiscard.equals("true"))
                break;
            else
                System.out.println(ERRORRETRY);
        }

        if(pUCard1.equals(p) && pUCard1Colour.equals(c)) {
            socketOut.println("Message Pick And Discard");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(pUCard1);
            socketOut.println(pUCard1Colour);
            System.out.println("Your spawn point is " + pUCard2Colour + "\n");
        }
        else {
            socketOut.println("Message Pick And Discard");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(pUCard2);
            socketOut.println(pUCard2Colour);
            System.out.println("Your spawn point is " + pUCard1Colour + "\n");
        }
    }

    public void action1() {
        Scanner in = new Scanner(System.in);
        String action;

        socketOut.println("Message Check Your Status");
        socketOut.println(game);
        socketOut.println(nickName);
        String yourStatus = socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" +
                socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" +
                socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine();

        System.out.println("\nYour status:\n" + yourStatus);

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

        try {
            if(action.equals("Move") || action.equals("move"))
                this.moveFirstAction();
            else if(action.equals("Shoot") || action.equals("shoot"))
                this.shootFirstAction();
            else if(action.equals("Grab") || action.equals("grab"))
                this.grabFirstAction();
        } catch(IOException e) {
            System.out.println("I/O Exception");
        }
    }

    private void moveFirstAction() {
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        boolean x;

        socketOut.println("Message Show Cards On Board");
        socketOut.println(game);
        String cardsOnBoard = socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" +
                socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine();

        System.out.println("\nThe AmmoCards on the Board are as below:\n" + cardsOnBoard);

        System.out.println("Enter the sequence of movements you want to do, one integer at a time, up to 3\n" +
                DIRECTIONS + "\n" +
                "Enter 0 to finish");

        while(true) {
            System.out.println("Next direction (integer):");
            int n = intScan.nextInt();

            if(n == 0) {
                socketOut.println("Message Is Valid First Action Move");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(l.size());
                for(Integer i : l)
                    socketOut.println(i);
                String validMove = socketIn.nextLine();

                if(validMove.equals("true"))
                    break;
                else if(validMove.equals("false")) {
                    System.out.println(ERRORRETRY);
                    l.clear();
                    x = exitHandler(in);
                    if(x) {
                        action1();
                        return;
                    }
                }
            }
            else
                l.add(n);
        }

        socketOut.println("Message First Action Move");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(l.size());
        for(Integer i : l)
            socketOut.println(i);
    }

    private void shootFirstAction() throws IOException {
        String inputReminder = "Below are the relevant strings (marked by capital letters) you must enter for this card,\nwith respect to any possible order of effects as " +
                "described in the manual. The order of the sub-effects MUST be respected.\nIn brackets is the additional ammo cost for certain effects and firing modes.\n" +
                "Also in brackets is the OPTIONAL tag for certain sub-effects, which MUST receive an empty string,\nor 0 in case of a direction, " +
                "should they not be used.\n";
        Scanner in = new Scanner(System.in);
        String s;

        while(true) {
            System.out.println("Choose one of the cards below to use. Make sure you have the required ammo at least for the basic effect/mode\n");

            socketOut.println("Message Get Weapon Card Loaded");
            socketOut.println(game);
            socketOut.println(nickName);
            int size = Integer.parseInt(socketIn.nextLine());
            for(int i = 0; i < size; i++)
                System.out.println(socketIn.nextLine());

            s = in.nextLine();

            socketOut.println("Message Is Valid Card");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);

            String isValidCard = socketIn.nextLine();

            if(isValidCard.equals("true"))
                break;
            else
                System.out.println(ERRORRETRY);
        }

        socketOut.println("Message Get Reload Cost");
        socketOut.println(game);
        socketOut.println(s);
        socketOut.println(nickName);
        System.out.println(socketIn.nextLine());

        socketOut.println("Message Get Description WC");
        socketOut.println(game);
        socketOut.println(s);
        socketOut.println(nickName);
        System.out.println(socketIn.nextLine());

        switch(s) {
            case "Cyberblade":
                System.out.println(inputReminder +
                        "basic effect: TARGET in your cell\n" +
                        "shadowstep: DIRECTION you want to move to\n" +
                        "slice and dice [1 yellow]: DIFFERENT TARGET in your cell");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Electroscythe":
                System.out.println(inputReminder +
                        "basic mode: none\n" +
                        "reaper mode [1 red 1 blue]: none");
                wPrompt.shootToUser2(game, socket, nickName, s);
                break;

            case "Flamethrower":
                System.out.println(inputReminder +
                        "basic mode: 1. TARGET 1 move away\n " +
                        "            2. [OPTIONAL] ANOTHER TARGET 1 more move away in the same direction\n" +
                        "barbecue mode [2 yellow]: 1. COORDINATES of cell of target(s) 1 move away\n" +
                        "                          2. [OPTIONAL] COORDINATES of another cell 1 more more away in the same direction");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Furnace":
                System.out.println(inputReminder +
                        "basic mode: COLOUR of room you can see that isn't your room\n" +
                        "cozy fire mode: COORDINATES of cell 1 move away");
                wPrompt.shootToUser3(game, socket, nickName, s);
                break;

            case "Grenade Launcher":
                System.out.println(inputReminder +
                        "basic effect: 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] DIRECTION you wish to move that target in\n" +
                        "extra grenade: COORDINATES of cell you can see [1 red]");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Heatseeker":
                System.out.println(inputReminder +
                        "effect: TARGET you canNOT see");
                wPrompt.shootToUser3(game, socket, nickName, s);
                break;

            case "Hellion":
                System.out.println(inputReminder +
                        "basic mode: TARGET you can see at least 1 move away\n" +
                        "nano-tracer mode [1 red]: as with basic mode");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Lock Rifle":
                System.out.println(inputReminder +
                        "basic effect: TARGET you can see\n" +
                        "second lock [1 red]: DIFFERENT TARGET you can see");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Machine Gun":
                System.out.println(inputReminder +
                        "basic effect: 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] ANOTHER TARGET you can see" +
                        "focus shot [1 yellow]: ONE of those TARGETS\n" +
                        "turret tripod [1 blue]: the OTHER of those TARGETS and/or a DIFFERENT TARGET you can see");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Plasma Gun":
                //TODO
                System.out.println(inputReminder +
                        "basic effect: TARGET you can see\n" +
                        "phase glide: NUMBER of cells you want to move (1 or 2) and the DIRECTION(s)\n" +
                        "charged shot [1 blue]: none");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Power Glove":
                //TODO
                System.out.println(inputReminder +
                        "basic mode: TARGET 1 move away\n" +
                        "rocket fist mode [1 blue]: 1. COORDINATES of cell 1 move away\n" +
                        "                           2. [OPTIONAL] TARGET on that cell\n" +
                        "(You may repeat 2. once with a cell in the same direction just 1 square away, plus a target on that cell\n" +
                        "This makes for a total of 4 empty strings if only the first target is chosen.)");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Railgun":
                System.out.println(inputReminder +
                        "basic mode: TARGET in a cardinal direction\n" +
                        "piercing mode: 1. TARGET in a cardinal direction\n" +
                        "               2. ANOTHER TARGET in that direction\n" +
                        "               (keep in mind this mode IGNORES walls)");
                wPrompt.shootToUser3(game, socket, nickName, s);
                break;

            case "Rocket Launcher":
                //TODO
                System.out.println(inputReminder +
                        "basic effect: 1. TARGET you can see but not in your cell\n" +
                        "              2. [OPTIONAL] DIRECTION in which you want to move them\n" +
                        "rocket jump [1 blue]: number of cells you want to move (1 or 2) and the direction(s)\n" +
                        "fragmenting warhead: none [1 yellow]");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Shockwave":
                System.out.println(inputReminder +
                        "basic mode: 1. TARGET in some cell\n" +
                        "            2. [OPTIONAL] TARGET in some other cell\n" +
                        "            3. [OPTIONAL] TARGET in yet another cell\n" +
                        "tsunami mode [1 yellow]: none");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Shotgun":
                System.out.println(inputReminder +
                        "basic mode: 1. TARGET in your cell\n" +
                        "            2. [OPTIONAL] DIRECTION you want to move them in\n" +
                        "long barrel mode: TARGET 1 move away");
                wPrompt.shootToUser3(game, socket, nickName, s);
                break;

            case "Sledgehammer":
                System.out.println(inputReminder +
                        "basic mode: TARGET in your cell\n" +
                        "pulverize mode: 1. TARGET in your cell\n" +
                        "                2. NUMBER of squares (1 or 2) you want to move them and the DIRECTION(s)\n" +
                        "                   (leave 1 empty string if just 1 direction");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "T.H.O.R.":
                System.out.println(inputReminder +
                        "basic effect: TARGET you can see\n" +
                        "chain reaction [1 blue]: TARGET your first target can see\n" +
                        "high voltage [1 blue]: TARGET your second target can see");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Tractor Beam":
                System.out.println(inputReminder +
                        "basic mode: 1. TARGET you may or may not see\n" +
                        "            2. COORDINATES of a cell you can see up to 2 squares away from you\n" +
                        "punisher mode [1 red 1 yellow]: TARGET up to 2 moves away");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Vortex Cannon":
                System.out.println(inputReminder +
                        "basic effect: 1. TARGET up to 1 move away from the 'vortex'\n" +
                        "              2. COORDINATES of the cell the vortex is to be placed in (must not be your cell)\n" +
                        "black hole [1 red]: 1. TARGET on the vortex or 1 move away from it\n" +
                        "                    2. [OPTIONAL] ANOTHER TARGET on the vortex or 1 move away from it");
                wPrompt.shootToUser1(game, socket, nickName, s);
                break;

            case "Whisper":
                System.out.println(inputReminder +
                        "effect: TARGET you can see at least 2 moves away)");
                wPrompt.shootToUser4(game, socket, nickName, s);
                break;

            case "ZX-2":
                System.out.println(inputReminder +
                        "basic mode: TARGET you can see\n" +
                        "scanner mode: 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] ANOTHER TARGET you can see\n" +
                        "              3. [OPTIONAL] YET ANOTHER TARGET you can see");
                wPrompt.shootToUser3(game, socket, nickName, s);
                break;
            default: break;
        }
        action1();
    }

    private void grabFirstAction() {
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

        socketOut.println("Message Show Cards On Board");
        socketOut.println(game);
        String cardsOnBoard = socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" +
                socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine();

        System.out.println("\nThe AmmoCards on the Board are as below:\n" + cardsOnBoard);

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
                List<String> lWS = new LinkedList<>();

                socketOut.println("Message Check Weapon Slot Contents");
                socketOut.println(game);
                socketOut.println(n);
                int size = Integer.parseInt(socketIn.nextLine());
                for(int i = 0; i < size; i++)
                    lWS.add(socketIn.nextLine());

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

                System.out.println("Enter the colour(s), in order and in all caps, of the required AmmoCube(s) to buy the card, if necessary; 0 to finish");
                while(true) {
                    String a = in.nextLine();
                    if(a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }

                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn, if necessary; 0 to finish");
                while(true) {
                    String p = in.nextLine();
                    if(p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }

                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn, if necessary; 0 to finish");
                while(true) {
                    String c = in.nextLine();
                    if(c.equals("0"))
                        break;
                    else
                        lPC.add(c);
                }
            }

            socketOut.println("Message Is Valid First Action Grab");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(lD.size());
            for(Integer i : lD)
                socketOut.println(i);
            socketOut.println(wCard);
            socketOut.println(weaponSlot);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getColourId());
            socketOut.println(lP.size());
            for(String s : lP)
                socketOut.println(s);
            socketOut.println(lPC.size());
            for(String s : lPC)
                socketOut.println(s);

            String isValidFirstActionGrab = socketIn.nextLine();

            if(isValidFirstActionGrab.equals("true"))
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

        socketOut.println("Message First Action Grab");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(lD.size());
        for(Integer i : lD)
            socketOut.println(i);
        socketOut.println(wCard);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getColourId());
        socketOut.println(lP.size());
        for(String s : lP)
            socketOut.println(s);
        socketOut.println(lPC.size());
        for(String s : lPC)
            socketOut.println(s);

        socketOut.println("Message Is Discard");
        socketOut.println(game);

        String isDiscard = socketIn.nextLine();

        if(isDiscard.equals("true")) {
            System.out.println("Enter the WeaponCard you want to discard");
            String wCDiscard = in.nextLine();

            socketOut.println("Message Discard Weapon Card");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(weaponSlot);
            socketOut.println(wCDiscard);
        }
    }

    public void action2() {
        Scanner in = new Scanner(System.in);
        String action;

        socketOut.println("Message Check Your Status");
        socketOut.println(game);
        socketOut.println(nickName);
        String yourStatus = socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" +
                socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" +
                socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine();

        System.out.println("\nYour status:\n" + yourStatus);

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
        try {
            if(action.equals("Move") || action.equals("move"))
                this.moveSecondAction();
            if(action.equals("Shoot") || action.equals("shoot"))
                this.shootSecondAction();
            if(action.equals("Grab") || action.equals("grab"))
                this.grabSecondAction();
        } catch (IOException e) {
            System.out.println("I/O Exception");
        }
    }

    private void moveSecondAction() {
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        boolean x;

        socketOut.println("Message Show Cards On Board");
        socketOut.println(game);
        String cardsOnBoard = socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" +
                socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine();

        System.out.println("The AmmoCards on the Board are as below:\n" + cardsOnBoard);

        System.out.println("Enter the sequence of movements you want to do, one integer at a time, up to 3\n" +
                DIRECTIONS + "\n" +
                "Enter 0 to finish");

        while(true) {
            System.out.println("Next direction (integer):");
            int n = intScan.nextInt();

            if(n == 0) {
                socketOut.println("Message Is Valid Second Action Move");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(l.size());
                for(Integer i : l)
                    socketOut.println(i);
                String validMove = socketIn.nextLine();

                if(validMove.equals("true"))
                    break;
                else if(validMove.equals("false")) {
                    System.out.println(ERRORRETRY);
                    l.clear();
                    x = exitHandler(in);
                    if(x) {
                        action1();
                        return;
                    }
                }
            }
            else
                l.add(n);
        }

        socketOut.println("Message Second Action Move");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(l.size());
        for(Integer i : l)
            socketOut.println(i);
    }

    private void shootSecondAction() throws IOException {
        String inputReminder = "Below are the relevant strings (marked by capital letters) you must enter for this card,\nwith respect to any possible order of effects as " +
                "described in the manual. The order of the sub-effects MUST be respected.\nIn brackets is the additional ammo cost for certain effects and firing modes.\n" +
                "Also in brackets is the OPTIONAL tag for certain sub-effects, which MUST receive an empty string,\nor 0 in case of a direction, " +
                "should they not be used.\n";
        Scanner in = new Scanner(System.in);
        String s;

        while(true) {
            System.out.println("Choose one of the cards below to use. Make sure you have the required ammo at least for the basic effect/mode\n");

            socketOut.println("Message Get Weapon Card Loaded");
            socketOut.println(game);
            socketOut.println(nickName);
            int size = Integer.parseInt(socketIn.nextLine());
            for(int i = 0; i < size; i++)
                System.out.println(socketIn.nextLine());

            s = in.nextLine();

            socketOut.println("Message Is Valid Card");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);

            String isValidCard = socketIn.nextLine();

            if(isValidCard.equals("true"))
                break;
            else
                System.out.println(ERRORRETRY);
        }

        socketOut.println("Message Get Reload Cost");
        socketOut.println(game);
        socketOut.println(s);
        socketOut.println(nickName);
        System.out.println(socketIn.nextLine());

        socketOut.println("Message Get Description WC");
        socketOut.println(game);
        socketOut.println(s);
        socketOut.println(nickName);
        System.out.println(socketIn.nextLine());

        switch(s) {
            case "Cyberblade":
                System.out.println(inputReminder +
                        "basic effect: TARGET in your cell\n" +
                        "shadowstep: DIRECTION you want to move to\n" +
                        "slice and dice [1 yellow]: DIFFERENT TARGET in your cell");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Electroscythe":
                System.out.println(inputReminder +
                        "basic mode: none\n" +
                        "reaper mode [1 red 1 blue]: none");
                wPrompt.shoot2ToUser2(game, socket, nickName, s);
                break;

            case "Flamethrower":
                System.out.println(inputReminder +
                        "basic mode: 1. TARGET 1 move away\n " +
                        "            2. [OPTIONAL] ANOTHER TARGET 1 more move away in the same direction\n" +
                        "barbecue mode [2 yellow]: 1. COORDINATES of cell of target(s) 1 move away\n" +
                        "                          2. [OPTIONAL] COORDINATES of another cell 1 more more away in the same direction");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Furnace":
                System.out.println(inputReminder +
                        "basic mode: COLOUR of room you can see that isn't your room\n" +
                        "cozy fire mode: COORDINATES of cell 1 move away");
                wPrompt.shoot2ToUser3(game, socket, nickName, s);
                break;

            case "Grenade Launcher":
                System.out.println(inputReminder +
                        "basic effect: 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] DIRECTION you wish to move that target in\n" +
                        "extra grenade: COORDINATES of cell you can see [1 red]");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Heatseeker":
                System.out.println(inputReminder +
                        "effect: TARGET you canNOT see");
                wPrompt.shoot2ToUser3(game, socket, nickName, s);
                break;

            case "Hellion":
                System.out.println(inputReminder +
                        "basic mode: TARGET you can see at least 1 move away\n" +
                        "nano-tracer mode [1 red]: as with basic mode");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Lock Rifle":
                System.out.println(inputReminder +
                        "basic effect: TARGET you can see\n" +
                        "second lock [1 red]: DIFFERENT TARGET you can see");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Machine Gun":
                System.out.println(inputReminder +
                        "basic effect: 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] ANOTHER TARGET you can see" +
                        "focus shot [1 yellow]: ONE of those TARGETS\n" +
                        "turret tripod [1 blue]: the OTHER of those TARGETS and/or a DIFFERENT TARGET you can see");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Plasma Gun":
                //TODO
                System.out.println(inputReminder +
                        "basic effect: TARGET you can see\n" +
                        "phase glide: NUMBER of cells you want to move (1 or 2) and the DIRECTION(s)\n" +
                        "charged shot [1 blue]: none");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Power Glove":
                //TODO
                System.out.println(inputReminder +
                        "basic mode: TARGET 1 move away\n" +
                        "rocket fist mode [1 blue]: 1. COORDINATES of cell 1 move away\n" +
                        "                           2. [OPTIONAL] TARGET on that cell\n" +
                        "(You may repeat 2. once with a cell in the same direction just 1 square away, plus a target on that cell\n" +
                        "This makes for a total of 4 empty strings if only the first target is chosen.)");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Railgun":
                System.out.println(inputReminder +
                        "basic mode: TARGET in a cardinal direction\n" +
                        "piercing mode: 1. TARGET in a cardinal direction\n" +
                        "               2. ANOTHER TARGET in that direction\n" +
                        "               (keep in mind this mode IGNORES walls)");
                wPrompt.shoot2ToUser3(game, socket, nickName, s);
                break;

            case "Rocket Launcher":
                //TODO
                System.out.println(inputReminder +
                        "basic effect: 1. TARGET you can see but not in your cell\n" +
                        "              2. [OPTIONAL] DIRECTION in which you want to move them\n" +
                        "rocket jump [1 blue]: number of cells you want to move (1 or 2) and the direction(s)\n" +
                        "fragmenting warhead: none [1 yellow]");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Shockwave":
                System.out.println(inputReminder +
                        "basic mode: 1. TARGET in some cell\n" +
                        "            2. [OPTIONAL] TARGET in some other cell\n" +
                        "            3. [OPTIONAL] TARGET in yet another cell\n" +
                        "tsunami mode [1 yellow]: none");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Shotgun":
                System.out.println(inputReminder +
                        "basic mode: 1. TARGET in your cell\n" +
                        "            2. [OPTIONAL] DIRECTION you want to move them in\n" +
                        "long barrel mode: TARGET 1 move away");
                wPrompt.shoot2ToUser3(game, socket, nickName, s);
                break;

            case "Sledgehammer":
                System.out.println(inputReminder +
                        "basic mode: TARGET in your cell\n" +
                        "pulverize mode: 1. TARGET in your cell\n" +
                        "                2. NUMBER of squares (1 or 2) you want to move them and the DIRECTION(s)\n" +
                        "                   (leave 1 empty string if just 1 direction");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "T.H.O.R.":
                System.out.println(inputReminder +
                        "basic effect: TARGET you can see\n" +
                        "chain reaction [1 blue]: TARGET your first target can see\n" +
                        "high voltage [1 blue]: TARGET your second target can see");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Tractor Beam":
                System.out.println(inputReminder +
                        "basic mode: 1. TARGET you may or may not see\n" +
                        "            2. COORDINATES of a cell you can see up to 2 squares away from you\n" +
                        "punisher mode [1 red 1 yellow]: TARGET up to 2 moves away");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Vortex Cannon":
                System.out.println(inputReminder +
                        "basic effect: 1. TARGET up to 1 move away from the 'vortex'\n" +
                        "              2. COORDINATES of the cell the vortex is to be placed in (must not be your cell)\n" +
                        "black hole [1 red]: 1. TARGET on the vortex or 1 move away from it\n" +
                        "                    2. [OPTIONAL] ANOTHER TARGET on the vortex or 1 move away from it");
                wPrompt.shoot2ToUser1(game, socket, nickName, s);
                break;

            case "Whisper":
                System.out.println(inputReminder +
                        "effect: TARGET you can see at least 2 moves away)");
                wPrompt.shoot2ToUser4(game, socket, nickName, s);
                break;

            case "ZX-2":
                System.out.println(inputReminder +
                        "basic mode: TARGET you can see\n" +
                        "scanner mode: 1. TARGET you can see\n" +
                        "              2. [OPTIONAL] ANOTHER TARGET you can see\n" +
                        "              3. [OPTIONAL] YET ANOTHER TARGET you can see");
                wPrompt.shoot2ToUser3(game, socket, nickName, s);
                break;
            default: break;
        }
        action2();
    }

    private void grabSecondAction() {
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

        socketOut.println("Message Show Cards On Board");
        socketOut.println(game);
        String cardsOnBoard = socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" +
                socketIn.nextLine() + "\n" + socketIn.nextLine() + "\n" + socketIn.nextLine();

        System.out.println("\nThe AmmoCards on the Board are as below:\n" + cardsOnBoard);

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
                List<String> lWS = new LinkedList<>();

                socketOut.println("Message Check Weapon Slot Contents");
                socketOut.println(game);
                socketOut.println(n);
                int size = Integer.parseInt(socketIn.nextLine());
                for(int i = 0; i < size; i++)
                    lWS.add(socketIn.nextLine());

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

            socketOut.println("Message Is Valid Second Action Grab");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(lD.size());
            for(Integer i : lD)
                socketOut.println(i);
            socketOut.println(wCard);
            socketOut.println(weaponSlot);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getColourId());
            socketOut.println(lP.size());
            for(String s : lP)
                socketOut.println(s);
            socketOut.println(lPC.size());
            for(String s : lPC)
                socketOut.println(s);

            String isValidFirstActionGrab = socketIn.nextLine();

            if(isValidFirstActionGrab.equals("true"))
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

        socketOut.println("Message Second Action Grab");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(lD.size());
        for(Integer i : lD)
            socketOut.println(i);
        socketOut.println(wCard);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getColourId());
        socketOut.println(lP.size());
        for(String s : lP)
            socketOut.println(s);
        socketOut.println(lPC.size());
        for(String s : lPC)
            socketOut.println(s);

        socketOut.println("Message Is Discard");
        socketOut.println(game);

        String isDiscard = socketIn.nextLine();

        if(isDiscard.equals("true")) {
            System.out.println("Enter the WeaponCard you want to discard");
            String wCDiscard = in.nextLine();

            socketOut.println("Message Discard Weapon Card");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(weaponSlot);
            socketOut.println(wCDiscard);
        }
    }

    public boolean doYouWantToUsePUC() {
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to use a PowerUpCard now?");
        String confirm = in.next();
        return (confirm.equals("Yes") || confirm.equals("yes") || confirm.equals("y"));
    }

    public void usePowerUpCard() {
        Scanner in = new Scanner(System.in);
        boolean x;
        String namePC;
        String colourPC;
        List<String> lS = new LinkedList<>();

        socketOut.println("Message Get PowerUp Card Name And Colour");
        socketOut.println(game);
        socketOut.println(nickName);
        int size = Integer.parseInt(socketIn.nextLine());

        System.out.println("Enter which PowerUpCard you want to use. You have the following:");
        for(int i = 0; i < size; i++) {
            System.out.println(socketIn.nextLine() + COLOURED + socketIn.nextLine());
        }

        namePC = in.nextLine();

        System.out.println("Enter the colour of the chosen PowerUpCard:");
        colourPC = in.nextLine();
        socketOut.println("Message Get Description PUC");
        socketOut.println(game);
        socketOut.println(namePC);
        socketOut.println(colourPC);
        socketOut.println(nickName);
        System.out.println(socketIn.nextLine());

        switch(namePC) {
            case "Tagback Grenade":
                while(true) {
                    System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                    lS.add(in.nextLine());

                    socketOut.println("Message Is Valid Use PowerUp Card");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(namePC);
                    socketOut.println(colourPC);
                    socketOut.println(lS.size());
                    for(String s : lS)
                        socketOut.println(s);
                    socketOut.println("null");

                    String isValidUsePUC = socketIn.nextLine();

                    if(isValidUsePUC.equals("true"))
                        break;
                    else {
                        System.out.println(ERRORRETRY);
                        lS.clear();
                        x = exitHandler(in);
                        if(x)
                            return;
                    }
                }
                socketOut.println("Message Use PowerUp Card");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(namePC);
                socketOut.println(colourPC);
                socketOut.println(lS.size());
                for(String s : lS)
                    socketOut.println(s);
                socketOut.println("null");
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

                    socketOut.println("Message Is Valid Use PowerUp Card");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(namePC);
                    socketOut.println(colourPC);
                    socketOut.println(lS.size());
                    for(String s : lS)
                        socketOut.println(s);
                    socketOut.println(c.getColourId());

                    String isValidUsePUC = socketIn.nextLine();

                    if(isValidUsePUC.equals("true"))
                        break;
                    else {
                        System.out.println(ERRORRETRY);
                        lS.clear();
                        x = exitHandler(in);
                        if(x)
                            return;
                    }
                }
                socketOut.println("Message Use PowerUp Card");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(namePC);
                socketOut.println(colourPC);
                socketOut.println(lS.size());
                for(String s : lS)
                    socketOut.println(s);
                socketOut.println(c.getColourId());
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

                    socketOut.println("Message Is Valid Use PowerUp Card");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(namePC);
                    socketOut.println(colourPC);
                    socketOut.println(lS.size());
                    for(String s : lS)
                        socketOut.println(s);
                    socketOut.println("null");

                    String isValidUsePUC = socketIn.nextLine();

                    if(isValidUsePUC.equals("true"))
                        break;
                    else {
                        System.out.println(ERRORRETRY);
                        lS.clear();
                        x = exitHandler(in);
                        if(x)
                            return;
                    }
                }
                socketOut.println("Message Use PowerUp Card");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(namePC);
                socketOut.println(colourPC);
                socketOut.println(lS.size());
                for(String s : lS)
                    socketOut.println(s);
                socketOut.println("null");
                break;

            case "Teleporter":
                while(true) {
                    System.out.println("Enter the coordinates of the cell you want to move to:");
                    lS.add(in.next());
                    lS.add(in.next());

                    socketOut.println("Message Is Valid Use PowerUp Card");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(namePC);
                    socketOut.println(colourPC);
                    socketOut.println(lS.size());
                    for(String s : lS)
                        socketOut.println(s);
                    socketOut.println("null");

                    String isValidUsePUC = socketIn.nextLine();

                    if(isValidUsePUC.equals("true"))
                        break;
                    else {
                        System.out.println(ERRORRETRY);
                        lS.clear();
                        x = exitHandler(in);
                        if(x)
                            return;
                    }
                }
                socketOut.println("Message Use PowerUp Card");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(namePC);
                socketOut.println(colourPC);
                socketOut.println(lS.size());
                for(String s : lS)
                    socketOut.println(s);
                socketOut.println("null");
                break;
            default: break;
        }
    }

    public void reload() {
        Scanner in = new Scanner(System.in);

        socketOut.println("Message Get Weapon Card Unloaded");
        socketOut.println(game);
        socketOut.println(nickName);
        int size = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size; i++)
            System.out.println(socketIn.nextLine());

        int i = 0;

        while(i == 0) {
            System.out.println("Choose the weapon card you want to reload, or enter 'end' if you don't need/want to");
            String s = in.nextLine();
            if(s.equals("end"))
                break;

            System.out.println("Enter 0 if you want to reload another card, otherwise 1");
            i = in.nextInt();

            socketOut.println("Message Is Valid Reload");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);

            String isValidReload = socketIn.nextLine();

            if(isValidReload.equals("true")) {
                socketOut.println("Message Reload");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(s);
                socketOut.println(i);
            }
            else
                System.out.println("You can't reload now");
        }
    }

    public void scoring() {
        socketOut.println("Message Is Valid Scoring");
        socketOut.println(game);

        String isValidScoring = socketIn.nextLine();

        if(isValidScoring.equals("true")) {
            System.out.println("Scoring this turn...");
            socketOut.println("Message Scoring");
            socketOut.println(game);
        }
        else
            System.out.println("No scoring yet");
    }

    public void newSpawnPoint() {
        Scanner in = new Scanner(System.in);

        List<String> deadList = new LinkedList<>();
        socketOut.println("Message Get Dead List");
        socketOut.println(game);
        int size = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size; i++)
            deadList.add(socketIn.nextLine());

        if(deadList.contains(this.nickName)) {
            String p;
            String c;

            while(true) {
                System.out.println("Enter the PowerUp card you want to discard; its colour will be your new spawn point:");
                p = in.nextLine();

                System.out.println("Enter the colour of the chosen PowerUp card:");
                c = in.nextLine();

                socketOut.println("Message Is Valid Discard Card For Spawn Point");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(p);
                socketOut.println(c);

                String isValidDiscardCardForSpawnPoint = socketIn.nextLine();

                if(isValidDiscardCardForSpawnPoint.equals("true"))
                    break;
                else
                    System.out.println(ERRORRETRY);
            }

            socketOut.println("Message Discard Card For Spawn Point");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(p);
            socketOut.println(c);
        }
    }

    public void replace() {
        socketOut.println("Message Is Valid To Replace");
        socketOut.println(game);

        String isValidReplace = socketIn.nextLine();

        if(isValidReplace.equals("true")) {
            System.out.println("Replacing...");
            socketOut.println("Message Replace");
            socketOut.println(game);
            System.out.println("Your turn has ended. Wait for other players to play their turn.");
        }
        else {
            System.out.println("It's not time to replace yet.");
            System.out.println("Your turn has ended. Wait for other players to play their turn.");
        }
    }

    public void finalFrenzyTurn() {
        Scanner in = new Scanner(System.in);
        List<String> l = new LinkedList<>();

        String isValidFFAction;
        int counterIsValidFFAction = 0;

        do {
            if(counterIsValidFFAction > 0)
                System.out.println(ERRORRETRY);

            System.out.println("\nThis is the final turn. Final frenzy mode activated.\n" +
                    "Choose the action(s) you want to do according to the fact you are before or after the player who started the game.");
            while(in.hasNext())
                l.add(in.next());

            socketOut.println("Message Is Valid Final Frenzy Action");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(l.size());
            for(String s : l)
                socketOut.println(s);

            isValidFFAction = socketIn.nextLine();
            counterIsValidFFAction++;
        } while(isValidFFAction.equals("false"));

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
                    int size;
                    int size1;
                    int size2;

                    String isValidFFAction1;
                    int counterIsValidFFAction1 = 0;

                    do {
                        if(counterIsValidFFAction1 > 0)
                            System.out.println(ERRORRETRY);

                        System.out.println("Write the direction you want to move:");
                        i = in.nextInt();

                        System.out.println("Write the card(s) you want to reload:");
                        socketOut.println("Message Get Weapon Card Unloaded");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        size = Integer.parseInt(socketIn.nextLine());
                        for(int i1 = 0; i1 < size; i1++)
                            System.out.println(socketIn.nextLine());
                        while(in.hasNext())
                            lW.add(in.next());

                        System.out.println("Write the card you want to use:");
                        socketOut.println("Message Get Player Weapon Card");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        size1 = Integer.parseInt(socketIn.nextLine());
                        for(int i1 = 0; i1 < size1; i1++)
                            System.out.println(socketIn.nextLine());
                        wC = in.next();

                        System.out.println("Enter the number of the effect you want to use:");
                        while(in.hasNext())
                            lI.add(in.nextInt());

                        System.out.println("Enter the relevant strings for the card:");
                        while(in.hasNext())
                            lS.add(in.next());

                        System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect, if necessary:");
                        while(in.hasNext())
                            lC.add(Colour.valueOf(in.next()));

                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn, if necessary:");
                        socketOut.println("Message Get PowerUp Card Name And Colour");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        size2 = Integer.parseInt(socketIn.nextLine());
                        for(int i1 = 0; i1 < size2; i1++)
                            System.out.println(socketIn.nextLine() + COLOURED + socketIn.nextLine());
                        while(in.hasNext())
                            lP.add(in.next());

                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                        while(in.hasNext())
                            lPC.add(in.next());

                        socketOut.println("Message Is Valid Final Frenzy Action 1");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        socketOut.println(i);
                        socketOut.println(wC);
                        socketOut.println(lI.size());
                        for(int i1 : lI)
                            socketOut.println(i1);
                        socketOut.println(lS.size());
                        for(String s1 : lS)
                            socketOut.println(s1);
                        socketOut.println(lC.size());
                        for(Colour c : lC)
                            socketOut.println(c.getColourId());
                        socketOut.println(lP.size());
                        for(String s1 : lP)
                            socketOut.println(s1);
                        socketOut.println(lPC.size());
                        for(String s1 : lPC)
                            socketOut.println(s1);

                        isValidFFAction1 = socketIn.nextLine();
                        counterIsValidFFAction1++;
                    } while(isValidFFAction1.equals("false"));

                    socketOut.println("Message Frenzy Action 1");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(i);
                    socketOut.println(lW.size());
                    for(String s1 : lW)
                        socketOut.println(s1);
                    socketOut.println(wC);
                    socketOut.println(lI.size());
                    for(int i1 : lI)
                        socketOut.println(i1);
                    socketOut.println(lS.size());
                    for(String s1 : lS)
                        socketOut.println(s1);
                    socketOut.println(lC.size());
                    for(Colour c : lC)
                        socketOut.println(c.getColourId());
                    socketOut.println(lP.size());
                    for(String s1 : lP)
                        socketOut.println(s1);
                    socketOut.println(lPC.size());
                    for(String s1 : lPC)
                        socketOut.println(s1);

                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                case "2":
                    if(doYouWantToUsePUC())
                        usePowerUpCard();

                    List<Integer> list = new LinkedList<>();

                    String isValidFFAction2;
                    int counterIsValidFFAction2 = 0;

                    do {
                        if(counterIsValidFFAction2 > 0)
                            System.out.println(ERRORRETRY);

                        System.out.println("Write the direction(s) you want to move:");
                        while(in.hasNext())
                            list.add(in.nextInt());

                        socketOut.println("Message Is Valid Final Frenzy Action 2");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        socketOut.println(list.size());
                        for(int i1 : list)
                            socketOut.println(i1);

                        isValidFFAction2 = socketIn.nextLine();
                        counterIsValidFFAction2++;
                    } while(isValidFFAction2.equals("false"));

                    socketOut.println("Message Final Frenzy Action 2");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(list.size());
                    for(int i1 : list)
                        socketOut.println(i1);

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

                    String isValidFFAction3;
                    int counterIsValidFFAction3 = 0;

                    do {
                        if(counterIsValidFFAction3 > 0)
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
                            socketOut.println("Message Get PowerUp Card Name And Colour");
                            socketOut.println(game);
                            socketOut.println(nickName);
                            size2 = Integer.parseInt(socketIn.nextLine());
                            for(int i1 = 0; i1 < size2; i1++)
                                System.out.println(socketIn.nextLine() + COLOURED + socketIn.nextLine());
                            while((in.hasNext()))
                                lP2.add(in.next());
                            System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                            while((in.hasNext()))
                                lPC2.add(in.next());
                        }

                        socketOut.println("Message Is Valid Final Frenzy Action 3");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        socketOut.println(list2.size());
                        for(int i1 : list2)
                            socketOut.println(i1);
                        socketOut.println(wCard);
                        socketOut.println(weaponSlot);
                        socketOut.println(lC2.size());
                        for(Colour c : lC2)
                            socketOut.println(c.getColourId());
                        socketOut.println(lP2.size());
                        for(String s1 : lP2)
                            socketOut.println(s1);
                        socketOut.println(lPC2.size());
                        for(String s1 : lPC2)
                            socketOut.println(s1);

                        isValidFFAction3 = socketIn.nextLine();
                        counterIsValidFFAction3++;
                    } while(isValidFFAction3.equals("false"));

                    socketOut.println("Message Final Frenzy Action 3");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(list2.size());
                    for(int i1 : list2)
                        socketOut.println(i1);
                    socketOut.println(wCard);
                    socketOut.println(lC2.size());
                    for(Colour c : lC2)
                        socketOut.println(c.getColourId());
                    socketOut.println(lP2.size());
                    for(String s1 : lP2)
                        socketOut.println(s1);
                    socketOut.println(lPC2.size());
                    for(String s1 : lPC2)
                        socketOut.println(s1);

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
                    String wC2;
                    int size3;
                    int size4;
                    int size5;

                    String isValidFFAction4;
                    int counterIsValidFFAction4 = 0;

                    do {
                        if(counterIsValidFFAction4 > 0)
                            System.out.println(ERRORRETRY);

                        System.out.println("Write the direction(s) you want to move:");
                        while(in.hasNext())
                            list3.add(in.nextInt());

                        System.out.println("Write the card(s) you want to reload:");
                        socketOut.println("Message Get Weapon Card Unloaded");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        size3 = Integer.parseInt(socketIn.nextLine());
                        for(int i1 = 0; i1 < size3; i1++)
                            System.out.println(socketIn.nextLine());
                        while(in.hasNext())
                            lW2.add(in.next());

                        System.out.println("Write the card you want to use:");
                        socketOut.println("Message Get Player Weapon Card");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        size4 = Integer.parseInt(socketIn.nextLine());
                        for(int i1 = 0; i1 < size4; i1++)
                            System.out.println(socketIn.nextLine());
                        wC2 = in.next();

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
                        socketOut.println("Message Get PowerUp Card Name And Colour");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        size5 = Integer.parseInt(socketIn.nextLine());
                        for(int i1 = 0; i1 < size5; i1++)
                            System.out.println(socketIn.nextLine() + COLOURED + socketIn.nextLine());
                        while(in.hasNext())
                            lP3.add(in.next());

                        System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn, if you have chosen one:");
                        while((in.hasNext()))
                            lPC3.add(in.next());

                        socketOut.println("Message Is Valid Final Frenzy Action 4");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        socketOut.println(list3.size());
                        for(int i1 : list3)
                            socketOut.println(i1);
                        socketOut.println(wC2);
                        socketOut.println(lI2.size());
                        for(int i1 : lI2)
                            socketOut.println(i1);
                        socketOut.println(lS2.size());
                        for(String s1 : lS2)
                            socketOut.println(s1);
                        socketOut.println(lC3.size());
                        for(Colour c : lC3)
                            socketOut.println(c.getColourId());
                        socketOut.println(lP3.size());
                        for(String s1 : lP3)
                            socketOut.println(s1);
                        socketOut.println(lPC3.size());
                        for(String s1 : lPC3)
                            socketOut.println(s1);

                        isValidFFAction4 = socketIn.nextLine();
                        counterIsValidFFAction4++;
                    } while(isValidFFAction4.equals("false"));

                    socketOut.println("Message Final Frenzy Action 4");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(list3.size());
                    for(int i1 : list3)
                        socketOut.println(i1);
                    socketOut.println(lW2.size());
                    for(String s1 : lW2)
                        socketOut.println(s1);
                    socketOut.println(wC2);
                    socketOut.println(lI2.size());
                    for(int i1 : lI2)
                        socketOut.println(i1);
                    socketOut.println(lS2.size());
                    for(String s1 : lS2)
                        socketOut.println(s1);
                    socketOut.println(lC3.size());
                    for(Colour c : lC3)
                        socketOut.println(c.getColourId());
                    socketOut.println(lP3.size());
                    for(String s1 : lP3)
                        socketOut.println(s1);
                    socketOut.println(lPC3.size());
                    for(String s1 : lPC3)
                        socketOut.println(s1);

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

                    String isValidFFAction5;
                    int counterIsValidFFAction5 = 0;

                    do {
                        if(counterIsValidFFAction5 > 0)
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

                        socketOut.println("Message Is Valid Final Frenzy Action 5");
                        socketOut.println(game);
                        socketOut.println(nickName);
                        socketOut.println(list4.size());
                        for(int i1 : list4)
                            socketOut.println(i1);
                        socketOut.println(wCard);
                        socketOut.println(weaponSlot);
                        socketOut.println(lC4.size());
                        for(Colour c : lC4)
                            socketOut.println(c.getColourId());
                        socketOut.println(lP4.size());
                        for(String s1 : lP4)
                            socketOut.println(s1);
                        socketOut.println(lPC4.size());
                        for(String s1 : lPC4)
                            socketOut.println(s1);

                        isValidFFAction5 = socketIn.nextLine();
                        counterIsValidFFAction5++;
                    } while(isValidFFAction5.equals("false"));

                    socketOut.println("Message Final Frenzy Action 5");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(list4.size());
                    for(int i1 : list4)
                        socketOut.println(i1);
                    socketOut.println(wCard);
                    socketOut.println(lC4.size());
                    for(Colour c : lC4)
                        socketOut.println(c.getColourId());
                    socketOut.println(lP4.size());
                    for(String s1 : lP4)
                        socketOut.println(s1);
                    socketOut.println(lPC4.size());
                    for(String s1 : lPC4)
                        socketOut.println(s1);

                    if(doYouWantToUsePUC())
                        usePowerUpCard();
                    break;

                default: break;
            }
        }

        socketOut.println("Message Final Frenzy Turn Scoring");
        socketOut.println(game);
    }

    public void endFinalFrenzy() {
        socketOut.println("Message End Turn Final Frenzy");
        socketOut.println(game);
        System.out.println("\nGame finished: calculating the result...");
    }

    public void finalScoring() {
        socketOut.println("Message Final Scoring");
        socketOut.println(game);

        System.out.println("\n\n---------- FINAL SCOREBOARD ----------\n");

        socketOut.println("Message Get Players");
        socketOut.println(game);
        int size = Integer.parseInt(socketIn.nextLine());
        List<String> players = new LinkedList<>();
        for(int i = 0; i < size; i++)
            players.add(socketIn.nextLine());

        socketOut.println("Message Get Score");
        List<Integer> score = new LinkedList<>();
        int size1 = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size1; i++)
            score.add(Integer.parseInt(socketIn.nextLine()));

        for(int i = 0; i < size; i++) {
            System.out.println(players.get(i) + "\t" + score.get(i));
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

    private boolean exitHandler(Scanner in) {
        System.out.println(EXITSTRING + YESPROMPT);
        String exitInput = in.next();
        return (exitInput.equals("Yes") || exitInput.equals("yes") || exitInput.equals("y"));
    }

    public void exit() {
        System.exit(0);
    }
}