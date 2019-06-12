package network;

import view.View;
import view.cli.CLI;
import view.cli.CLISocket;
import view.gui.GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Timer;

public class SocketProcesses {

    private static CLISocket view;
    private static int game;
    private static int identifier;

    public static void socketProcesses(Socket socket) throws RemoteException, InterruptedException, IOException, ClassNotFoundException {
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        ObjectInputStream socketObjectIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream socketObjectOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner in = new Scanner(System.in);

        socketOut.println("Get Games");
        System.out.println("Enter the number of the game you want to play. There are " + socketIn.nextLine() + " games now.\n" +
                "You can choose one of the current games or you can create a new game entering the number you saw +1.");
        game = in.nextInt()-1;

        System.out.println("Are you an old player of this game?");
        String n = in.next();
        if(n.equals("yes") || n.equals("Yes") || n.equals("YES")) {
            System.out.println("Enter you old identifier:");
            identifier = in.nextInt();
            socketOut.println("Is A Suspended Identifier");
            socketOut.println(game);
            socketOut.println(identifier);
            String isASuspendedID = socketIn.nextLine();
            while (isASuspendedID.equals("false")) {
                System.out.println("We couldn't find your identifier, please try again.");
                System.out.println("Enter you old identifier:");
                identifier = in.nextInt();
                socketOut.println("Is A Suspended Identifier");
                socketOut.println(game);
                socketOut.println(identifier);
                isASuspendedID = socketIn.nextLine();
            }
            System.out.println("Your identifier is:" + identifier);

            boolean cliGui = false;
            do {
                System.out.println("Do you want to use CLI or GUI?");
                switch (in.next()) {
                    case "CLI":
                    case "Cli":
                    case "cli":
                        cliGui = true;
                        view = new CLISocket(game, socket);
                        break;
                    /*case "GUI":
                    case "Gui":
                    case "gui":
                        cliGui = true;
                        view = new GUI(game);
                        break;*/
                    default:
                        break;
                }
            } while (!cliGui);

            socketOut.println("Set View");
            socketOut.println(game);
            socketOut.println(identifier);
            socketObjectOut.writeObject(view.getView());
            socketObjectOut.flush();

            socketOut.println("Get Type");
            socketOut.println(game);
            int type = socketIn.nextInt();
            socketIn.nextLine();
            view.setType(type);

            view.setInformation(identifier);

            socketOut.println("Manage Reconnection");
            socketOut.println(game);
            socketOut.println(identifier);

            view.printType();
        }

        else {
            socketOut.println("Too Many");
            socketOut.println(game);
            String tooMany = socketIn.nextLine();
            while (tooMany.equals("true")) {
                System.out.println("Too many people on this game, please choose another one:");
                game = in.nextInt() - 1;
                socketOut.println("Too Many");
                socketOut.println(game);
                tooMany = socketIn.nextLine();
            }

            socketOut.println("Set Game");
            socketOut.println(game);


            System.out.println("Wait for five players to connect. When time will be out, the game will start even with three or four players.");
            socketOut.println("Receive Identifier");
            socketOut.println(game);
            identifier = socketIn.nextInt();
            socketIn.nextLine();

            socketOut.println("Merge Group");
            socketOut.println(game);

            while (true) {
                socketOut.println("Can Start");
                socketOut.println(game);
                String canStart = socketIn.nextLine();
                if (canStart.equals("true"))
                    break;
            }

            System.out.println("\nYour identifier is: " + identifier);

            boolean cliGui = false;
            do {
                System.out.println("Do you want to use CLI or GUI?");
                switch (in.next()) {
                    case "CLI":
                    case "Cli":
                    case "cli":
                        cliGui = true;
                        view = new CLISocket(game, socket);
                        break;
                    /*case "GUI":
                    case "Gui":
                    case "gui":
                        cliGui = true;
                        view = new GUI(game);
                        break;*/
                    default:
                        break;
                }
            } while (!cliGui);

            socketOut.println("Set View");
            socketOut.println(game);
            socketOut.println(identifier);
            socketObjectOut.writeObject(view.getView());
            socketObjectOut.flush();

            view.setIdentifier(identifier);

            view.askNameAndColour();                    //identifier 1 has to have the first player card and he has to choose the type
            view.selectSpawnPoint();
            view.printType();
        }

        try {
            while (true) {
                socketOut.println("Stop Game");
                socketOut.println(game);
                String stopGame = socketIn.nextLine();
                if (stopGame.equals("true"))
                    break;

                socketOut.println("Is My Turn");
                socketOut.println(game);
                socketOut.println(identifier);
                String isMyTurn = socketIn.nextLine();

                if (isMyTurn.equals("true")) {
                    socketOut.println("Is Not Final Frenzy");
                    socketOut.println(game);
                    String isNotFF = socketIn.nextLine();

                    if (isNotFF.equals("true")) {
                        if (view.doYouWantToUsePUC()) {
                            MyTaskSocket task = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                            Timer timer = new Timer();
                            timer.schedule(task, 150000);
                            view.usePowerUpCard();
                            timer.cancel();
                        }
                        MyTaskSocket task2 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                        Timer timer2 = new Timer();
                        timer2.schedule(task2, 150000);
                        view.action1();
                        timer2.cancel();
                        if (view.doYouWantToUsePUC()) {
                            MyTaskSocket task3 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                            Timer timer3 = new Timer();
                            timer3.schedule(task3, 150000);
                            view.usePowerUpCard();
                            timer3.cancel();
                        }
                        MyTaskSocket task4 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                        Timer timer4 = new Timer();
                        timer4.schedule(task4, 150000);
                        view.action2();
                        timer4.cancel();
                        if (view.doYouWantToUsePUC()) {
                            MyTaskSocket task5 = new MyTaskSocket(game, identifier, view.getNickName(), socket);
                            Timer timer5 = new Timer();
                            timer5.schedule(task5, 150000);
                            view.usePowerUpCard();
                            timer5.cancel();
                        }

                        view.reload();
                        view.scoring();
                        view.replace();

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

                socketOut.println("Game Is Finished");
                socketOut.println(game);
                String gameIsFinished = socketIn.nextLine();
                if(gameIsFinished.equals("true"))
                    break;
            }

            view.endFinalFrenzy();
            view.finalScoring();
            System.exit(0);

        }catch (RemoteException e){
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
}