package view;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javax.naming.*;

public class Client {

    private static View view;
    private static int game;
    private static int identifier;

    public static void main(String[] args) throws NamingException, RemoteException, AlreadyBoundException, NotBoundException, MalformedURLException, InterruptedException {
        //Registry registry = LocateRegistry.getRegistry();
        ServerInterface centralServer = (ServerInterface) Naming.lookup("rmi://localhost:5099/central_server");
        System.out.println("Client --> " + centralServer.echo("Hello there!"));

        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of the game you want to play: " +
                "there are " + centralServer.getGames()+ " games now");
        game = centralServer.setGame(in.nextInt()-1);
        System.out.println("Wait for five players to connect, if time will be out you will start even with three or four players");
        identifier = centralServer.receiveIdentifier(game);

        while(true){
            if(centralServer.canStart(game))
                break;
        }

        System.out.println("Your identifier is:"+identifier);
        System.out.println("Do you want to use CLI or GUI?");
        switch (in.next()) {
            case "CLI":
                view = new Cli();
                break;
            case "Cli":
                view = new Cli();
                break;
            case "cli":
                view = new Cli();
                break;
            case "GUI":
                view = new Gui();
                break;
            case "Gui":
                view = new Gui();
                break;
            case "gui":
                view = new Gui();
                break;
        }
        view.setServer(centralServer);
        view.setGame(game);

        view.askNameAndColour();
        view.selectSpawnPoint();
        while (true) {
            if(centralServer.stopGame(game))
                break;
            if (centralServer.isMyTurn(game, identifier)) {
                if (centralServer.isNotFinalFrenzy(game)) {
                    if(view.doYouWantToUsePUC())
                        view.usePowerUpCard();
                    view.action1();
                    if(view.doYouWantToUsePUC())
                        view.usePowerUpCard();
                    view.action2();
                    if(view.doYouWantToUsePUC())
                        view.usePowerUpCard();
                    view.reload();
                    view.scoring();
                    view.newSpawnPoint();               //TODO it must be asked to every player
                    view.replace();
                    centralServer.finishTurn(game);
                    if(centralServer.stopGame(game))
                        break;
                } else {
                    view.finalFrenzyTurn();
                    centralServer.finishTurn(game);
                    break;              //TODO is it now the client has to break?
                }
            }
        }
        view.endFinalFrenzy();
        if(centralServer.gameIsFinished(game)){
            view.finalScoring();
        }
    }
}
