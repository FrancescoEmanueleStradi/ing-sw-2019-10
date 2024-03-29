package controller;

import model.*;
import model.board.WeaponSlot;
import model.cards.*;
import model.cards.powerupcards.*;
import model.cards.weaponcards.*;
import model.player.AmmoCube;
import model.player.DamageToken;
import model.player.Player;
import network.ServerInterface;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static controller.GameState.*;

/**
 * The dedicated controller class. Contains methods modifying the model through the Grid class and a myriad of
 * useful functions.
 */
public class Game {

    private int iD;
    private ServerInterface server;
    private GameState gameState;
    private boolean init = false;
    private Grid grid;
    private boolean discard = false;
    private List<String> deadList = new LinkedList<>();
    private boolean finalFrenzy = false;
    private int cardToPickAfterDeath;

    /**
     * Creates a new Game class, linking the game to the server and creating a grid.
     *
     * @param iD     game identifier
     * @param server server
     */
    public Game(int iD, ServerInterface server) {
        this.iD = iD;
        this.server = server;
        grid = new Grid(iD, server);
    }

    /**
     * Gets game id.
     *
     * @return id
     */
    public int getID() {
        return iD;
    }

    /**
     * Sets game id.
     *
     * @param iD id
     */
    public void setID(int iD) {
        this.iD = iD;
    }

    /**
     * Gets server.
     *
     * @return server
     */
    public ServerInterface getServer() {
        return server;
    }

    /**
     * Sets server.
     *
     * @param server server
     */
    public void setServer(ServerInterface server) {
        this.server = server;
    }

    /**
     * Gets grid.
     *
     * @return grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Gets game state.
     *
     * @return game state
     */
    GameState getGameState() {
        return gameState;
    }

    /**
     * Determines whether or not game has not started.
     *
     * @return boolean
     */
    public boolean gameIsNotStarted() {
        return this.grid.getPlayers().isEmpty();
    }

    /**
     * Very first state.
     *
     * @param nickName nickname
     * @param c        colour
     * @throws RemoteException RMI exception
     */
    public void gameStart(String nickName, Colour c) throws RemoteException {
       if(!init) {
           init = true;
           Player p = new Player(nickName, c, true);
           p.setTurnFinalFrenzy(1);
           this.grid.addPlayer(p);               //first state
           this.gameState = START;
       }
    }

    /**
     * Sets player's FF turn apart from first player's.
     *
     * @param nickName nickname
     * @param turn     turn
     */
    public void changeTurnFinalFrenzy(String nickName, int turn) {
        Player p = this.grid.getPlayerObject(nickName);
        if(!p.hasFirstPlayerCard())
            p.setTurnFinalFrenzy(turn);
    }

    /**
     * Gets player colour.
     *
     * @param nickName nickname
     * @return colour
     */
    public Colour getColour(String nickName) {
        return this.grid.getPlayerColour(nickName);
    }

    /**
     * Determines whether or not the added player is valid.
     * Returns a flag as int , not a boolean.
     *
     * @param nickName nickname
     * @param c        colour
     * @return flag
     */
    public int isValidAddPlayer(String nickName, Colour c) {
        if(!init)
            return 0;       //first player have not chosen yet
        else if(this.grid.getPlayersNickName().contains(nickName))
            return 1;       //first player have chosen but nick is already picked
        else if((this.grid.getPlayersColour().contains(c)))
            return 2;       //first player have chosen but colour is already picked
        else return 3;      //first player hve chosen and nick and colour are available
    }

    /**
     * Adds player.
     *
     * @param nickName nickname
     * @param c        colour
     * @throws RemoteException RMI exception
     */
    public synchronized void addPlayer(String nickName, Colour c) throws RemoteException {
        Player p = new Player(nickName, c, false);
        this.grid.addPlayer(p);
    }

    /**
     * Removes player.
     *
     * @param nickName nickname
     * @return boolean
     */
    public boolean removePlayer(String nickName) {
        if(init) {
            this.grid.removePlayer(this.grid.getPlayerObject(nickName));
            return true;
        }
        return false;
    }

    /**
     * Returns list of players.
     *
     * @return player list
     */
    public synchronized List<String> getPlayers() {
        return this.grid.getPlayers().stream().map(Player::getNickName).collect(Collectors.toList());
    }

    /**
     * Returns reduced reload cost.
     *
     * @param s weapon name
     * @return reload cost
     */
    public synchronized List<Colour> getReloadCostReduced(String s) {
        WeaponCard wC = grid.getWeaponCardObject(s);
        List<AmmoCube> l = new ArrayList<>(Arrays.asList(wC.getReloadCost()));
        l.remove(0);
        List<Colour> lC = new LinkedList<>();
        if(l.isEmpty())
            return lC;
        for(AmmoCube aC : l)
            lC.add(aC.getC());
        return lC;
    }

    /**
     * Returns player's weapon cards.
     *
     * @param nickName nickname
     * @return weapon card list
     */
    public List<String> getPlayerWeaponCard(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return p.getWeaponCards().stream().map(WeaponCard::getCardName).collect(Collectors.toList());
    }

    /**
     * Returns player's loaded weapon cards.
     *
     * @param nickName nickname
     * @return weapon card list
     */
    public List<String> getPlayerWeaponCardLoaded(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return p.getWeaponCards().stream().filter(WeaponCard::isReloaded).map(WeaponCard::getCardName).collect(Collectors.toList());
    }

    /**
     * Returns player's unloaded weapon cards.
     *
     * @param nickName nickname
     * @return weapon card list
     */
    public List<String> getPlayerWeaponCardUnloaded(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return p.getWeaponCards().stream().filter(a -> !a.isReloaded()).map(WeaponCard::getCardName).collect(Collectors.toList());
    }

    /**
     * Returns description of player's given weapon card.
     *
     * @param s        weapon name
     * @param nickName nickname
     * @return weapon description
     */
    public String getPlayerDescriptionWC(String s, String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wC = p.getWeaponCardObject(s);
        return wC.getDescription();
    }

    /**
     * Returns reload cost of player's given weapon card.
     *
     * @param s        weapon name
     * @param nickName nickname
     * @return reload cost
     */
    public List<Colour> getPlayerReloadCost(String s, String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wC = p.getWeaponCardObject(s);
        return Arrays.stream(wC.getReloadCost()).map(AmmoCube::getC).collect(Collectors.toList());
    }

    /**
     * Returns player's powerup cards.
     *
     * @param nickName nickname
     * @return powerup list
     */
    public List<String> getPlayerPowerUpCard(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return p.getPowerUpCards().stream().map(PowerUpCard::getCardName).collect(Collectors.toList());
    }

    /**
     * Returns colours of player's powerup cards.
     *
     * @param nickName nickname
     * @return powerup colour list
     */
    public List<String> getPlayerPowerUpCardColour(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return p.getPowerUpCards().stream().map(PowerUpCard::getC).map(Colour::getColourId).collect(Collectors.toList());
    }

    /**
     * Returns description of player's given powerup card.
     *
     * @param s        powerup name
     * @param colour   colour
     * @param nickName nickname
     * @return powerup description
     */
    public String getPlayerDescriptionPUC(String s, String colour, String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        Colour col = Colour.valueOf(colour);
        PowerUpCard pC = p.getPowerUpCardObject(s, col);
        return pC.getDescription();
    }

    /**
     * Gets player's scores.
     *
     * @return score list
     */
    public List<Integer> getScore() {
        return this.grid.getPlayers().stream().map(Player::getScore).collect(Collectors.toList());
    }

    /**
     * Returns string containing information about the player's own damage taken, position, weapon cards,
     * powerup cards, and ammo. Broadcast only to the client calling it.
     *
     * @param nickName nickname
     * @return player info
     */
    public String checkYourStatus(String nickName) {
        Player p = grid.getPlayerObject(nickName);
        List<String> wCards = new LinkedList<>();
        List<String> pCards = new LinkedList<>();
        List<String> aCubes = new LinkedList<>();
        int count = 0;

        for(int d = 0; d < 12; d++) {
            if(p.getPlayerBoard().getDamage().getDamageTokens()[d] != null)
                count++;
        }
        String damageDetails = "You have received the following amount of damage: " + count + "\n";
        String posDetails = "You are currently in cell " + p.getCell().getPos().getX() + " " + p.getCell().getPos().getY() + "\n";

        String yourWeapons = "These are the WeaponCards currently in your possession:\n";
        for(WeaponCard w : p.getWeaponCards())
            wCards.add(w.getCardName());

        String yourPups = "These are the PowerUpCards currently in your possession:\n";
        for(PowerUpCard c : p.getPowerUpCards()) {
            pCards.add(c.getCardName() + " coloured " + c.getC().getColourId());
        }

        String yourAmmo = "These are the AmmoCubes currently in your possession:\n";
        for(int i = 0; i < 9; i++) {
            if(p.getAmmoCubes()[i] != null)
                aCubes.add(p.getAmmoCubes()[i].getC().getColourId());
        }

        String joinWC = String.join(", ", wCards);
        String joinPC = String.join(", ", pCards);
        String joinAC = String.join(", ", aCubes);

        return damageDetails + posDetails + yourWeapons + joinWC + "\n" + yourPups + joinPC + "\n" + yourAmmo + joinAC + "\n";
    }

    /**
     * Returns string containing information about all the other players' damage taken and position.
     *
     * @param yourNick nickname
     * @return player info
     */
    public String checkOthersStatus(String yourNick) {
        List<Player> players = new LinkedList<>();
        List<Integer> damageVals = new LinkedList<>();
        List<String> positions = new LinkedList<>();

        for(Player p : grid.getPlayers()) {
            if (!p.equals(grid.getPlayerObject(yourNick)))
                players.add(p);
        }

        for(Player p : players) {
            int count = 0;
            for(int d = 0; d < 12; d++) {
                if(p.getPlayerBoard().getDamage().getDamageTokens()[d] != null)
                    count++;
            }
            damageVals.add(count);
            if(p.getCell() != null)
                positions.add(p.getCell().getPos().getX() + " " + p.getCell().getPos().getY());
        }

        StringBuilder build = new StringBuilder();
        for(int i = 0; i < players.size(); i++) {
            build.append("\nPlayer " + players.get(i).getNickName() + " in " + positions.get(i) + " has taken " + damageVals.get(i) + " damage.");
        }

        return build.toString();
    }

    /**
     * Determines whether or not the arena type given is valid.
     *
     * @param type type
     * @return boolean
     */
    public synchronized boolean isValidReceiveType(int type) {
        return this.gameState.equals(START) && (type == 1 || type == 2 || type == 3 || type == 4);
    }

    /**
     * Receives arena type.
     *
     * @param type type
     * @throws RemoteException RMI exception
     */
    public synchronized void receiveType(int type) throws RemoteException {
        this.grid.setType(type);                 //find a condition
        this.grid.setUpAmmoCard();
        this.gameState = INITIALIZED;
    }

    /**
     * Gives 2 powerup cards to player (who should then pick one).
     *
     * @param nickName nickname
     */
    public synchronized void giveTwoPUCard(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        if(p.getCell() == null) {
            p.addPowerUpCard(this.grid.pickPowerUpCard());
            p.addPowerUpCard(this.grid.pickPowerUpCard());
        }
    }

    /**
     * Determines whether or not the action of a powerup card discard is valid.
     *
     * @param nickName nickname
     * @param p1       player
     * @param c1       colour
     * @return boolean
     */
    public synchronized boolean isValidPickAndDiscard(String nickName, String p1, String c1) {
        Player p = this.grid.getPlayerObject(nickName);
        return (p.getCell() == null &&
                (p1.equals(p.getPowerUpCards().get(0).getCardName()) && Colour.valueOf(c1).equals(p.getPowerUpCards().get(0).getC()) || p1.equals(p.getPowerUpCards().get(1).getCardName()) && Colour.valueOf(c1).equals(p.getPowerUpCards().get(1).getC())));
    }

