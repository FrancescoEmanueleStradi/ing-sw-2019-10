package controller;

import model.*;
import model.board.Cell;
import model.board.WeaponSlot;
import model.cards.AmmoCard;
import model.cards.PowerUpCard;
import model.Position;
import model.cards.WeaponCard;
import model.cards.powerupcards.Newton;
import model.cards.powerupcards.TagbackGrenade;
import model.cards.powerupcards.TargetingScope;
import model.cards.powerupcards.Teleporter;
import model.cards.weaponcards.*;
import model.player.AmmoCube;
import model.player.DamageToken;
import model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void gameStart(String nickName, Colour c) throws InvalidColourException {
       if(!init) {
           init = true;
           this.grid = new Grid();
           Player p = new Player(nickName, c, true);
           this.grid.addPlayer(p);               //first state
           this.gameState = START;
       }
    }



//----------------------------------------------------------------------------------------------------




    public boolean isValidAddPlayer(String nickName, Colour c) {
       return ((init) && !(this.grid.getPlayersNickName().contains(nickName)) && !(this.grid.getPlayersColour().contains(c)));
    }


    public void addPlayer(String nickName, Colour c) throws InvalidColourException {
        Player p = new Player(nickName, c, false);
        this.grid.addPlayer(p);
    }

    public boolean removePlayer(String nickName) {
        if(init) {
            this.grid.removePlayer(this.grid.getPlayerObject(nickName));
            return true;
        }
        return false;
    }



