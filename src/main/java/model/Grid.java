package model;

import model.board.*;
import model.decks.AmmoDeck;
import model.decks.PowerUpDeck;
import model.decks.WeaponDeck;
import model.cards.*;
import model.player.DamageToken;
import model.player.Player;
import network.ServerInterface;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * This class houses a variety of methods belonging to the game "grid" as a whole including the weapon slots, player
 * board, and so forth. It communicates directly with controller.
 */
public class Grid {

    private int iD;
    private ServerInterface server;
    private ArrayList<Player> players;
    private Board board;
    private WeaponDeck weaponDeck;
    private PowerUpDeck powerUpDeck;
    private AmmoDeck ammoDeck;
    private List<AmmoCard> ammoDiscardPile;
    private List<PowerUpCard> powerUpDiscardPile;

    /**
     * Creates a new grid. This is called as soon as a new game is created. Needs to be linked to the server so
     * notify methods function properly.
     *
     * @param iD     game id
     * @param server server
     */
    public Grid(int iD, ServerInterface server)  {
        this.iD = iD;
        this.server = server;
        this.players = new ArrayList<>();
        this.weaponDeck = new WeaponDeck();
        this.weaponDeck.startingShuffle();
        this.powerUpDeck = new PowerUpDeck();
        this.powerUpDeck.startingShuffle();
        this.ammoDeck = new AmmoDeck();
        this.ammoDeck.startingShuffle();
        this.ammoDiscardPile = new LinkedList<>();
        this.powerUpDiscardPile = new LinkedList<>();
    }

    /**
     * Sets up weapon slots and creates a new board of the given arena type.
     *
     * @param aType arena type
     * @throws RemoteException RMI exception
     */
    public void setType(int aType) throws RemoteException {
        WeaponSlot ws1 = new WeaponSlot(1, pickWeaponCard(), pickWeaponCard(), pickWeaponCard());
        WeaponSlot ws2 = new WeaponSlot(2, pickWeaponCard(), pickWeaponCard(), pickWeaponCard());
        WeaponSlot ws3 = new WeaponSlot(3, pickWeaponCard(), pickWeaponCard(), pickWeaponCard());
        this.board = new Board(aType, ws1, ws2, ws3);
        server.notifyType(this.iD, aType);
    }

    /**
     * Adds a player and saves their nickname and colour as notify information.
     *
     * @param p player
     * @throws RemoteException RMI exception
     */
    public void addPlayer(Player p) throws RemoteException {
        this.players.add(p);
        List<String> information = new LinkedList<>();
        information.add(p.getNickName());
        information.add(p.getC().toString());
        server.notifyPlayer(this.iD, information);
    }

    /**
     * Removes a player.
     *
     * @param p player
     */
    public void removePlayer(Player p) {
        this.players.remove(p);
    }

    /**
     * Gets current list of players.
     *
     * @return player list
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets players' nicknames.
     *
     * @return nickname list
     */
    public List<String> getPlayersNickName() {
        LinkedList<String> l = new LinkedList<>();
        for(Player p: this.getPlayers())
            l.add(p.getNickName());
        return l;
    }

    /**
     * Gets a player's colour.
     *
     * @param name player nickname
     * @return player colour
     */
    public Colour getPlayerColour(String name) {
        for(Player p: this.getPlayers()) {
            if(p.getNickName().equals(name))
                return p.getC();
        }
        return null;
    }

    /**
     * Gets players' colours.
     *
     * @return player colour list
     */
    public List<Colour> getPlayersColour() {
        LinkedList<Colour> l = new LinkedList<>();
        for(Player p: this.getPlayers())
            l.add(p.getC());
        return l;
    }

    /**
     * Returns the player as an object from their nickname string.
     *
     * @param name player nickname
     * @return player object
     */
    public Player getPlayerObject(String name) {
        for(Player p : players ) {
            if(p.getNickName().equals(name))
                return p;
        }
        return null;
    }

    /**
     * Returns the player as an object from their (unique) colour attribute.
     *
     * @param c player colour
     * @return player object
     */
    public Player getPlayerObjectByColour(Colour c) {
        for(Player p : players ) {
            if(p.getC().equals(c))
                return p;
        }
        return null;
    }