    /**
     * Performs the action if isValidPickAndDiscard returns true.
     *
     * @param nickName nickname
     * @param p1       player
     * @param c1       colou
     */
    public synchronized void pickAndDiscardCard(String nickName, String p1, String c1) {     //p1 and c1 name and colour of the card the player want to keep
       Player p = this.grid.getPlayerObject(nickName);
       if(p1.equals(p.getPowerUpCards().get(0).getCardName()) && Colour.valueOf(c1).equals(p.getPowerUpCards().get(0).getC())) {
           this.grid.getPowerUpDiscardPile().add(p.getPowerUpCards().get(1));
           chooseSpawnPoint(p.getPowerUpCards().get(1).getC(), p);
           p.removePowerUpCard(p.getPowerUpCards().get(1));
       }
       else if(p1.equals(p.getPowerUpCards().get(1).getCardName()) && Colour.valueOf(c1).equals(p.getPowerUpCards().get(1).getC())) {
           this.grid.getPowerUpDiscardPile().add(p.getPowerUpCards().get(0));
           chooseSpawnPoint(p.getPowerUpCards().get(0).getC(), p);
           p.removePowerUpCard(p.getPowerUpCards().get(0));
       }
    }

    /**
     * Assigns the player's spawn point.
     *
     * @param c colour
     * @param p player
     */
    private synchronized void chooseSpawnPoint(Colour c, Player p) {
       if(c.equals(Colour.YELLOW))
           p.changeCell(this.grid.getBoard().getArena()[2][3]);
       if(c.equals(Colour.RED))
           p.changeCell(this.grid.getBoard().getArena()[1][0]);
       if(c.equals(Colour.BLUE))
           p.changeCell(this.grid.getBoard().getArena()[0][2]);
       if(this.gameState == INITIALIZED)
           this.gameState = STARTTURN;
    }

    /**
     * Determines whether or not the player's given weapon card is reloaded.
     *
     * @param nickName   nickname
     * @param weaponCard weapon card
     * @return boolean
     */
    public boolean isValidCard(String nickName, String weaponCard) {
        return this.grid.getPlayerObject(nickName).getWeaponCardObject(weaponCard)!= null && this.grid.getPlayerObject(nickName).getWeaponCardObject(weaponCard).isReloaded();
    }

