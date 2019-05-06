package view;

import controller.Game;
import controller.GameState;
import model.Colour;
import model.cards.PowerUpCard;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.LinkedList;
import java.util.List;

public class Server extends UnicastRemoteObject implements ServerInterface {

    static private List<Game> games;
    //static private List<List<View>> views;
    static private List<Integer> playersTakingTheirTurn;        //position n --> game n

    public Server() throws RemoteException {
        super();
    }

    public String echo(String input) throws RemoteException {
        return "From server: " + input;
    }

    public static void main(String[] args) throws RemoteException {
        System.out.println("Generating server...");
        Server centralServer = new Server();

        System.out.println("Binding server to registry...");
        Registry registry = LocateRegistry.createRegistry(5099);
        //registry = LocateRegistry.getRegistry();
        registry.rebind("central_server", centralServer);

        System.out.println("Client may now invoke methods");
        games = new LinkedList<>();
        //views = new LinkedList<>();
        playersTakingTheirTurn = new LinkedList<>();            //TODO methods in the controller to notify the server
    }

    public int getGames() throws RemoteException {
        return games.size();
    }

    public int setGame(int numGame) throws RemoteException {
        if (games.size() < numGame)
            return numGame;
        else{
            games.add(numGame, new Game());             //TODO add a game even if it shouldn't
            //views.add(numGame, new LinkedList<>());
            playersTakingTheirTurn.add(numGame, 1);
            return numGame;
        }
    }

    public int receiveIdentifier(int game) throws RemoteException{
        return games.get(game).getPlayers().size();                                      //It should be correct
    }

    /*public void setCli(int game, int identifier) throws RemoteException{
        views.get(game).add(identifier, new Cli());
        views.get(game).get(identifier).setGame(games.get(identifier));
    }

    public void setGui(int game, int identifier) throws RemoteException{
        views.get(game).add(identifier, new Gui());
        views.get(game).get(identifier).setGame(games.get(identifier));
    }*/

    public boolean isMyTurn(int game, int identifier) throws RemoteException {
        return(playersTakingTheirTurn.get(game) == identifier);
    }

    public boolean isNotFinalFrenzy(int game) throws RemoteException {
        return !games.get(game).isFinalFrenzy();
    }

    public boolean gameIsFinished(int game) throws RemoteException {
        return games.get(game).getGameState() == GameState.ENDALLTURN;
    }

    public void finishTurn(int game) throws RemoteException {
        if(games.get(game).getPlayers().size() < playersTakingTheirTurn.get(game))
            playersTakingTheirTurn.add(game, playersTakingTheirTurn.get(game)+1);
        else playersTakingTheirTurn.add(game, 1);
    }


    public boolean messageGameIsNotStarted(int game) throws RemoteException {
        return games.get(game).gameIsNotStarted();
    }

    public void messageGameStart(int game, String nick, Colour c) throws RemoteException {
        games.get(game).gameStart(nick,c);
    }

    public boolean messageIsValidReceiveType(int game, int type) throws RemoteException {
        return games.get(game).isValidReceiveType(type);
    }

    public void messageReceiveType(int game, int type) throws RemoteException {
        games.get(game).receiveType(type);
    }

    public boolean messageIsValidAddPlayer(int game, String nick, Colour c) throws RemoteException {
        return games.get(game).isValidAddPlayer(nick, c);
    }

    public void messageAddPlayer(int game, String nick, Colour c) throws RemoteException {
        games.get(game).addPlayer(nick, c);

    }
    public List<PowerUpCard> messageGiveTwoPUCard(int game, String nick) throws RemoteException {
        return games.get(game).giveTwoPUCard(nick);
    }

    public boolean messageIsValidPickAndDiscard(int game, String nick) throws RemoteException {
        return games.get(game).isValidPickAndDiscard(nick);
    }
    public void messagePickAndDiscardCard(int game, String nick, PowerUpCard p1, PowerUpCard p2) throws RemoteException {
        games.get(game).pickAndDiscardCard(nick, p1, p2);
    }

