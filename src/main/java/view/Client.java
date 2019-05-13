package view;

import java.net.MalformedURLException;
import java.rmi.*;
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
        int g = in.nextInt()-1;
        System.out.println("Are you an old Player of this game?");
        String n = in.next();
        if(n.equals("yes") || n.equals("Yes") || n.equals("YES")){
            System.out.println("Enter you old identifier:");
            identifier = in.nextInt();
            while(!centralServer.isASuspendedIdentifier(g, identifier)){
                System.out.println("Enter you old identifier:");
                identifier = in.nextInt();
            }
            centralServer.manageReconnection(g,identifier);
            System.out.println("Your identifier is:" + identifier);
            System.out.println("Do you want to use CLI or GUI?");
            switch (in.next()) {
                case "CLI":
                case "Cli":
                case "cli":
                    view = new CLI();
                    break;
                case "GUI":
                case "Gui":
                case "gui":
                    view = new GUI();
                    break;
            }
            game = centralServer.setGame(g);
            view.setServer(centralServer);
            view.setGame(game);
            view.setInformation(identifier);
        }
        else {
            while (centralServer.tooMany(g)) {
                System.out.println("Too many people on this game, choose another one:");
                g = in.nextInt() - 1;
            }

            game = centralServer.setGame(g);
            System.out.println("Wait for five players to connect, if time will be out you will start even with three or four players");
            identifier = centralServer.receiveIdentifier(game);
            centralServer.mergeGroup(game);

            while (true) {
                if (centralServer.canStart(game))
                    break;
            }


            System.out.println("Your identifier is:" + identifier);
            System.out.println("Do you want to use CLI or GUI?");
            switch (in.next()) {
                case "CLI":
                case "Cli":
                case "cli":
                    view = new CLI();
                    break;
                case "GUI":
                case "Gui":
                case "gui":
                    view = new GUI();
                    break;
            }
            view.setServer(centralServer);
            view.setGame(game);

            view.askNameAndColour();                    //identifier 1 has to have the first player  card
            view.selectSpawnPoint();
        }
        try {
            while (true) {
                if(centralServer.isThereDisconnection(game))
                    view.disconnected();
                if (centralServer.stopGame(game))
                    break;
                if (centralServer.isMyTurn(game, identifier)) {
                    if (centralServer.isNotFinalFrenzy(game)) {
                        if (view.doYouWantToUsePUC())
                            view.usePowerUpCard();
                        view.action1();
                        if (view.doYouWantToUsePUC())
                            view.usePowerUpCard();
                        view.action2();
                        if (view.doYouWantToUsePUC())
                            view.usePowerUpCard();
                        view.reload();
                        view.scoring();
                        view.newSpawnPoint();               //TODO it must be asked to every player
                        view.replace();
                        centralServer.finishTurn(game);
                        if (centralServer.stopGame(game))
                            break;
                    } else {
                        centralServer.setFinalTurn(game, identifier, view.getNickName());
                        view.finalFrenzyTurn();
                        centralServer.finishTurn(game);
                    }
                }
                if(centralServer.gameIsFinished(game))
                    break;
            }
            view.endFinalFrenzy();
            //if (centralServer.gameIsFinished(game)) {
            view.finalScoring();
            //}
        }catch (RemoteException e){                                     //TODO Question: is it correct?
            centralServer.manageDisconnection(game, identifier, view.getNickName());
        }
    }
}
