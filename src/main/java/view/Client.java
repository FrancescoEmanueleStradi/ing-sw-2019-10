package view;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javax.naming.*;

public class Client {

    //private static View view;
    static private int game;
    static private int identifier;

    public static void main(String[] args) throws NamingException, RemoteException, AlreadyBoundException, NotBoundException, MalformedURLException {
        //Registry registry = LocateRegistry.getRegistry();
        ServerInterface centralServer = (ServerInterface) Naming.lookup("rmi://localhost:5099/central_server");
        System.out.println("Client --> " + centralServer.echo("Hello there!"));

        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of the game you want to play: " +
                "there are " + centralServer.getGames()+ " games now");
        game = centralServer.setGame(in.nextInt()-1);
        identifier = centralServer.receiveIdentifier(game);
        System.out.println("Do you want to use CLI or GUI?");
        switch (in.next()) {
            case "CLI":
                centralServer.setCli(game, identifier);
                //view = new Cli();
                break;
            case "Cli":
                centralServer.setCli(game, identifier);
                //view = new Cli();
                break;
            case "cli":
                centralServer.setCli(game, identifier);
                //view = new Cli();
                break;
            case "GUI":
                centralServer.setGui(game, identifier);
                //view = new Gui();
                break;
            case "Gui":
                centralServer.setGui(game, identifier);
                //view = new Gui();
                break;
            case "gui":
                centralServer.setGui(game, identifier);
                //view = new Gui();
                break;
        }

        //view.askNameAndColour();
        centralServer.messageAskNameAndColour(game, identifier);        //TODO IT PRINTS ON THE SERVER TERMINAL
        //view.selectSpawnPoint();
        centralServer.messageSelectSpawnPoint(game, identifier);
        while (true) {
            if (centralServer.isMyTurn(game, identifier)) {
                if (centralServer.isNotFinalFrenzy(game)) {
                    //if(view.doYouWantToUsePUC())                //TODO Control if in this part of the game the player can use the power up card?
                    if (centralServer.messageDoYouWantToUsePUC(game, identifier))
                        //view.usePowerUpCard();
                        centralServer.messageUsePowerUpCard(game, identifier);
                    //view.action1();
                    centralServer.messageAction1(game, identifier);
                    //if(view.doYouWantToUsePUC())
                    if (centralServer.messageDoYouWantToUsePUC(game, identifier))
                        //view.usePowerUpCard();
                        centralServer.messageUsePowerUpCard(game, identifier);
                    //view.action2();
                    centralServer.messageAction2(game, identifier);
                    //if(view.doYouWantToUsePUC())
                    if (centralServer.messageDoYouWantToUsePUC(game, identifier))
                        //view.usePowerUpCard();
                        centralServer.messageUsePowerUpCard(game, identifier);
                    //view.reload();
                    centralServer.messageReload(game, identifier);
                    //view.scoring();
                    centralServer.messageScoring(game, identifier);
                    //view.newSpawnPoint();
                    centralServer.messageNewSpawnPoint(game, identifier);
                    //view.replace();
                    centralServer.messageReplace(game, identifier);
                    centralServer.finishTurn(game);
                } else {
                    //view.finalFrenzyTurn();                       //TODO
                    centralServer.messageFinalFrenzyTurn(game, identifier);
                    centralServer.finishTurn(game);
                    break;              //TODO is it now the client has to break?
                }
            }
        }
        //view.endFinalFrenzy();                  //TODO
        centralServer.messageEndFinalFrenzy(game, identifier);
        if(centralServer.gameIsFinished(game)){
            //view.finalScoring();
            centralServer.messageFinalScoring(game, identifier);
        }
    }
}
