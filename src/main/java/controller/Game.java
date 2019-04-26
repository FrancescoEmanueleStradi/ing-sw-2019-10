package controller;

import model.*;
import model.board.Cell;
import model.board.WeaponSlot;
import model.cards.AmmoCard;
import model.cards.PowerUpCard;
import model.Position;
import model.cards.WeaponCard;
import model.cards.weaponcards.*;
import model.player.AmmoCube;
import model.player.DamageToken;
import model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static controller.GameState.*;

public class Game {                                 //Cli or Gui -- Rmi or Socket

    private GameState gameState;
    private boolean init = false;
    private Grid grid;
    private boolean discard = false;
    private List<String> deadList = new LinkedList<>();

    public boolean gameIsNotStarted(){
        return this.grid.getPlayers().isEmpty();
    }

   public void gameStart(String nickName, Colour c) throws InvalidColourException{
       if(!init) {
           init = true;
           this.grid = new Grid();
           Player p = new Player(nickName, c, true);
           this.grid.addPlayer(p);               //first state
           this.gameState = START;
       }
   }



//----------------------------------------------------------------------------------------------------




   public boolean isValidAddPlayer(String nickName, Colour c){
       return ((init) && !(this.grid.getPlayersNickName().contains(nickName)) && !(this.grid.getPlayersColour().contains(c)));
   }


   public void addPlayer(String nickName, Colour c) throws InvalidColourException{
           Player p = new Player(nickName, c, false);
           this.grid.addPlayer(p);
    }

    public boolean removePlayer(String nickName){
        if(init) {
            this.grid.removePlayer(this.grid.getPlayerObject(nickName));
            return true;
        }
        return false;
    }



//----------------------------------------------------------------------------------------------------




    public boolean isValidReceiveType() {
        return this.gameState.equals(START);    //also check if int type is 1, 2, 3 or 4?
    }

   public void receiveType(int type){
        this.grid.setType(type);                 //find a condition
        this.grid.setUpAmmoCard();
        this.gameState = INITIALIZED;
   }

   public List<PowerUpCard> giveTwoPUCard(String nickName){
        Player p = this.grid.getPlayerObject(nickName);
        if(p.getCell() == null) {
            List<PowerUpCard> l = new LinkedList<>();
            l.add(this.grid.pickPowerUpCard());
            l.add(this.grid.pickPowerUpCard());
            return l;
        }
                                            //View control -> empty list
       return new LinkedList<>();
   }

   public boolean isValidPickAndDiscard(String nickName){
        Player p = this.grid.getPlayerObject(nickName);
        return (p.getCell() == null);
   }

   public void pickAndDiscardCard(String nickName, PowerUpCard p1, PowerUpCard p2){     //p1 choose, p2 discard
       Player p = this.grid.getPlayerObject(nickName);
       p.addPowerUpCard(p1);
       chooseSpawnPoint(p2.getValue().getC(), p);
   }


   public void chooseSpawnPoint(Colour c, Player p){
           if(c.equals(Colour.YELLOW))
               this.grid.move(p, new Position(2,3));
           if(c.equals(Colour.RED))
               this.grid.move(p, new Position(1,0));            //view ask the choice
           if(c.equals(Colour.BLUE))
               this.grid.move(p, new Position(0,2));
           this.gameState = STARTTURN;
   }



   //----------------------------------------------------------------------------------------------------




   public boolean isValidFirstActionShoot(Player p){                //TODO switch with isValid
       return(this.gameState.equals(STARTTURN));
    }





   private void firstActionShootNotAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS){                    //is better to use a file?
        switch(nameWC){                                                                                                              //TODO pay for the effect
            case "Cyberblade":
                int x = 0;
                for(Integer i : lI) {
                    if (lI.get(i) == 1) {
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        x = 1;
                    }
                    if (lI.get(i) == 2) {
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(1), lS.get(2));
                    }
                    if ((x == 1) && lI.get(i) == 3)
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p , this.grid.getPlayerObject(lS.get(3)));
                }
                break;
            case "Electroscythe":
                if(lI.get(0) == 1)
                    ((Electroscythe) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p);
                if(lI.get(0) == 2)
                    ((Electroscythe) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p);
                break;
            case "Flamethrower":
                if(lI.get(0) == 1)
                    ((Flamethrower) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)));
                if(lI.get(0) == 2)
                    ((Flamethrower) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(0), lS.get(1), lS.get(2), lS.get(3));
                break;
            case "Furnace":
                if(lI.get(0) == 1)
                    ((Furnace) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, Colour.valueOf(lS.get(0)));
                if(lI.get(0) == 2)
                    ((Furnace) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(0), lS.get(1));
                break;
            case "Grenade Launcher":
                for(int i : lI){
                    if(i == 1){
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).moveEnemy(this.grid, this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));
                    }
                    if(i == 2)
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(2), lS.get(3));
                }
                break;
            case "Heatseeker":
                ((Heatseeker) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Hellion":
                if(lI.get(0) == 1)
                    ((Hellion) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 2)
                    ((Hellion) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Lock Rifle":
                int y = 0;
                if(lI.get(0) == 1) {
                    ((LockRifle) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    y = 1;
                }
                if(lI.get(1) == 2 && y == 1)
                    ((LockRifle) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(1)));
                break;
            case "Machine Gun":
                if(lI.get(0) == 1)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)));
                if(lI.get(1) == 2)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(2)));
                if(lI.get(2) == 3)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p,this.grid.getPlayerObject(lS.get(3)), this.grid.getPlayerObject(lS.get(4)));
                break;
            case "Plasma Gun":
                break;
            case "Power Glove":
                break;
            case "Railgun":
                break;
            case "Rocket Launcher":
                break;
            case "Shockwave":
                break;
            case "Shotgun":
                break;
            case "Sledgehammer":
                break;
            case "T.H.O.R.":
                break;
            case "Tractor Beam":
                break;
            case "Vortex Cannon":
                break;
            case "Whisper":
                break;
            case "ZX-2":
                break;


        }





           this.gameState = ACTION1;
    }



    public void firstActionShoot(){
        //TODO
    }

