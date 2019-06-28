package network;

import controller.Game;
import model.Colour;
import view.View;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles the server-side information about each game for every connection, including the suspended identifiers
 * (determining initial turn order) and nicknames of disconnected players. It also contains the message... methods
 * for RMI and notify... methods used by both socket and RMI to communicate with the controller.
 */
public class ServerMethods extends UnicastRemoteObject implements ServerInterface, Serializable {

    private static List<Game> games;
    private static List<Integer> types;
    private static List<Boolean> canStartList;
    private static List<List<Connection>> connections;
    private static List<LinkedList<Integer>> suspendedIdentifier;
    private static List<LinkedList<String>> suspendedName;
    private static int frenzyTurn = 0;

    /**
     * Creates the lists for each game number, arena type, connection, list of players who can start the game,
     * identifiers and names of suspended players.
     *
     * @throws RemoteException RMI exception
     */
    public ServerMethods() throws RemoteException {
        games = new LinkedList<>();
        types = new LinkedList<>();
        connections = new LinkedList<>();
        canStartList = new LinkedList<>();
        suspendedIdentifier = new LinkedList<>();
        suspendedName = new LinkedList<>();
    }


    /**
     * Gets number of games, active or not.
     *
     * @return number of games
     * @throws RemoteException RMI exception
     */
    public synchronized int getGames() throws RemoteException {
        return games.size();
    }

    /**
     * Adds lists to newly created game or if there are no games. Adds only new connection to existing game.
     *
     * @param numGame number of the game
     * @throws RemoteException RMI exception
     */
    public synchronized void setGame(int numGame) throws RemoteException {
        if(games.isEmpty() || games.size() <= numGame) {
            games.add(numGame, new Game(numGame, this));

            connections.add(numGame, new LinkedList<>());
            suspendedName.add(numGame, new LinkedList<>());
            suspendedIdentifier.add(numGame, new LinkedList<>());
            canStartList.add(numGame, false);
            connections.get(numGame).add(new Connection());             //this index should be the identifier - 1
            connections.get(numGame).get(connections.get(numGame).size()-1).setMyTurn(true);
        }
        else
            connections.get(numGame).add(new Connection());
    }

    /**
     * Sets player's view in a given game.
     *
     * @param game game number
     * @param identifier player identifier
     * @param view player view
     * @throws RemoteException RMI exception
     */
    public synchronized void setView(int game, int identifier, View view) throws RemoteException {
        connections.get(game).get(identifier-1).setView(view);
    }

    /**
     * Sets player nickname in a given game.
     *
     * @param game game number
     * @param identifier player identifier
     * @param nickName player nickname
     * @throws RemoteException RMI exception
     */
    public synchronized void setNickName(int game, int identifier, String nickName) throws RemoteException {
        connections.get(game).get(identifier-1).setNickName(nickName);
    }

    /**
     * Determines whether or not the game can start with the players in the game.
     *
     * @param game game number
     * @return boolean
     * @throws RemoteException RMI exception
     */
    public synchronized boolean canStart(int game) throws RemoteException {
        return canStartList.get(game);
    }

    /**
     * Determines whether or not there are too many players in game.
     * Even if a player is suspended, the total number of players can't be more than five.
     *
     * @param game game number
     * @return boolean
     * @throws RemoteException RMI exception
     */
    public synchronized boolean tooMany(int game) throws RemoteException {
        return (!connections.isEmpty() && (games.size() > game) &&  connections.get(game).size() == 5);
    }

    /**
     * Determines whether or not the game should be stopped due to missing players.
     *
     * @param game game number
     * @return boolean
     * @throws RemoteException RMI exception
     */
    public synchronized boolean stopGame(int game) throws RemoteException {
        if(suspendedIdentifier.isEmpty())
            return (connections.get(game).size() < 3);
        else
            return (connections.get(game).size()-suspendedIdentifier.get(game).size() < 3);
    }

    /**
     * Determines the identifier for the player who is just joining based on number of players already in the game.
     *
     * @param game game number
     * @return number of connections in game
     * @throws RemoteException RMI exception
     */
    public synchronized int receiveIdentifier(int game) throws RemoteException {
        connections.get(game).get(connections.get(game).size()-1).setIdentifier(connections.get(game).size());
        return connections.get(game).size();
    }