    /**
     * Determines whether or not the shoot (non-adrenaline) parameters are valid. There are case statements for every
     * weapon card in the game.
     *
     * @param p player
     * @param nameWC weapon card
     * @param lI effect number list
     * @param lS various string list
     * @param lA ammocube list
     * @param lP powerup list
     * @return boolean
     */
    private boolean isValidShootNotAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, List<AmmoCube> lA, List<PowerUpCard> lP) {
        boolean x = false;
        if(p.getWeaponCardObject(nameWC).isReloaded()) {
           List<AmmoCube> l = choosePayment(lA, lP);
           List<Colour> lC = l.stream().map(AmmoCube::getC).collect(Collectors.toList());
           switch(nameWC) {
               case "Cyberblade":
                   List<Integer> move2 = new LinkedList<>();
                   if(!lS.get(1).isEmpty())
                       move2.add(Integer.parseInt(lS.get(1)));
                   if(lI.size() == 1 && lI.get(0).equals(1) && !lS.isEmpty() &&
                           (p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell())))
                           x = true;
                   else if(lI.size() == 2 && lI.contains(1) && lI.contains(2) && lS.size() > 1) {
                       if((Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4) && this.grid.canGhostMove(p, move2) &&
                               (lI.indexOf(2) > lI.indexOf(1) && p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) ||
                               lI.indexOf(2) < lI.indexOf(1) && this.grid.ghostMove(p, move2).getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell())))
                           x = true;
                   }
                   else if(lI.size() == 2 && lI.contains(1) && lI.contains(3) && lS.size() > 2) {
                       if(p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) &&
                               p.getCell().equals(this.grid.getPlayerObject(lS.get(2)).getCell()) &&
                               !this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(2))) && lC.contains(Colour.YELLOW))
                           x = true;
                   }
                   else if(lI.size() == 3 && lS.size() > 2 && ((lI.indexOf(2) == 0 && (Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4) && this.grid.canGhostMove(p, move2) &&
                               this.grid.ghostMove(p, move2).getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && this.grid.ghostMove(p, move2).getCell().equals(this.grid.getPlayerObject(lS.get(2)).getCell()) && !this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(2)))) ||
                               (lI.indexOf(2) == 1 && lI.indexOf(1) < lI.indexOf(3) && p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && this.grid.ghostMove(p, move2).getCell().equals(this.grid.getPlayerObject(lS.get(2)).getCell())) ||
                               (lI.indexOf(2) == 1 && lI.indexOf(1) > lI.indexOf(3) && p.getCell().equals(this.grid.getPlayerObject(lS.get(2)).getCell()) && this.grid.ghostMove(p, move2).getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell())) ||
                               (lI.indexOf(2) == 2 && p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && p.getCell().equals(this.grid.getPlayerObject(lS.get(2)).getCell()) && !this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(2)))) && lC.contains(Colour.YELLOW)))
                           x = true;
                   break;
               case "Electroscythe":
                   if(lI.size() == 1 && (lI.get(0) == 1 ||
                           (lI.get(0) == 2 && lC.containsAll(Arrays.asList(Colour.RED, Colour.BLUE)))))
                       x = true;
                   break;
               case "Flamethrower":
                   if(lI.contains(1) && !lI.contains(2) && lS.size() > 1 && !lS.get(0).isEmpty() &&
                           this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) == 1 && !this.grid.isThereAWall(p, this.grid.getPlayerObject(lS.get(0)).getCell().getPos()) &&
                           (lS.get(1).isEmpty() || this.grid.distance(this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1))) == 1 && ((this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getX() == this.grid.getPlayerObject(lS.get(1)).getCell().getPos().getX()) || (this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getY() == this.grid.getPlayerObject(lS.get(1)).getCell().getPos().getY())) &&
                                   !this.grid.isThereAWall(this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)).getCell().getPos())) ||
                   (!lI.contains(1) && lI.contains(2) && lS.size() > 1 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() &&
                           this.grid.distance(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) == 1 && !this.grid.isThereAWall(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) &&
                           ((lS.size() == 4 && lS.get(2).isEmpty() && lS.get(3).isEmpty()) || (this.grid.distance(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos(), this.grid.getBoard().getArena()[Integer.parseInt(lS.get(2))][Integer.parseInt(lS.get(3))].getPos()) == 1 && !this.grid.isThereAWall(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos(), this.grid.getBoard().getArena()[Integer.parseInt(lS.get(2))][Integer.parseInt(lS.get(3))].getPos()) &&
                           (Integer.parseInt(lS.get(0)) == Integer.parseInt(lS.get(2)) || Integer.parseInt(lS.get(1)) == Integer.parseInt(lS.get(3))))) && Collections.frequency(lC, Colour.YELLOW) == 2))
                       x = true;
                   break;
               case "Furnace":
                   if(lI.contains(1) && !lI.contains(2) && !lS.isEmpty() && !lS.get(0).isEmpty() && !p.getCell().getC().equals(Colour.valueOf(lS.get(0))) && this.grid.colourOfOtherViewZone(p).contains(Colour.valueOf(lS.get(0))))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) && lS.size() > 1 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && this.grid.distance(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) == 1)
                           x = true;
                   break;
               case "Grenade Launcher":
                   if(lI.size() == 1 && lI.get(0) == 1 && lS.size() > 1 && !lS.get(0).isEmpty()) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && (lS.get(1).isEmpty() || this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4))
                           x = true;
                   }
                   else if(lI.size() == 2 && ((lI.get(0) == 1 && lI.get(1) == 2) || (lI.get(0) == 2 && lI.get(1) == 1)) && lS.size() > 3 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() &&
                           this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && (Integer.parseInt(lS.get(1)) == 0 || this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4) &&
                           this.grid.isInViewZone(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(2))][Integer.parseInt(lS.get(3))].getPos()) && lC.contains(Colour.RED))
                           x = true;
                   break;
               case "Heatseeker":
                   if(!lS.isEmpty() && !lS.get(0).isEmpty() && !this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                            x = true;
                   break;
               case "Hellion":
                   if((lI.contains(1) && !lI.contains(2) && !lS.isEmpty() && !lS.get(0).isEmpty() &&
                           (this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()))) ||
                   (!lI.contains(1) && lI.contains(2) && !lS.isEmpty() && !lS.get(0).isEmpty() &&
                           (this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && lC.contains(Colour.RED))))
                            x = true;
                   break;
               case "Lock Rifle":
                   if(lI.size() == 1 && lI.get(0) == 1 && !lS.isEmpty() && !lS.get(0).isEmpty()) {
                        if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                            x = true;
                   }
                   else if(lI.size() == 2 && lI.get(0) == 1 && lI.get(1) == 2 && lS.size() > 1 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() &&
                           this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && !this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(1))) && lC.contains(Colour.RED))
                            x = true;
                   break;
               case "Machine Gun":      //if player wants to use Turret Tripod (3) he must use the basic effect AND Focus Shot before that
                   if(lI.size() == 1 && lI.get(0) == 1 && !lS.isEmpty()) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && (lS.get(1).isEmpty() || this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1)))))
                           x = true;
                   }
                   else if(lI.size() == 2 && lI.get(0) == 1 && lI.get(1) == 2 && lS.size() > 2) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && ((lS.get(1).isEmpty() && this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(0)))) ||
                               (!lS.get(1).isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && (this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(0))) || this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(1)))))) && lC.contains(Colour.YELLOW))
                           x = true;
                   }
                   else if(lI.size() == 3 && lI.get(0) == 1 && lI.get(1) == 2 && lI.get(2) == 3 && lS.size() > 4 &&
                           (this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && ((lS.get(1).isEmpty() && this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(0)))) ||
                               (!lS.get(1).isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && (this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(0))) || this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(1)))))) &&
                               !this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(2))) && (lS.get(4).isEmpty() || (!this.grid.getPlayerObject(lS.get(4)).equals(this.grid.getPlayerObject(lS.get(0))) && (lS.get(1).isEmpty() || !this.grid.getPlayerObject(lS.get(4)).equals(this.grid.getPlayerObject(lS.get(1)))))) &&
                               (lS.get(4).isEmpty() || this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(4))) && !this.grid.getPlayerObject(lS.get(4)).equals(this.grid.getPlayerObject(lS.get(3)))) && lC.containsAll(Arrays.asList(Colour.YELLOW, Colour.BLUE))))
                           x = true;
                   break;
               case "Plasma Gun":
                   if(!lI.isEmpty() && lI.get(0) == 1 && !lS.isEmpty() && !lS.get(0).isEmpty()) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                           x = true;
                   }
                   else if(lI.size() == 2 && lI.get(0) == 1 && lI.get(1) == 2 && lS.size() > 1 && !lS.get(0).isEmpty()) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 2 && !lS.get(2).isEmpty() && Integer.parseInt(lS.get(2)) >= 0 && Integer.parseInt(lS.get(2)) <= 4)
                           directions.add(Integer.parseInt(lS.get(2)));
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && lS.get(1).equals("2") && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.canGhostMove(p, directions))
                           x = true;
                   }
                   else if(lI.size() == 2 && lI.get(0) == 2 && lI.get(1) == 1 && lS.size() > 1 && !lS.get(0).isEmpty()) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 2 && !lS.get(2).isEmpty() && Integer.parseInt(lS.get(2)) >= 0 && Integer.parseInt(lS.get(2)) <= 4)
                           directions.add(Integer.parseInt(lS.get(2)));
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && lS.get(1).equals("2") && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(this.grid.canGhostMove(p, directions) && this.grid.isInViewZone(this.grid.ghostMove(p, directions), this.grid.getPlayerObject(lS.get(0))))
                           x = true;
                   }
                   else if(lI.size() == 2 && lI.get(0) == 1 && lI.get(1) == 3 && !lS.isEmpty() && !lS.get(0).isEmpty()) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   else if(lI.size() == 3 && lI.get(0) == 1 && lI.get(1) == 2 && lI.get(2) == 3 && lS.size() > 1 && !lS.get(0).isEmpty()) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 2 && !lS.get(2).isEmpty() && Integer.parseInt(lS.get(2)) >= 0 && Integer.parseInt(lS.get(2)) <= 4)
                           directions.add(Integer.parseInt(lS.get(2)));
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && lS.get(1).equals("2") && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.canGhostMove(p, directions) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   else if(lI.size() == 3 && lI.get(0) == 2 && lI.get(1) == 1 && lI.get(2) == 3 && lS.size() > 1 && !lS.get(0).isEmpty()) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 2 && !lS.get(2).isEmpty() && Integer.parseInt(lS.get(2)) >= 0 && Integer.parseInt(lS.get(2)) <= 4)
                           directions.add(Integer.parseInt(lS.get(2)));
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && lS.get(1).equals("2") && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(this.grid.canGhostMove(p, directions) && this.grid.isInViewZone(this.grid.ghostMove(p, directions), this.grid.getPlayerObject(lS.get(0))) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   break;
               case "Power Glove":
                   if((lI.contains(1) && !lI.contains(2) && !lS.isEmpty() && !lS.get(0).isEmpty() &&
                           (this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) == 1 && this.grid.isInViewZone(p,this.grid.getPlayerObject(lS.get(0)))))
                           ||
                           (!lI.contains(1) && lI.contains(2) && !lI.contains(3) && !lI.contains(4) && !lI.contains(5) && lS.size() > 1 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() &&
                           (this.grid.distance(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) == 1 && !this.grid.isThereAWall(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) && lC.contains(Colour.BLUE)))
                           ||
                           (!lI.contains(1) && lI.contains(2) && lI.contains(3) && !lI.contains(4) && !lI.contains(5) && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() &&
                           (this.grid.distance(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) == 1 && !this.grid.isThereAWall(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) && this.grid.getPlayerObject(lS.get(2)).getCell().getPos().equals(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) && lC.contains(Colour.BLUE)))
                           ||
                           (!lI.contains(1) && lI.contains(2) && lI.contains(3) && lI.contains(4) && !lI.contains(5) && lS.size() > 4 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && !lS.get(3).isEmpty() && !lS.get(4).isEmpty() &&
                           (this.grid.distance(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) == 1 && !this.grid.isThereAWall(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) && this.grid.getPlayerObject(lS.get(2)).getCell().getPos().equals(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) &&
                               this.grid.distance(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos(), this.grid.getBoard().getArena()[Integer.parseInt(lS.get(3))][Integer.parseInt(lS.get(4))].getPos()) == 1 && !this.grid.isThereAWall(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos(), this.grid.getBoard().getArena()[Integer.parseInt(lS.get(3))][Integer.parseInt(lS.get(4))].getPos()) &&
                               (Integer.parseInt(lS.get(3)) == Integer.parseInt(lS.get(0)) || Integer.parseInt(lS.get(4)) == Integer.parseInt(lS.get(1)))&& lC.contains(Colour.BLUE)))
                           ||
                           (!lI.contains(1) && lI.contains(2) && lI.contains(3) && lI.contains(4) && lI.contains(5) && lS.size() > 5 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && !lS.get(3).isEmpty() && !lS.get(4).isEmpty() && !lS.get(5).isEmpty() &&
                           (this.grid.distance(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) == 1 && !this.grid.isThereAWall(p, this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) && this.grid.getPlayerObject(lS.get(2)).getCell().getPos().equals(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos()) &&
                                   this.grid.distance(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos(), this.grid.getBoard().getArena()[Integer.parseInt(lS.get(3))][Integer.parseInt(lS.get(4))].getPos()) == 1 && !this.grid.isThereAWall(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getPos(), this.grid.getBoard().getArena()[Integer.parseInt(lS.get(3))][Integer.parseInt(lS.get(4))].getPos()) &&
                                   (Integer.parseInt(lS.get(3)) == Integer.parseInt(lS.get(0)) || Integer.parseInt(lS.get(4)) == Integer.parseInt(lS.get(1))) &&
                                   this.grid.getPlayerObject(lS.get(5)).getCell().getPos().equals(this.grid.getBoard().getArena()[Integer.parseInt(lS.get(3))][Integer.parseInt(lS.get(4))].getPos()) && lC.contains(Colour.BLUE))))
                           x = true;
                   break;
               case "Railgun":
                   if(lI.contains(1) && !lI.contains(2) && !lS.isEmpty() && !lS.get(0).isEmpty() &&
                           (p.getCell().getPos().getX() == this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getX() || p.getCell().getPos().getY() == this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getY()))
                           x = true;
                   else if(!lI.contains(1) && lI.contains(2) && lS.size() > 1 && !lS.get(0).isEmpty()) {
                       if(lS.get(1).isEmpty()) {
                           if(p.getCell().getPos().getX() == this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getX() || p.getCell().getPos().getY() == this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getY())
                               x = true;
                       } else if(!lS.get(1).isEmpty() &&
                               (p.getCell().getPos().getX() == this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getX() && p.getCell().getPos().getX() == this.grid.getPlayerObject(lS.get(1)).getCell().getPos().getX() && (p.getCell().getPos().getY() <= this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getY() && p.getCell().getPos().getY() <= this.grid.getPlayerObject(lS.get(1)).getCell().getPos().getY() || p.getCell().getPos().getY() >= this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getY() && p.getCell().getPos().getY() >= this.grid.getPlayerObject(lS.get(1)).getCell().getPos().getY()) ||
                                       p.getCell().getPos().getY() == this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getY() && p.getCell().getPos().getY() == this.grid.getPlayerObject(lS.get(1)).getCell().getPos().getY() && (p.getCell().getPos().getX() <= this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getX() && p.getCell().getPos().getX() <= this.grid.getPlayerObject(lS.get(1)).getCell().getPos().getX() || p.getCell().getPos().getX() >= this.grid.getPlayerObject(lS.get(0)).getCell().getPos().getX() && p.getCell().getPos().getX() >= this.grid.getPlayerObject(lS.get(1)).getCell().getPos().getX())))
                           x = true;
                   }
                   break;
               case "Rocket Launcher":
                   if(lI.size() == 1 && lI.get(0) == 1 && !lS.isEmpty() && !lS.get(0).isEmpty() &&
                           this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()))
                           x = true;
                   else if(lI.size() == 2 && lI.get(0) == 1 && lI.get(1) == 2 && lS.size() > 1 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() &&
                           this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4 && this.grid.canMove(p, Integer.parseInt(lS.get(1))))
                           x = true;
                   else if(lI.size() == 2 && lI.get(0) == 1 && lI.get(1) == 3 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(2).isEmpty() && (Integer.parseInt(lS.get(2)) == 0 || Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(lS.size() > 4 && Integer.parseInt(lS.get(2)) == 2 && !lS.get(4).isEmpty() && Integer.parseInt(lS.get(4)) >= 0 && Integer.parseInt(lS.get(4)) <= 4)
                           directions.add(Integer.parseInt(lS.get(4)));

                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && this.grid.canGhostMove(p, directions) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   else if(lI.size() == 2 && lI.get(0) == 3 && lI.get(1) == 1 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(2).isEmpty() && (Integer.parseInt(lS.get(2)) == 0 || Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(lS.size() > 4 && Integer.parseInt(lS.get(2)) == 2 && !lS.get(4).isEmpty() && Integer.parseInt(lS.get(4)) >= 0 && Integer.parseInt(lS.get(4)) <= 4)
                           directions.add(Integer.parseInt(lS.get(4)));

                       if(this.grid.canGhostMove(p, directions) && this.grid.isInViewZone(this.grid.ghostMove(p, directions), this.grid.getPlayerObject(lS.get(0))) && !this.grid.ghostMove(p, directions).getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   else if(lI.size() == 3 && lI.get(0) == 1 && lI.get(1) == 2 && lI.get(2) == 3 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && (Integer.parseInt(lS.get(2)) == 0 || Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(lS.size() > 4 && Integer.parseInt(lS.get(2)) == 2 && !lS.get(4).isEmpty() && Integer.parseInt(lS.get(4)) >= 0 && Integer.parseInt(lS.get(4)) <= 4)
                           directions.add(Integer.parseInt(lS.get(4)));

                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4 &&  this.grid.canMove(p, Integer.parseInt(lS.get(1))) && this.grid.canGhostMove(p, directions) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   else if(lI.size() == 3 && lI.get(0) == 3 && lI.get(1) == 1 && lI.get(2) == 2 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && (Integer.parseInt(lS.get(2)) == 0 || Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(lS.size() > 4 && Integer.parseInt(lS.get(2)) == 2 && !lS.get(4).isEmpty() && Integer.parseInt(lS.get(4)) >= 0 && Integer.parseInt(lS.get(4)) <= 4)
                           directions.add(Integer.parseInt(lS.get(4)));

                       if(this.grid.canGhostMove(p, directions) && this.grid.isInViewZone(this.grid.ghostMove(p, directions), this.grid.getPlayerObject(lS.get(0))) && !this.grid.ghostMove(p, directions).getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4 && this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   else if(lI.size() == 2 && lI.get(0) == 1 && lI.get(1) == 4 && !lS.isEmpty() && !lS.get(0).isEmpty() &&
                           this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && lC.contains(Colour.YELLOW))
                           x = true;
                   else if(lI.size() == 3 && lI.get(0) == 1 && lI.get(1) == 2 && lI.get(2) == 4 && lS.size() > 1 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() &&
                           this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4 && this.grid.canMove(p, Integer.parseInt(lS.get(1))) && lC.contains(Colour.YELLOW))
                           x = true;
                   else if(lI.size() == 3 && lI.get(0) == 1 && lI.get(1) == 3 && lI.get(2) == 4 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(2).isEmpty() && (Integer.parseInt(lS.get(2)) == 0 || Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(lS.size() > 4 && Integer.parseInt(lS.get(2)) == 2 && !lS.get(4).isEmpty() && Integer.parseInt(lS.get(4)) >= 0 && Integer.parseInt(lS.get(4)) <= 4)
                           directions.add(Integer.parseInt(lS.get(4)));

                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && this.grid.canGhostMove(p, directions) && lC.containsAll(Arrays.asList(Colour.BLUE, Colour.YELLOW)))
                           x = true;
                   }
                   else if(lI.size() == 3 && lI.get(0) == 3 && lI.get(1) == 1 && lI.get(2) == 4 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(2).isEmpty() && (Integer.parseInt(lS.get(2)) == 0 || Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(lS.size() > 4 && Integer.parseInt(lS.get(2)) == 2 && !lS.get(4).isEmpty() && Integer.parseInt(lS.get(4)) >= 0 && Integer.parseInt(lS.get(4)) <= 4)
                           directions.add(Integer.parseInt(lS.get(4)));

                       if(this.grid.canGhostMove(p, directions) && this.grid.isInViewZone(this.grid.ghostMove(p, directions), this.grid.getPlayerObject(lS.get(0))) && !this.grid.ghostMove(p, directions).getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && lC.containsAll(Arrays.asList(Colour.BLUE, Colour.YELLOW)))
                           x = true;
                   }
                   else if(lI.size() == 4 && lI.get(0) == 3 && lI.get(1) == 1 && lI.get(2) == 2 && lI.get(3) == 4 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && (Integer.parseInt(lS.get(2)) == 0 || Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(lS.size() > 4 && Integer.parseInt(lS.get(2)) == 2 && !lS.get(4).isEmpty() && Integer.parseInt(lS.get(4)) >= 0 && Integer.parseInt(lS.get(4)) <= 4)
                           directions.add(Integer.parseInt(lS.get(4)));

                       if(this.grid.canGhostMove(p, directions) && this.grid.isInViewZone(this.grid.ghostMove(p, directions), this.grid.getPlayerObject(lS.get(0))) && !this.grid.ghostMove(p, directions).getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4 && this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) && lC.containsAll(Arrays.asList(Colour.BLUE, Colour.YELLOW)))
                           x = true;
                   }
                   else if(lI.size() == 4 && lI.get(0) == 1 && lI.get(1) == 2 && lI.get(2) == 3 && lI.get(3) == 4 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && (Integer.parseInt(lS.get(2)) == 0 || Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() > 3 && !lS.get(3).isEmpty() && Integer.parseInt(lS.get(3)) >= 0 && Integer.parseInt(lS.get(3)) <= 4)
                           directions.add(Integer.parseInt(lS.get(3)));
                       if(lS.size() > 4 && Integer.parseInt(lS.get(2)) == 2 && !lS.get(4).isEmpty() && Integer.parseInt(lS.get(4)) >= 0 && Integer.parseInt(lS.get(4)) <= 4)
                           directions.add(Integer.parseInt(lS.get(4)));

                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4 && this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) && this.grid.canGhostMove(p, directions) && lC.containsAll(Arrays.asList(Colour.BLUE, Colour.YELLOW)))
                           x = true;
                   }
                   break;
               case "Shockwave":
                   if(lI.contains(1) && !lI.contains(2) &&
                           ((!lS.get(0).isEmpty() && lS.get(1).isEmpty() && lS.get(2).isEmpty() && this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) <= 1) ||
                                   (!lS.get(0).isEmpty() && !lS.get(1).isEmpty() && lS.get(2).isEmpty() && this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) <= 1 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(1))) <= 1 && !(this.grid.getPlayerObject(lS.get(0)).getCell().equals(this.grid.getPlayerObject(lS.get(1)).getCell()))) ||
                                   (!lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) <= 1 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(1))) <= 1 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(2))) <= 1) && !(this.grid.getPlayerObject(lS.get(0)).getCell().equals(this.grid.getPlayerObject(lS.get(1)).getCell())) && !(this.grid.getPlayerObject(lS.get(1)).getCell().equals(this.grid.getPlayerObject(lS.get(2)).getCell())) && !(this.grid.getPlayerObject(lS.get(0)).getCell().equals(this.grid.getPlayerObject(lS.get(2)).getCell()))))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) &&
                           (lC.contains(Colour.YELLOW)))
                        x = true;
                   break;
               case "Shotgun":
                   if(lI.contains(1) && !lI.contains(3) &&
                           (p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && (lI.size() < 2 || lI.size() == 2 && this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 4)))
                           x = true;
                   if(!lI.contains(1) && lI.contains(3) &&
                           (this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) == 1))
                           x = true;
                   break;
               case "Sledgehammer":
                   if(lI.contains(1) && !lI.contains(2) && !lS.isEmpty() && !lS.get(0).isEmpty() &&
                           (p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell())))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty()) {
                       List<Integer> directions = new LinkedList<>();
                       if(!lS.get(2).isEmpty())
                           directions.add(Integer.parseInt(lS.get(2)));
                       if(p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) &&
                               (Integer.parseInt(lS.get(1)) == 0 || ((Integer.parseInt(lS.get(1))) == 1 && this.grid.canGhostMove(p, directions) && Integer.parseInt(lS.get(2) )>= 0 && Integer.parseInt(lS.get(2)) <= 4) ||
                               (Integer.parseInt(lS.get(1)) == 2 && this.grid.canGhostMove(p, directions) && this.grid.canGhostMove(this.grid.ghostMove(p, directions), directions) && Integer.parseInt(lS.get(2)) >= 0 && Integer.parseInt(lS.get(2)) <= 4)) && lC.contains(Colour.RED))
                           x = true;    //is the if correct?
                   }
                   break;
               case "T.H.O.R.":
                   if(lI.size() == 1 && lI.get(0) == 1 && !lS.isEmpty() && !lS.get(0).isEmpty()) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                           x = true;
                   }
                   else if(lI.size() == 2 && lI.get(0) == 1 && lI.get(1) == 2 && lS.size() > 1 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty()) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.isInViewZone(this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1))) && !(this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(1)))) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   else if(lI.size() == 3 && lI.get(0) == 1 && lI.get(1) == 2 && lI.get(2) == 3 && lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.isInViewZone(this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1))) && this.grid.isInViewZone(this.grid.getPlayerObject(lS.get(1)), this.grid.getPlayerObject(lS.get(2))) &&
                               !(this.grid.getPlayerObject(lS.get(1)).equals(this.grid.getPlayerObject(lS.get(2)))) && !(this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(2)))) && !(this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(1)))) && Collections.frequency(lC, Colour.BLUE) == 2)
                           x = true;
                   break;
               case "Tractor Beam":
                   if(lI.contains(1) && !lI.contains(2)) {
                       if((lS.get(1).isEmpty() && lS.get(2).isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0)))) ||
                               (this.grid.isInViewZone(p, grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) && Integer.parseInt(lS.get(1)) >= 0 && Integer.parseInt(lS.get(1)) <= 2 && Integer.parseInt(lS.get(2)) >= 0 && Integer.parseInt(lS.get(2)) <= 3))
                            x = true;
                   }
                   else if(!lI.contains(1) && lI.contains(2) &&
                           (this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) < 3 && lC.containsAll(Arrays.asList(Colour.RED, Colour.YELLOW))))
                           x = true;
                   break;
               case "Vortex Cannon":
                   if((lI.size() == 1 && lI.contains(1) && lS.size() > 2 &&
                           this.grid.isInViewZone(p, grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) && !p.getCell().getPos().equals(grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) &&
                               this.grid.distance(this.grid.getPlayerObject(lS.get(0)), grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) < 2) ||
                   (lI.size() == 2 && lI.contains(1) && lI.contains(2) && lS.size() > 4 &&
                           (this.grid.isInViewZone(p, grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) && !p.getCell().getPos().equals(grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) &&
                                   this.grid.distance(this.grid.getPlayerObject(lS.get(0)), grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) < 2) &&
                           this.grid.distance(this.grid.getPlayerObject(lS.get(3)), grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) < 2 && (lS.get(4).isEmpty() || this.grid.distance(this.grid.getPlayerObject(lS.get(4)), grid.getBoard().getArena()[Integer.parseInt(lS.get(1))][Integer.parseInt(lS.get(2))].getPos()) < 2) && lC.contains(Colour.RED)))
                           x = true;
                   break;
               case "Whisper":
                   if(!lS.isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) >= 2)
                       x = true;
                   break;
               case "ZX-2":
                   if((lI.contains(1) && !lI.contains(2) && !lS.isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0)))) ||
                           (!lI.contains(1) && lI.contains(2) && ((!lS.isEmpty() && !lS.get(0).isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0)))) ||
                                   (lS.size() > 1 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && !(this.grid.getPlayerObject(lS.get(0)).getCell().getC().equals(this.grid.getPlayerObject(lS.get(1)).getCell().getC()))) ||
                                   (lS.size() > 2 && !lS.get(0).isEmpty() && !lS.get(1).isEmpty() && !lS.get(2).isEmpty() && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(2))) &&
                                       !(this.grid.getPlayerObject(lS.get(0)).getCell().getC().equals(this.grid.getPlayerObject(lS.get(1)).getCell().getC())) && !(this.grid.getPlayerObject(lS.get(1)).getCell().getC().equals(this.grid.getPlayerObject(lS.get(2)).getCell().getC())) && !(this.grid.getPlayerObject(lS.get(0)).getCell().getC().equals(this.grid.getPlayerObject(lS.get(2)).getCell().getC()))))))
                            x = true;
                   break;
               default: x = false;
           }
        }
        return x;
    }

    /**
     * Performs the shoot (non-adrenaline) action associated with the isValid.
     *
     * @param p player
     * @param nameWC weapon card
     * @param lI effect number list
     * @param lS various string list
     * @param lA ammocube list
     * @param lP powerup list
     * @throws RemoteException RMI exception
     */
    private void shootNotAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, List<AmmoCube> lA, List<PowerUpCard> lP) throws RemoteException {
        switch(nameWC) {
            case "Cyberblade":
                int x = 0;
                for(Integer i : lI) {
                    if(i == 1) {
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        x = 1;
                    }
                    if(i == 2) {
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(1));
                    }
                    if((x == 1) && i == 3)
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p , this.grid.getPlayerObject(lS.get(2)));
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
                if(lI.get(0) == 2) {
                    if(lS.size() == 4 && !lS.get(2).isEmpty() && !lS.get(3).isEmpty())
                        ((Flamethrower) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(0), lS.get(1), lS.get(2), lS.get(3));
                    else
                        ((Flamethrower) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(0), lS.get(1), null, null);
                }
                break;
            case "Furnace":
                if(lI.get(0) == 1)
                    ((Furnace) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, Colour.valueOf(lS.get(0)));
                if(lI.get(0) == 2)
                    ((Furnace) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(0), lS.get(1));
                break;
            case "Grenade Launcher":
                for(Integer i : lI) {
                    if(i == 1) {
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        if(!lS.get(1).isEmpty())
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
                if(lI.size() == 2 && lI.get(1) == 2 && y == 1)
                    ((LockRifle) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(1)));
                break;
            case "Machine Gun":
                if(lI.get(0) == 1)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)));
                if(lI.size() > 1 && lI.get(1) == 2)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(2)));
                if(lI.size() == 3 && lI.get(2) == 3)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p,this.grid.getPlayerObject(lS.get(3)), this.grid.getPlayerObject(lS.get(4)));
                break;
            case "Plasma Gun":
                int z = 0;
                for(int i : lI) {
                    if(i == 1) {
                        ((PlasmaGun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        z = 1;
                    }
                    else if(i == 2) {
                        if(!lS.get(3).isEmpty())
                            ((PlasmaGun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)));
                        else
                            ((PlasmaGun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)), 0);
                    }
                    else if(i == 3 && z == 1)
                        ((PlasmaGun) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                }
                break;
            case "Power Glove":
                if(lI.get(0) == 1)
                    ((PowerGlove) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                else if(lI.get(0) == 2) {
                    ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart1(p, this.grid, lS.get(0), lS.get(1));
                    if(lI.contains(3))
                        ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart2(this.grid, p, this.grid.getPlayerObject(lS.get(2)));
                    if(lI.contains(4))
                        ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart3(p, this.grid, lS.get(3), lS.get(4));
                    if(lI.contains(5))
                        ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart4(this.grid, p, this.grid.getPlayerObject(lS.get(5)));
                }
                break;
            case "Railgun":
                if(lI.get(0) == 1)
                    ((Railgun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                else if(lI.get(0) == 2)
                    ((Railgun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)));
                break;
            case "Rocket Launcher":
                int h = 0;
                for(int i : lI) {
                    if(i == 1) {
                        ((RocketLauncher) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        h = 1;
                        if(lI.size() > 1 && lI.get(lI.indexOf(1) + 1) == 2)
                            ((RocketLauncher) p.getWeaponCardObject(nameWC)).movePlayer(this.grid,this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));
                    }
                    else if(i == 3) {
                        if(!lS.get(4).isEmpty())
                            ((RocketLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)), Integer.parseInt(lS.get(4)));
                        else
                            ((RocketLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)), 0);
                    }
                    else if(i == 4 && h == 1)
                        ((RocketLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p);
                }
                break;
            case "Shockwave":
                if(lI.get(0) == 1)
                    ((Shockwave) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)), this.grid.getPlayerObject(lS.get(2)));
                else if(lI.get(0) == 2)
                    ((Shockwave) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p);
                break;
            case "Shotgun":
                if(lI.get(0) == 1) {
                    ((Shotgun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    if(lI.size() > 1)
                        ((Shotgun) p.getWeaponCardObject(nameWC)).movePlayer(this.grid, this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));
                }
                else if(lI.get(0) == 3)
                    ((Shotgun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Sledgehammer":
                if(lI.get(0) == 1)
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                else if(lI.get(0) == 2) {
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    if(!lS.get(2).isEmpty())
                        ((Sledgehammer) p.getWeaponCardObject(nameWC)).moveEnemy(this.grid.getPlayerObject(lS.get(0)), this.grid, Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)));
                }
                break;
            case "T.H.O.R.":
                if(lI.get(0) == 1)
                    ((THOR) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.size() > 1 && lI.get(1) == 2)
                    ((THOR) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(1)));
                if(lI.size() > 2 && lI.get(2) == 3)
                    ((THOR) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p, this.grid.getPlayerObject(lS.get(2)));
                break;
            case "Tractor Beam":
                if(lI.get(0) == 1)
                    ((TractorBeam) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)));
                else if(lI.get(0) == 2)
                    ((TractorBeam) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Vortex Cannon":
                if(lI.get(0) == 1) {
                    ((VortexCannon) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), lS.get(1), lS.get(2));
                    if(lI.size() == 2 && lI.get(1) == 2)
                        ((VortexCannon) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(3)), this.grid.getPlayerObject(lS.get(4)));
                }
                break;
            case "Whisper":
                ((Whisper) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "ZX-2":
                if(lI.get(0) == 1)
                    ((ZX2) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                else if(lI.get(0) == 2)
                    ((ZX2) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)), this.grid.getPlayerObject(lS.get(2)));
                break;

            default: return;

        }
        p.getWeaponCardObject(nameWC).unload();
        p.payCard(lA, lP);
        this.grid.getPowerUpDiscardPile().addAll(lP);
    }

    /**
     * Determines whether or not the adrenaline shoot parameters are valid.
     *
     * @param p player
     * @param nameWC weapon card
     * @param lI effect number list
     * @param lS various string list
     * @param direction direction
     * @param lA ammo cube list
     * @param lP powerup list
     * @return boolean
     */
    private boolean isValidShootAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, int direction, List<AmmoCube> lA, List<PowerUpCard> lP) {
        if(!this.grid.canMove(p, direction))
            return false;
        else {
            Player future = new Player("?fUtUrE!", p.getC(), p.hasFirstPlayerCard());
            future.changeCell(p.getCell());
            for(WeaponCard w : p.getWeaponCards())
                future.addWeaponCard(w);
            this.grid.moveWithoutNotify(future, direction);
            return this.isValidShootNotAdrenaline(future, nameWC, lI, lS, lA, lP);
        }
    }

    /**
     * Performs the adrenaline shoot action associated with the isValid.
     *
     * @param p player
     * @param nameWC weapon card
     * @param lI effect number list
     * @param lS various string list
     * @param direction direction
     * @param lA ammo cube list
     * @param lP powerup list
     * @throws RemoteException RMI exception
     */
    private void shootAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, int direction, List<AmmoCube> lA, List<PowerUpCard> lP) throws RemoteException {
        this.grid.moveWithoutNotify(p, direction);
        this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
    }


    /**
     * Determines whether or not the shoot (first action) parameters are valid.
     *
     * @param nickName      nickname
     * @param nameWC        weapon card
     * @param lI            effect number list
     * @param lS            various string list
     * @param direction     direction
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour list
     * @return boolean
     */
    public boolean isValidFirstActionShoot(String nickName, String nameWC, List<Integer> lI, List<String> lS, int direction, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        if(this.gameState.equals(ENDTURN))
            this.gameState = STARTTURN;
        List<AmmoCube> lA = new LinkedList<>();
        List<PowerUpCard> lP = new LinkedList<>();

        Set<Integer> lIset = new HashSet<>(lI);
        if(lIset.size() < lI.size())                //to check if there are repetitions in lI, which means that player wants to apply the same effect multiple times
            return false;

        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));
        AmmoCube[] cubeArray = new AmmoCube[lA.size()];
        lA.toArray(cubeArray);
        if(!p.checkAmmoCube(cubeArray))
            return false;

        for(int i = 0; i < lPInput.size(); i++) {
            if(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                return false;
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }

        if(this.gameState.equals(STARTTURN)) {
            if(!p.isAdrenaline2())
                return isValidShootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
            else
                return isValidShootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        }
        return false;
    }

    /**
     * Performs shoot (first action) associated with the isValid.
     *
     * @param nickName      nickname
     * @param nameWC        weapon card
     * @param lI            effect number list
     * @param lS            various string list
     * @param direction     direction
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour list
     * @throws RemoteException RMI exception
     */
    public synchronized void firstActionShoot(String nickName, String nameWC, List<Integer> lI, List<String> lS, int direction, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) throws RemoteException {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA= new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for(Colour c : lAInput)
                lA.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP= new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for(int i = 0; i < lPInput.size(); i++)
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }
        if(!p.isAdrenaline2())
            this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
        else
            this.shootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        this.gameState = ACTION1;
    }

    /**
     * Moves player in the given directions.
     *
     * @param p player
     * @param directions  direction list
     * @throws RemoteException RMI exception
     */
    private void move(Player p, List<Integer> directions) throws RemoteException {
        for(Integer i : directions) {
            this.grid.move(p, i);    //view will tell player if there's a wall
        }
    }


    /**
     * Determines whether or not the move (first action) parameters are valid.
     *
     * @param nickName   nickname
     * @param directions direction list
     * @return boolean
     */
    public boolean isValidFirstActionMove(String nickName, List<Integer> directions) {
        Player p = this.grid.getPlayerObject(nickName);
        if(this.gameState.equals(ENDTURN))
            this.gameState = STARTTURN;
        return (this.gameState.equals(STARTTURN) && (directions.isEmpty() || (directions.size() < 4 && grid.canGhostMove(p, directions))));
    }

    /**
     * Performs the move (first action) associated with the isValid.
     *
     * @param nickName   nickname
     * @param directions direction list
     * @throws RemoteException RMI exception
     */
    public synchronized void firstActionMove(String nickName, List<Integer> directions) throws RemoteException { //player p moves 1,2,3 cells: directions contains every direction from cell to cell
        Player p = this.grid.getPlayerObject(nickName);
        move(p, directions);
        this.gameState = ACTION1;
    }

    /**
     * Gives player the contents of the given ammo card.
     *
     * @param p player
     * @param card ammo card
     */
    private void giveWhatIsOnAmmoCard(Player p, AmmoCard card) {
       if(card.haspC())
           this.grid.pickPowerUpCard(p);
       for(AmmoCube cube : card.getaC())
           p.addNewAC(cube);
       this.grid.getAmmoDiscardPile().add(card);
    }

    /**
     * Determines whether or not the given ammo cube list can fulfill the given weapon's reload cost.
     *
     * @param w weapon card
     * @param l ammo cube list
     * @return boolean
     */
    private boolean canPay(WeaponCard w, List<AmmoCube> l) {
        List<AmmoCube> l2 = new ArrayList<>(Arrays.asList(w.getReloadCost()));          //this way the original array is not modified
        l2.remove(0);

        if(l2.isEmpty())
            return true;

        List<Colour> lInput = new LinkedList<>();
        for(AmmoCube aC : l)
            lInput.add(aC.getC());

        List<Colour> lCost = new LinkedList<>();
        for(AmmoCube aCost : l2)
            lCost.add(aCost.getC());

        return lInput.containsAll(lCost);
    }

    /**
     * Determines whether or not the grab (first action) parameters are valid.
     *
     * @param nickName      nickname
     * @param directionList direction list
     * @param wCardInput    weapon card
     * @param wSlotInput    weapon slot
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour list
     * @return boolean
     */
    public boolean isValidFirstActionGrab(String nickName, List<Integer> directionList, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        if(this.gameState.equals(ENDTURN))
            this.gameState = STARTTURN;
        if(this.gameState.equals(STARTTURN)) {
            Player p = this.grid.getPlayerObject(nickName);
            WeaponCard wCard = null;
            if(!wCardInput.equals(""))
                wCard = this.grid.getWeaponCardObject(wCardInput);
            List<AmmoCube> lA = new LinkedList<>();
            List<PowerUpCard> lP = new LinkedList<>();

            if((!p.isAdrenaline1() && directionList.size() > 1) || (p.isAdrenaline1() && directionList.size() > 2))
                return false;

            if(!this.grid.canGhostMove(p, directionList))
                return false;

            for(Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);

            if(wCard != null && wSlotInput.isEmpty())
                return false;
            else if(wCard != null && ((wSlotInput.equals("1") && (this.grid.ghostMove(p, directionList).getCell().getPos().getX() == 0 && this.grid.ghostMove(p, directionList).getCell().getPos().getY() == 2) && this.checkWeaponSlotContents(1).contains(wCardInput)) ||
                    (wSlotInput.equals("2") && (this.grid.ghostMove(p, directionList).getCell().getPos().getX() == 2 && this.grid.ghostMove(p, directionList).getCell().getPos().getY() == 3) && this.checkWeaponSlotContents(2).contains(wCardInput)) ||
                    (wSlotInput.equals("3") && (this.grid.ghostMove(p, directionList).getCell().getPos().getX() == 1 && this.grid.ghostMove(p, directionList).getCell().getPos().getY() == 0) && this.checkWeaponSlotContents(3).contains(wCardInput)))) {
                if(cubeArray.length != 0 && !p.checkAmmoCube(cubeArray))
                    return false;
                for(int i = 0; i < lPInput.size(); i++) {
                    if(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                        return false;
                    lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
                }
                if(!canPay(wCard, choosePayment(lA, lP)))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Performs the grab (first action) associated with the isValid.
     *
     * @param nickName      nickname
     * @param directions direction list
     * @param wCardInput    weapon card
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour list
     * @throws RemoteException RMI exception
     */
    public synchronized void firstActionGrab(String nickName, List<Integer> directions, String wCardInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) throws RemoteException { //directions contains where p wants to go. directions contains '0' if p doesn't want to move and only grab
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> l = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for(Colour c : lAInput)
                l.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for(int i = 0; i < lPInput.size(); i++)
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }
        if(!(p.isAdrenaline1()))
            grabNotAdrenaline(p, directions, wCard, l, lP);

        if(p.isAdrenaline1()||p.isAdrenaline2()) {
            grabAdrenaline(p, directions, wCard, l, lP);
        }
        this.gameState = ACTION1;
    }

    /**
     * Performs the non-adrenaline grab action.
     *
     * @param p player
     * @param d direction list (considers only first member)
     * @param w weapon card
     * @param lA ammo cube list
     * @param lP powerup list
     * @throws RemoteException RMI exception
     */
    private void grabNotAdrenaline(Player p, List<Integer> d, WeaponCard w, List<AmmoCube> lA, List<PowerUpCard> lP) throws RemoteException {
        if(!d.isEmpty())
            this.grid.move(p, d.get(0));
        if(p.getCell().getStatus() == 0) {
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
            p.getCell().setA(null);
        }
        else if(p.getCell().getStatus() == 1) {
            p.payCard(lA, lP);
            p.getWeaponCards().add(w);
            p.getWeaponCards().get(p.getWeaponCards().size() - 1).reload();
            this.removeFromWeaponSlot(w);
        }
        if(p.getWeaponCards().size() > 3)
            this.discard = true;
    }

    /**
     * Removes weapon card from the weapon slot containing it.
     *
     * @param wCard weapon card
     */
    private void removeFromWeaponSlot(WeaponCard wCard) {
        if(this.checkWeaponSlotContents(1).contains(wCard.getCardName())) {
            if(grid.getBoard().getW1().getCard1().equals(wCard))
                grid.getBoard().getW1().setCard1(null);
            else if(grid.getBoard().getW1().getCard2().equals(wCard))
                grid.getBoard().getW1().setCard2(null);
            else if(grid.getBoard().getW1().getCard3().equals(wCard))
                grid.getBoard().getW1().setCard3(null);
        }
        else if(this.checkWeaponSlotContents(2).contains(wCard.getCardName())) {
            if(grid.getBoard().getW2().getCard1().equals(wCard))
                grid.getBoard().getW2().setCard1(null);
            else if(grid.getBoard().getW2().getCard2().equals(wCard))
                grid.getBoard().getW2().setCard2(null);
            else if(grid.getBoard().getW2().getCard3().equals(wCard))
                grid.getBoard().getW2().setCard3(null);
        }
        else if(this.checkWeaponSlotContents(3).contains(wCard.getCardName())) {
            if(grid.getBoard().getW3().getCard1().equals(wCard))
                grid.getBoard().getW3().setCard1(null);
            else if(grid.getBoard().getW3().getCard2().equals(wCard))
                grid.getBoard().getW3().setCard2(null);
            else if(grid.getBoard().getW3().getCard3().equals(wCard))
                grid.getBoard().getW3().setCard3(null);
        }
    }

    /**
     * Socket method for when a card is discarded.
     *
     * @return boolean
     */
    public boolean isDiscard() {
        return discard;
    }

    /**
     * Discards weapon card.
     *
     * @param nickName nickname
     * @param wSInput  weapon slot
     * @param wCInput  weapon card
     */
    public void discardWeaponCard(String nickName, String wSInput, String wCInput) {
       Player p = this.grid.getPlayerObject(nickName);
       WeaponCard wC = p.getWeaponCardObject(wCInput);
       WeaponSlot wS = this.grid.getWeaponSlotObject(wSInput);
       p.removeWeaponCard(wC);
       if(wS.getCard1() == null) {
           wS.setCard1(wC);
           return;
       }
        if(wS.getCard2() == null) {
            wS.setCard2(wC);
            return;
        }
        if(wS.getCard3() == null)
            wS.setCard3(wC);
    }

    /**
     * Performs the adrenaline grab action associated with the isValid.
     *
     * @param p player
     * @param d direction list
     * @param w weapon card
     * @param lA ammo cube list
     * @param lP powerup list
     * @throws RemoteException RMI exception.
     */
    private void grabAdrenaline(Player p, List<Integer> d, WeaponCard w, List<AmmoCube> lA, List<PowerUpCard> lP) throws RemoteException {
        if(!d.isEmpty())
            this.grid.move(p, d.get(0));
        if(d.size() == 2)
            this.grid.move(p, d.get(1));
        if(p.getCell().getStatus() == 0) {
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
            p.getCell().setA(null);
        }
        else if(p.getCell().getStatus() == 1) {
                p.payCard(lA, lP);
                p.getWeaponCards().add(w);
                p.getWeaponCards().get(p.getWeaponCards().size() - 1).reload();
                this.removeFromWeaponSlot(w);
        }
        if(p.getWeaponCards().size() > 3)
            this.discard = true;
    }

    /**
     * Returns list merging the ammocubes and the powerups' colour values (acting as ammo cubes) which should then
     * be used for paying a reload cost, for example.
     *
     * @param lA ammo cube list
     * @param lP powerup list
     * @return merged ammo cube list
     */
    private List<AmmoCube> choosePayment(List<AmmoCube> lA, List<PowerUpCard> lP) {
        List<AmmoCube> l = new LinkedList<>();
        if(!lA.isEmpty())
            l.addAll(lA);
        if(!lP.isEmpty()) {
            for(PowerUpCard p : lP)
                l.add(new AmmoCube(p.getC()));
        }
        return l;
    }

    /**
     * Checks contents of given weapon slot.
     *
     * @param n weapon slot
     * @return weapon list (default: null)
     */
    public List<String> checkWeaponSlotContents(int n) {
        List<String> lEmpty = new LinkedList<>();
        if(n == 1)
            return checkWeaponSlot1Contents();
        else if(n == 2)
            return checkWeaponSlot2Contents();
        else if(n == 3)
            return checkWeaponSlot3Contents();

        return lEmpty;
    }

    /**
     * Checks contents of weapon slot.
     * GUI-exclusive method.
     *
     * @param n weapon slot
     * @return weapon list (default: null)
     */
    public List<String> checkWeaponSlotContentsReduced(int n) {
        List<String> lEmpty = new LinkedList<>();
        if(n == 1)
            return checkWeaponSlot1ContentsReduced();
        else if(n == 2)
            return checkWeaponSlot2ContentsReduced();
        else if(n == 3)
            return checkWeaponSlot3ContentsReduced();

        return lEmpty;

    }

    /**
     * Checks contents of weapon slot 1.
     *
     * @return weapon list
     */
    private List<String> checkWeaponSlot1Contents() {
        List<String> l = new LinkedList<>();
        if(grid.getBoard().getW1().getCard1() != null) {
            l.add(grid.getBoard().getW1().getCard1().getCardName());
            for(AmmoCube a : grid.getBoard().getW1().getCard1().getReloadCost())
                l.add(a.getC().getColourId());
        }
        if(grid.getBoard().getW1().getCard2() != null) {
            l.add(grid.getBoard().getW1().getCard2().getCardName());
            for(AmmoCube a : grid.getBoard().getW1().getCard2().getReloadCost())
                l.add(a.getC().getColourId());
        }
        if(grid.getBoard().getW1().getCard3() != null) {
            l.add(grid.getBoard().getW1().getCard3().getCardName());
            for(AmmoCube a : grid.getBoard().getW1().getCard3().getReloadCost())
                l.add(a.getC().getColourId());
        }
        return l;
    }

    /**
     * Checks contents of weapon slot 2.
     *
     * @return weapon list
     */
    private List<String> checkWeaponSlot2Contents() {
        List<String> l = new LinkedList<>();
        if(grid.getBoard().getW2().getCard1() != null) {
            l.add(grid.getBoard().getW2().getCard1().getCardName());
            for(AmmoCube a : grid.getBoard().getW2().getCard1().getReloadCost())
                l.add(a.getC().getColourId());
        }
        if(grid.getBoard().getW2().getCard2() != null) {
            l.add(grid.getBoard().getW2().getCard2().getCardName());
            for(AmmoCube a : grid.getBoard().getW2().getCard2().getReloadCost())
                l.add(a.getC().getColourId());
        }
        if(grid.getBoard().getW2().getCard3() != null) {
            l.add(grid.getBoard().getW2().getCard3().getCardName());
            for(AmmoCube a : grid.getBoard().getW2().getCard3().getReloadCost())
                l.add(a.getC().getColourId());
        }
        return l;
    }

    /**
     * Checks contents of weapon slot 3.
     *
     * @return weapon list
     */
    private List<String> checkWeaponSlot3Contents() {
        List<String> l = new LinkedList<>();
        if(grid.getBoard().getW3().getCard1() != null) {
            l.add(grid.getBoard().getW3().getCard1().getCardName());
            for(AmmoCube a : grid.getBoard().getW3().getCard1().getReloadCost())
                l.add(a.getC().getColourId());
        }
        if(grid.getBoard().getW3().getCard2() != null) {
            l.add(grid.getBoard().getW3().getCard2().getCardName());
            for(AmmoCube a : grid.getBoard().getW3().getCard2().getReloadCost())
                l.add(a.getC().getColourId());
        }
        if(grid.getBoard().getW3().getCard3() != null) {
            l.add(grid.getBoard().getW3().getCard3().getCardName());
            for(AmmoCube a : grid.getBoard().getW3().getCard3().getReloadCost())
                l.add(a.getC().getColourId());
        }
        return l;
    }

    /**
     * Checks contents of weapon slot 1.
     * GUI-exclusive method.
     *
     * @return weapon list
     */
    private List<String> checkWeaponSlot1ContentsReduced() {
        List<String> l = new LinkedList<>();
        if(grid.getBoard().getW1().getCard1() != null)
            l.add(grid.getBoard().getW1().getCard1().getCardName());
        if(grid.getBoard().getW1().getCard2() != null)
            l.add(grid.getBoard().getW1().getCard2().getCardName());
        if(grid.getBoard().getW1().getCard3() != null)
            l.add(grid.getBoard().getW1().getCard3().getCardName());
        return l;
    }

    /**
     * Checks contents of weapon slot 1.
     * GUI-exclusive method.
     *
     * @return weapon list
     */
    private List<String> checkWeaponSlot2ContentsReduced() {
        List<String> l = new LinkedList<>();
        if(grid.getBoard().getW2().getCard1() != null)
            l.add(grid.getBoard().getW2().getCard1().getCardName());
        if(grid.getBoard().getW2().getCard2() != null)
            l.add(grid.getBoard().getW2().getCard2().getCardName());
        if(grid.getBoard().getW2().getCard3() != null)
            l.add(grid.getBoard().getW2().getCard3().getCardName());
        return l;
    }

    /**
     * Checks contents of weapon slot 3.
     * GUI-exclusive method.
     *
     * @return weapon list
     */
    private List<String> checkWeaponSlot3ContentsReduced() {
        List<String> l = new LinkedList<>();
        if(grid.getBoard().getW3().getCard1() != null)
            l.add(grid.getBoard().getW3().getCard1().getCardName());
        if(grid.getBoard().getW3().getCard2() != null)
            l.add(grid.getBoard().getW3().getCard2().getCardName());
        if(grid.getBoard().getW3().getCard3() != null)
            l.add(grid.getBoard().getW3().getCard3().getCardName());
        return l;
    }

    /**
     * Reveals ammo cards on each cell in an ASCII representation of the arena.
     *
     * @return ASCII arena
     */
    public String showCardsOnBoard() {
        List<String> rows = new LinkedList<>();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                if(grid.getBoard().getArena()[i][j].getStatus() == 0 && grid.getBoard().getArena()[i][j].getA() != null)
                    rows.add(grid.getBoard().getArena()[i][j].getA().getAmmoCardName() + "   ");
                else if(grid.getBoard().getArena()[i][j].getStatus() == 0 && grid.getBoard().getArena()[i][j].getA() == null)
                    rows.add("empty ");
                else if(grid.getBoard().getArena()[i][j].getStatus() == 1)
                    rows.add("spawn ");
                else
                    rows.add("N/A   ");
            }
        }
        return rows.get(0) + rows.get(1) + rows.get(2) + rows.get(3) + "\n\n" +
                rows.get(4) + rows.get(5) + rows.get(6) + rows.get(7) + "\n\n" +
                rows.get(8) + rows.get(9) + rows.get(10) + rows.get(11) +"\n";
    }

    /**
     * Determines whether or not the move (second action) parameters are valid.
     *
     * @param nickName   nickname
     * @param directions direction list
     * @return boolean
     */
    public boolean isValidSecondActionMove(String nickName, List<Integer> directions) {
        Player p = this.grid.getPlayerObject(nickName);
        return (this.gameState.equals(ACTION1) && (directions.isEmpty() || (directions.size() < 4 && grid.canGhostMove(p, directions))));
    }

    /**
     * Performs the move (second action) associated with the isValid.
     *
     * @param nickName   nickname
     * @param directions direction list
     * @throws RemoteException RMI exception
     */
    public synchronized void secondActionMove(String nickName, List<Integer> directions ) throws RemoteException { //player p moves 1,2,3 cells: directions contains every direction from cell to cell
        Player p = this.grid.getPlayerObject(nickName);
        move(p, directions);
        this.gameState = ACTION2;
    }

    /**
     * Determines whether or not the grab (second action) parameters are valid.
     *
     * @param nickName      nickname
     * @param directionList direction list
     * @param wCardInput    weapon card
     * @param wSlotInput    weapon slot input
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @return boolean
     */
    public boolean isValidSecondActionGrab(String nickName, List<Integer> directionList, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        if(this.gameState.equals(ACTION1)) {
            Player p = this.grid.getPlayerObject(nickName);
            WeaponCard wCard = null;
            if(!wCardInput.equals(""))
                wCard = this.grid.getWeaponCardObject(wCardInput);
            List<AmmoCube> lA = new LinkedList<>();
            List<PowerUpCard> lP = new LinkedList<>();

            if((!p.isAdrenaline1() && directionList.size() > 1) || (p.isAdrenaline1() && directionList.size() > 2))
                return false;

            if(!this.grid.canGhostMove(p, directionList))
                return false;

            for(Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);

            if(wCard != null && wSlotInput.isEmpty())
                return false;
            else if(wCard != null && ((wSlotInput.equals("1") && (this.grid.ghostMove(p, directionList).getCell().getPos().getX() == 0 && this.grid.ghostMove(p, directionList).getCell().getPos().getY() == 2) && this.checkWeaponSlotContents(1).contains(wCardInput)) ||
                        (wSlotInput.equals("2") && (this.grid.ghostMove(p, directionList).getCell().getPos().getX() == 2 && this.grid.ghostMove(p, directionList).getCell().getPos().getY() == 3) && this.checkWeaponSlotContents(2).contains(wCardInput)) ||
                        (wSlotInput.equals("3") && (this.grid.ghostMove(p, directionList).getCell().getPos().getX() == 1 && this.grid.ghostMove(p, directionList).getCell().getPos().getY() == 0) && this.checkWeaponSlotContents(3).contains(wCardInput)))) {
                    if(cubeArray.length != 0 && !p.checkAmmoCube(cubeArray))
                        return false;
                    for(int i = 0; i < lPInput.size(); i++) {
                        if(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                            return false;
                        lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
                    }
                    if(!canPay(wCard, choosePayment(lA, lP)))
                        return false;
                }
            return true;
        }
        return false;
    }

    /**
     * Performs the grab (second action) associated with the isValid.
     *
     * @param nickName      nickname
     * @param directions direction list
     * @param wCardInput    weapon card
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @throws RemoteException RMI exception
     */
    public synchronized void secondActionGrab(String nickName, List<Integer> directions, String wCardInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) throws RemoteException { //directions contains where p wants to go. directions contains '0' if p doesn't want to move and only grab
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> l = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for(Colour c : lAInput)
                l.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for(int i = 0; i < lPInput.size(); i++)
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }
        if(!(p.isAdrenaline1()))
            grabNotAdrenaline(p, directions, wCard, l, lP);

        if(p.isAdrenaline1()||p.isAdrenaline2()) {
            grabAdrenaline(p, directions, wCard, l, lP);
        }
        this.gameState = ACTION2;
    }

    /**
     * Determines whether or not the shoot (second action) parameters are valid.
     *
     * @param nickName      nickname
     * @param nameWC        weapon card
     * @param lI            effect number list
     * @param lS            various string list
     * @param direction     direction
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour list
     * @return boolean
     */
    public boolean isValidSecondActionShoot(String nickName, String nameWC, List<Integer> lI, List<String> lS, int direction, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA = new LinkedList<>();
        List<PowerUpCard> lP = new LinkedList<>();

        Set<Integer> lIset = new HashSet<>(lI);
        if(lIset.size() < lI.size())                //to check if there are repetitions in lI, which means that player wants to apply the same effect multiple times
            return false;

        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));
        AmmoCube[] cubeArray = new AmmoCube[lA.size()];
        lA.toArray(cubeArray);
        if(!p.checkAmmoCube(cubeArray))
            return false;

        for(int i = 0; i < lPInput.size(); i++) {
            if(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                return false;
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }

        if(this.gameState.equals(ACTION1)) {
            if(!p.isAdrenaline2())
                return isValidShootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
            else
                return isValidShootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        }
        return false;
    }

    /**
     * Performs the shoot (second action) associated with the isValid.
     *
     * @param nickName      nickname
     * @param nameWC        weapon card
     * @param lI            effect number list
     * @param lS            various string list
     * @param direction     direction
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour list
     * @throws RemoteException RMI exception
     */
    public synchronized void secondActionShoot(String nickName, String nameWC, List<Integer> lI, List<String> lS, int direction, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) throws RemoteException {
        Player p = this.grid.getPlayerObject(nickName);

        List<AmmoCube> lA= new LinkedList<>();
        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));

        List<PowerUpCard> lP= new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++)
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));

        if(!p.isAdrenaline2())
            this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
        else
            this.shootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        this.gameState = ACTION2;
    }

    /**
     * Determines whether or not the powerup card use conditions are valid. There is a case statement for evvery
     * powerup in the game.
     *
     * @param nickName nickname
     * @param namePC   powerup card
     * @param colourPC powerup colour
     * @param lS       various string list
     * @param c        ammo cube colour
     * @return boolean
     */
    public boolean isValidUsePowerUpCard(String nickName, String namePC, String colourPC, List<String> lS, Colour c) {
        Player p = this.grid.getPlayerObject(nickName);
        boolean x = false;
        if(this.gameState.equals(ACTION1) || this.gameState.equals(ACTION2)) {
            if(namePC.equals("Tagback Grenade")) {
                if(p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                    x = true;
                boolean exit = false;
                for(DamageToken dT : p.getPlayerBoard().getDamage().getDamageTokens()) {
                    if(dT != null && dT.getC().equals(this.grid.getPlayerObject(lS.get(0)).getC())) {
                        exit = true;
                        break;
                    }
                }
                if(!exit)
                    x = false;
            }
            else if(namePC.equals("Targeting Scope")) {
                    if(p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && p.checkAmmoCube(new AmmoCube[]{new AmmoCube(c)}))
                        x = true;
                    boolean exit = false;
                    for(int i = 0; i < this.grid.getPlayerObject(lS.get(0)).getPlayerBoard().getDamage().getDamageTokens().length - 1; i++) {
                        if(this.grid.getPlayerObject(lS.get(0)).getPlayerBoard().getDamage().getDamageTokens()[i+1] == null && this.grid.getPlayerObject(lS.get(0)).getPlayerBoard().getDamage().getDamageTokens()[i].getC().equals(p.getC())) {
                            exit = true;
                            break;
                        }
                    }
                    if(!exit)
                        x = false;
                }
        }
        if(this.gameState.equals(STARTTURN) || this.gameState.equals(ACTION1) || this.gameState.equals(ACTION2)) {
                if(namePC.equals("Newton")) {
                    List<Integer> directions = new LinkedList<>();
                    if(lS.size() == 2) {
                        if(p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) &&
                                (Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4))
                            x = true;
                    } else if(lS.size() == 3) {
                        directions.add(Integer.parseInt(lS.get(1)));
                        directions.add(Integer.parseInt(lS.get(2)));
                        if(p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && this.grid.canGhostMove(this.grid.getPlayerObject(lS.get(0)), directions) &&
                                (Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4) && (Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2 || Integer.parseInt(lS.get(2)) == 3 || Integer.parseInt(lS.get(2)) == 4))
                            x = true;
                    }
                }
                else if(namePC.equals("Teleporter") &&
                        (p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getStatus() != -1))
                        x = true;
                }
        return x;
    }

    /**
     * Performs the use powerup card action associated with the isValid.
     *
     * @param nickName nickname
     * @param namePC   powerup card
     * @param colourPC powerup colour
     * @param lS       various string list
     * @param c        ammo cube colour
     * @throws RemoteException RMI exception
     */
    public synchronized void usePowerUpCard(String nickName, String namePC, String colourPC, List<String> lS, Colour c) throws RemoteException {
        Player p = this.grid.getPlayerObject(nickName);
        switch(namePC) {
            case "Newton" :
                List<Integer> directions = new LinkedList<>();
                if(lS.size() == 2)
                    directions.add(Integer.parseInt(lS.get(1)));
                if(lS.size() == 3) {
                    directions.add(Integer.parseInt(lS.get(1)));
                    directions.add(Integer.parseInt(lS.get(2)));
                }
                ((Newton) p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC))).applyEffect(this.grid, this.grid.getPlayerObject(lS.get(0)), directions);
                break;
            case "Teleporter" :
                ((Teleporter) p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC))).applyEffect(this.grid, p, lS.get(0), lS.get(1));
                break;
            case "Tagback Grenade" :
                ((TagbackGrenade) p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC))).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Targeting Scope" :
                    ((TargetingScope) p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC))).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                List<AmmoCube> lA = new LinkedList<>();
                lA.add(new AmmoCube(c));
                List<PowerUpCard> lP = new LinkedList<>();
                p.payCard(lA, lP);
                break;
            default: return;
        }
        p.removePowerUpCard(p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)));
        this.grid.getPowerUpDiscardPile().add(p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)));
    }

    /**
     * Determines whether or not the reload is valid.
     *
     * @param nickName nickname
     * @param s        weapon name
     * @return boolean
     */
    public boolean isValidReload(String nickName, String s) {
        Player p = this.grid.getPlayerObject(nickName);
        if(p.getWeaponCardObject(s) != null)
            return (p.checkAmmoCube(p.getWeaponCardObject(s).getReloadCost()) && this.gameState.equals(ACTION2));
        return false;
    }

    /**
     * Reloads a given weapon.
     *
     * @param nickName nickname
     * @param s        weapon name
     */
    public synchronized void reload(String nickName, String s) {
           Player p = this.grid.getPlayerObject(nickName);
           p.getWeaponCardObject(s).reload();
           p.removeArrayAC(p.getWeaponCardObject(s).getReloadCost());
    }

    /**
     * Carries out the necessary modifications upon a player's death.
     *
     * @param p player
     * @throws RemoteException RMI exception
     */
    private void death(Player p) throws RemoteException {
       p.changeCell(null);
       if(p.getPlayerBoard().getDamage().getDamageTokens()[11] != null) {
           int n = this.grid.getBoard().substituteSkull(2);
           this.grid.getBoard().getK().getC()[n] = p.getPlayerBoard().getDamage().getDT(11).getC();
           p.getPlayerBoard().addMark(new DamageToken(p.getPlayerBoard().getDamage().getDT(11).getC()));      //OverKill
           List<String> information = new LinkedList<>();
           information.add(this.grid.getPlayerObjectByColour(p.getPlayerBoard().getDamage().getDT(11).getC()).getNickName());
           information.add(p.getNickName());

           server.notifyMark(this.iD, information);
        }
        else {
            int n = this.grid.getBoard().substituteSkull(1);
            this.grid.getBoard().getK().getC()[n] = p.getPlayerBoard().getDamage().getDT(10).getC();
        }
        if(p.getPlayerBoard().getPoints().getPoints().size()>1)
            p.getPlayerBoard().getPoints().remove();
        p.getPlayerBoard().getDamage().clean();
        p.addPowerUpCard(this.grid.getPowerUpDeck().getDeck().get(cardToPickAfterDeath));
        cardToPickAfterDeath++;
    }

    /**
     * Determines whether or not scoring should occur, i.e. at the end of a player's turn in which some player died.
     *
     * @return boolean
     */
    public boolean isValidScoring() {
        return this.gameState.equals(ACTION2) && (!this.grid.whoIsDead().isEmpty());
    }

    /**
     * Performs the scoring associated with the isValid.
     *
     * @throws RemoteException RMI exception
     */
    public synchronized void scoring() throws RemoteException {
        int c = 0;
        cardToPickAfterDeath = 0;
        for(Player p : this.grid.whoIsDead()) {
            this.deadList.add(p.getNickName());
            this.grid.scoringByColour(p.getPlayerBoard().getDamage().getDamageTokens()[0].getC(), 1);
            if(!p.getPlayerBoard().getDamage().scoreBoard().isEmpty())
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(0), p.getPlayerBoard().getPoints().getInt(1));
            if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 2)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(1), p.getPlayerBoard().getPoints().getInt(2));
            if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 3)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(2), p.getPlayerBoard().getPoints().getInt(3));
            if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 4)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(3), p.getPlayerBoard().getPoints().getInt(4));
            if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 5)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(4), p.getPlayerBoard().getPoints().getInt(5));
            if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 6)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(5), p.getPlayerBoard().getPoints().getInt(6));
            p.getPlayerBoard().getDamage().cleanL();
            c++;
            if(c == 2)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getDamageTokens()[10].getC(),1);        //Double Kill
            this.death(p);
            this.gameState = ENDTURN;
            List<String> information = new LinkedList<>();
            information.add(p.getNickName());
            information.add(Integer.toString(p.getScore()));

            server.notifyScore(this.iD, information);
        }

    }

    /**
     * Gets dead player list.
     *
     * @return player list
     */
    public List<String> getDeadList() {
        return deadList;
    }

    /**
     * Determines whether or not the action of discarding a powerup when choosing a new spawn point is valid.
     *
     * @param nickName nickname
     * @param s1       powerup name
     * @param c1       powerup colour
     * @return boolean
     */
    public boolean isValidDiscardCardForSpawnPoint(String nickName, String s1, String c1) {
        Player p = this.grid.getPlayerObject(nickName);
        return (p.getPowerUpCardObject(s1, Colour.valueOf(c1)) != null);
    }

    /**
     * Performs the discard powerup card action associated with the isValid.
     *
     * @param nickName nickname
     * @param s1       powerup name
     * @param c1       powerup colour
     */
    public synchronized void discardCardForSpawnPoint(String nickName, String s1, String c1) {      //Attention to the view
        Player p = this.grid.getPlayerObject(nickName);
        PowerUpCard p1 = p.getPowerUpCardObject(s1, Colour.valueOf(c1));
        chooseSpawnPoint(p1.getC(), p);
        p.removePowerUpCard(p1);
        this.grid.getPowerUpDeck().getDeck().remove(cardToPickAfterDeath-1); //It should be OK
        cardToPickAfterDeath--;
        this.grid.getPowerUpDiscardPile().add(p1);
        this.deadList.remove(nickName);
        if(this.deadList.isEmpty())
            this.gameState = STARTTURN;
    }

    /**
     * Replaces necessary board elements.
     */
    public synchronized void replace() {
       this.grid.replaceAmmoCard();
       this.grid.replaceWeaponCard();
       if(this.grid.getBoard().getK().getSkulls()[7] != 0)
           finalFrenzy = true;
       this.gameState = STARTTURN;
    }

    //FINAL FRENZY ACTIONS

    //player can do 2 actions (choosing between 1, 2, 3) if he takes his final turn before the first player
    //player can do 1 action (choosing between 4, 5) if he is the first player, or takes his final turn after the first player

    /**
     * Determines whether or not the game is in Final Frenzy mode.
     *
     * @return boolean
     */
    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * Determines whether or not the chosen FF actions are valid.
     *
     * @param nickName nickname
     * @param lS       action list
     * @return boolean
     */
    public boolean isValidFinalFrenzyAction(String nickName, List<String> lS) {
        if(!(this.gameState == STARTTURN && finalFrenzy))
            return false;
        Player p = this.grid.getPlayerObject(nickName);
        if((p.getTurnFinalFrenzy() == 0 && lS.size() == 2 && (lS.get(0).equals("1") || lS.get(0).equals("2") || lS.get(0).equals("3")) &&
                (lS.get(1).equals("1") || lS.get(1).equals("2") || lS.get(1).equals("3"))) ||
                (p.getTurnFinalFrenzy() != 0 && lS.size() == 1 && (lS.get(0).equals("4") || lS.get(0).equals("5"))))
            return true;
        return false;
    }

    //first action available for players who take their final turn before the first player
    //player can move up to 1 cell, reload if he wants, and then shoot

    /**
     * Determines whether or not the FF action 1 (move 1, reload, shoot) parameters are valid.
     *
     * @param nickName      nickname
     * @param direction     direction
     * @param weaponToUse   weapon name
     * @param lI            effect number list
     * @param lS            various strings list
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @return boolean
     */
    public boolean isValidFinalFrenzyAction1(String nickName, int direction, String weaponToUse, List<Integer> lI, List<String> lS, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        Set<Integer> lIset = new HashSet<>(lI);
        if(lIset.size() < lI.size())                //to check if there are repetitions in lI, which means that player wants to apply the same effect multiple times
            return false;

        List<AmmoCube> lA = new LinkedList<>();
        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));
        AmmoCube[] cubeArray = new AmmoCube[lA.size()];
        lA.toArray(cubeArray);
        if(!p.checkAmmoCube(cubeArray))
            return false;

        List<PowerUpCard> lP = new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++) {
            if(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                return false;
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }
        return (finalFrenzy && this.grid.canMove(p, direction) && this.isValidShootNotAdrenaline(p, weaponToUse, lI, lS, lA, lP) &&
                    direction >= 0 && direction <= 4);

    }

    /**
     * Performs the FF action 1 associated with the isValid.
     *
     * @param nickName       nickname
     * @param direction      direction
     * @param weaponToReload weapon name list
     * @param weaponToUse    weapon name
     * @param lI             effect number list
     * @param lS             various strings list
     * @param lAInput        colour list
     * @param lPInput        powerup list
     * @param lPColourInput  powerup colour input
     * @throws RemoteException RMI exception
     */
    public synchronized void finalFrenzyAction1(String nickName, int direction, List<String> weaponToReload, String weaponToUse, List<Integer> lI, List<String> lS, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) throws RemoteException {
        Player p = this.grid.getPlayerObject(nickName);

        List<AmmoCube> lA = new LinkedList<>();
        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));

        List<PowerUpCard> lP = new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++)
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));

        if(direction != 0)
            this.grid.move(p, direction);
        if(!weaponToReload.isEmpty()) {
            for(String weapon : weaponToReload)
                this.reloadFrenzy(p, weapon);
        }

        this.shootNotAdrenaline(p, weaponToUse, lI, lS, lA, lP);
        this.gameState = ENDTURN;
    }

    /**
     * Determines whether or not the FF action 2 (move up to 4) parameters are valid.
     *
     * @param nickName   nickname
     * @param directions direction list
     * @return boolean
     */
    public boolean isValidFinalFrenzyAction2(String nickName, List<Integer> directions) {
        Player p = this.grid.getPlayerObject(nickName);
        for(Integer i : directions) {
            if(i < 0 || i > 4)
                return false;
        }
        return(finalFrenzy && directions.size() <= 4 && this.grid.canGhostMove(p, directions));
    }

    /**
     * Performs the FF action 2 associated with the isValid.
     *
     * @param nickName   nickname
     * @param directions direction list
     * @throws RemoteException RMI exception
     */
    public synchronized void finalFrenzyAction2(String nickName, List<Integer> directions) throws RemoteException {
        Player p = this.grid.getPlayerObject(nickName);
        for(int i : directions)
            this.grid.move(p, i);
        this.gameState = ENDTURN;
    }

    //third action available for players who take their final turn before the first player
    //player can move up to 2 cells and grab something there

    /**
     * Determines whether or not the FF action 3 (move up to 2, grab) parameters are valid.
     *
     * @param nickName      nickname
     * @param directions     direction list
     * @param wCardInput    weapon name
     * @param wSlotInput    weapon slot
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @return boolean
     */
    public boolean isValidFinalFrenzyAction3(String nickName, List<Integer> directions, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        for(Integer i : directions) {
            if(i < 0 || i > 4)
                return false;
        }
        return(finalFrenzy && directions.size() <= 2 && this.grid.canGhostMove(p, directions) && isValidFrenzyGrab(nickName, wCardInput, wSlotInput, lAInput, lPInput, lPColourInput));
    }

    /**
     * Performs the FF action 3 associated with the isValid.
     *
     * @param nickName      nickname
     * @param directions     direction list
     * @param wCardInput    weapon name
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @throws RemoteException RMI exception
     */
    public synchronized void finalFrenzyAction3(String nickName, List<Integer> directions, String wCardInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) throws RemoteException {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);

        List<AmmoCube> lA = new LinkedList<>();
        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));

        List<PowerUpCard> lP = new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++) {
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }
        for(int i : directions)
            this.grid.move(p, i);

        this.frenzyGrab(p, wCard, lA, lP);
        this.gameState = ENDTURN;
    }

    /**
     * Determines whether or not the FF action 4 parameters are valid.
     *
     * @param nickName      nickname
     * @param directions    direction list
     * @param weaponToUse   weapon name
     * @param lI            effect number list
     * @param lS            various strings list
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @return boolean
     */
    public boolean isValidFinalFrenzyAction4(String nickName, List<Integer> directions, String weaponToUse, List<Integer> lI, List<String> lS, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);

        List<AmmoCube> lA = new LinkedList<>();
        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));
        AmmoCube[] cubeArray = new AmmoCube[lA.size()];
        lA.toArray(cubeArray);
        if(!p.checkAmmoCube(cubeArray))
            return false;

        List<PowerUpCard> lP = new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++) {
            if(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                return false;
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }

        for(Integer i : directions) {
            if(i < 0 || i > 4)
                return false;
        }
        return (finalFrenzy && directions.size() <= 2 && this.grid.canGhostMove(p, directions) && this.isValidShootNotAdrenaline(p, weaponToUse, lI, lS, lA, lP));
    }

    /**
     * Performs the FF action 4 associated with the isValid.
     *
     * @param nickName      nickname
     * @param directions    direction list
     * @param weaponToReload weapon to reload list
     * @param weaponToUse   weapon name
     * @param lI            effect number list
     * @param lS            various strings list
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @throws RemoteException RMI exception
     */
    public synchronized void finalFrenzyAction4(String nickName, List<Integer> directions, List<String> weaponToReload, String weaponToUse, List<Integer> lI, List<String> lS, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) throws RemoteException {
        Player p = this.grid.getPlayerObject(nickName);

        List<AmmoCube> lA = new LinkedList<>();
        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));

        List<PowerUpCard> lP = new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++) {
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }

        for(Integer i : directions)
            this.grid.move(p, i);

        if(!weaponToReload.isEmpty()) {
            for(String weapon : weaponToReload)
                this.reloadFrenzy(p, weapon);
        }
        this.shootNotAdrenaline(p, weaponToUse, lI, lS, lA, lP);
        this.gameState = ENDTURN;
    }

    /**
     * Determines whether or not the FF action 5 (move up to 3, grab) parameters are valid.
     *
     * @param nickName      nickname
     * @param directions    direction list
     * @param wCardInput    weapon name
     * @param wSlotInput    weapon slot
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @return boolean
     */
    public boolean isValidFinalFrenzyAction5(String nickName, List<Integer> directions, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        for(Integer i : directions) {
            if(i < 0 || i > 4)
                return false;
        }
        return(finalFrenzy && directions.size() <= 3 && this.grid.canGhostMove(p, directions) && isValidFrenzyGrab(nickName, wCardInput, wSlotInput, lAInput, lPInput, lPColourInput));
    }

    /**
     * Performs the FF action 5 associated with the isValid.
     *
     * @param nickName      nickname
     * @param directions    direction list
     * @param wCardInput    weapon name
     * @param lAInput       colour list
     * @param lPInput       powerup list
     * @param lPColourInput powerup colour input
     * @throws RemoteException RMI exception
     */
    public synchronized void finalFrenzyAction5(String nickName, List<Integer> directions, String wCardInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) throws RemoteException {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);

        List<AmmoCube> lA = new LinkedList<>();
        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));

        List<PowerUpCard> lP = new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++) {
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }

        for(Integer i : directions)
            this.grid.move(p, i);

        this.frenzyGrab(p, wCard, lA, lP);
        this.gameState = ENDTURN;
    }

    /**
     * Special reload case during Final Frenzy.
     *
     * @param p player
     * @param s weapon name
     */
    private void reloadFrenzy(Player p, String s) {
        if(p.checkAmmoCube(p.getWeaponCardObject(s).getReloadCost())) {
            p.getWeaponCardObject(s).reload();
            p.removeArrayAC(p.getWeaponCardObject(s).getReloadCost());
        }
    }

    /**
     * Determines whether or not the FF grab parameters are valid.
     *
     * @param nickName player
     * @param wCardInput weapon name
     * @param wSlotInput weapon slot
     * @param lAInput colour list
     * @param lPInput powerup list
     * @param lPColourInput powerup colour list
     * @return boolean
     */
    private boolean isValidFrenzyGrab(String nickName, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> lA = new LinkedList<>();
        List<PowerUpCard> lP = new LinkedList<>();

        for(Colour c : lAInput)
            lA.add(new AmmoCube(c));
        AmmoCube[] cubeArray = new AmmoCube[lA.size()];
        lA.toArray(cubeArray);
        if(!p.checkAmmoCube(cubeArray))
            return false;

        if(wCard != null && wSlotInput.isEmpty())
            return false;
        else if(wCard != null) {
            if((wSlotInput.equals("1") && (p.getCell().getPos().getX() == 0 && p.getCell().getPos().getY() == 2) && this.checkWeaponSlotContents(1).contains(wCardInput)) ||
                    (wSlotInput.equals("2") && (p.getCell().getPos().getX() == 2 && p.getCell().getPos().getY() == 3) && this.checkWeaponSlotContents(2).contains(wCardInput)) ||
                    (wSlotInput.equals("3") && (p.getCell().getPos().getX() == 1 && p.getCell().getPos().getY() == 0) && this.checkWeaponSlotContents(3).contains(wCardInput))) {
                if(cubeArray.length != 0 && !p.checkAmmoCube(cubeArray))
                    return false;
                for(int i = 0; i < lPInput.size(); i++) {
                    if(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                        return false;
                    lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
                }
            }
            if(!canPay(wCard, choosePayment(lA, lP)))
                return false;
        }
        return true;
    }

    /**
     * Performs the FF grab action assocaited with the is valid.
     *
     * @param p player
     * @param wCard weapon card
     * @param lA ammo cube list
     * @param lP powerup list
     */
    private void frenzyGrab(Player p, WeaponCard wCard, List<AmmoCube> lA, List<PowerUpCard> lP) {
        if(p.getCell().getStatus() == 0) {
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
            p.getCell().setA(null);
        }
        else if(p.getCell().getStatus() == 1) {
            p.payCard(lA, lP);
            p.getWeaponCards().add(wCard);
            p.getWeaponCards().get(p.getWeaponCards().size() - 1).reload();
            this.removeFromWeaponSlot(wCard);
        }
        if(p.getWeaponCards().size() > 3)
            this.discard = true;                    //View saved the Weapon Slot
    }

    /**
     * Special scoring case during Final Frenzy.
     *
     * @throws RemoteException RMI exception
     */
    public synchronized void finalFrenzyTurnScoring() throws RemoteException {
        int c = 0;
        for(Player p : this.grid.whoIsDead()) {
            this.deadList.add(p.getNickName());
            if(!p.getPlayerBoard().getDamage().scoreBoard().isEmpty())
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(0), 2);
            if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 2)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(1), 1);
            if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 3)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(2), 1);
            if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 4)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(3), 1);
            p.getPlayerBoard().getDamage().cleanL();
            c++;
            if(c == 2)
                this.grid.scoringByColour(p.getPlayerBoard().getDamage().getDamageTokens()[10].getC(),1);        //Double Kill
            p.changeCell(null);
            p.getPlayerBoard().getDamage().clean();
            List<String> information = new LinkedList<>();
            information.add(p.getNickName());
            information.add(Integer.toString(p.getScore()));

            server.notifyScore(this.iD, information);
        }
        this.gameState = STARTTURN;
    }

    /**
     * Sets the game state to ENDALLTURN when each player has had their turn in Final Frenzy.
     */
    public synchronized void endTurnFinalFrenzy() {
        this.gameState = ENDALLTURN;
    }

    /**
     * Final scoring of all player boards plus any bonus points before the game ends.
     */
    public synchronized void finalScoring() {
        if(this.gameState == ENDALLTURN) {
            for(Player p : this.grid.getPlayers()) {
                if(p.getPlayerBoard().getDamage().getDamageTokens()[0] == null) {
                    if(!p.getPlayerBoard().getDamage().scoreBoard().isEmpty())
                        this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(0), 2);
                    if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 2)
                        this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(1), 1);
                    if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 3)
                        this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(2), 1);
                    if(p.getPlayerBoard().getDamage().scoreBoard().size() >= 4)
                        this.grid.scoringByColour(p.getPlayerBoard().getDamage().getColourPosition(3), 1);
                    p.getPlayerBoard().getDamage().cleanL();
                }
            }
            if(!this.grid.getBoard().getK().scoreBoard().isEmpty())
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(0), 8);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 2)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(1), 6);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 3)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(2), 4);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 4)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(3), 2);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 5)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(4), 1);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 6)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(5), 1);
            this.grid.getBoard().getK().cleanL();

            this.gameState = ENDGAME;
        }
    }
}