//----------------------------------------------------------------------------------------------------



   private void move(Player p, List<Integer> directions) {
       for(int i = 0; i < directions.size(); i++) {
           this.grid.move(p, directions.get(i));    //view will tell player if there's a wall
       }
   }


   public boolean isValidFirstActionMove(List<Integer> directions){
        return (this.gameState.equals(STARTTURN) && (directions.size() < 4) && (!directions.isEmpty()));
   }

    public void firstActionMove(String s, List<Integer> directions){ //player p moves 1,2,3 cells: directions contains every direction from cell to cell
                Player p = this.grid.getPlayerObject(s);
                move(p, directions);
                this.gameState = ACTION1;
    }





//----------------------------------------------------------------------------------------------------




    private void giveWhatIsOnAmmoCard(Player p, AmmoCard card) {
       if(card.ispC())
           p.addPowerUpCard(this.grid.pickPowerUpCard());
       for(AmmoCube cube : card.getaC())
           p.addNewAC(cube);
    }

    private boolean canPay(WeaponCard w, List<AmmoCube> l){
        List<AmmoCube> l2 = new ArrayList<>(Arrays.asList(w.getReloadCost()));          //this way the original array is not modified
        l2.remove(0);                                             //containsAll does not work: AmmoCubes have not the same references!
        int count = 0;
        for(AmmoCube a : l) {
            for(AmmoCube aCost : l2) {
                if(a.getC().equals(aCost.getC())) {
                    count++;
                    break;
                }
                if(count == l2.size())
                    return true;
            }
        }
        return false;

    }

    private void grabNotAdrenaline(Player p, int direction, WeaponCard wCard, List<AmmoCube> lA, List<PowerUpCard> lP) {
        this.grid.move(p, direction);
        if(p.getCell().getStatus() == 0)
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
        else if((p.getCell().getStatus() == 1) && wCard != null) {
            if(canPay(wCard, choosePayment(lA, lP)))
                p.payWeaponCard(lA, lP);
        }
        if(p.getwC().size() > 3)
            this.discard = true;                    //View saved the Weapon Slot
    }

    public void discardWeaponCard(Player p, WeaponSlot wS, WeaponCard wC){
       p.removeWeaponCard(wC);
       if(wS.getCard1() == null){
           wS.setCard1(wC);
           return;
       }
        if(wS.getCard2() == null){
            wS.setCard2(wC);
            return;
        }
        if(wS.getCard3() == null)
            wS.setCard3(wC);
    }

    private void grabAdrenaline(Player p, int[] d, WeaponCard w, List<AmmoCube> lA, List<PowerUpCard> lP) {
        this.grid.move(p, d[0]);
        if(d.length == 2)
            this.grid.move(p, d[1]);
        if(p.getCell().getStatus() == 0)
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
        else if((p.getCell().getStatus() == 1) && w != null) {
            if(canPay(w, choosePayment(lA, lP)))
                p.payWeaponCard(lA, lP);
        }
        if(p.getwC().size() > 3)
            this.discard = true;
    }

    private List<AmmoCube> choosePayment(List<AmmoCube> lA, List<PowerUpCard> lP){
        List<AmmoCube> l = new LinkedList<>();
        if(!lA.isEmpty())
            l.addAll(lA);
        if(!lA.isEmpty()) {
            for (PowerUpCard p : lP)
                l.add(p.getValue());
        }
        return l;
    }

    public boolean isValidFirstActionGrab(int[] directions){
        return(this.gameState.equals(STARTTURN) && (directions.length <= 2));
    }

    public void firstActionGrab(Player p, int[] directions, WeaponCard wCard, List<AmmoCube> l, List<PowerUpCard> lP){ //directions contains where p wants to go. directions contains '0' if p doesn't want to move and only grab
        if(!(p.isAdrenaline1()) /*&& (directions.length == 1)*/)
                grabNotAdrenaline(p, directions[0], wCard, l, lP);

        if(p.isAdrenaline1()||p.isAdrenaline2()){
                grabAdrenaline(p, directions, wCard, l, lP);
        }

        this.gameState = ACTION1;
    }