    /**
     * Waits for five players to join in which case the game starts immediately, otherwise a timer starts if
     * there are just three or four players. After 30 seconds have passed the game will start regardless.
     *
     * @param game game number
     * @throws RemoteException RMI exception
     * @throws InterruptedException Thread interruption
     */
    public synchronized void mergeGroup(int game) throws RemoteException, InterruptedException {
        if(connections.get(game).size() == 5) {
            canStartList.add(game, true);
            notifyAll();
        }
        if(connections.get(game).size() == 3) {
            wait(2000);
            while(connections.get(game).size() < 3)
                wait(2000);
            canStartList.add(game, true);
        }
    }

    /**
     * Determines whether or not it is the player with given identifier's turn.
     * By default player 1 goes first and player 5 goes last, but the method is recursive and works even
     * if some players are missing, and loops around to min{identifiers} after max{identifiers} has had its turn.
     *
     * @param game game number
     * @param identifier player identifier
     * @return boolean
     * @throws RemoteException RMI exception
     */
    public synchronized boolean isMyTurn(int game, int identifier) throws RemoteException {
        if(suspendedIdentifier.get(game).isEmpty())
            return(connections.get(game).get(identifier-1).isMyTurn()  && (connections.get(game).size()-1 != frenzyTurn));
        else
            return(connections.get(game).get(identifier-1).isMyTurn() && (connections.get(game).size()-1-suspendedIdentifier.get(game).size()-1 != frenzyTurn));
    }

    /**
     * Determines if the game is not in Final Frenzy. If it is, frenzyTurn must be updated.
     *
     * @param game game number
     * @return boolean
     * @throws RemoteException RMI exception
     */
    public synchronized boolean isNotFinalFrenzy(int game) throws RemoteException {
        boolean n = !games.get(game).isFinalFrenzy();
        if(!n)
            frenzyTurn++;
        return n;
    }

    /**
     * Sets the player's Final Frenzy turn based on the turn order at the start of the game.
     * This assumes player 1 has the first player card.
     *
     * @param game game number
     * @param identifier player identifier
     * @param nickName player nickname
     */
    public synchronized void setFinalTurn(int game, int identifier, String nickName) {
        if(identifier-frenzyTurn > 0)
            games.get(game).changeTurnFinalFrenzy(nickName, 0);
        if(identifier-frenzyTurn < 0 )
            games.get(game).changeTurnFinalFrenzy(nickName, 2);
    }

    /**
     * Determines whether or not the game has finished, i.e. everyone has had their Frenzy turn or fewer than three
     * players have remained.
     *
     * @param game game number
     * @return boolean
     * @throws RemoteException RMI exception
     */
    public synchronized boolean gameIsFinished(int game) throws RemoteException {
        return (connections.get(game).size()-suspendedIdentifier.get(game).size() == frenzyTurn ||
                connections.get(game).size()-suspendedIdentifier.get(game).size() < 3);
    }

    /**
     * Gets number of player's turn in a given game.
     *
     * @param game game number
     * @return player turn
     */
    private int getPlayerTurn(int game) {
        for(Connection c : connections.get(game)) {
            if(c.isMyTurn())
                return c.getIdentifier();
        }
        return -1;
    }

    /**
     * Properly handles a player's turn ending. Players may disconnect before someone ends their turn, so the next
     * turn must be assigned to the next remaining player.
     *
     * @param game game number
     * @throws RemoteException RMI exception
     */
    public synchronized void finishTurn(int game) throws RemoteException {
        if(connections.get(game).size() - suspendedIdentifier.get(game).size() >= 3) {
            do {
                if(games.get(game).getPlayers().size() > getPlayerTurn(game)) {
                    connections.get(game).get(getPlayerTurn(game)).setMyTurn(true);
                    connections.get(game).get(getPlayerTurn(game) - 1).setMyTurn(false);
                } else {
                    connections.get(game).get(getPlayerTurn(game) - 1).setMyTurn(false);
                    connections.get(game).get(0).setMyTurn(true);
                }
            } while(suspendedIdentifier.get(game).contains(getPlayerTurn(game)));
        }
    }


