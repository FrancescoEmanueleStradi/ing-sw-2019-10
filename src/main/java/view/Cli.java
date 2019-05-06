package view;

import controller.Game;
import model.Colour;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Cli implements View{

    private Game game;
    private ServerInterface server;
    private String nickName;                //TODO maybe nickName and colour should go in the client and not here because View must be the RMI registry
    private Colour colour;
    private CLIWeaponPrompt wPrompt;

    public void ciao() throws RemoteException {
        server.isMyTurn(1,1 ) ;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void askNameAndColour() {
        Scanner in = new Scanner(System.in);
        if (this.server.messageGameIsNotStarted()) {
            System.out.println("Enter your name:");
            this.nickName = in.nextLine();
            System.out.println("Enter your colour (YELLOW, BLUE, GREEN, PURPLE, BLACK):");
            String stringColour = in.nextLine();
            this.colour = Colour.valueOf(stringColour);
            this.server.messageGameStart(nickName, colour);
            System.out.println("Choose the type of arena (1, 2, 3, 4):");
            int type = in.nextInt();
            while(!this.server.messageIsValidReceiveType(type)){
                System.out.println("Error: retry");
                System.out.println("Choose the type of arena (1, 2, 3, 4):");
                type = in.nextInt();
            }
            this.server.messageReceiveType(type);
            System.out.println("---------GENERATING ARENA...---------");
            return;
        }
        System.out.println("---------WAITING FOR PLAYERS TO JOIN---------");
        System.out.println("Enter your name:");
        this.nickName = in.nextLine();
        System.out.println("Enter your colour:");
        String stringColour = in.nextLine();
        this.colour = Colour.valueOf(stringColour);
        while(!this.server.messageIsValidAddPlayer(this.nickName, this.colour)){
            System.out.println("Error: retry");
            System.out.println("Enter your name:");
            this.nickName = in.nextLine();
            System.out.println("Enter your colour:");
            String stringColour1 = in.nextLine();
            this.colour = Colour.valueOf(stringColour1);
        }
        this.server.messageAddPlayer(this.nickName, this.colour);
    }

    @Override
    public void selectSpawnPoint() {
        Scanner in = new Scanner(System.in);
        List<PowerUpCard> l = new LinkedList<>();
        for(PowerUpCard p : this.server.messageGiveTwoPUCard(this.nickName)){
            System.out.println(p.getCardName());
            l.add(p);
        }
        System.out.println("---------SPAWN POINT SELECT---------");
        System.out.println("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                "colour of your spawn point");
        String p1 = in.nextLine();
        while(!this.server.messageIsValidPickAndDiscard(this.nickName)) {
            System.out.println("Error: retry");
            System.out.println("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                    "colour of your spawn point");
            p1 = in.nextLine();
        }
            if(l.get(0).getCardName().equals(p1))
                this.server.messagePickAndDiscardCard(this.nickName, l.get(0), l.get(1));
            else
                this.server.messagePickAndDiscardCard(this.nickName, l.get(1), l.get(0));
    }

    @Override
    public void action1() {
        Scanner in = new Scanner(System.in);
        System.out.println("---------START FIRST ACTION---------");
        System.out.println("Choose the action you want to do (Move, Shoot, Grab):");
        String action = in.nextLine();
        while (!(action.equals("Move") || action.equals("Shoot") || action.equals("Grab"))){
            System.out.println("Choose the action you want to do (Move, Shoot, Grab):");
            action = in.nextLine();
        }
        if(action.equals("Move") || action.equals("move"))
            this.moveFirstAction();
        if(action.equals("Shoot") || action.equals("shoot"))
            this.shootFirstAction();
        if(action.equals("Grab") || action.equals("grab"))
            this.grabFirstAction();
    }

    private void moveFirstAction() {
        Scanner in = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        while(!this.server.messageIsValidFirstActionMove(l)) {
            System.out.println("Choose the sequence of movement you want to do");
            while (in.hasNext())
                l.add(in.nextInt());
        }
        this.server.messageFirstActionMove(this.nickName, l);
    }

    private void shootFirstAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot:");
        this.server.messageGetWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
        String s = in.next();
        while(!this.server.messageIsValidCard(nickName, s)){
            System.out.println("Error: choose one of these cards to shoot:");
            this.server.messageGetWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
            s = in.next();
        }
        System.out.println(this.server.messageGetReloadCost(s, nickName));
        System.out.println(this.server.messageGetDescriptionWC(s,nickName));

        switch(s){
            case "Cyberblade":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Electroscythe":
                wPrompt.shootToUser2(game, nickName);
                break;

            case "Flamethrower":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Furnace":
                wPrompt.shootToUser3(game, nickName);
                break;

            case "Grenade Launcher":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Heatseeker":
                wPrompt.shootToUser3(game, nickName);
                break;

            case "Hellion":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Lock Rifle":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Machine Gun":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Plasma Gun":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Power Glove":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Railgun":
                wPrompt.shootToUser3(game, nickName);
                break;

            case "Rocket Launcher":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Shockwave":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Shotgun":
                wPrompt.shootToUser3(game, nickName);
               break;

            case "Sledgehammer":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "T.H.O.R.":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Tractor Beam":
                wPrompt.shootToUser1(game, nickName);
               break;

            case "Vortex Cannon":
                wPrompt.shootToUser1(game, nickName);
                break;

            case "Whisper":
                wPrompt.shootToUser4(game, nickName);
                break;

            case "ZX-2":
                wPrompt.shootToUser3(game, nickName);
                break;
        }
    }

    private void grabFirstAction() {
        Scanner in = new Scanner(System.in);
        Integer[] directions = null;
        List<Integer> l = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        String wCard;
        String weaponSlot = null;
        System.out.println("Enter the direction(s) where you want to move, or 0 if you want to remain in your cell:");
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
            System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
            while ((in.hasNext()))
                lP.add(in.next());
        }
        while (!this.server.messageIsValidFirstActionGrab(nickName, l.toArray(directions), wCard, weaponSlot, lC, lP)){
            System.out.println("Error: retry");
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
                System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                while ((in.hasNext()))
                    lP.add(in.next());
            }
        }
        this.server.messageFirstActionGrab(nickName, l.toArray(directions), wCard, lC, lP );
        if(this.server.messageIsDiscard()) {
            System.out.println("Choose the weapon card you want to discard");
            String wCDiscard = in.next();
            this.server.messageDiscardWeaponCard(nickName, weaponSlot, wCDiscard);
        }
    }

    @Override
    public void action2() {
        System.out.println("---------START SECOND ACTION---------");
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

    private void moveSecondAction() {
        Scanner in = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        while(!this.server.messageIsValidSecondActionMove(l)) {
            System.out.println("Choose the sequence of movement you want to do");
            while (in.hasNext())
                l.add(in.nextInt());
        }
        this.server.messageSecondActionMove(this.nickName, l);
    }

    private void shootSecondAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot: ");
        this.server.messageGetWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
        String s = in.next();
        while(!this.server.messageIsValidCard(nickName, s)){
            System.out.println("Error: choose one of these cards to shoot: ");
            this.server.messageGetWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
            s = in.next();
        }
        System.out.println(this.server.messageGetReloadCost(s, nickName));
        System.out.println(this.server.messageGetDescriptionWC(s,nickName));

        switch(s) {
            case "Cyberblade":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Electroscythe":
                wPrompt.shoot2ToUser2(game, nickName);
                break;

            case "Flamethrower":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Furnace":
                wPrompt.shoot2ToUser3(game, nickName);
                break;

            case "Grenade Launcher":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Heatseeker":
                wPrompt.shoot2ToUser3(game, nickName);
                break;

            case "Hellion":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Lock Rifle":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Machine Gun":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Plasma Gun":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Power Glove":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Railgun":
                wPrompt.shoot2ToUser3(game, nickName);
                break;

            case "Rocket Launcher":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Shockwave":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Shotgun":
                wPrompt.shoot2ToUser3(game, nickName);
                break;

            case "Sledgehammer":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "T.H.O.R.":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Tractor Beam":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Vortex Cannon":
                wPrompt.shoot2ToUser1(game, nickName);
                break;

            case "Whisper":
                wPrompt.shoot2ToUser4(game, nickName);
                break;

            case "ZX-2":
                wPrompt.shoot2ToUser3(game, nickName);
                break;
        }
    }

    private void grabSecondAction() {
        Scanner in = new Scanner(System.in);
        Integer[] directions = null;
        List<Integer> l = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        String wCard;
        String weaponSlot = null;
        System.out.println("Enter the direction(s) where you want to move, or 0 if you want to remain in your cell:");
        while (in.hasNext())
            l.add(in.nextInt());
        System.out.println("Enter the WeaponCard you want to buy, if you want:");
        wCard = in.next();
        if(!wCard.equals("")) {
            System.out.println("Enter the number of the WeaponSlot from which you want to buy the card:");
            weaponSlot = in.next();
            System.out.println("Enter the colour(s) of the required AmmoCube(s) to buy the card, if necessary:");
            while ((in.hasNext()))
                lC.add(Colour.valueOf(in.next()));
            System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
            while ((in.hasNext()))
                lP.add(in.next());
        }
        while (!this.server.messageIsValidSecondActionGrab(nickName, l.toArray(directions), wCard, weaponSlot, lC, lP)){
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
            }
        }
        this.server.messageSecondActionGrab(nickName, l.toArray(directions), wCard, lC, lP );
        if(this.server.messageIsDiscard()) {
            System.out.println("Choose the weapon card you want to discard");
            String wCDiscard = in.next();
            this.server.messageDiscardWeaponCard(nickName, weaponSlot, wCDiscard);
        }
    }

    @Override
    public boolean doYouWantToUsePUC(){
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to use the power up card now?");
        return (in.next().equals("Yes") || in.next().equals("yes"));
    }

    @Override
    public void usePowerUpCard() {
        Scanner in = new Scanner(System.in);
        String namePC;
        List<String> lS = new LinkedList<>();
        System.out.println("Enter which PowerUpCard you want to use:");
        this.server.messageGetPowerUpCard(nickName).stream().forEach(System.out::println);
        namePC = in.next();
        this.server.messageGetDescriptionPUC(namePC, nickName);
        switch (namePC){
            case "Tagback Grenade":
                System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                lS.add(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(nickName, namePC, lS, null)){
                    System.out.println("Error: retry");
                    System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                    lS.add(in.next());
                }
                this.server.messageUsePowerUpCard(nickName, namePC, lS, null);
                break;

            case "Targeting Scope":
                System.out.println("Enter the nickname of one or more players you have damaged:");
                while(in.hasNext())
                    lS.add(in.next());
                System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                Colour c = Colour.valueOf(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(nickName, namePC, lS, c)){
                    System.out.println("Error: retry");
                    System.out.println("Enter the nickname of one or more players you have damaged:");
                    while(in.hasNext())
                        lS.add(in.next());
                    System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                    c = Colour.valueOf(in.next());
                }
                this.server.messageUsePowerUpCard(nickName, namePC, lS, c);
                break;

            case "Newton":
                System.out.println("Enter the nickname of a player:");
                lS.add(in.next());
                System.out.println("Enter the direction(s) where you want the enemy to go:");
                while(in.hasNext())
                    lS.add(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(nickName, namePC, lS, null)){
                    System.out.println("Error: retryt");
                    System.out.println("Enter the nickname of a player:");
                    lS.add(in.next());
                    System.out.println("write the directions where you want the enemy to go:");
                    while(in.hasNext())
                        lS.add(in.next());
                }
                this.server.messageUsePowerUpCard(nickName, namePC, lS, null);
                break;

            case "Teleporter":
                System.out.println("Enter the coordinates of the cell you want to move (x y):");
                lS.add(in.next());
                lS.add(in.next());
                while(!this.server.messageIsValidUsePowerUpCard(nickName, namePC, lS, null)) {
                    System.out.println("Error: retry");
                    System.out.println("Enter the coordinates of the cell you want to move:");
                    lS.add(in.next());
                    lS.add(in.next());
                }
                this.server.messageUsePowerUpCard(nickName, namePC, lS, null);
                break;
        }

    }
    
    @Override
    public void reload() {               //the player knows everything!
        Scanner in = new Scanner(System.in);
        this.server.messageGetWeaponCardUnloaded(this.nickName).stream().forEach(System.out::println);
        int i = 0;
        while(i == 0){
            System.out.println("Choose the weapon card you want to reload");
            String s = in.nextLine();
            System.out.println("Enter 0 if you want to reload another card, otherwise 1");
            i = in.nextInt();
            if(this.server.messageIsValidReload())
                this.server.messageReload(this.nickName, s, i);
            else
                System.out.println("You can't reload now: pay attention to the rules! (you can find the manual inside the box)");
        }
    }

    @Override
    public void scoring() {
        if(this.server.messageIsValidScoring())
            this.server.messageScoring();
        else
            System.out.println("It is not time for scoring: pay attention to the rule! (you can find the rule book near the board you bought)");
    }

    @Override
    public void newSpawnPoint() {
        if(this.server.messageGetDeadList().contains(this.nickName)) {
            System.out.println("Enter the PowerUp card you want to discard:");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            while(!this.server.messageIsValidDiscardCardForSpawnPoint()){
                System.out.println("Enter the PowerUp card you want to discard:");
                s = in.nextLine();
            }
                    this.server.messageDiscardCardForSpawnPoint(this.nickName, s);
        }
        else
            System.out.println("What are you doing, man?");
    }

    @Override
    public void replace() {
        if(this.server.messageIsValidToReplace())
            this.server.messageReplace();
        else
            System.out.println("Time for replacing has not come yet: pay attention to the rule! (you can find the rule book near the board you bought)");
    }


    @Override
    public void finalFrenzyTurn(){
        Scanner in = new Scanner(System.in);
        List<String> l = new LinkedList<>();
        System.out.println("This is the final turn. final frenzy mode:\nchoose the moves you want to do according to the fact you are before or after the player who started the game");
        while (in.hasNext())
            l.add(in.next());
        while(!this.server.messageIsValidFinalFrenzyAction(nickName, l)){
            System.out.println("Error: repeat");
            System.out.println("This is the final turn. final frenzy mode:\nchoose the moves you want to do according to the fact you are before or after the player who started the game");
            while (in.hasNext())
                l.add(in.next());
        }
        for(String s : l){
            switch (s) {
                case "1":
                    System.out.println("write the direction you want to move");
                    int i = in.nextInt();
                    System.out.println("Write the card(s) you want to reload:"+this.server.messageGetWeaponCardUnloaded(nickName));
                    List<String> lW = new LinkedList<>();
                    while (in.hasNext())
                        lW.add(in.next());
                    System.out.println("Write the card you want to use:"+this.server.messageGetWeaponCard(nickName));
                    String wC = in.next();
                    List<Integer> lI = new LinkedList<>();
                    List<String> lS = new LinkedList<>();
                    List<Colour> lC = new LinkedList<>();
                    List<String> lP = new LinkedList<>();
                    System.out.println("Enter the number of the effect you want to use:");
                    while (in.hasNext())
                        lI.add(in.nextInt());
                    System.out.println("Enter the relevant strings for the card:");
                    while (in.hasNext())
                        lS.add(in.next());
                    System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect:");
                    while (in.hasNext())
                        lC.add(Colour.valueOf(in.next()));
                    game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                    while (in.hasNext())
                        lP.add(in.next());
                    while(!this.server.messageIsValidFinalFrenzyAction1(nickName, i, wC, lI, lS, lC, lP)){
                        System.out.println("Error: repeat");
                        System.out.println("write the direction you want to move");
                        i = in.nextInt();
                        System.out.println("Write the card(s) you want to reload:"+this.server.messageGetWeaponCardUnloaded(nickName));
                        while (in.hasNext())
                            lW.add(in.next());
                        System.out.println("Write the card you want to use:"+this.server.messageGetWeaponCard(nickName));
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
                        game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                        while (in.hasNext())
                            lP.add(in.next());
                    }
                    this.server.messageFinalFrenzyAction1(nickName, i, lW, wC, lI, lS, lC, lP);
                    break;

                case "2":
                    System.out.println("write the direction(s) you want to move");
                    List<Integer> list = new LinkedList<>();
                    while(in.hasNext())
                        list.add(in.nextInt());
                     while(!this.server.messageIsValidFinalFrenzyAction2(nickName, list)) {
                         System.out.println("Error: repeat");
                         System.out.println("write the direction(s) you want to move");
                         while (in.hasNext())
                             list.add(in.nextInt());
                     }
                     this.server.messageFinalFrenzyAction2(nickName, list);
                     break;

                case "3":
                    List<Integer> list2 = new LinkedList<>();
                    List<Colour> lC2 = new LinkedList<>();
                    List<String> lP2 = new LinkedList<>();
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
                    }
                    while(!this.server.messageIsValidFinalFrenzyAction3(nickName, list2, wCard, weaponSlot, lC2, lP2)){
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
                        }
                    }
                    this.server.messageFinalFrenzyAction3(nickName, list2, wCard, lC2, lP2);
                    break;

                case "4":
                    System.out.println("write the direction(s) you want to move");
                    List<Integer> list3 = new LinkedList<>();
                    while(in.hasNext())
                        list3.add(in.nextInt());
                    System.out.println("Write the card(s) you want to reload:"+this.server.messageGetWeaponCardUnloaded(nickName));
                    List<String> lW2 = new LinkedList<>();
                    while (in.hasNext())
                        lW2.add(in.next());
                    System.out.println("Write the card you want to use:"+this.server.messageGetWeaponCard(nickName));
                    String wC2 = in.next();
                    List<Integer> lI2 = new LinkedList<>();
                    List<String> lS2 = new LinkedList<>();
                    List<Colour> lC3 = new LinkedList<>();
                    List<String> lP3 = new LinkedList<>();
                    System.out.println("Enter the number of the effect you want to use:");
                    while (in.hasNext())
                        lI2.add(in.nextInt());
                    System.out.println("Enter the relevant strings for the card:");
                    while (in.hasNext())
                        lS2.add(in.next());
                    System.out.println("Enter the colour(s) of the required AmmoCube(s) needed for the effect:");
                    while (in.hasNext())
                        lC3.add(Colour.valueOf(in.next()));
                    game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                    System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                    while (in.hasNext())
                        lP3.add(in.next());
                    while(!this.server.messageIsValidFinalFrenzyAction4(nickName, list3, wC2, lI2, lS2, lC3, lP3)) {
                        System.out.println("Error: repeat");
                        System.out.println("write the direction(s) you want to move");
                        while (in.hasNext())
                            list3.add(in.nextInt());
                        System.out.println("Write the card(s) you want to reload:" + this.server.messageGetWeaponCardUnloaded(nickName));
                        while (in.hasNext())
                            lW2.add(in.next());
                        System.out.println("Write the card you want to use:" + this.server.messageGetWeaponCard(nickName));
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
                        game.getPowerUpCard(nickName).stream().forEach(System.out::println);
                        System.out.println("Enter the PowerUpCard you want to use for paying during your turn:");
                        while (in.hasNext())
                            lP3.add(in.next());
                    }
                    this.server.messageFinalFrenzyAction4(nickName, list3, lW2, wC2, lI2, lS2, lC3, lP3);
                    break;

                case "5":
                    weaponSlot = null;
                    List<Colour> lC4= new LinkedList<>();
                    List<String> lP4= new LinkedList<>();
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
                    }
                    while(!this.server.messageIsValidFinalFrenzyAction5(nickName, list4, wCard, weaponSlot, lC4, lP4)){
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
                        }
                    }
                    this.server.messageFinalFrenzyAction5(nickName, list4, wCard, lC4, lP4);
                    break;
            }
        }
        this.server.messageFinalFrenzyTurnScoring();
    }

    @Override
    public void endFinalFrenzy(){
        this.server.messageEndTurnFinalFrenzy();
        System.out.println("We are calculating the result");
    }

    @Override
    public void finalScoring(){
        this.server.messageFinalScoring();
        System.out.println("FINAL SCORE");
        this.server.messageGetPlayers().stream().forEach(System.out::print);
        System.out.println();
        this.server.messageGetScore().stream().forEach(System.out::print);
        System.out.println();
        System.out.println("END GAME");
    }
}