//----------------------------------------------------------------------------------------------------




    public boolean isValidReceiveType(int type) {
        return this.gameState.equals(START) && (type == 1 || type == 2 || type == 3 || type == 4);
    }

   public void receiveType(int type) {
        this.grid.setType(type);                 //find a condition
        this.grid.setUpAmmoCard();
        this.gameState = INITIALIZED;
   }

   public List<PowerUpCard> giveTwoPUCard(String nickName) {
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

   public boolean isValidPickAndDiscard(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return (p.getCell() == null);
   }

   public void pickAndDiscardCard(String nickName, PowerUpCard p1, PowerUpCard p2) {     //p1 choose, p2 discard
       Player p = this.grid.getPlayerObject(nickName);
       p.addPowerUpCard(p1);
       chooseSpawnPoint(p2.getValue().getC(), p);
   }


   public void chooseSpawnPoint(Colour c, Player p) {
           if(c.equals(Colour.YELLOW))
               this.grid.move(p, new Position(2,3));
           if(c.equals(Colour.RED))
               this.grid.move(p, new Position(1,0));            //view ask the choice
           if(c.equals(Colour.BLUE))
               this.grid.move(p, new Position(0,2));
           this.gameState = STARTTURN;
   }



   //----------------------------------------------------------------------------------------------------




   public boolean isValidShootNotAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, List<AmmoCube> lA, List<PowerUpCard> lP) {
       boolean x = false;
       if(this.gameState.equals(STARTTURN) && p.getWeaponCardObject(nameWC).isReloaded()){
           List<AmmoCube> l = choosePayment(lA, lP);
           List<Colour> lC = l.stream().map(a -> a.getC()).collect(Collectors.toList());
           switch(nameWC){
               case "Cyberblade":
                   if(lI.contains(1)){
                       if(this.grid.whereAmI(p).equals(this.grid.whereAmI(this.grid.getPlayerObject(lS.get(0)))))
                           x = true;
                   }
                   if(lI.contains(2) && lI.contains(1)){
                       x = false;
                       if(this.grid.distance(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) == 1 && !(this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2))))))
                           x = true;
                   }
                   if(lI.contains(3) && lI.contains(1)){
                       x = false;
                       if(lI.contains(1) && this.grid.whereAmI(p).equals(this.grid.whereAmI(this.grid.getPlayerObject(lS.get(3)))) && !this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(0))) && lC.contains(Colour.YELLOW))
                           x = true;
                   }
                   break;
               case "Electroscythe":
                   if(lI.contains(1) && !lI.contains(2)){
                       x = true;
                   }
                   if(!lI.contains(1) && lI.contains(2)){
                       if(lC.contains(Colour.RED) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   break;
               case "Flamethrower":
                   if(lI.contains(1) && !lI.contains(2)){
                       if(this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) == 1 && !(this.grid.isThereAWall(p, new Position(this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX(), this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY())) &&
                               (lS.size()<2 || this.grid.distance(this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1))) == 1 && ((this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX() == this.grid.getPlayerObject(lS.get(1)).getCell().getP().getX()) || (this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY() == this.grid.getPlayerObject(lS.get(1)).getCell().getP().getY())) && !(this.grid.isThereAWall(this.grid.getPlayerObject(lS.get(0)), new Position(this.grid.getPlayerObject(lS.get(1)).getCell().getP().getX(), this.grid.getPlayerObject(lS.get(1)).getCell().getP().getY()))))))
                           x = true;
                   }
                   if(!lI.contains(1) && lI.contains(2)){
                       if(this.grid.distance(p, new Position(Integer.parseInt(lS.get(0)), Integer.parseInt(lS.get(1)))) == 1 && this.grid.distance(new Position(Integer.parseInt(lS.get(2)),Integer.parseInt(lS.get(3))), new Position(Integer.parseInt(lS.get(0)), Integer.parseInt(lS.get(1)))) == 1 && ((Integer.parseInt(lS.get(0)) == Integer.parseInt(lS.get(2))||Integer.parseInt(lS.get(1)) == Integer.parseInt(lS.get(2)))) && lC.containsAll(Arrays.asList(Colour.YELLOW, Colour.YELLOW)))
                           x = true;
                   }
                   break;
               case "Furnace":
                   if(lI.contains(1) && !lI.contains(2)){
                       if(!p.getCell().getC().equals(Colour.valueOf(lS.get(0))) && this.grid.colourOfOtherViewZone(p).contains(Colour.valueOf(lS.get(0))))
                           x =true;
                   }
                   if(!lI.contains(1) && lI.contains(2)){
                       if(this.grid.distance(p, new Position(Integer.parseInt(lS.get(0)), Integer.parseInt(lS.get(1)))) == 1)
                           x = true;
                   }
                   break;
               case "Grenade Launcher":
                   if(lI.contains(1)){
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && (Integer.parseInt(lS.get(1)) == 0 || this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)))))
                           x = true;
                   }
                   if(lI.contains(2) && lI.contains(1)){
                       x = false;
                       if(this.grid.isInViewZone(p, new Position(Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)))) && lC.contains(Colour.RED))
                           x = true;
                   }
                   break;
               case "Heatseeker":
                   if(lI.size() == 0) {
                        if(!this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                            x = true;
                   }
                   break;
               case "Hellion":
                   if(lI.contains(1) && !lI.contains(2)){
                        if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()))
                            x = true;
                   }
                   if(!lI.contains(1) && lI.contains(2)){
                        if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && lC.contains(Colour.RED))
                            x = true;
                   }
                   break;
               case "Lock Rifle":
                   if(lI.contains(1)) {
                        if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                            x = true;
                   }
                   if(lI.contains(2) && lI.contains(1)) {
                        x = false;
                        if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && !this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(1))) && lC.contains(Colour.RED))
                            x = true;
                   }
                   break;
               case "Machine Gun":
                   if(lI.contains(1)) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && (lS.get(1) == null || this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1)))))
                           x = true;
                   }
                   if(lI.contains(2) && lI.contains(1)) {
                       x = false;
                       if((this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(0))) || (lS.get(1)!= null && (this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(1)))) || this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(0))))) && lC.contains(Colour.YELLOW))
                           x = true;
                   }
                   //TODO cover the remaining cases
                   if(lI.contains(3) && lI.contains(2) && lI.contains(1)) {
                       x = false;
                       if(!this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(2))) && (lS.get(4) == null || this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(4)))) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   break;
               case "Plasma Gun":
                   if(lI.contains(1)) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                           x = true;
                   }
                   if(lI.contains(2) && lI.contains(1)){
                       x = false;
                       if(Integer.parseInt(lS.get(1)) < 3 && this.grid.canMove(p, Integer.parseInt(lS.get(2))) && (lS.size() < 4 || this.grid.canMove(p, Integer.parseInt(lS.get(3)))))
                           x = true;
                   }
                   if(lI.contains(3) && lI.contains(1)){
                       x = false;
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && (!l.contains(2)|| l.indexOf(2) > l.indexOf(3)||l.contains(2) && l.indexOf(2) < l.indexOf(3)) && lC.contains(Colour.BLUE))              /* TODO && this.grid.isInViewZone(this.grid.getPlayerObject(lS.get(0)), )*/
                           x = true;
                   }
                   break;
               case "Power Glove":
                   if(lI.contains(1) && !lI.contains(2)) {
                       if(this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) == 1 && this.grid.isInViewZone(p,this.grid.getPlayerObject(lS.get(0))))
                           x = true;
                   }
                   if(!lI.contains(1) && lI.contains(2) && !lI.contains(3)) {
                       if(this.grid.distance(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) == 1 && !this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && lC.contains(Colour.BLUE)) /*&& (!lI.contains(3)|| this.grid.getPlayerObject(lS.get(3)).getCell().getP().equals(new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2))))))*/   //TODO
                           x = true;
                   }
                   if(!lI.contains(1) && lI.contains(2) && lI.contains(3) && !lI.contains(4)) {
                       if(this.grid.distance(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) == 1 && !this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && lC.contains(Colour.BLUE)) {
                       }
                   }
                   break;
               /*case "Railgun":
                   if() {

                   }
                   break;
               case "Rocket Launcher":
                   if() {

                   }
                   break;
               case "Shockwave":
                   if() {

                   }
                   break;
               case "Shotgun":
                   if() {

                   }
                   break;
               case "Sledgehammer":
                   if() {

                   }
                   break;
               case "T.H.O.R.":
                   if() {

                   }
                   break;
               case "Tractor Beam":
                   if() {

                   }
                   break;
               case "Vortex Cannon":
                   if() {
                   }
                   break;
               case "Whisper":
                   if() {

                   }
                   break;
               case "ZX-2":
                   if() {

                   }
                   break;

*/
           }

       }
       return x;
    }





   private void shootNotAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, List<AmmoCube> lA, List<PowerUpCard> lP){                    //is better to use a file?
        switch(nameWC){
            case "Cyberblade":                                      
                int x = 0;
                for(Integer i : lI) {
                    if (i == 1) {
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        x = 1;
                    }
                    if (i == 2) {
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(1), lS.get(2));
                    }
                    if ((x == 1) && i == 3)
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
                for(Integer i : lI){
                    if(i == 1){
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).moveEnemy(this.grid, this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));          //we give 0 if the player doesn't want to move
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
                int z = 0;
                for(int i : lI){
                    if(i == 1) {
                        ((PlasmaGun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        z = 1;
                    }
                    if(i == 2)
                        ((PlasmaGun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)));
                    if(i == 3 && z == 1)
                        ((PlasmaGun) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                }
                break;
            case "Power Glove":
                    if (lI.get(0) == 1)
                        ((PowerGlove) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    if (lI.get(0) == 2) {
                        ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart1(p, this.grid, lS.get(1), lS.get(2));
                        if(lI.contains(3))
                            ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart2(this.grid, p, this.grid.getPlayerObject(lS.get(3)));
                        if(lI.contains(4))
                            ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart3(p, this.grid, lS.get(4), lS.get(5));
                        if(lI.contains(5))
                            ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart4(this.grid, p, this.grid.getPlayerObject(lS.get(6)));
                }
                break;
            case "Railgun":
                if(lI.get(0) == 1)
                    ((Railgun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 2)
                    ((Railgun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)));
                break;
            case "Rocket Launcher":
                int h = 0;
                for(int i : lI) {
                    if (i == 1) {
                        ((RocketLauncher) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        h = 1;
                        if(lI.get(i+1) == 2)
                            ((RocketLauncher) p.getWeaponCardObject(nameWC)).movePlayer(this.grid,this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));
                    }
                    if(i == 3)
                        ((RocketLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)), Integer.parseInt(lS.get(4)));
                    if(i == 4 && h == 1)
                        ((RocketLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p);
                }
                break;
            case "Shockwave":
                if(lI.get(0) == 1)
                    ((Shockwave) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)), this.grid.getPlayerObject(lS.get(2)));
                if(lI.get(0) == 2)
                    ((Shockwave) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p);
                break;
            case "Shotgun":
                if(lI.get(0) == 1 && lI.get(1) == 2) {
                    ((Shotgun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    ((Shotgun) p.getWeaponCardObject(nameWC)).movePlayer(this.grid, this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));
                }
                if(lI.get(0) == 1 && lI.size()<2)
                    ((Shotgun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 3)
                    ((Shotgun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Sledgehammer":
                if(lI.get(0) == 1)
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 2 && lI.get(1) == 3) {
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).moveEnemy(this.grid.getPlayerObject(lS.get(0)), this.grid, lS.get(1), lS.get(2));
                }
                if(lI.get(0) == 2 && lI.size()<2)
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "T.H.O.R.":
                if(lI.get(0) == 1)
                    ((THOR) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(1) == 2)
                    ((THOR) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(1)));
                if(lI.get(2) == 3)
                    ((THOR) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p, this.grid.getPlayerObject(lS.get(2)));
                break;
            case "Tractor Beam":
                if(lI.get(0) == 1)
                    ((TractorBeam) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), lS.get(1), lS.get(2));
                if(lI.get(0) == 2)
                    ((TractorBeam) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Vortex Cannon":
                if(lI.get(0) == 1){
                    ((VortexCannon) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), lS.get(1), lS.get(2));
                    if(lI.get(1) == 2)
                        ((VortexCannon) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(3)), this.grid.getPlayerObject(lS.get(4)));
                }
                break;
            case "Whisper":
                ((Whisper) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "ZX-2":
                if(lI.get(0) == 1)
                    ((ZX2) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 2)
                    ((ZX2) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)), this.grid.getPlayerObject(lS.get(2)));
                break;
        }
        p.getWeaponCardObject(nameWC).unload();
        p.payWeaponCard(lA, lP);
    }

    private void shootAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, int direction, List<AmmoCube> lA, List<PowerUpCard> lP){
        this.grid.move(p, direction);
        this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
    }

    public void firstActionShoot(Player p, String nameWC, List<Integer> lI, List<String> lS, int direction, List<AmmoCube> lA, List<PowerUpCard> lP){
        if(!p.isAdrenaline2())
            this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
        else if (p.isAdrenaline2())
            this.shootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        this.gameState = ACTION1;
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

    //TODO public boolean isValidSecondActionShoot

    public void SecondActionShoot(Player p, String nameWC, List<Integer> lI, List<String> lS, int direction, List<AmmoCube> lA, List<PowerUpCard> lP){
        if(!p.isAdrenaline2())
            this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
        else if (p.isAdrenaline2())
            this.shootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        this.gameState = ACTION2;
    }

//----------------------------------------------------------------------------------------------------


    public boolean isValidUsePowerUpCard(Player p, String namePC, List<String> lS) {
        boolean x = false;
        if(this.gameState.equals(STARTTURN) || this.gameState.equals(ACTION1) || this.gameState.equals(ACTION2)) {
            switch(namePC) {
                case "Newton" :
                    if(this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))));
                       x = true;
                    break;
                case "Teleporter" :
                    x = true;
                    break;
                case "Tagback Grenade" :
                    if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                        x = true;
                    break;
                case "Targeting Scope" :

            }

        }
        return x;
    }


    public void usePowerUpCard(Player p, String namePC, List<String> lS) {
        switch(namePC) {
            case "Newton" :
                ((Newton) p.getPowerUpCardObject(namePC)).applyEffect(this.grid, this.grid.getPlayerObject(lS.get(0)), lS.get(1), lS.get(2));
                break;
            case "Teleporter" :
                ((Teleporter) p.getPowerUpCardObject(namePC)).applyEffect(this.grid, p, lS.get(0), lS.get(1));
                break;
            case "Tagback Grenade" :
                ((TagbackGrenade) p.getPowerUpCardObject(namePC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
            case "Targeting Scope" :
                ((TargetingScope) p.getPowerUpCardObject(namePC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
        }
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