    /**
     * Gets number of players.
     *
     * @return num of players
     */
    int getNumPlayers() {
        return this.players.size();
    }

    /**
     * Increases one or more players' score(s) by an amount based on the number of damage tokens of their colour.
     *
     * @param c damage token colour
     * @param n points
     */
    public void scoringByColour(Colour c, int n) {
        for(Player p : this.players) {
            if(c.equals(p.getC()))
                p.addScore(n);
        }
    }

    /**
     * Gets the game board.
     *
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Adds a number of player p's damage tokens to target player p1's player board. Further condition if p1 is
     * overkilled. Also saves notify information.
     *
     * @param p         player (self)
     * @param p1        target player
     * @param numDamage amount of damage dealt
     * @throws RemoteException RMI exception
     */
    public void damage(Player p, Player p1, int numDamage) throws RemoteException { //p attacks, p1 is attacked
        removeMarkAndAdd(p1, p);
        p1.getPlayerBoard().getDamage().addDamage(numDamage, p.getC());
        List<String> informationDamage = new LinkedList<>();
        informationDamage.add(p.getNickName());
        informationDamage.add(Integer.toString(numDamage));
        informationDamage.add(p1.getNickName());
        server.notifyDamage(this.iD, informationDamage);

        if(p1.isOverkilled()) {
            p.getPlayerBoard().addMark(new DamageToken(p1.getC()));
            List<String> information = new LinkedList<>();
            information.add(p.getNickName());
            information.add(p1.getNickName());
            server.notifyMark(this.iD, information);
        }
    }

    /**
     * Removes all damage tokens from a player board.
     *
     * @param p player
     */
    void clean(Player p) {
        p.getPlayerBoard().getDamage().clean();
    }

    /**
     * Adds one mark from p1 to p2's player board. Also adds notify information.
     *
     * @param p1 player (self)
     * @param p2 target player
     * @throws RemoteException RMI exception
     */
    public void addMark(Player p1, Player p2) throws RemoteException {
        p2.getPlayerBoard().addMark(new DamageToken(p1.getC()));
        List<String> information = new LinkedList<>();
        information.add(p1.getNickName());
        information.add(p2.getNickName());
        server.notifyMark(this.iD, information);
    }

    /**
     * Applies the additional damage from marks on a marked player, removing those marks.
     *
     * @param p1 target player
     * @param p2 player (self)
     * @throws RemoteException RemoteException
     */
    private void removeMarkAndAdd(Player p1, Player p2) throws RemoteException {
        long x = p1.getPlayerBoard().getMarks().stream().filter(a -> a.getC() == p2.getC()).count();
        if(x > 0) {
            int y = (int) x;
            p1.getPlayerBoard().getDamage().addDamage(y, p2.getC());
            List<String> informationDamage = new LinkedList<>();
            informationDamage.add(p1.getNickName());
            informationDamage.add(Integer.toString(y));
            informationDamage.add(p2.getNickName());
            server.notifyDamage(this.iD, informationDamage);
            p1.getPlayerBoard().clearMark(p2.getC());
        }
    }

    /**
     * Computes distance between two players.
     *
     * @param p1 first player
     * @param p2 second player
     * @return distance
     */
    public int distance(Player p1, Player p2) {
        return abs(p1.getCell().getPos().getX()-p2.getCell().getPos().getX()) + abs(p1.getCell().getPos().getY()-p2.getCell().getPos().getY());
    }

    /**
     * Computes distance between a player some cell.
     *
     * @param p   player
     * @param pos cell position
     * @return distance
     */
    public int distance(Player p, Position pos) {
        return abs(p.getCell().getPos().getX()-pos.getX()) + abs(p.getCell().getPos().getY()-pos.getY());
    }

    /**
     * Computes distance between two cells.
     *
     * @param pos1 cell position 1
     * @param pos2 cell position 2
     * @return distance
     */
    public int distance(Position pos1, Position pos2) {
        return abs(pos1.getX()-pos2.getX()) + abs(pos1.getY()-pos2.getY());
    }

