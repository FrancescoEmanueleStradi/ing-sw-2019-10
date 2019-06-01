package view;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Scanner;
import java.util.Timer;
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
                "there are " + centralServer.getGames()+ " games now; you can choose one of the current games or you can create a new game entering the number you saw +1");
        game = in.nextInt()-1;
        System.out.println("Are you an old Player of this game?");
        String n = in.next();
        if(n.equals("yes") || n.equals("Yes") || n.equals("YES")){
            System.out.println("Enter you old identifier:");
            identifier = in.nextInt();
            while(!centralServer.isASuspendedIdentifier(game, identifier)){
                System.out.println("Enter you old identifier:");
                identifier = in.nextInt();
            }
            System.out.println("Your identifier is:" + identifier);
            System.out.println("Do you want to use CLI or GUI?");
            switch (in.next()) {
                case "CLI":
                case "Cli":
                case "cli":
                    view = new CLI(game, centralServer);
                    break;
                case "GUI":
                case "Gui":
                case "gui":
                    view = new GUI(game, centralServer);
                    break;
            }
            //centralServer.setGame(game);
            //view.setServer(centralServer);
            //view.setGame(game);
            centralServer.setView(game, identifier, view.getView());
            view.setType(centralServer.getType(game));
            view.setInformation(identifier);
            centralServer.manageReconnection(game,identifier);
            view.printType();
        }
        else {
            while (centralServer.tooMany(game)) {
                System.out.println("Too many people on this game, choose another one:");
                game = in.nextInt() - 1;
            }

            centralServer.setGame(game);
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
                    view = new CLI(game, centralServer);
                    break;
                case "GUI":
                case "Gui":
                case "gui":
                    view = new GUI(game, centralServer);
                    break;
            }
            centralServer.setView(game, identifier, view.getView());
            view.setIdentifier(identifier);
            //view.setServer(centralServer);
           // view.setGame(game);

            view.askNameAndColour();                    //identifier 1 has to have the first player  card and he has to choose the type
            view.selectSpawnPoint();
            view.printType();
        }
        try {
            while (true) {
                if (centralServer.stopGame(game))
                    break;
                if (centralServer.isMyTurn(game, identifier)) {
                    if (centralServer.isNotFinalFrenzy(game)) {
                        if (view.doYouWantToUsePUC()) {
                            MyTask task = new MyTask(game, identifier, view.getNickName(), centralServer);
                            Timer timer = new Timer();
                            timer.schedule(task, 150000);
                            view.usePowerUpCard();
                            timer.cancel();
                        }
                        MyTask task2 = new MyTask(game, identifier, view.getNickName(), centralServer);
                        Timer timer2 = new Timer();
                        timer2.schedule(task2, 150000);
                        view.action1();
                        timer2.cancel();
                        if (view.doYouWantToUsePUC()) {
                            MyTask task3 = new MyTask(game, identifier, view.getNickName(), centralServer);
                            Timer timer3 = new Timer();
                            timer3.schedule(task3, 150000);
                            view.usePowerUpCard();
                            timer3.cancel();
                        }
                        MyTask task4 = new MyTask(game, identifier, view.getNickName(), centralServer);
                        Timer timer4 = new Timer();
                        timer4.schedule(task4, 150000);
                        view.action2();
                        timer4.cancel();
                        if (view.doYouWantToUsePUC()) {
                            MyTask task5 = new MyTask(game, identifier, view.getNickName(), centralServer);
                            Timer timer5 = new Timer();
                            timer5.schedule(task5, 150000);
                            view.usePowerUpCard();
                            timer5.cancel();
                        }
                        view.reload();
                        view.scoring();
                        //view.newSpawnPoint();
                        view.replace();
                        centralServer.finishTurn(game);
                        if (centralServer.stopGame(game))
                            break;
                    } else {
                        if (centralServer.stopGame(game))
                            break;
                        centralServer.setFinalTurn(game, identifier, view.getNickName());
                        MyTask task6 = new MyTask(game, identifier, view.getNickName(), centralServer);
                        Timer timer6 = new Timer();
                        timer6.schedule(task6, 500000);
                        view.finalFrenzyTurn();
                        timer6.cancel();
                        centralServer.finishTurn(game);
                        if (centralServer.stopGame(game))
                            break;
                    }
                }
                view.newSpawnPoint();
                if(centralServer.gameIsFinished(game))
                    break;
            }
            view.endFinalFrenzy();
            //if (centralServer.gameIsFinished(game)) {
            view.finalScoring();                        //TODO at the end of the game every player must go there and not start a new turn
            System.exit(0);
            //}
        }catch (RemoteException e){
            centralServer.finishTurn(game);                         //we inserted it here to manage a possible problem during the first part of the game
            centralServer.manageDisconnection(game, identifier, view.getNickName());
            System.exit(0);
        }
    }
}