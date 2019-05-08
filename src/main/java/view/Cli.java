package view;

import controller.Game;
import model.Colour;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Cli implements View {

    private int game;
    private ServerInterface server;
    private String nickName;
    private Colour colour;
    private CLIWeaponPrompt wPrompt;
    private String errorRetry = "Error: please retry";


    public int getGame() {              //for the test
        return game;
    }

    @Override
    public void setGame(int game) {
        this.game = game;
    }

    /*public String getNickName() {
        return nickName;
    }*/

    @Override
    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void askNameAndColour() throws RemoteException{
        Scanner in = new Scanner(System.in);
        int type;
        if (this.server.messageGameIsNotStarted(game)) {
            System.out.println("Enter your name:");
            this.nickName = in.nextLine();
            System.out.println("Enter your colour in all caps (YELLOW, BLUE, GREEN, PURPLE, BLACK):");
            String s1 = in.nextLine();
            this.colour = Colour.valueOf(s1);
            this.server.messageGameStart(game, nickName, colour);
            /*System.out.println("Choose the type of arena (1, 2, 3, 4):");
            int type = in.nextInt();*/
            while (true) {
                System.out.println("Choose the type of arena (1, 2, 3, 4):");
                type = in.nextInt();
                if (this.server.messageIsValidReceiveType(game, type))
                    break;
                else
                    System.out.println(errorRetry);
            }
            this.server.messageReceiveType(game, type);
            System.out.println("\n---------GENERATING ARENA...---------\n");
            return;
        }
        System.out.println("\n---------WAITING FOR PLAYERS TO JOIN---------\n");
        /*System.out.println("Enter your name:");
        this.nickName = in.nextLine();
        System.out.println("Enter your colour in all caps:");
        String stringColour = in.nextLine();
        this.colour = Colour.valueOf(stringColour);*/
        while (true) {
            System.out.println("Enter your name:");
            this.nickName = in.nextLine();
            System.out.println("Enter your colour in all caps (YELLOW, BLUE, GREEN, PURPLE, BLACK):");
            String s2 = in.nextLine();
            this.colour = Colour.valueOf(s2);
            if (this.server.messageIsValidAddPlayer(game, this.nickName, this.colour))
                break;
            else
                System.out.println(errorRetry);
        }
        this.server.messageAddPlayer(game, this.nickName, this.colour);
    }

    @Override
    public void selectSpawnPoint() throws RemoteException{
        Scanner in = new Scanner(System.in);
        List<String> l = this.server.messageGiveTwoPUCard(game, this.nickName);
        String p;
        String c;
        System.out.println("The following are " + this.nickName +"'s starting PowerUpCards");
        System.out.println(l.get(0) + " coloured " + l.get(1));
        System.out.println(l.get(2) + " coloured " + l.get(3));
        System.out.println("\n---------SPAWN POINT SELECT---------\n");
        /*System.out.println("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                "colour of your spawn point");
        String p1 = in.nextLine();
        System.out.println("Enter the colour of that card: ");
        String c1 = in.nextLine();*/
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
            if (l.get(0).equals(p) && l.get(1).equals(c))
                this.server.messagePickAndDiscardCard(game, this.nickName, l.get(0), l.get(1));
            else
                this.server.messagePickAndDiscardCard(game, this.nickName, l.get(2), l.get(3));
    }

    @Override
    public void action1() throws RemoteException{
        Scanner in = new Scanner(System.in);
        String action;
        System.out.println("\n---------START OF " + this.nickName + "'s FIRST ACTION---------\n");
        /*System.out.println("Choose the action you want to do (Move, Shoot, Grab):");
        String action = in.nextLine();*/
        while (true) {
            System.out.println("Choose the action you want to do (Move, Shoot, Grab):");
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
                "1 = north, 2 = east, 3 = south, 4 = west\n" +
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

    private void shootFirstAction() throws RemoteException{
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot:");
        this.server.messageGetWeaponCardLoaded(game, this.nickName).stream().forEach(System.out::println);
        String s = in.next();
        while(!this.server.messageIsValidCard(game, nickName, s)){
            System.out.println("Error: choose one of these cards to shoot:");
            this.server.messageGetWeaponCardLoaded(game, this.nickName).stream().forEach(System.out::println);
            s = in.next();
        }
        System.out.println(this.server.messageGetReloadCost(game, s, nickName));
        System.out.println(this.server.messageGetDescriptionWC(game, s,nickName));

        switch(s){
            case "Cyberblade":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Electroscythe":
                wPrompt.shootToUser2(game, server, nickName);
                break;

            case "Flamethrower":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Furnace":
                wPrompt.shootToUser3(game, server, nickName);
                break;

            case "Grenade Launcher":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Heatseeker":
                wPrompt.shootToUser3(game, server, nickName);
                break;

            case "Hellion":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Lock Rifle":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Machine Gun":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Plasma Gun":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Power Glove":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Railgun":
                wPrompt.shootToUser3(game, server, nickName);
                break;

            case "Rocket Launcher":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Shockwave":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Shotgun":
                wPrompt.shootToUser3(game, server, nickName);
               break;

            case "Sledgehammer":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "T.H.O.R.":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Tractor Beam":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Vortex Cannon":
                wPrompt.shootToUser1(game, server, nickName);
                break;

            case "Whisper":
                wPrompt.shootToUser4(game, server, nickName);
                break;

            case "ZX-2":
                wPrompt.shootToUser3(game, server, nickName);
                break;
        }
    }

    private void grabFirstAction() throws RemoteException{
        Scanner in = new Scanner(System.in);
        Integer[] directions = null;
        List<Integer> l = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();
        String wCard;
        String weaponSlot = null;
        while (true) {
            System.out.println("If you wish to grab whatever is in your cell, enter 0\n" +
                    "Otherwise, enter the sequence of movements you want to do, one integer at a time: only one is permitted\n" +
                    "if you haven't unlocked the Adrenaline move, up to two otherwise\n" +
                    "1 = north, 2 = east, 3 = south, 4 = west\n" +
                    "Press any letter char to finish");
            while (in.hasNextInt())
                l.add(in.nextInt());
            //TODO method that displays the cards in a weapon slot of the player's choice; should be in a while() so as to let the player inspect all of them
            System.out.println("If it is a WeaponCard you wish to buy, enter its name");
            wCard = in.next();
            if (!wCard.equals("")) {
                System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                weaponSlot = in.next();
                System.out.println("Enter the colour(s), in order and in all caps, of the required AmmoCube(s) to buy the card,\n" +
                        "or 0 if not necessary");
                while (in.hasNext()) {
                    String a = in.next();
                    if (a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }
                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn, or 0 if not necessary");
                while (in.hasNext()) {
                    String p = in.next();
                    if (p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }
                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn, or 0 if not necessary");
                while (in.hasNext()) {
                    String c = in.next();
                    if (c.equals("0"))
                        break;
                    else
                        lPC.add(c);
                }
            }
            if (this.server.messageIsValidFirstActionGrab(game, nickName, l.toArray(directions), wCard, weaponSlot, lC, lP, lPC))
                break;
            else {
                System.out.println(errorRetry);
                l.clear();
                lC.clear();
                lP.clear();
                lPC.clear();
            }
        }
        /*while (!this.server.messageIsValidFirstActionGrab(game, nickName, l.toArray(directions), wCard, weaponSlot, lC, lP, lPC)){
            System.out.println(errorRetry);
            System.out.println("Enter the direction(s) where you want to move");
            while (in.hasNext())
                l.add(in.nextInt());
            System.out.println("Enter the WeaponCard you want to buy, if you want:");
            wCard = in.next();
            if(!wCard.equals("")) {
                System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                weaponSlot = in.next();
                System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary");
                while ((in.hasNext()))
                    lC.add(Colour.valueOf(in.next()));
                System.out.println("Enter the PowerUpCard you want to use to pay during your turn:");
                while ((in.hasNext()))
                    lP.add(in.next());
                System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                while ((in.hasNext()))
                    lPC.add(in.next());
            }
        }*/
        this.server.messageFirstActionGrab(game, nickName, l.toArray(directions), wCard, lC, lP, lPC);
        if(this.server.messageIsDiscard(game)) {
            System.out.println("Enter the WeaponCard you want to discard");
            String wCDiscard = in.next();
            this.server.messageDiscardWeaponCard(game, nickName, weaponSlot, wCDiscard);
        }
    }

    @Override
    public void action2() throws RemoteException{
        System.out.println("---------START OF " + this.nickName + "'s SECOND ACTION---------");
        Scanner in = new Scanner(System.in);
        System.out.println("Choose the action you want to do (Move, Shoot, Grab):");
        String action = in.nextLine();
        while (!(action.equals("Move") || action.equals("Shoot") || action.equals("Grab"))){
            System.out.println("Choose the action you want to do (Move, Shoot, Grab):");
            action = in.nextLine();
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
                "1 = north, 2 = east, 3 = south, 4 = west\n" +
                "Press 0 to finish");
        while(true) {
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
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot: ");
        this.server.messageGetWeaponCardLoaded(game, this.nickName).stream().forEach(System.out::println);
        String s = in.next();
        while(!this.server.messageIsValidCard(game, nickName, s)){
            System.out.println("Error: choose one of these cards to shoot: ");
            this.server.messageGetWeaponCardLoaded(game, this.nickName).stream().forEach(System.out::println);
            s = in.next();
        }
        System.out.println(this.server.messageGetReloadCost(game, s, nickName));
        System.out.println(this.server.messageGetDescriptionWC(game, s,nickName));

        switch(s){
            case "Cyberblade":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Electroscythe":
                wPrompt.shoot2ToUser2(game, server, nickName);
                break;

            case "Flamethrower":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Furnace":
                wPrompt.shoot2ToUser3(game, server, nickName);
                break;

            case "Grenade Launcher":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Heatseeker":
                wPrompt.shoot2ToUser3(game, server, nickName);
                break;

            case "Hellion":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Lock Rifle":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Machine Gun":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Plasma Gun":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Power Glove":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Railgun":
                wPrompt.shoot2ToUser3(game, server, nickName);
                break;

            case "Rocket Launcher":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Shockwave":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Shotgun":
                wPrompt.shoot2ToUser3(game, server, nickName);
                break;

            case "Sledgehammer":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "T.H.O.R.":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Tractor Beam":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Vortex Cannon":
                wPrompt.shoot2ToUser1(game, server, nickName);
                break;

            case "Whisper":
                wPrompt.shoot2ToUser4(game, server, nickName);
                break;

            case "ZX-2":
                wPrompt.shoot2ToUser3(game, server, nickName);
                break;
        }
    }

    private void grabSecondAction() throws RemoteException{
        Scanner in = new Scanner(System.in);
        Integer[] directions = null;
        List<Integer> l = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();
        String wCard;
        String weaponSlot = null;
        while (true) {
            System.out.println("If you wish to grab whatever is in your cell, enter 0\n" +
                    "Otherwise, enter the sequence of movements you want to do, one integer at a time: only one is permitted\n" +
                    "if you haven't unlocked the Adrenaline move, up to two otherwise\n" +
                    "1 = north, 2 = east, 3 = south, 4 = west\n" +
                    "Press any letter char to finish");
            while (in.hasNextInt())
                l.add(in.nextInt());
            //TODO method that displays the cards in a weapon slot of the player's choice; should be in a while() so as to let the player inspect all of them
            System.out.println("If it is a WeaponCard you wish to buy, enter its name");
            wCard = in.next();
            if (!wCard.equals("")) {
                System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
                weaponSlot = in.next();
                System.out.println("Enter the colour(s), in order and in all caps, of the required AmmoCube(s) to buy the card,\n" +
                        "or 0 if not necessary");
                while (in.hasNext()) {
                    String a = in.next();
                    if (a.equals("0"))
                        break;
                    else
                        lC.add(Colour.valueOf(a));
                }
                System.out.println("Enter the PowerUpCard(s) you want to use to pay during your turn, or 0 if not necessary");
                while (in.hasNext()) {
                    String p = in.next();
                    if (p.equals("0"))
                        break;
                    else
                        lP.add(p);
                }
                System.out.println("Enter the colour(s) of the PowerUpCard(s) you want to use to pay during your turn, or 0 if not necessary");
                while (in.hasNext()) {
                    String c = in.next();
                    if (c.equals("0"))
                        break;
                    else
                        lPC.add(c);
                }
                if (this.server.messageIsValidSecondActionGrab(game, nickName, l.toArray(directions), wCard, weaponSlot, lC, lP, lPC))
                    break;
                else {
                    System.out.println(errorRetry);
                    l.clear();
                    lC.clear();
                    lP.clear();
                    lPC.clear();
                }
            }
        }
        /*while (!this.server.messageIsValidSecondActionGrab(game, nickName, l.toArray(directions), wCard, weaponSlot, lC, lP, lPC)){
            System.out.println("Error: repeat");
            System.out.println("Enter the direction(s) where you want to move, or 0 if you want to remain in your cell:");
            while (in.hasNext())
                l.add(in.nextInt());
            System.out.println("Write the Weapon card you want to buy, if you want:");
            wCard = in.next();
            if(!wCard.equals("")) {
                System.out.println("Write the number of the WeaponSlot from which you want to buy the card:");
                weaponSlot = in.next();
                System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
                while ((in.hasNext()))
                    lC.add(Colour.valueOf(in.next()));
                System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                while ((in.hasNext()))
                    lP.add(in.next());
                System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                while ((in.hasNext()))
                    lPC.add(in.next());
            }
        }*/
        this.server.messageSecondActionGrab(game, nickName, l.toArray(directions), wCard, lC, lP, lPC);
        if(this.server.messageIsDiscard(game)) {
            System.out.println("Enter the WeaponCard you want to discard");
            String wCDiscard = in.next();
            this.server.messageDiscardWeaponCard(game, nickName, weaponSlot, wCDiscard);
        }
    }

    @Override
    public boolean doYouWantToUsePUC(){
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to use the power up card now?");
        String s1 = in.next();
        return (s1.equals("Yes") || s1.equals("yes"));
    }

    @Override
    public void usePowerUpCard() throws RemoteException{
        Scanner in = new Scanner(System.in);
        String namePC;
        String colourPC;
        List<String> lS = new LinkedList<>();
        System.out.println("Enter which PowerUpCard you want to use:");
        this.server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
        namePC = in.next();
        System.out.println("Enter the colour of the PowerUpCard:");
        colourPC = in.next();
        this.server.messageGetDescriptionPUC(game, namePC, colourPC, nickName);
        switch (namePC){
            case "Tagback Grenade":
                System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                lS.add(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, null)){
                    System.out.println(errorRetry);
                    System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                    lS.add(in.next());
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;

            case "Targeting Scope":
                System.out.println("Enter the nickname of one or more players you have damaged:");
                while(in.hasNext())
                    lS.add(in.next());
                System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                Colour c = Colour.valueOf(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, c)){
                    System.out.println(errorRetry);
                    System.out.println("Enter the nickname of one or more players you have damaged:");
                    while(in.hasNext())
                        lS.add(in.next());
                    System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                    c = Colour.valueOf(in.next());
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, c);
                break;

            case "Newton":
                System.out.println("Enter the nickname of a player:");
                lS.add(in.next());
                System.out.println("Enter the direction(s) where you want the enemy to go:");
                while(in.hasNext())
                    lS.add(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(game, nickName, namePC, colourPC, lS, null)){
                    System.out.println("Error: please retryt");
                    System.out.println("Enter the nickname of a player:");
                    lS.add(in.next());
                    System.out.println("write the directions where you want the enemy to go:");
                    while(in.hasNext())
                        lS.add(in.next());
                }
                this.server.messageUsePowerUpCard(game, nickName, namePC, colourPC, lS, null);
                break;

            case "Teleporter":
                System.out.println("Enter the coordinates of the cell you want to move (x y):");
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
    public void reload() throws RemoteException{               //the player knows everything!
        Scanner in = new Scanner(System.in);
        this.server.messageGetWeaponCardUnloaded(game, this.nickName).stream().forEach(System.out::println);
        int i = 0;
        while(i == 0){
            System.out.println("Choose the weapon card you want to reload, or 'end' if you don't need/want to");
            String s = in.nextLine();
            if (s.equals("end"))
                break;
            System.out.println("Enter 0 if you want to reload another card, otherwise 1");
            i = in.nextInt();
            if(this.server.messageIsValidReload(game, this.nickName, s))
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
        else
            System.out.println("What are you doing, man?");
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
            System.out.println("Error: repeat");
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
                    this.server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
                    System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                    while (in.hasNext())
                        lP.add(in.next());
                    System.out.println("Enter the colour of the PowerUpCard you want to use for paying during your turn:");
                    while (in.hasNext())
                        lPC.add(in.next());
                    while(!this.server.messageIsValidFinalFrenzyAction1(game, nickName, i, wC, lI, lS, lC, lP, lPC)){
                        System.out.println("Error: repeat");
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
                        server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
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
                         System.out.println("Error: repeat");
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
                        System.out.println("Error: repeat");
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
                    server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
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
                        server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
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
        this.server.messageGetPlayers(game).stream().forEach(System.out::print);
        System.out.println();
        this.server.messageGetScore(game).stream().forEach(System.out::print);
        System.out.println();
        System.out.println("END GAME");
    }
}