//----------------------------------------------------------------------------------------------------






    public boolean isValidSecondActionMove(List<Integer> directions){
        return (this.gameState.equals(ACTION1) && (directions.size() < 4) && (!directions.isEmpty()));
    }



    public void secondActionMove(String s, List<Integer> directions){ //player p moves 1,2,3 cells: directions contains every direction from cell to cell
        Player p = this.grid.getPlayerObject(s);
        move(p, directions);
        this.gameState = ACTION2;
    }





//----------------------------------------------------------------------------------------------------





    public boolean isValidSecondActionGrab(int[] directions){
        return(this.gameState.equals(ACTION1) && (directions.length <= 2));
    }


    public void secondActionGrab(Player p, int[] directions, WeaponCard wCard, List<AmmoCube> l, List<PowerUpCard> lP){ //directions contains where p wants to go. directions contains '0' if p doesn't want to move and only grab
        if(!(p.isAdrenaline1()) /*&& (directions.length == 1)*/)
            grabNotAdrenaline(p, directions[0], wCard, l, lP);

        if(p.isAdrenaline1()||p.isAdrenaline2()){
            grabAdrenaline(p, directions, wCard, l, lP);
        }

        this.gameState = ACTION2;
    }






//----------------------------------------------------------------------------------------------------





    public boolean usePowerUpCard(Player p, String s){
        if(this.gameState.equals(STARTTURN) || this.gameState.equals(ACTION1) || this.gameState.equals(ACTION2)){
            //TODO

            return true;
        }
        return false;
    }






//----------------------------------------------------------------------------------------------------





    public boolean isValidReload(){
        return this.gameState.equals(ACTION2);
    }

    public void reload(String p1, String s, int end){  // end is 1 if the player has finished to reload
           Player p = this.grid.getPlayerObject(p1);
           if(p.checkAmmoCube(p.getWeaponCardObject(s).getReloadCost())){
                p.getWeaponCardObject(s).reload();
                p.removeArrayAC(p.getWeaponCardObject(s).getReloadCost());
           }
           if(end == 1)
               this.gameState = RELOADED;

    }


    private void death(Player p){
       p.changeCell(null);
       if(p.getpB().getDamages().getDamageTr()[11] != null) {
           int n = this.grid.getBoard().substituteSkull(2);
           this.grid.getBoard().getK().getC()[n] = p.getpB().getDamages().getDT(11).getC();
           p.getpB().addMark(new DamageToken(p.getpB().getDamages().getDT(11).getC()));      //OverKill
        }
        else {
            int n = this.grid.getBoard().substituteSkull(1);
            this.grid.getBoard().getK().getC()[n] = p.getpB().getDamages().getDT(10).getC();
        }
        if(p.getpB().getPoints().getPoints().size()>1)
            p.getpB().getPoints().remove();
        p.getpB().getDamages().clean();
        p.addPowerUpCard(this.grid.pickPowerUpCard());

    }

    public boolean isValidScoring(){
        return this.gameState.equals(RELOADED) && (this.grid.whoIsDead()!=null);
    }

    public void scoring(){
           int c = 0;
           for(Player p : this.grid.whoIsDead()) {
               this.deadList.add(p.getNickName());
               this.grid.scoringByColour(p.getpB().getDamages().getDamageTr()[0].getC(), 1);
               for(int i = 1; i < p.getpB().getPoints().getPoints().size(); i++)
                   this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(i-1), p.getpB().getPoints().getInt(i));
               p.getpB().getDamages().cleanL();
               c++;
               if(c == 2)
                   this.grid.scoringByColour(p.getpB().getDamages().getDamageTr()[10].getC(),1);        //Double Kill
               this.death(p);
               this.gameState = DEATH;
           }

    }

    public List<String> getDeadList() {
        return deadList;
    }

    public boolean isValidDiscardCardForSpawnPoint(){
        return this.gameState == DEATH;
    }

    public void discardCardForSpawnPoint(String pS, String s1){      //Attention to the view
           Player p = this.grid.getPlayerObject(pS);
           PowerUpCard p1 = p.getPowerUpCardObject(s1);
           chooseSpawnPoint(p1.getC(), p);
           p.removePowerUpCard(p1);
           this.gameState = ENDTURN;
           this.deadList.clear();
    }

    public boolean isValidToReplace(){
        return this.gameState == ENDTURN;
    }

    public void replace(){

       this.grid.replaceAmmoCard();
       this.grid.replaceWeaponCard();
       this.gameState = STARTTURN;
    }
}
