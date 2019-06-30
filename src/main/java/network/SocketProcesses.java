package network;

import view.View;
import view.cli.CLISocket;
import view.gui.GUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import static java.lang.Thread.sleep;

/**
 * Contains the necessary functions socket functions for client-server communication and controlling game flow.
 */
public class SocketProcesses {

    private static View view;
    private static int game;
    private static int identifier;

    private static PrintWriter socketOut;
    private static Scanner socketIn;

    /**
     * Client-side, this asks the user to choose a game, reconnect with their old identifier if they were
     * previously disconnected. A first-time user has to choose their type of view and is then asked to pick
     * a unique name and colour. Throughout the game, the method sets up tasks for each action the player takes
     * and communicates with the server to manage disconnections accordingly.
     *
     * @param socket socket
     * @throws IOException          some I/O exception
     * @throws InterruptedException thread interruption
     */
    public static void socketProcesses(Socket socket) throws IOException, InterruptedException {
        socketOut = new PrintWriter(socket.getOutputStream(), true);
        socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        String s;

        socketOut.println("Get Games");
        String numGames = socketIn.nextLine();

        System.out.println("Enter the number of the game you want to play. There are " + numGames + " games now.\n" +
                "You can choose one of the current games or you can create a new game entering the number you saw +1.");
        game = in.nextInt() - 1;

        System.out.println("Are you an old player of this game?");
        String n = in.next();

        if(n.equals("yes") || n.equals("Yes") || n.equals("YES")) {
            String isASuspendedID;
            int counterOldID = 0;
            do {
                if(counterOldID > 0)
                    System.out.println("We couldn't find your identifier, please try again.");

                System.out.println("Enter you old identifier:");
                identifier = in.nextInt();

                socketOut.println("Is A Suspended Identifier");
                socketOut.println(game);
                socketOut.println(identifier);

                isASuspendedID = socketIn.nextLine();
                counterOldID++;
            } while(isASuspendedID.equals("false"));

            System.out.println("Welcome back! Your identifier is:" + identifier);

            boolean cliGui = false;
            do {
                System.out.println("Do you want to use CLI or GUI?");
                s = in.next();
                if(s.equals("CLI") || s.equals("Cli") || s.equals("cli")) {
                    cliGui = true;
                    view = new CLISocket(game, socket);
                }
                else if(s.equals("GUI") || s.equals("Gui") || s.equals("gui")) {
                    cliGui = true;
                    //view = new GUI(game, centralServer);
                }
            }while(!cliGui);

            socketOut.println("Get Type");
            socketOut.println(game);
            int type = Integer.parseInt(socketIn.nextLine());
            view.setType(type);

            view.setInformation(identifier);

            socketOut.println("Manage Reconnection");
            socketOut.println(game);
            socketOut.println(identifier);

            view.printType();
        }

        else {
            String tooMany;
            int counterTooMany = 0;

            do {
                if(counterTooMany > 0)
                    System.out.println("Too many people on this game, please choose another one:");

                socketOut.println("Too Many");
                socketOut.println(game);

                tooMany = socketIn.nextLine();
                counterTooMany++;
            } while(tooMany.equals("true"));

            socketOut.println("Set Game");
            socketOut.println(game);

            System.out.println("Wait for five players to connect. When time will be out, the game will start even with three or four players.");

            socketOut.println("Receive Identifier");
            socketOut.println(game);
            identifier = Integer.parseInt(socketIn.nextLine());

            socketOut.println("Merge Group");
            socketOut.println(game);

            String canStart;

            do {
                socketOut.println("Can Start");
                socketOut.println(game);

                canStart = socketIn.nextLine();
            } while(canStart.equals("false"));

            System.out.println("\nWelcome! Your identifier is: " + identifier);

            boolean cliGui = false;
            do {
                System.out.println("Do you want to use CLI or GUI?");
                s = in.next();
                if(s.equals("CLI") || s.equals("Cli") || s.equals("cli")) {
                    cliGui = true;
                    view = new CLISocket(game, socket);
                }
                else if(s.equals("GUI") || s.equals("Gui") || s.equals("gui")) {
                    cliGui = true;
                    //view = new GUI(game, centralServer);
                }
            }while(!cliGui);

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
                    socketOut.println("Stop Game");
                    socketOut.println(game);
                    String stopGame = socketIn.nextLine();

                    if (stopGame.equals("true"))
                        break;

                    notifyHandler();

                    socketOut.println("Is My Turn");
                    socketOut.println(game);
                    socketOut.println(identifier);
                    String isMyTurn = socketIn.nextLine();

                    if (isMyTurn.equals("true")) {
                        socketOut.println("Is Not Final Frenzy");
                        socketOut.println(game);
                        String isNotFF = socketIn.nextLine();

                        if (isNotFF.equals("true")) {

                            notifyHandler();

                            if (view.doYouWantToUsePUC()) {
                                MyTaskSocket task = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                                Timer timer = new Timer();
                                timer.schedule(task, 150000);
                                view.usePowerUpCard();
                                timer.cancel();
                            }

                            notifyHandler();

                            MyTaskSocket task2 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                            Timer timer2 = new Timer();
                            timer2.schedule(task2, 150000);
                            view.action1();
                            timer2.cancel();

                            notifyHandler();

                            if (view.doYouWantToUsePUC()) {
                                MyTaskSocket task3 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                                Timer timer3 = new Timer();
                                timer3.schedule(task3, 150000);
                                view.usePowerUpCard();
                                timer3.cancel();
                            }

                            notifyHandler();

                            MyTaskSocket task4 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                            Timer timer4 = new Timer();
                            timer4.schedule(task4, 150000);
                            view.action2();
                            timer4.cancel();

                            notifyHandler();

                            if (view.doYouWantToUsePUC()) {
                                MyTaskSocket task5 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                                Timer timer5 = new Timer();
                                timer5.schedule(task5, 150000);
                                view.usePowerUpCard();
                                timer5.cancel();
                            }

                            notifyHandler();

                            view.reload();
                            view.scoring();
                            view.replace();

                            notifyHandler();

                            socketOut.println("Finish Turn");
                            socketOut.println(game);

                            socketOut.println("Stop Game");
                            socketOut.println(game);
                            stopGame = socketIn.nextLine();

                            if (stopGame.equals("true"))
                                break;

                        } else {

                            socketOut.println("Stop Game");
                            socketOut.println(game);
                            stopGame = socketIn.nextLine();
                            if (stopGame.equals("true"))
                                break;

                            notifyHandler();

                            socketOut.println("Set Final Turn");
                            socketOut.println(game);
                            socketOut.println(identifier);
                            String nickToSend = view.getNickName();
                            socketOut.println(nickToSend);

                            MyTaskSocket task6 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                            Timer timer6 = new Timer();
                            timer6.schedule(task6, 500000);
                            view.finalFrenzyTurn();
                            timer6.cancel();

                            notifyHandler();

                            socketOut.println("Finish Turn");
                            socketOut.println(game);

                            socketOut.println("Stop Game");
                            socketOut.println(game);
                            stopGame = socketIn.nextLine();

                            if (stopGame.equals("true"))
                                break;
                        }
                    }

                    view.newSpawnPoint();

                    notifyHandler();

                    socketOut.println("Game Is Finished");
                    socketOut.println(game);
                    String gameIsFinished = socketIn.nextLine();

                    if (gameIsFinished.equals("true"))
                        break;
                }

                view.endFinalFrenzy();
                view.finalScoring();
                System.exit(0);
            }
            else if(s.equals("GUI") || s.equals("Gui") || s.equals("gui")){
                view.doYouWantToUsePUC();
            }


        } catch(RemoteException e) {
            //we inserted it here to manage a possible problem during the first part of the game
            socketOut.println("Manage Disconnection");
            socketOut.println(game);
            socketOut.println(identifier);
            String nickToSend = view.getNickName();
            socketOut.println(nickToSend);

            socketOut.println("Finish Turn");
            socketOut.println(game);

            System.exit(0);
        }
    }

    /**
     * Socket has a dedicated handler for notify methods.
     *
     * @throws RemoteException RMI exception
     */
    private static void notifyHandler() throws RemoteException   {
        //Notify Player
        socketOut.println("Notify Player Size");
        socketOut.println(game);
        socketOut.println(identifier);

        int notifyPlayerSize = Integer.parseInt(socketIn.nextLine());

        if (notifyPlayerSize > 0) {
            for (int i = 0; i < notifyPlayerSize; i++) {
                socketOut.println("Get Notify Player");
                socketOut.println(game);
                socketOut.println(identifier);

                List<String> information = new LinkedList<>();
                information.add(socketIn.nextLine());
                information.add(socketIn.nextLine());
                information.add(socketIn.nextLine());

                view.printPlayer(information);
            }
        }

        //Notify Score
        socketOut.println("Notify Score Size");
        socketOut.println(game);
        socketOut.println(identifier);

        int notifyScoreSize = Integer.parseInt(socketIn.nextLine());

        if (notifyScoreSize > 0) {
            for (int i = 0; i < notifyScoreSize; i++) {
                socketOut.println("Get Notify Score");
                socketOut.println(game);
                socketOut.println(identifier);

                List<String> information = new LinkedList<>();
                information.add(socketIn.nextLine());
                information.add(socketIn.nextLine());

                view.printScore(information);
            }
        }

        //Notify Position
        socketOut.println("Notify Position Size");
        socketOut.println(game);
        socketOut.println(identifier);

        int notifyPositionSize = Integer.parseInt(socketIn.nextLine());

        if (notifyPositionSize > 0) {
            for (int i = 0; i < notifyPositionSize; i++) {
                socketOut.println("Get Notify Position");
                socketOut.println(game);
                socketOut.println(identifier);

                List<String> information = new LinkedList<>();
                information.add(socketIn.nextLine());
                information.add(socketIn.nextLine());
                information.add(socketIn.nextLine());

                view.printPosition(information);
            }
        }

        //Notify Mark
        socketOut.println("Notify Mark Size");
        socketOut.println(game);
        socketOut.println(identifier);

        int notifyMarkSize = Integer.parseInt(socketIn.nextLine());

        if (notifyMarkSize > 0) {
            for (int i = 0; i < notifyMarkSize; i++) {
                socketOut.println("Get Notify Mark");
                socketOut.println(game);
                socketOut.println(identifier);

                List<String> information = new LinkedList<>();
                information.add(socketIn.nextLine());
                information.add(socketIn.nextLine());

                view.printMark(information);
            }
        }

        //Notify Damage
        socketOut.println("Notify Damage Size");
        socketOut.println(game);
        socketOut.println(identifier);

        int notifyDamageSize = Integer.parseInt(socketIn.nextLine());

        if (notifyDamageSize > 0) {
            for (int i = 0; i < notifyDamageSize; i++) {
                socketOut.println("Get Notify Damage");
                socketOut.println(game);
                socketOut.println(identifier);

                List<String> information = new LinkedList<>();
                information.add(socketIn.nextLine());
                information.add(socketIn.nextLine());
                information.add(socketIn.nextLine());

                view.printDamage(information);
            }
        }
    }
}