package view;

import controller.Game;
import controller.GameState;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.LinkedList;
import java.util.List;

public class Server extends UnicastRemoteObject implements ServerInterface {

    static private List<Game> games;
    static private List<List<View>> views;
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
        views = new LinkedList<>();
        playersTakingTheirTurn = new LinkedList<>();            //TODO methods in the controller to notify the server
    }

    public int getGames(){
        return games.size();
    }

    public int setGame(int numGame) throws RemoteException{
        if (games.size() < numGame)
            return numGame;
        else{
            games.add(numGame, new Game());             //TODO add a game even if it shouldn't
            views.add(numGame, new LinkedList<>());
            playersTakingTheirTurn.add(numGame, 1);
            return numGame;
        }
    }

    public int receiveIdentifier(int game) throws RemoteException{
        return views.get(game).size();                  //It should be correct
    }

    public void setCli(int game, int identifier) throws RemoteException{
        views.get(game).add(identifier, new Cli());
        views.get(game).get(identifier).setGame(games.get(identifier));         //TODO this could be a problem!!!!!!!
    }

    public void setGui(int game, int identifier) throws RemoteException{
        views.get(game).add(identifier, new Gui());
        views.get(game).get(identifier).setGame(games.get(identifier));         //TODO this could be a problem!!!!!!!
    }

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

    public void messageAskNameAndColour(int game, int identifier) throws RemoteException{        //TODO just a doubt, does view print on the right terminal?
        views.get(game).get(identifier).askNameAndColour();
    }
    public void messageSelectSpawnPoint(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).selectSpawnPoint();
    }
    public boolean messageDoYouWantToUsePUC(int game, int identifier)throws RemoteException{
        return views.get(game).get(identifier).doYouWantToUsePUC();
    }
    public void messageUsePowerUpCard(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).usePowerUpCard();
    }
    public void messageAction1(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).action1();
    }
    public void messageAction2(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).action2();
    }
    public void messageReload(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).reload();
    }
    public void messageScoring(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).scoring();
    }
    public void messageNewSpawnPoint(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).newSpawnPoint();
    }
    public void messageReplace(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).replace();
    }
    public void messageFinalFrenzyTurn(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).finalFrenzyTurn();
    }
    public void messageEndFinalFrenzy(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).endFinalFrenzy();
    }
    public void messageFinalScoring(int game, int identifier)throws RemoteException{
        views.get(game).get(identifier).finalScoring();
    }

    /* We should insert methods who take parameters from the view end give them to the controller, returning the boolean (only for the
    IsValidMethod) to the Cli/Gui
     */
}