    /**
     * Adds disconnected player to suspended list and notifies of the disconnection.
     *
     * @param game game number
     * @param identifier player identifier
     * @param nickName player nickname
     * @throws RemoteException RMI exception
     * @throws InterruptedException thread interruption
     */
    public synchronized void manageDisconnection(int game, int identifier, String nickName) throws RemoteException, InterruptedException {
        suspendedIdentifier.get(game).add(identifier);
        suspendedName.get(game).add(nickName);

        for(Connection c : connections.get(game)) {
            if(!c.getView().getNickName().equals(nickName))
                c.getView().disconnected(identifier);
        }
    }

    /**
     * Determines whether or not player with some identifier is suspended.
     *
     * @param game game number
     * @param identifier player identifier
     * @return boolean
     * @throws RemoteException RMI exception
     */
    public synchronized boolean isASuspendedIdentifier(int game, int identifier) throws RemoteException {
        return (suspendedIdentifier.size() > game && suspendedIdentifier.get(game).contains(identifier));
    }

    /**
     * Removes reconnected player's identifier from suspended list.
     *
     * @param game game number
     * @param identifier player identifier
     * @throws RemoteException RMI exception
     */
    public synchronized void manageReconnection(int game, int identifier) throws RemoteException {
        suspendedIdentifier.get(game).removeFirstOccurrence(identifier);
    }

    /**
     * Gets suspended player's name while removing it from the list.
     *
     * @param game game number
     * @param identifier player identifier
     * @return suspended player name
     * @throws RemoteException RMI exception
     */
    public String getSuspendedName(int game, int identifier) throws RemoteException {
        String s = suspendedName.get(game).get(suspendedIdentifier.get(game).indexOf(identifier));
        suspendedName.get(game).remove(s);
        return s;
    }

    /**
     * Gets suspended player's colour.
     *
     * @param game game number
     * @param nickName player nickname
     * @return suspended player colour
     * @throws RemoteException RMI exception
     */
    public Colour getSuspendedColour(int game, String nickName) throws RemoteException {
        return games.get(game).getColour(nickName);
    }

    //notify to client

