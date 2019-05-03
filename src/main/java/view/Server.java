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
    static private List<List<Integer>> players;
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
        players = new LinkedList<>();
        playersTakingTheirTurn = new LinkedList<>();            //TODO methods in the controller to notify the server
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
        else playersTakingTheirTurn.add(game, 0);
    }

    /* We should insert here methods who take parameters from the view end give them to the controller, returning the boolean (only for the
    IsValidMethod) to the Cli/Gui
     */
}
