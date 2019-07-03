package network.rmi;

import network.ServerInterface;
import view.View;
import view.cli.CLI;
import view.gui.GUI;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Timer;

import static java.lang.Thread.sleep;

/**
 * Contains the necessary RMI functions for client-server communication and controlling game flow.
 */
public class RMIProcesses {

    private static View view;
    private static int game;
    private static int identifier;

    /**
     * Client-side, this asks the user to choose a game, reconnect with their old identifier if they were
     * previously disconnected. A first-time user has to choose their type of view and is then asked to pick
     * a unique name and colour. Throughout the game, the method sets up tasks for each action the player takes
     * and communicates with the server to manage disconnections accordingly.
     *
     * @param centralServer central server
     * @throws RemoteException RMI exception
     * @throws InterruptedException Thread interruption
     */
    public static void rmiProcesses(ServerInterface centralServer) throws RemoteException, InterruptedException {
        Scanner in = new Scanner(System.in);
        String s;

        System.out.println("Enter the number of the game you want to play. There are " + centralServer.getGames()+ " games now.\n" +
                "You can choose one of the current games or you can create a new game entering the number you saw +1.");
        game = in.nextInt() - 1;

        System.out.println("Are you an old player of this game?");
        String n = in.next();

        if(n.equals("yes") || n.equals("Yes") || n.equals("YES")) {
            System.out.println("Enter you old identifier:");
            identifier = in.nextInt();

            while(!centralServer.isASuspendedIdentifier(game, identifier)) {
                System.out.println("We couldn't find your identifier, please try again.\nEnter you old identifier:");
                identifier = in.nextInt();
            }

            System.out.println("Welcome back! Your identifier is:" + identifier);

            boolean cliGui = false;
            do {
                System.out.println("Do you want to use CLI or GUI?");
                s = in.next();
                if(s.equals("CLI") || s.equals("Cli") || s.equals("cli")) {
                    cliGui = true;
                    view = new CLI(game, centralServer);
                }
                else if(s.equals("GUI") || s.equals("Gui") || s.equals("gui")) {
                    cliGui = true;
                    view = new GUI(game, centralServer);
                }
            } while(!cliGui);

            centralServer.setView(game, identifier, view.getView());
            view.setType(centralServer.getType(game));
            view.setInformation(identifier);
            centralServer.manageReconnection(game,identifier);
            view.printType();
        }

        else {
            while(centralServer.tooMany(game)) {
                System.out.println("Too many people on this game, please choose another one:");
                game = in.nextInt() - 1;
            }

            centralServer.setGame(game);

            System.out.println("Wait for five players to connect. When time will be out, the game will start even with three or four players.");

            identifier = centralServer.receiveIdentifier(game);
            centralServer.mergeGroup(game);

            while(true) {
                if(centralServer.canStart(game))
                    break;
            }

            System.out.println("\nWelcome! Your identifier is: " + identifier);

            boolean cliGui = false;
            do {
                System.out.println("Do you want to use CLI or GUI?");
                s = in.next();
                if(s.equals("CLI") || s.equals("Cli") || s.equals("cli")) {
                    cliGui = true;
                    view = new CLI(game, centralServer);
                }
                else if(s.equals("GUI") || s.equals("Gui") || s.equals("gui")) {
                    cliGui = true;
                    view = new GUI(game, centralServer);
                }
            } while(!cliGui);

            centralServer.setView(game, identifier, view.getView());
            view.setIdentifier(identifier);

            if(s.equals("CLI") || s.equals("Cli") || s.equals("cli")) {
                view.askNameAndColour();                    //identifier 1 has to have the first player card and he has to choose the type
                view.selectSpawnPoint();
                view.printType();
            }

            else if(s.equals("GUI") || s.equals("Gui") || s.equals("gui")) {
                view.askNameAndColour();
                sleep(10000);

            }


        }

        try {

            if(s.equals("CLI") || s.equals("Cli") || s.equals("cli")) {

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

                    if (centralServer.gameIsFinished(game))
                        break;

                }

                view.endFinalFrenzy();
                view.finalScoring();
                System.exit(0);

            }
            else if(s.equals("GUI") || s.equals("Gui") || s.equals("gui")){
                while(true){
                    if(centralServer.isMyTurn(game, identifier))
                        break;
                }
                view.doYouWantToUsePUC();
            }


        } catch (RemoteException e) {
            //we inserted it here to manage a possible problem during the first part of the game
            centralServer.manageDisconnection(game, identifier, view.getNickName());
            centralServer.finishTurn(game);
            System.exit(0);
        }
    }
}