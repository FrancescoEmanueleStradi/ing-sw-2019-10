package view;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javax.naming.*;

public class Client {

    private static View view;
    static private int game;
    static private int identifier;

    public static void main(String[] args) throws NamingException, RemoteException, AlreadyBoundException, NotBoundException, MalformedURLException {
        Registry registry = LocateRegistry.getRegistry();
        ServerInterface centralServer = (ServerInterface) Naming.lookup("rmi://localhost:5099/central_server");
        System.out.println("Client --> " + centralServer.echo("Hello there!"));

        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to use CLI or GUI?");
        switch (in.next()) {
            case "CLI":
                view = new Cli();
                break;
            case "GUI":
                view = new Gui();
                break;
        }

        view.askNameAndColour();
        view.selectSpawnPoint();
        while (centralServer.isMyTurn(game, identifier)) {
            if (centralServer.isNotFinalFrenzy(game)) {
                if(view.doYouWantToUsePUC())                //TODO Control if in this part of the game the player can use the power up card?
                    view.usePowerUpCard();
                view.action1();
                if(view.doYouWantToUsePUC())
                    view.usePowerUpCard();
                view.action2();
                if(view.doYouWantToUsePUC())
                    view.usePowerUpCard();
                view.reload();
                view.scoring();
                view.newSpawnPoint();
                view.replace();
            }
            else {
                view.finalFrenzyTurn();                       //TODO
                view.endFinalFrenzy();
            }
        }

        if(centralServer.gameIsFinished(game)){
            view.finalScoring();
        }


    }

}