    /**
     * Returns the given player's current cell.
     *
     * @param p player
     * @return cell
     */
    public Cell whereAmI(Player p) {
        return p.getCell();
    }

    /**
     * Moves a player in some direction and adds notify information.
     * As a reminder, 1 = up, 2 = right, 3 = down, 4 = left.
     *
     * @param p player
     * @param d direction
     * @throws RemoteException RMI exception
     */
    public void move(Player p, int d) throws RemoteException {
        moveWithoutNotify(p, d);
        List<String> information = new LinkedList<>();
        information.add(p.getNickName());
        information.add(Integer.toString(p.getCell().getPos().getX()));
        information.add(Integer.toString(p.getCell().getPos().getY()));

        server.notifyPosition(this.iD, information);
    }

    /**
     * The "regular" move method called by move().
     *
     * @param p player
     * @param d direction
     */
    public void moveWithoutNotify(Player p, int d) {
        int n = 0;

        for(int i = 0; i<p.getCell().getPosWall().length; i++) {
            //player can't move
            if(p.getCell().getPosWall()[i] == d) {
                n = 1;
            }
        }

        if(n == 0) {
            if((d == 1) && (p.getCell().getPos().getX()>0))
                p.changeCell(board.getArena()[p.getCell().getPos().getX()-1][p.getCell().getPos().getY()]);
            else if((d == 2) && (p.getCell().getPos().getY()<3))
                p.changeCell(board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY()+1]);
            else if((d == 3) && (p.getCell().getPos().getX()<2))
                p.changeCell(board.getArena()[p.getCell().getPos().getX()+1][p.getCell().getPos().getY()]);
            else if((d == 4) && (p.getCell().getPos().getY()>0))
                p.changeCell(board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY()-1]);
        }
    }

    /**
     * Determines whether or not a player can move in the given direction.
     *
     * @param p player
     * @param d direction
     * @return boolean
     */
    public boolean canMove(Player p, int d) {
        for(int i = 0; i<p.getCell().getPosWall().length; i++) {
            if(p.getCell().getPosWall()[i] == d) {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves a player to the cell corresponding to the given coordinates.
     *
     * @param p player
     * @param x x coordinate
     * @param y y coordinate
     * @throws RemoteException RMI exception
     */
    public void move(Player p, int x, int y) throws RemoteException {
        p.changeCell(board.getArena()[x][y]);
        List<String> information = new LinkedList<>();
        information.add(p.getNickName());
        information.add(Integer.toString(x));
        information.add(Integer.toString(y));
        server.notifyPosition(this.iD, information);
    }

    /**
     * Determines whether or not there is a wall separating a player from a cell.
     * Note that cell.posWalls is of type int[] and as such listPosWalls.contains checks for integer.
     *
     * @param p  player
     * @param pT cell position
     * @return boolean
     */
    public boolean isThereAWall (Player p, Position pT) {
        int direction = 0;
        if(p.getCell().getPos().getX() > pT.getX())
            direction = 1;
        else if(p.getCell().getPos().getX() < pT.getX())
            direction = 3;
        else if(p.getCell().getPos().getY() > pT.getY())
            direction = 4;
        else if(p.getCell().getPos().getY() < pT.getY())
            direction = 2;
        List<Integer> listPosWalls = new LinkedList<>();
        for(int i : this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY()].getPosWall())
            listPosWalls.add(i);
        return listPosWalls.contains(direction);
    }

    /**
     * Determines whether or not there is a wall separating two cells.
     *
     * @param pT1 cell position 1
     * @param pT2 cell position 2
     * @return boolean
     */
    public boolean isThereAWall (Position pT1, Position pT2) {
        int direction = 0;
        if(pT1.getX() > pT2.getX())
            direction = 1;
        else if(pT1.getX() < pT2.getX())
            direction = 3;
        else if(pT1.getY() > pT2.getY())
            direction = 4;
        else if(pT1.getY() < pT2.getY())
            direction = 2;
        List<Integer> listPosWalls = new LinkedList<>();
        for(int i : this.board.getArena()[pT1.getX()][pT1.getY()].getPosWall())
            listPosWalls.add(i);
        return listPosWalls.contains(direction);
    }

    /**
     * Determines whether or not two players are in the same room (by its colour alone).
     *
     * @param p  player
     * @param p1 other player
     * @return boolean
     */
    boolean isInTheRoom(Player p, Player p1) {
        return (p.getCell().getC().equals(p1.getCell().getC()));
    }

    /**
     * Determines whether or not player p can "see" another player p1, i.e. p1 lies within p's view zone.
     * Note that this method is not symmetric, so player p1 may not necessarily see p even though p sees him.
     *
     * @param p  player
     * @param p1 other player
     * @return boolean
     */
    public boolean isInViewZone(Player p, Player p1) {
        boolean b = false;
        for(int i = 0; i<p.getCell().getPosDoor().length; i++) {
            if((!b) && ((p.getCell().getPosDoor()[i] == 1 && p.getCell().getPos().getX() - 1 >= 0 && p1.getCell().getC().equals(this.board.getArena()[p.getCell().getPos().getX() - 1][p.getCell().getPos().getY()].getC())) ||
                    (p.getCell().getPosDoor()[i] == 2 && p.getCell().getPos().getY() + 1 <= 3 && p1.getCell().getC().equals(this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() + 1].getC())) ||
                    (p.getCell().getPosDoor()[i] == 3 && p.getCell().getPos().getX() + 1 <= 2 && p1.getCell().getC().equals(this.board.getArena()[p.getCell().getPos().getX() + 1][p.getCell().getPos().getY()].getC())) ||
                    p.getCell().getPosDoor()[i] == 4 && p.getCell().getPos().getY() - 1 >= 0 && p1.getCell().getC().equals(this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() - 1].getC())))
                b = true;
            }
        return (isInTheRoom(p, p1) || ((p.getCell().getPosDoor()!=null) && b));
    }

    /**
     * Determines whether or not a cell is in a player's view zone.
     *
     * @param p   player
     * @param pos cell position
     * @return boolean
     */
    public boolean isInViewZone(Player p, Position pos) {
        if(this.board.getArena()[pos.getX()][pos.getY()].getC().equals(p.getCell().getC()))
            return true;
        for(int i = 0; i<p.getCell().getPosDoor().length; i++)
            if((p.getCell().getPosDoor()[i] == 1 && p.getCell().getPos().getX() - 1 >= 0 &&
                    this.board.getArena()[p.getCell().getPos().getX() - 1][p.getCell().getPos().getY()].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC())) ||
                    (p.getCell().getPosDoor()[i] == 2 && p.getCell().getPos().getY() + 1 <= 3 &&
                            this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() + 1].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC())) ||
                    (p.getCell().getPosDoor()[i] == 3 && p.getCell().getPos().getX() + 1 <= 2 &&
                            this.board.getArena()[p.getCell().getPos().getX() + 1][p.getCell().getPos().getY()].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC())) ||
                    (p.getCell().getPosDoor()[i] == 4 && p.getCell().getPos().getY() - 1 >= 0 &&
                            this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() - 1].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC())))
                    return true;
        return false;
    }

    /**
     * Returns a list of the colours of the rooms which are in the view zone of player p, except for the colour of the
     * room player p is in.
     *
     * @param p player
     * @return colour list
     */
    public List<Colour> colourOfOtherViewZone(Player p) {
        List<Colour> l = new LinkedList<>();
        for(int i = 0; i<p.getCell().getPosDoor().length; i++) {
            if(p.getCell().getPosDoor()[i] == 1)
                l.add(this.board.getArena()[p.getCell().getPos().getX() - 1][p.getCell().getPos().getY()].getC());
            else if(p.getCell().getPosDoor()[i] == 2)
                l.add(this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() + 1].getC());
            else if(p.getCell().getPosDoor()[i] == 3)
                l.add(this.board.getArena()[p.getCell().getPos().getX() + 1][p.getCell().getPos().getY()].getC());
            else if(p.getCell().getPosDoor()[i] == 4)
                l.add(this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() - 1].getC());
        }
        return l.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Returns list of players who are in the same room as some player p.
     *
     * @param p player
     * @return player list
     */
    List<Player> whoIsInTheRoom(Player p) {
        ArrayList<Player> pRoom = new ArrayList<>();
        for(Player px : players) {
            if(isInTheRoom(p, px) && p!=px)
                pRoom.add(px);
        }
        return pRoom;
    }

    /**
     * Returns list of players who are in player p's view zone.
     *
     * @param p player
     * @return player list
     */
    List<Player> whoIsInTheViewZone(Player p) {
        ArrayList<Player> pViewZone = new ArrayList<>();
        for(Player px : players) {
            if(isInViewZone(p, px) && p != px)
                pViewZone.add(px);
        }
        return pViewZone;
    }

    /**
     * Returns a weapon card as its object from its name.
     *
     * @param wCardName weapon card name
     * @return weapon card object
     */
    public WeaponCard getWeaponCardObject(String wCardName) {
        if(board.getW1().getCard1() != null && board.getW1().getCard1().getCardName().equals(wCardName))
            return board.getW1().getCard1();
        else if(board.getW1().getCard2() != null && board.getW1().getCard2().getCardName().equals(wCardName))
            return board.getW1().getCard2();
        else if(board.getW1().getCard3() != null && board.getW1().getCard3().getCardName().equals(wCardName))
            return board.getW1().getCard3();
        else if(board.getW2().getCard1() != null && board.getW2().getCard1().getCardName().equals(wCardName))
            return board.getW2().getCard1();
        else if(board.getW2().getCard2() != null && board.getW2().getCard2().getCardName().equals(wCardName))
            return board.getW2().getCard2();
        else if(board.getW2().getCard3() != null && board.getW2().getCard3().getCardName().equals(wCardName))
            return board.getW2().getCard3();
        else if(board.getW3().getCard1() != null && board.getW3().getCard1().getCardName().equals(wCardName))
            return board.getW3().getCard1();
        else if(board.getW3().getCard2() != null && board.getW3().getCard2().getCardName().equals(wCardName))
            return board.getW3().getCard2();
        else if(board.getW3().getCard3() != null && board.getW3().getCard3().getCardName().equals(wCardName))
            return board.getW3().getCard3();
        return null;
    }

    /**
     * Draws a weapon card from the top of the weapon deck.
     *
     * @return weapon card
     */
    private WeaponCard pickWeaponCard() {
        return this.weaponDeck.getTopOfDeck();
    }

    /**
     * Moves a weapon card from the deck to all empty weapon slots.
     */
    public void replaceWeaponCard() {
        if(this.board.getW1().getCard1() == null)
            this.board.getW1().setCard1(pickWeaponCard());
        if(this.board.getW1().getCard2() == null)
            this.board.getW1().setCard2(pickWeaponCard());
        if(this.board.getW1().getCard3() == null)
            this.board.getW1().setCard3(pickWeaponCard());
        if(this.board.getW2().getCard1() == null)
            this.board.getW2().setCard1(pickWeaponCard());
        if(this.board.getW2().getCard2() == null)
            this.board.getW2().setCard2(pickWeaponCard());
        if(this.board.getW2().getCard3() == null)
            this.board.getW2().setCard3(pickWeaponCard());
        if(this.board.getW3().getCard1() == null)
            this.board.getW3().setCard1(pickWeaponCard());
        if(this.board.getW3().getCard2() == null)
            this.board.getW3().setCard2(pickWeaponCard());
        if(this.board.getW3().getCard3() == null)
            this.board.getW3().setCard3(pickWeaponCard());
    }

    /**
     * Draws a powerup card which is added to the player's deck.
     * This method should be used every time a player wants a PowerUpCard, except for the final "pick and discard"
     * when he is dead.
     *
     * @param p player
     */
    public void pickPowerUpCard(Player p) {
        if(p.getPowerUpCards().size() < 3) {
            if(powerUpDeck.getDeck().isEmpty()) {
                Collections.shuffle(powerUpDiscardPile);
                powerUpDeck.getDeck().addAll(powerUpDiscardPile);
                powerUpDiscardPile.clear();
            }
            p.addPowerUpCard(this.powerUpDeck.getTopOfDeck());
        }
    }

    /**
     * Draws a powerup card which is added to the player's deck.
     * This method should be used for the final "pick and discard" when the player is dead.
     *
     * @return powerup card
     */
    public PowerUpCard pickPowerUpCard() {
        if(powerUpDeck.getDeck().isEmpty()) {
            Collections.shuffle(powerUpDiscardPile);
            powerUpDeck.getDeck().addAll(powerUpDiscardPile);
            powerUpDiscardPile.clear();
        }
        return this.powerUpDeck.getTopOfDeck();
    }

    /**
     * Gets powerup card discard pile.
     *
     * @return powerup discard pile
     */
    public List<PowerUpCard> getPowerUpDiscardPile() {
        return powerUpDiscardPile;
    }

    /**
     * Draws an ammo card from the top of the deck, which if empty receives all of the discard pile cards (shuffled).
     *
     * @return ammo card
     */
    private AmmoCard pickAmmoCard() {
        if(ammoDeck.getDeck().isEmpty()) {
            Collections.shuffle(ammoDiscardPile);
            ammoDeck.getDeck().addAll(ammoDiscardPile);
            ammoDiscardPile.clear();
        }
        return this.ammoDeck.getTopOfDeck();
    }

    /**
     * Lays out the ammo cards face up on the board.
     */
    public void setUpAmmoCard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
               this.board.getArena()[i][j].setA(this.pickAmmoCard());
            }
        }
    }

    /**
     * Replaces empty cells with ammo cards.
     */
    public void replaceAmmoCard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                if(this.board.getArena()[i][j].getStatus() == 0 && this.board.getArena()[i][j].getA() == null)
                    this.board.getArena()[i][j].setA(this.pickAmmoCard());
            }
        }
    }

    /**
     * Gets ammo card discard pile.
     *
     * @return ammo discard pile
     */
    public List<AmmoCard> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    /**
     * Gets powerup card deck.
     *
     * @return powerup deck
     */
    public PowerUpDeck getPowerUpDeck() {
        return powerUpDeck;
    }

    /**
     * Returns list of players who are currently dead.
     *
     * @return dead player list
     */
    public List<Player> whoIsDead() {
        LinkedList<Player> l = new LinkedList<>();
        for(Player p: this.players) {
            if(p.isDead())
                l.add(p);

        }
        return l;
    }

    /**
     * A "ghost move" is used in certain card effects to mock an imaginary player's movement. It does not affect
     * the game itself.
     *
     * @param p          player
     * @param directions direction list
     * @return ghost player
     */
    public Player ghostMove(Player p, List<Integer> directions) {
        Player ghost = new Player("?gHoSt!", p.getC(), p.hasFirstPlayerCard());
        ghost.changeCell(p.getCell());

        for(Integer i : directions)
            this.moveWithoutNotify(ghost, i);

        return ghost;
    }

    /**
     * Determines whether or not a "ghost move" is valid.
     *
     * @param p          player
     * @param directions direction list
     * @return boolean
     */
    public boolean canGhostMove(Player p, List<Integer> directions) {
        Player ghost = new Player("?gHoSt!", p.getC(), p.hasFirstPlayerCard());
        ghost.changeCell(p.getCell());
        Position initialPos = ghost.getCell().getPos();

        for(Integer i : directions) {
            this.moveWithoutNotify(ghost, i);
            if(i != 0 && ghost.getCell().getPos().equals(initialPos))
                return false;
            else
                initialPos = ghost.getCell().getPos();
        }
        return true;
    }

    /**
     * Gets weapon slot as its object from its string representation (1, 2 or 3).
     *
     * @param s weapon slot number
     * @return weapon slot object
     */
    public WeaponSlot getWeaponSlotObject(String s) {
        if(s.equals("1"))
            return this.board.getW1();
        if(s.equals("2"))
            return this.board.getW2();
        if(s.equals("3"))
            return this.board.getW3();
        return null;
    }
}