package controller;

import model.*;
import model.board.WeaponSlot;
import model.cards.AmmoCard;
import model.cards.PowerUpCard;
import model.Position;
import model.cards.WeaponCard;
import model.player.AmmoCube;
import model.player.DamageToken;
import model.player.Player;

import java.util.LinkedList;
import java.util.List;

import static controller.GameState.*;

public class Game {                                 //Cli or Gui -- Rmi or Socket

    private GameState gameState;
    private boolean init = false;
    private Grid grid;
    private boolean discard = false;

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
               this.grid.move(p, new Position(1,0));
           if(c.equals(Colour.BLUE))
               this.grid.move(p, new Position(0,2));
           this.gameState = STARTTURN;
   }
                                                            //view ask the choice

   public boolean firstActionShoot(Player p){
       if(this.gameState.equals(STARTTURN)){
           //TODO

           this.gameState = ACTION1;
           return true;
       }
       return false;
   }

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

    private void giveWhatIsOnAmmoCard(Player p, AmmoCard card) {
       if(card.ispC())
           p.addPowerUpCard(this.grid.pickPowerUpCard());
       for(AmmoCube cube : card.getaC())
           p.addNewAC(cube);
    }

    private void GrabNotAdrenaline(Player p, int direction, WeaponCard wCard ) {
        this.grid.move(p, direction);
        if(p.getCell().getStatus() == 0)
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
        else if((p.getCell().getStatus() == 1) && wCard != null) {
            //if(p.canPay(wCard))
                //p.payWeaponCard(wCard);
        }
        if(p.getwC().size() > 3)
            this.discard = true;                    //View saved the Weapon Slot
    }

    private void discardWeaponCard(Player p, WeaponSlot wS, WeaponCard wC){
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

    private void GrabAdrenaline() {

    }

    public boolean firstActionGrab(Player p, int[] directions, WeaponCard wCard){ //directions contains where p wants to go. directions contains '0' if p doesn't want to move and only grab
        if(this.gameState.equals(STARTTURN)){
            if(!(p.isAdrenaline1()) && (directions.length == 1))
                GrabNotAdrenaline(p, directions[0], wCard);
            else return false;

            if(p.isAdrenaline1()||p.isAdrenaline2()){

            }

            this.gameState = ACTION1;
            return true;
        }
        return false;
    }

    public boolean secondActionMove(Player p, List<Integer> directions){ //player p moves 1,2,3 cells: directions contains every direction from cell to cell
        if(this.gameState.equals(ACTION1)) {
            if (directions.size() < 4) {
                move(p, directions);
                this.gameState = ACTION2;
                return true;
            }
        }
        return false;
    }


    public boolean usePowerUpCard(Player p, String s){
        if(this.gameState.equals(STARTTURN) || this.gameState.equals(ACTION1) || this.gameState.equals(ACTION2)){
            //TODO

            return true;
        }
        return false;
    }

    public boolean reload(Player p, String s, int end){  // end is 1 if the player has finished to reload
       if(this.gameState.equals(ACTION2)){
           if(p.checkAmmoCube(p.getWeaponCardObject(s).getReloadCost())){
                p.getWeaponCardObject(s).reload();
                p.removeArrayAC(p.getWeaponCardObject(s).getReloadCost());
           }
           if(end == 1)
               this.gameState = RELOADED;
           return true;
       }
       return false;
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

    public boolean scoring(){
       if(this.gameState.equals(RELOADED) && (this.grid.whoIsDead()!=null)){
           int c = 0;
           for(Player p : this.grid.whoIsDead()) {
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
           return true;
       }
       return false;
    }

    public boolean discardCardForSpawnPoint(Player p, PowerUpCard p1){      //Attention to the view
       if(this.gameState == DEATH) {
           chooseSpawnPoint(p1.getC(), p);
           p.removePowerUpCard(p1);
           return true;
       }
       return false;
    }

    public void replace(){

       this.grid.replaceAmmoCard();
       this.grid.replaceWeaponCard();
       this.gameState = STARTTURN;
    }
}