    public boolean messageIsValidFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP) throws RemoteException {
        return games.get(game).isValidFirstActionShoot(nick, wC, lI, lS, d, lC, lP);
    }

    public void messageFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).firstActionShoot(nick, wC, lI, lS, d, lC, lP);
    }

    public boolean messageIsValidFirstActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidFirstActionMove(nick, d);
    }


    public void messageFirstActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).firstActionMove(nick, d);
    }


    public List<String> messageGetWeaponCardLoaded(int game, String nick) throws RemoteException {
        return games.get(game).getWeaponCardLoaded(nick);
    }
    public boolean messageIsValidCard(int game, String nick, String weaponCard) throws RemoteException {
        return games.get(game).isValidCard(nick, weaponCard);
    }

    public List<Colour> messageGetReloadCost(int game, String s, String nick) throws RemoteException {
        return games.get(game).getReloadCost(s, nick);
    }

    public String messageGetDescriptionWC(int game, String s, String nick) throws RemoteException {
        return games.get(game).getDescriptionWC(s, nick);
    }

    public boolean messageIsValidFirstActionGrab(int game, String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP) throws RemoteException {
        return games.get(game).isValidFirstActionGrab(nick, d, wC, wS,lA, lP);
    }
    public void messageFirstActionGrab(int game, String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).firstActionGrab(nick, d, wC, lC, lP);
    }

    public boolean messageIsDiscard(int game) throws RemoteException {
        return games.get(game).isDiscard();
    }

    public void messageDiscardWeaponCard(int game, String nick, String wS, String wC) throws RemoteException {
        games.get(game).discardWeaponCard(nick, wS, wC);
    }

    public boolean messageIsValidSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP) throws RemoteException {
        return games.get(game).isValidSecondActionShoot(nick, wC, lI, lS, d, lC, lP);
    }

    public void messageSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).secondActionShoot(nick, wC, lI, lS, d, lC, lP);
    }

    public boolean messageIsValidSecondActionMove(int game, List<Integer> d) throws RemoteException {
        return games.get(game).isValidSecondActionMove(d);
    }

    public void messageSecondActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).secondActionMove(nick, d);
    }

    public boolean messageIsValidSecondActionGrab(int game, String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP) throws RemoteException {
        return games.get(game).isValidFirstActionGrab(nick, d, wC, wS, lA, lP);
    }

    public void messageSecondActionGrab(int game, String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).secondActionGrab(nick, d, wC, lC, lP);

    }
    public List<String> messageGetPowerUpCard(int game, String nick) throws RemoteException {
        return games.get(game).getPowerUpCard(nick);
    }

    public String messageGetDescriptionPUC(int game, String pC, String nick) throws RemoteException {
        return games.get(game).getDescriptionPUC(pC, nick);
    }

    public boolean messageIsValidUsePowerUpCard(int game, String nick, String pC, List<String> l, Colour c) throws RemoteException {
        return games.get(game).isValidUsePowerUpCard(nick, pC, l, c);
    }


    public void messageUsePowerUpCard(int game, String nick, String pC, List<String> l, Colour c) throws RemoteException {
        games.get(game).usePowerUpCard(nick, pC, l, c);
    }

    public List<String> messageGetWeaponCardUnloaded(int game, String nick) throws RemoteException {
        return games.get(game).getWeaponCardUnloaded(nick);
    }

    public boolean messageIsValidReload(int game) throws RemoteException {
        return games.get(game).isValidReload();
    }

    public void messageReload(int game, String nick, String s, int end) throws RemoteException {
        games.get(game).reload(nick, s, end);

    }
    public boolean messageIsValidScoring(int game) throws RemoteException {
        return games.get(game).isValidScoring();
    }

    public void messageScoring(int game) throws RemoteException {
        games.get(game).scoring();
    }

    public List<String> messageGetDeadList(int game) throws RemoteException {
        return games.get(game).getDeadList();
    }

    public boolean messageIsValidDiscardCardForSpawnPoint(int game) throws RemoteException {
        return games.get(game).isValidDiscardCardForSpawnPoint();
    }

    public void messageDiscardCardForSpawnPoint(int game, String nick, String s) throws RemoteException {
        games.get(game).discardCardForSpawnPoint(nick, s);
    }

    public boolean messageIsValidToReplace(int game) throws RemoteException {
        return games.get(game).isValidToReplace();
    }

    public void messageReplace(int game) throws RemoteException {
        games.get(game).replace();;

    }
    public boolean messageIsValidFinalFrenzyAction(int game, String nick, List<String> l) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction(nick, l);
    }

    public List<String> messageGetWeaponCard(int game, String nick) throws RemoteException {
        return games.get(game).getWeaponCard(nick);
    }

    public boolean messageIsValidFinalFrenzyAction1(int game, String nick, int d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction1(nick, d, wC, lI, lS, lC, lP);
    }

    public void messageFinalFrenzyAction1(int game, String nick, int d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).finalFrenzyAction1(nick, d, lW, wC, lI, lS, lC, lP);
    }

    public boolean messageIsValidFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction2(nick, d);
    }

    public void messageFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).finalFrenzyAction2(nick, d);
    }
    public boolean messageIsValidFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lP) throws RemoteException {
       return games.get(game).isValidFinalFrenzyAction3(nick, d, wC, wS, lC, lP);
    }

    public void messageFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).finalFrenzyAction3(nick, d, wC, lC, lP);

    }
    public boolean messageIsValidFinalFrenzyAction4(int game, String nick, List<Integer> d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction4(nick, d, wC, lI, lS, lC, lP);
    }

    public void messageFinalFrenzyAction4(int game, String nick, List<Integer> d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).finalFrenzyAction4(nick, d, lW, wC, lI, lS, lC, lP);

    }
    public boolean messageIsValidFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lS) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction5(nick, d, wC, wS, lC, lS);
    }

    public void messageFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).finalFrenzyAction5(nick, d, wC, lC, lP);

    }
    public void messageFinalFrenzyTurnScoring(int game) throws RemoteException {
        games.get(game).finalFrenzyTurnScoring();

    }
    public void messageEndTurnFinalFrenzy(int game) throws RemoteException {
        games.get(game).endTurnFinalFrenzy();

    }
    public void messageFinalScoring(int game) throws RemoteException {
        games.get(game).finalScoring();

    }
    public  List<String> messageGetPlayers(int game) throws RemoteException {
        return games.get(game).getPlayers();
    }
    public List<Integer> messageGetScore(int game) throws RemoteException {
        return games.get(game).getScore();
    }

    /* We should insert methods who take parameters from the view end give them to the controller, returning the public boolean (only for the
    IsValidMethod) to the Cli/Gui
     */
}