    /**
     * Notifies of nickname and colour of the player who has just joined the game.
     *
     * @param game game number
     * @param information player information
     * @throws RemoteException RMI exception
     */
    public synchronized void notifyPlayer(int game, List<String> information) throws RemoteException {
        for(Connection c : connections.get(game)) {
            if(information.get(0).equals(c.getNickName()))
                information.add(Integer.toString(c.getIdentifier()));
        }
        for(Connection c : connections.get(game)) {
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier())) {
                c.getView().printPlayer(information);
            }
            else if(!suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.setPrintPlayerList(information);
        }
    }

    /**
     * Notifies of number of players in game.
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return player list size
     */
    public int notifyPlayerSize(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier)
                return c.getPrintPlayerList().size();
        }
        return 0;
    }

    /**
     * Gets list of notify player lists (see notifyPlayer() and printPlayerList()).
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return notify player lists
     */
    public List<List<String>> getNotifyPlayer(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier) {
                List<List<String>> listNotifyPlayer = c.getPrintPlayerList();
                c.removeFirstPrintPlayerList();
                return listNotifyPlayer;
            }
        }
        return new LinkedList<>();
    }

    /**
     * Notifies of players' scores.
     *
     * @param game game number
     * @param information player information
     * @throws RemoteException RMI exception
     */
    public synchronized void notifyScore(int game, List<String> information) throws RemoteException {
        for(Connection c : connections.get(game)) {
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.getView().printScore(information);
            else if(!suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.setPrintScoreList(information);
        }
    }

    /**
     * Notifies of size of score list.
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return score list size
     */
    public int notifyScoreSize(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier)
                return c.getPrintScoreList().size();
        }
        return 0;
    }

    /**
     * Gets list of notify score lists (see notifyScore() and printScoreList()).
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return notify score lists
     */
    public List<List<String>> getNotifyScore(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier) {
                List<List<String>> listNotifyScore = c.getPrintScoreList();
                c.removeFirstPrintScoreList();
                return listNotifyScore;
            }
        }
        return new LinkedList<>();
    }

    /**
     * Notifies of player's position.
     *
     * @param game game number
     * @param information player information
     * @throws RemoteException RMI exception
     */
    public  synchronized void notifyPosition(int game, List<String> information) throws RemoteException {
        for(Connection c : connections.get(game)) {
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.getView().printPosition(information);
            else if(!suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.setPrintPositionList(information);
        }
    }

    /**
     * Notifies of size of position list.
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return position list size
     */
    public int notifyPositionSize(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier)
                return c.getPrintPositionList().size();
        }
        return 0;
    }

    /**
     * Gets list of notify position lists (see notifyPosition() and printPositionList()).
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier game identifier
     * @return notify position lists
     */
    public List<List<String>> getNotifyPosition(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier) {
                List<List<String>> listNotifyPosition = c.getPrintPositionList();
                c.removeFirstPrintPositionList();
                return listNotifyPosition;
            }
        }
        return new LinkedList<>();
    }

    /**
     * Notifies of player receiving a mark.
     *
     * @param game game number
     * @param information player information
     * @throws RemoteException RMI exception
     */
    public synchronized void notifyMark(int game, List<String> information) throws RemoteException {
        for(Connection c : connections.get(game)) {
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.getView().printMark(information);
            else if(!suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.setPrintMarkList(information);
        }
    }

    /**
     * Notifies of number of players who have received marks.
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return mark list size
     */
    public int notifyMarkSize(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier)
                return c.getPrintMarkList().size();
        }
        return 0;
    }

    /**
     * Gets list of notify mark lists (see notifyMark() and printMarkList()).
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return notify mark lists
     */
    public List<List<String>> getNotifyMark(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier) {
                List<List<String>> listNotifyMark = c.getPrintMarkList();
                c.removeFirstPrintMarkList();
                return listNotifyMark;
            }
        }
        return new LinkedList<>();
    }

    /**
     * Notifies of player receiving damage.
     *
     * @param game game number
     * @param information player information
     * @throws RemoteException RMI exception
     */
    public synchronized void notifyDamage(int game, List<String> information) throws RemoteException {
        for(Connection c : connections.get(game)) {
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.getView().printDamage(information);
            else if(!suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.setPrintDamageList(information);
        }
    }

    /**
     * Notifies of number of players who have received damage.
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return damage list size
     */
    public int notifyDamageSize(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier)
                return c.getPrintDamageList().size();
        }
        return 0;
    }

    /**
     * Gets list of notify damage lists (see notifyDamage() and printDamageList()).
     * Socket-exclusive.
     *
     * @param game       game number
     * @param identifier player identifier
     * @return notify damage lists
     */
    public List<List<String>> getNotifyDamage(int game, int identifier) {
        for(Connection c : connections.get(game)) {
            if(c.getIdentifier() == identifier) {
                List<List<String>> listNotifyDamage = c.getPrintDamageList();
                c.removeFirstPrintDamageList();
                return listNotifyDamage;
            }
        }
        return new LinkedList<>();
    }

    /**
     * Notifies of game's arena type.
     *
     * @param game game number
     * @param type arena type
     * @throws RemoteException RMI exception
     */
    public synchronized void notifyType(int game, int type) throws RemoteException {
        types.add(game, type);
    }

    /**
     * Gets game's arena type.
     *
     * @param game game number
     * @return arena type
     * @throws RemoteException RMI exception
     */
    public synchronized int getType(int game) throws RemoteException {
        if(types.size() > game)
            return types.get(game);
        else
            return 0;
    }


    //message from client to the controller


    public synchronized boolean messageGameIsNotStarted(int game) throws RemoteException {
        return games.get(game).gameIsNotStarted();
    }

    public synchronized void messageGameStart(int game, String nick, Colour c) throws RemoteException {
        games.get(game).gameStart(nick,c);
    }

    public synchronized boolean messageIsValidReceiveType(int game, int type) throws RemoteException {
        return (games.size() > game && games.get(game).isValidReceiveType(type));
    }

    public synchronized void messageReceiveType(int game, int type) throws RemoteException {
        games.get(game).receiveType(type);
    }

    public synchronized int messageIsValidAddPlayer(int game, String nick, Colour c) throws RemoteException {
        return games.get(game).isValidAddPlayer(nick, c);
    }

    public synchronized void messageAddPlayer(int game, String nick, Colour c) throws RemoteException {
        games.get(game).addPlayer(nick, c);
    }

    public synchronized void messageGiveTwoPUCard(int game, String nick) throws RemoteException {
        games.get(game).giveTwoPUCard(nick);
    }

    public synchronized String messageCheckYourStatus(int game, String nick) throws RemoteException {
        return games.get(game).checkYourStatus(nick);
    }

    public synchronized String messageShowCardsOnBoard(int game) throws RemoteException {
        return games.get(game).showCardsOnBoard();
    }

    public synchronized List<String> messageCheckWeaponSlotContents(int game, int n) throws RemoteException {
        return games.get(game).checkWeaponSlotContents(n);
    }

    public synchronized List<String> messageCheckWeaponSlotContentsReduced(int game, int n) throws RemoteException {
        return games.get(game).checkWeaponSlotContentsReduced(n);
    }

    public synchronized boolean messageIsValidPickAndDiscard(int game, String nick, String p1, String c1) throws RemoteException {
        return games.get(game).isValidPickAndDiscard(nick, p1, c1);
    }

    public synchronized void messagePickAndDiscardCard(int game, String nick, String p1, String c1) throws RemoteException {
        games.get(game).pickAndDiscardCard(nick, p1, c1);
    }

    public synchronized boolean messageIsValidFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFirstActionShoot(nick, wC, lI, lS, d, lC, lP, lPC);
    }

    public synchronized void messageFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).firstActionShoot(nick, wC, lI, lS, d, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidFirstActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidFirstActionMove(nick, d);
    }

    public synchronized void messageFirstActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).firstActionMove(nick, d);
    }

    public synchronized List<String> messageGetWeaponCardLoaded(int game, String nick) throws RemoteException {
        return games.get(game).getPlayerWeaponCardLoaded(nick);
    }

    public synchronized boolean messageIsValidCard(int game, String nick, String weaponCard) throws RemoteException {
        return games.get(game).isValidCard(nick, weaponCard);
    }

    public synchronized List<Colour> messageGetReloadCost(int game, String s, String nick) throws RemoteException {
        return games.get(game).getPlayerReloadCost(s, nick);
    }

    public synchronized List<Colour> messageGetReloadCostReduced(int game, String s) throws RemoteException {
        return games.get(game).getReloadCostReduced(s);
    }

    public synchronized String messageGetDescriptionWC(int game, String s, String nick) throws RemoteException {
        return games.get(game).getPlayerDescriptionWC(s, nick);
    }

    public synchronized boolean messageIsValidFirstActionGrab(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lA, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFirstActionGrab(nick, d, wC, wS,lA, lP, lPC);
    }

    public synchronized void messageFirstActionGrab(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).firstActionGrab(nick, d, wC, lC, lP, lPC);
    }

    public synchronized boolean messageIsDiscard(int game) throws RemoteException {
        return games.get(game).isDiscard();
    }

    public synchronized void messageDiscardWeaponCard(int game, String nick, String wS, String wC) throws RemoteException {
        games.get(game).discardWeaponCard(nick, wS, wC);
    }

    public synchronized boolean messageIsValidSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidSecondActionShoot(nick, wC, lI, lS, d, lC, lP, lPC);
    }

    public synchronized void messageSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).secondActionShoot(nick, wC, lI, lS, d, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidSecondActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidSecondActionMove(nick, d);
    }

    public synchronized void messageSecondActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).secondActionMove(nick, d);
    }

    public synchronized boolean messageIsValidSecondActionGrab(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lA, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidSecondActionGrab(nick, d, wC, wS, lA, lP, lPC);
    }

    public synchronized void messageSecondActionGrab(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).secondActionGrab(nick, d, wC, lC, lP, lPC);
    }

    public synchronized List<String> messageGetPowerUpCard(int game, String nick) throws RemoteException {
        return games.get(game).getPlayerPowerUpCard(nick);
    }

    public synchronized List<String> messageGetPowerUpCardColour(int game, String nickName) throws RemoteException {
        return games.get(game).getPlayerPowerUpCardColour(nickName);
    }

    public synchronized String messageGetDescriptionPUC(int game, String pC, String col, String nick) throws RemoteException {
        return games.get(game).getPlayerDescriptionPUC(pC, col, nick);
    }

    public synchronized boolean messageIsValidUsePowerUpCard(int game, String nick, String pC, String col, List<String> l, Colour c) throws RemoteException {
        return games.get(game).isValidUsePowerUpCard(nick, pC, col, l, c);
    }

    public synchronized void messageUsePowerUpCard(int game, String nick, String pC, String col, List<String> l, Colour c) throws RemoteException {
        games.get(game).usePowerUpCard(nick, pC, col, l, c);
    }

    public synchronized List<String> messageGetWeaponCardUnloaded(int game, String nick) throws RemoteException {
        return games.get(game).getPlayerWeaponCardUnloaded(nick);
    }

    public synchronized boolean messageIsValidReload(int game, String nick, String s) throws RemoteException {
        return games.get(game).isValidReload(nick, s);
    }

    public synchronized void messageReload(int game, String nick, String s, int end) throws RemoteException {
        games.get(game).reload(nick, s, end);
    }

    public synchronized boolean messageIsValidScoring(int game) throws RemoteException {
        return games.get(game).isValidScoring();
    }

    public synchronized void messageScoring(int game) throws RemoteException {
        games.get(game).scoring();
    }

    public synchronized List<String> messageGetDeadList(int game) throws RemoteException {
        return games.get(game).getDeadList();
    }

    public synchronized boolean messageIsValidDiscardCardForSpawnPoint(int game, String nick, String s, String c) throws RemoteException {
        return games.get(game).isValidDiscardCardForSpawnPoint(nick, s, c);
    }

    public synchronized void messageDiscardCardForSpawnPoint(int game, String nick, String s, String c) throws RemoteException {
        games.get(game).discardCardForSpawnPoint(nick, s, c);
    }

    public synchronized boolean messageIsValidToReplace(int game) throws RemoteException {
        return games.get(game).isValidToReplace();
    }

    public synchronized void messageReplace(int game) throws RemoteException {
        games.get(game).replace();
    }

    public synchronized boolean messageIsValidFinalFrenzyAction(int game, String nick, List<String> l) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction(nick, l);
    }

    public synchronized List<String> messageGetPlayerWeaponCard(int game, String nick) throws RemoteException {
        return games.get(game).getPlayerWeaponCard(nick);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction1(int game, String nick, int d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction1(nick, d, wC, lI, lS, lC, lP, lPC);
    }

    public synchronized void messageFinalFrenzyAction1(int game, String nick, int d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).finalFrenzyAction1(nick, d, lW, wC, lI, lS, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction2(nick, d);
    }

    public synchronized void messageFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).finalFrenzyAction2(nick, d);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction3(nick, d, wC, wS, lC, lP, lPC);
    }

    public synchronized void messageFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).finalFrenzyAction3(nick, d, wC, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction4(int game, String nick, List<Integer> d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction4(nick, d, wC, lI, lS, lC, lP, lPC);
    }

    public synchronized void messageFinalFrenzyAction4(int game, String nick, List<Integer> d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).finalFrenzyAction4(nick, d, lW, wC, lI, lS, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lS, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction5(nick, d, wC, wS, lC, lS, lPC);
    }

    public synchronized void messageFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).finalFrenzyAction5(nick, d, wC, lC, lP, lPC);
    }

    public synchronized void messageFinalFrenzyTurnScoring(int game) throws RemoteException {
        games.get(game).finalFrenzyTurnScoring();
    }

    public synchronized void messageEndTurnFinalFrenzy(int game) throws RemoteException {
        games.get(game).endTurnFinalFrenzy();

    }
    public synchronized void messageFinalScoring(int game) throws RemoteException {
        games.get(game).finalScoring();
    }

    public synchronized List<String> messageGetPlayers(int game) throws RemoteException {
        return games.get(game).getPlayers();
    }

    public synchronized List<Integer> messageGetScore(int game) throws RemoteException {
        return games.get(game).getScore();
    }
}
