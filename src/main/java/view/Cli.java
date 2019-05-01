package view;

import controller.Game;
import model.Colour;
import model.cards.PowerUpCard;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Cli extends View{

    private Game game;
    private String nickName;                //TODO maybe nickName and colour should go in the client and not hear beacuse View must be the RMI registry
    private Colour colour;
    private CLIWeaponPrompt wPrompt;


    public Game getGame() {
        return game;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public void askNameAndColour() {
        Scanner in = new Scanner(System.in);
        if (this.game.gameIsNotStarted()) {
            System.out.println("WELCOME");
            System.out.println("Enter your name:");
            this.nickName = in.nextLine();
            System.out.println("Enter your colour (YELLOW, BLUE, GREEN, PURPLE, BLACK):");
            String stringColour = in.nextLine();
            this.colour = Colour.valueOf(stringColour);
            this.game.gameStart(nickName, colour);
            System.out.println("Choose the type of arena (1, 2, 3, 4):");
            int type = in.nextInt();
            while(!this.game.isValidReceiveType(type)){
                System.out.println("Error: retry");
                System.out.println("Choose the type of arena (1, 2, 3, 4):");
                type = in.nextInt();
            }
            this.game.receiveType(type);
            System.out.println("---------GENERATING ARENA...---------");
            return;
        }
        System.out.println("---------WAITING FOR PLAYERS TO JOIN---------");
        System.out.println("Enter your name:");
        this.nickName = in.nextLine();
        System.out.println("Enter your colour:");
        String stringColour = in.nextLine();
        this.colour = Colour.valueOf(stringColour);
        while(!this.game.isValidAddPlayer(this.nickName, this.colour)){
            System.out.println("Enter your name:");
            this.nickName = in.nextLine();
            System.out.println("Enter your colour:");
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
        System.out.println("---------SPAWN POINT SELECT---------");
        System.out.println("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                "colour of your spawn point");
        String p1 = in.nextLine();
        while(!this.game.isValidPickAndDiscard(this.nickName)) {
            System.out.println("Error: retry");
            System.out.println("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                    "colour of your spawn point");
            p1 = in.nextLine();
        }
            if(l.get(0).getCardName().equals(p1))
                this.game.pickAndDiscardCard(this.nickName, l.get(0), l.get(1));
            else
                this.game.pickAndDiscardCard(this.nickName, l.get(1), l.get(0));
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
        while(!this.game.isValidFirstActionMove(l)) {
            System.out.println("Choose the sequence of movement you want to do");
            while (in.hasNext())
                l.add(in.nextInt());
        }
        this.game.firstActionMove(this.nickName, l);
    }

    private void shootFirstAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot:");
        this.game.getWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
        String s = in.next();
        while(!this.game.isValidCard(nickName, s)){
            System.out.println("Error: choose one of these cards to shoot:");
            this.game.getWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
            s = in.next();
        }
        System.out.println(this.game.getReloadCost(s, nickName));
        System.out.println(this.game.getDescriptionWC(s,nickName));

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
        while (!this.game.isValidFirstActionGrab(nickName, l.toArray(directions), wCard, weaponSlot, lC, lP)){
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
        this.game.firstActionGrab(nickName, l.toArray(directions), wCard, lC, lP );
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
        while(!this.game.isValidSecondActionMove(l)) {
            System.out.println("Choose the sequence of movement you want to do");
            while (in.hasNext())
                l.add(in.nextInt());
        }
        this.game.firstActionMove(this.nickName, l);
    }

    private void shootSecondAction() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of these cards to shoot: ");
        this.game.getWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
        String s = in.next();
        while(!this.game.isValidCard(nickName, s)){
            System.out.println("Error: choose one of these cards to shoot: ");
            this.game.getWeaponCardLoaded(this.nickName).stream().forEach(System.out::println);
            s = in.next();
        }
        System.out.println(this.game.getReloadCost(s, nickName));
        System.out.println(this.game.getDescriptionWC(s,nickName));

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
        while (!this.game.isValidSecondActionGrab(nickName, l.toArray(directions), wCard, weaponSlot, lC, lP)){
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
        this.game.secondActionGrab(nickName, l.toArray(directions), wCard, lC, lP );
    }

    public void usePowerUpCard() {
        Scanner in = new Scanner(System.in);
        String namePC;
        List<String> lS = new LinkedList<>();
        System.out.println("Enter which PowerUpCard you want to use:");
        this.game.getPowerUpCard(nickName).stream().forEach(System.out::println);
        namePC = in.next();
        switch (namePC){
            case "Tagback Grenade":
                System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                lS.add(in.next());
                while(!this.game.isValidUsePowerUpCard(nickName, namePC, lS, null)){
                    System.out.println("Error: retry");
                    System.out.println("Enter the nickname of a player you can see and that gave you damage:");
                    lS.add(in.next());
                }
                this.game.usePowerUpCard(nickName, namePC, lS, null);
                break;

            case "Targeting Scope":
                System.out.println("Enter the nickname of one or more players you have damaged:");
                while(in.hasNext())
                    lS.add(in.next());
                System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                Colour c = Colour.valueOf(in.next());
                while(!this.game.isValidUsePowerUpCard(nickName, namePC, lS, c)){
                    System.out.println("Error: retry");
                    System.out.println("Enter the nickname of one or more players you have damaged:");
                    while(in.hasNext())
                        lS.add(in.next());
                    System.out.println("Enter the colour of the AmmoCube you want to use to pay:");
                    c = Colour.valueOf(in.next());
                }
                this.game.usePowerUpCard(nickName, namePC, lS, c);
                break;

            case "Newton":
                System.out.println("Enter the nickname of a player:");
                lS.add(in.next());
                System.out.println("Enter the direction(s) where you want the enemy to go:");
                while(in.hasNext())
                    lS.add(in.next());
                while(!this.game.isValidUsePowerUpCard(nickName, namePC, lS, null)){
                    System.out.println("Error: retryt");
                    System.out.println("Enter the nickname of a player:");
                    lS.add(in.next());
                    System.out.println("write the directions where you want the enemy to go:");
                    while(in.hasNext())
                        lS.add(in.next());
                }
                this.game.usePowerUpCard(nickName, namePC, lS, null);
                break;

            case "Teleporter":
                System.out.println("Enter the coordinates of the cell you want to move (x y):");
                lS.add(in.next());
                lS.add(in.next());
                while(!this.game.isValidUsePowerUpCard(nickName, namePC, lS, null)) {
                    System.out.println("Error: retry");
                    System.out.println("Enter the coordinates of the cell you want to move:");
                    lS.add(in.next());
                    lS.add(in.next());
                }
                this.game.usePowerUpCard(nickName, namePC, lS, null);
                break;
        }

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
            System.out.println("Enter the PowerUp card you want to discard:");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            while(!this.game.isValidDiscardCardForSpawnPoint()){
                System.out.println("Enter the PowerUp card you want to discard:");
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
