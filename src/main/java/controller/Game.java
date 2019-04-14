package controller;

import model.*;
import model.cards.PowerUpCard;
import model.Position;
import model.player.DamageToken;
import model.player.Player;

import java.util.LinkedList;
import java.util.List;

import static controller.GameState.*;

public class Game {                                 //Cli or Gui -- Rmi or Socket

    private GameState gameState;
    private boolean init = false;
    private Grid grid;


   public boolean gameStart(String nickName, Colour c) throws InvalidColourException{
       if(!init) {
           init = true;
           this.grid = new Grid();
           Player p = new Player(nickName, c, true);
           this.grid.addPlayer(p);               //first state
           this.gameState = START;
           return true;
       }
       return false;
   }

    public boolean addPlayer(String nickName, Colour c) throws InvalidColourException{
       if((init) && !(this.grid.getPlayersNickName().contains(nickName)) && !(this.grid.getPlayersColour().contains(c))) {
           Player p = new Player(nickName, c, false);
           this.grid.addPlayer(p);
           return true;
       }
       return false;
    }

    public boolean removePlayer(String nickName){
        if(init) {
            this.grid.removePlayer(this.grid.getPlayerObject(nickName));
            return true;
        }
        return false;
    }

   public boolean receiveType(int type){
       if(this.gameState.equals(START)) {
           this.grid.setType(type);                 //find a condition
           this.grid.setUpAmmoCard();
           this.gameState = INITIALIZED;
           return true;
       }
       return false;
   }

   public List<PowerUpCard> giveTwoPUCard(Player p){
       if(p.getCell() == null) {
           List<PowerUpCard> l = new LinkedList<>();
           l.add(this.grid.pickPowerUpCard());
           l.add(this.grid.pickPowerUpCard());
           return l;
       }
                                            //View control -> empty list
       return new LinkedList<>();
   }

   public boolean pickAndDiscardCard(Player p, PowerUpCard p1, PowerUpCard p2){     //p1 choose, p2 discard
           p.addPowerUpCard(p1);
           return chooseSpawnPoint(p2.getValue().getC(), p);
   }


   public boolean chooseSpawnPoint(Colour c, Player p){
       if(p.getCell() == null){
           if(c.equals(Colour.YELLOW))
               this.grid.move(p, new Position(2,3));
           if(c.equals(Colour.RED))
               this.grid.move(p, new Position(1,0));
           if(c.equals(Colour.BLUE))
               this.grid.move(p, new Position(0,2));
           this.gameState = STARTTURN;
            return true;
       }
       return false;
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


    public boolean firstActionMove(Player p, String s){
        if(this.gameState.equals(STARTTURN)){

            //TODO
            this.gameState = ACTION1;
            return true;
        }
        return false;
    }

    public boolean firstActionGrab(Player p, String s){
        if(this.gameState.equals(STARTTURN)){
            //TODO

            this.gameState = ACTION1;
            return true;
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

    public boolean reaload(Player p, String s, int end){  // end is 1 if the player has finished to reload
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
