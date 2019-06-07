package network;

import model.Colour;
import view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SocketServerClientHandler implements Runnable {

    private Socket socket;
    private ServerMethods server;

    public SocketServerClientHandler(Socket socket, ServerMethods server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            PrintWriter outPrinter = new PrintWriter(socket.getOutputStream(), true);
            Scanner inScanner = new Scanner(socket.getInputStream());
            ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());

            while(true) {
                boolean exit = false;
                switch (inScanner.nextLine()) {
                    //SocketProcesses calls
                    case "Get Games":
                        outPrinter.println(server.getGames());
                        break;
                    case "Is A Suspended Identifier":
                        outPrinter.println(server.isASuspendedIdentifier(Integer.parseInt(inScanner.nextLine()), Integer.parseInt(inScanner.nextLine())));
                        break;
                    case "Set View":
                        server.setView(inScanner.nextInt(), inScanner.nextInt(), (View) inObject.readObject());
                        break;
                    case "Get Type":
                        outPrinter.println(server.getType(inScanner.nextInt()));
                        break;
                    case "Manage Reconnection":
                        server.manageReconnection(inScanner.nextInt(), inScanner.nextInt());
                        break;
                    case "Too Many":
                        outPrinter.println(server.tooMany(inScanner.nextInt()));
                        break;
                    case "Set Game":
                        server.setGame(inScanner.nextInt());
                        break;
                    case "Receive Identifier":
                        outPrinter.println(server.receiveIdentifier(inScanner.nextInt()));
                        break;
                    case "Merge Group":
                        server.mergeGroup(inScanner.nextInt());
                        break;
                    case "Can Start":
                        outPrinter.println(server.canStart(inScanner.nextInt()));
                        break;
                    case "Stop Game":
                        outPrinter.println(server.stopGame(inScanner.nextInt()));
                        break;
                    case "Is My Turn":
                        outPrinter.println(server.isMyTurn(inScanner.nextInt(), inScanner.nextInt()));
                        break;
                    case "Is Not Final Frenzy":
                        outPrinter.println(server.isNotFinalFrenzy(inScanner.nextInt()));
                        break;
                    case "Set Final Turn":
                        server.setFinalTurn(inScanner.nextInt(), inScanner.nextInt(), inScanner.nextLine());
                        break;
                    case "Game Is Finished":
                        outPrinter.println(server.gameIsFinished(inScanner.nextInt()));
                        break;
                    case "Manage Disconnection":
                        server.manageDisconnection(inScanner.nextInt(), inScanner.nextInt(), inScanner.nextLine());
                        break;
                    case "Finish Turn":
                        server.finishTurn(inScanner.nextInt());
                        break;

                    //CLISocket calls
                    case "Get Suspended Name":
                        outPrinter.println(server.getSuspendedName(inScanner.nextInt(), inScanner.nextInt()));
                        break;
                    case "Get Suspended Colour":
                        outPrinter.println(server.getSuspendedColour(inScanner.nextInt(), inScanner.nextLine()));
                        break;
                    case "Message Game Is Not Started":
                        outPrinter.println(server.messageGameIsNotStarted(inScanner.nextInt()));
                        break;
                    case "Set Nickname":
                        server.setNickName(inScanner.nextInt(), inScanner.nextInt(), inScanner.nextLine());
                        break;
                    case "Message Game Start":
                        server.messageGameStart(inScanner.nextInt(), inScanner.nextLine(), Colour.valueOf(inScanner.nextLine()));
                        break;
                    case "Message Is Valid Receive Type":
                        outPrinter.println(server.messageIsValidReceiveType(inScanner.nextInt(), inScanner.nextInt()));
                        break;
                    case "Message Receive Type":
                        server.messageReceiveType(inScanner.nextInt(), inScanner.nextInt());
                        break;
                    case "Message Is Valid Add Player":
                        outPrinter.println(server.messageIsValidAddPlayer(inScanner.nextInt(), inScanner.nextLine(), Colour.valueOf(inScanner.nextLine())));
                        break;
                    case "Message Add Player":
                        server.messageAddPlayer(inScanner.nextInt(), inScanner.nextLine(), Colour.valueOf(inScanner.nextLine()));
                        break;
                    case "Message Give Two PU Card":
                        server.messageGiveTwoPUCard(inScanner.nextInt(), inScanner.nextLine());
                        break;
                    case "Message Get Initial PowerUp Card":
                        outPrinter.println(server.messageGetPowerUpCard(inScanner.nextInt(), inScanner.nextLine()).get(0));
                        outPrinter.println(server.messageGetPowerUpCard(inScanner.nextInt(), inScanner.nextLine()).get(1));
                        break;
                    case "Message Get Initial PowerUp Card Colour":
                        outPrinter.println(server.messageGetPowerUpCardColour(inScanner.nextInt(), inScanner.nextLine()).get(0));
                        outPrinter.println(server.messageGetPowerUpCardColour(inScanner.nextInt(), inScanner.nextLine()).get(1));
                        break;
                    case "Message Is Valid Pick And Discard":
                        outPrinter.println(server.messageIsValidPickAndDiscard(inScanner.nextInt(), inScanner.nextLine(), inScanner.nextLine(), inScanner.nextLine()));
                        break;
                    case "Message Pick And Discard":
                        server.messagePickAndDiscardCard(inScanner.nextInt(), inScanner.nextLine(), inScanner.nextLine(), inScanner.nextLine());
                        break;
                    case "Message Get PowerUp Card":
                        int game = inScanner.nextInt();
                        String nickname = inScanner.nextLine();
                        int size = server.messageGetPowerUpCard(game, nickname).size();
                        outPrinter.println(size);
                        for(int i = 0; i < size; i++) {
                            outPrinter.println(server.messageGetPowerUpCard(game, nickname).get(i));
                            outPrinter.println(server.messageGetPowerUpCardColour(game, nickname).get(i));
                        }
                        break;
                    case "Message Get Description PUC":
                        outPrinter.println(server.messageGetDescriptionPUC(inScanner.nextInt(), inScanner.nextLine(), inScanner.nextLine(), inScanner.nextLine()));
                        break;
                    case "Message Is Valid Use PowerUp Card":
                        int game1 = inScanner.nextInt();
                        String nickname1 = inScanner.nextLine();
                        String namePC = inScanner.nextLine();
                        String colourPC = inScanner.nextLine();
                        int size1 = inScanner.nextInt();
                        List<String> lS = new LinkedList<>();
                        for(int i = 0; i < size1; i++)
                            lS.add(inScanner.nextLine());
                        String colour = inScanner.nextLine();
                        if(colour.equals("null"))
                            outPrinter.println(server.messageIsValidUsePowerUpCard(game1, nickname1, namePC, colourPC, lS, null));
                        else
                            outPrinter.println(server.messageIsValidUsePowerUpCard(game1, nickname1, namePC, colourPC, lS, Colour.valueOf(colour)));
                        break;
                    case "Message Use PowerUp Card":
                        int game2 = inScanner.nextInt();
                        String nickname2 = inScanner.nextLine();
                        String namePC1 = inScanner.nextLine();
                        String colourPC1 = inScanner.nextLine();
                        int size2 = inScanner.nextInt();
                        List<String> lS1 = new LinkedList<>();
                        for(int i = 0; i < size2; i++)
                            lS1.add(inScanner.nextLine());
                        String colour1 = inScanner.nextLine();
                        if(colour1.equals("null"))
                            server.messageUsePowerUpCard(game2, nickname2, namePC1, colourPC1, lS1, null);
                        else
                            server.messageUsePowerUpCard(game2, nickname2, namePC1, colourPC1, lS1, Colour.valueOf(colour1));
                        break;
                    case "Message Check Your Status":
                        outPrinter.println(server.messageCheckYourStatus(inScanner.nextInt(), inScanner.nextLine()));
                        break;
                    case "Message Get Weapon Card Unloaded":
                        int game3 = inScanner.nextInt();
                        String nickname3 = inScanner.nextLine();
                        int size3 = server.messageGetWeaponCardUnloaded(game3, nickname3).size();
                        outPrinter.println(size3);
                        for(int i = 0; i < size3; i++)
                            outPrinter.println(server.messageGetWeaponCardUnloaded(game3, nickname3).get(i));
                        break;
                    case "Message Is Valid Reload":
                        outPrinter.println(server.messageIsValidReload(inScanner.nextInt(), inScanner.nextLine(), inScanner.nextLine()));
                        break;
                    case "Message Reload":
                        server.messageReload(inScanner.nextInt(), inScanner.nextLine(), inScanner.nextLine(), inScanner.nextInt());
                        break;
                    case "Message Is Valid Scoring":
                        outPrinter.println(server.messageIsValidScoring(inScanner.nextInt()));
                        break;
                    case "Message Scoring":
                        server.messageScoring(inScanner.nextInt());
                        break;
                    case "Message Is Valid To Replace":
                        outPrinter.println(server.messageIsValidToReplace(inScanner.nextInt()));
                        break;
                    case "Message Replace":
                        server.messageReplace(inScanner.nextInt());
                        break;
                    case "Message Get Player Weapon Card":
                        int game4 = inScanner.nextInt();
                        String nickname4 = inScanner.nextLine();
                        int size4 = server.messageGetWeaponCardUnloaded(game4, nickname4).size();
                        outPrinter.println(size4);
                        for(int i = 0; i < size4; i++)
                            outPrinter.println(server.messageGetPlayerWeaponCard(game4, nickname4).get(i));
                        break;
                    case "Message Is Valid Final Frenzy Action":
                        int game5 = inScanner.nextInt();
                        String nickname5 = inScanner.nextLine();
                        int size5 = inScanner.nextInt();
                        List<String> lS2 = new LinkedList<>();
                        for(int i = 0; i < size5; i++)
                            lS2.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction(game5, nickname5, lS2));
                        break;
                    case "Message Is Valid Final Frenzy Action 1":
                        int game6 = inScanner.nextInt();
                        String nickname6 = inScanner.nextLine();
                        int direction = inScanner.nextInt();
                        String wC = inScanner.nextLine();
                        List<Integer> lI = new LinkedList<>();
                        int size6 = inScanner.nextInt();
                        for(int i = 0; i < size6; i++)
                            lI.add(inScanner.nextInt());
                        List<String> lS3 = new LinkedList<>();
                        int size7 = inScanner.nextInt();
                        for(int i = 0; i < size7; i++)
                            lS3.add(inScanner.nextLine());
                        List<Colour> lC = new LinkedList<>();
                        int size8 = inScanner.nextInt();
                        for(int i = 0; i < size8; i++)
                            lC.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP = new LinkedList<>();
                        int size9 = inScanner.nextInt();
                        for(int i = 0; i < size9; i++)
                            lP.add(inScanner.nextLine());
                        List<String> lPC = new LinkedList<>();
                        int size10 = inScanner.nextInt();
                        for(int i = 0; i < size10; i++)
                            lPC.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction1(game6, nickname6, direction, wC, lI, lS3, lC, lP, lPC));
                        break;
                    case "Message Final Frenzy Action 1":
                        int game7 = inScanner.nextInt();
                        String nickname7 = inScanner.nextLine();
                        int direction1 = inScanner.nextInt();
                        List<String> lW = new LinkedList<>();
                        int size11 = inScanner.nextInt();
                        for(int i = 0; i < size11; i++)
                            lW.add(inScanner.nextLine());
                        String wC1 = inScanner.nextLine();
                        List<Integer> lI1 = new LinkedList<>();
                        int size12 = inScanner.nextInt();
                        for(int i = 0; i < size12; i++)
                            lI1.add(inScanner.nextInt());
                        List<String> lS4 = new LinkedList<>();
                        int size13 = inScanner.nextInt();
                        for(int i = 0; i < size13; i++)
                            lS4.add(inScanner.nextLine());
                        List<Colour> lC1 = new LinkedList<>();
                        int size14 = inScanner.nextInt();
                        for(int i = 0; i < size14; i++)
                            lC1.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP1 = new LinkedList<>();
                        int size15 = inScanner.nextInt();
                        for(int i = 0; i < size15; i++)
                            lP1.add(inScanner.nextLine());
                        List<String> lPC1 = new LinkedList<>();
                        int size16 = inScanner.nextInt();
                        for(int i = 0; i < size16; i++)
                            lPC1.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction1(game7, nickname7, direction1, lW, wC1, lI1, lS4, lC1, lP1, lPC1);
                        break;
                    case "Message Is Valid Final Frenzy Action 2":
                        int game8 = inScanner.nextInt();
                        String nickname8 = inScanner.nextLine();
                        List<Integer> directions = new LinkedList<>();
                        int size17 = inScanner.nextInt();
                        for(int i = 0; i < size17; i++)
                            directions.add(inScanner.nextInt());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction2(game8, nickname8, directions));
                        break;
                    case "Message Final Frenzy Action 2":
                        int game9 = inScanner.nextInt();
                        String nickname9 = inScanner.nextLine();
                        List<Integer> directions1 = new LinkedList<>();
                        int size18 = inScanner.nextInt();
                        for(int i = 0; i < size18; i++)
                            directions1.add(inScanner.nextInt());
                        server.messageFinalFrenzyAction2(game9, nickname9, directions1);
                        break;
                    case "Message Is Valid Final Frenzy Action 3":
                        int game10 = inScanner.nextInt();
                        String nickname10 = inScanner.nextLine();
                        List<Integer> list = new LinkedList<>();
                        int size19 = inScanner.nextInt();
                        for(int i = 0; i < size19; i++)
                            list.add(inScanner.nextInt());
                        String wCard = inScanner.nextLine();
                        String wSlot = inScanner.nextLine();
                        List<Colour> lC2 = new LinkedList<>();
                        int size20 = inScanner.nextInt();
                        for(int i = 0; i < size20; i++)
                            lC2.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP2 = new LinkedList<>();
                        int size21 = inScanner.nextInt();
                        for(int i = 0; i < size21; i++)
                            lP2.add(inScanner.nextLine());
                        List<String> lPC2 = new LinkedList<>();
                        int size22 = inScanner.nextInt();
                        for(int i = 0; i < size22; i++)
                            lPC2.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction3(game10, nickname10, list, wCard, wSlot, lC2, lP2, lPC2));
                        break;
                    case "Message Final Frenzy Action 3":
                        int game11 = inScanner.nextInt();
                        String nickname11 = inScanner.nextLine();
                        List<Integer> list1 = new LinkedList<>();
                        int size23 = inScanner.nextInt();
                        for(int i = 0; i < size23; i++)
                            list1.add(inScanner.nextInt());
                        String wCard1 = inScanner.nextLine();
                        List<Colour> lC3 = new LinkedList<>();
                        int size24 = inScanner.nextInt();
                        for(int i = 0; i < size24; i++)
                            lC3.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP3 = new LinkedList<>();
                        int size25 = inScanner.nextInt();
                        for(int i = 0; i < size25; i++)
                            lP3.add(inScanner.nextLine());
                        List<String> lPC3 = new LinkedList<>();
                        int size26 = inScanner.nextInt();
                        for(int i = 0; i < size26; i++)
                            lPC3.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction3(game11, nickname11, list1, wCard1, lC3, lP3, lPC3);
                        break;
                    case "Message Is Valid Final Frenzy Action 4":
                        int game12 = inScanner.nextInt();
                        String nickname12 = inScanner.nextLine();
                        List<Integer> list2 = new LinkedList<>();
                        int size27 = inScanner.nextInt();
                        for(int i = 0; i < size27; i++)
                            list2.add(inScanner.nextInt());
                        String wCard2 = inScanner.nextLine();
                        List<Integer> lI2 = new LinkedList<>();
                        int size28 = inScanner.nextInt();
                        for(int i = 0; i < size28; i++)
                            lI2.add(inScanner.nextInt());
                        List<String> lS5 = new LinkedList<>();
                        int size29 = inScanner.nextInt();
                        for(int i = 0; i < size29; i++)
                            lS5.add(inScanner.nextLine());
                        List<Colour> lC4 = new LinkedList<>();
                        int size30 = inScanner.nextInt();
                        for(int i = 0; i < size30; i++)
                            lC4.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP4 = new LinkedList<>();
                        int size31 = inScanner.nextInt();
                        for(int i = 0; i < size31; i++)
                            lP4.add(inScanner.nextLine());
                        List<String> lPC4 = new LinkedList<>();
                        int size32 = inScanner.nextInt();
                        for(int i = 0; i < size32; i++)
                            lPC4.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction4(game12, nickname12, list2, wCard2, lI2, lS5, lC4, lP4, lPC4));
                        break;
                    case "Message Final Frenzy Action 4":
                        int game13 = inScanner.nextInt();
                        String nickname13 = inScanner.nextLine();
                        List<Integer> list3 = new LinkedList<>();
                        int size33 = inScanner.nextInt();
                        for(int i = 0; i < size33; i++)
                            list3.add(inScanner.nextInt());
                        List<String> cards = new LinkedList<>();
                        int size34 = inScanner.nextInt();
                        for(int i = 0; i < size34; i++)
                            cards.add(inScanner.nextLine());
                        String wCard3 = inScanner.nextLine();
                        List<Integer> lI3 = new LinkedList<>();
                        int size35 = inScanner.nextInt();
                        for(int i = 0; i < size35; i++)
                            lI3.add(inScanner.nextInt());
                        List<String> lS6 = new LinkedList<>();
                        int size36 = inScanner.nextInt();
                        for(int i = 0; i < size36; i++)
                            lS6.add(inScanner.nextLine());
                        List<Colour> lC5 = new LinkedList<>();
                        int size37 = inScanner.nextInt();
                        for(int i = 0; i < size37; i++)
                            lC5.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP5 = new LinkedList<>();
                        int size38 = inScanner.nextInt();
                        for(int i = 0; i < size38; i++)
                            lP5.add(inScanner.nextLine());
                        List<String> lPC5 = new LinkedList<>();
                        int size39 = inScanner.nextInt();
                        for(int i = 0; i < size39; i++)
                            lPC5.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction4(game13, nickname13, list3, cards, wCard3, lI3, lS6, lC5, lP5, lPC5);
                        break;
                    case "Message Is Valid Final Frenzy Action 5":
                        int game14 = inScanner.nextInt();
                        String nickname14 = inScanner.nextLine();
                        List<Integer> list4 = new LinkedList<>();
                        int size40 = inScanner.nextInt();
                        for(int i = 0; i < size40; i++)
                            list4.add(inScanner.nextInt());
                        String wCard4 = inScanner.nextLine();
                        String wSlot1 = inScanner.nextLine();
                        List<Colour> lC6 = new LinkedList<>();
                        int size41 = inScanner.nextInt();
                        for(int i = 0; i < size41; i++)
                            lC6.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP6 = new LinkedList<>();
                        int size42 = inScanner.nextInt();
                        for(int i = 0; i < size42; i++)
                            lP6.add(inScanner.nextLine());
                        List<String> lPC6 = new LinkedList<>();
                        int size43 = inScanner.nextInt();
                        for(int i = 0; i < size43; i++)
                            lPC6.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction5(game14, nickname14, list4, wCard4, wSlot1, lC6, lP6, lPC6));
                        break;
                    case "Message Final Frenzy Action 5":
                        int game15 = inScanner.nextInt();
                        String nickname15 = inScanner.nextLine();
                        List<Integer> list5 = new LinkedList<>();
                        int size44 = inScanner.nextInt();
                        for(int i = 0; i < size44; i++)
                            list5.add(inScanner.nextInt());
                        String wCard5 = inScanner.nextLine();
                        List<Colour> lC7 = new LinkedList<>();
                        int size45 = inScanner.nextInt();
                        for(int i = 0; i < size45; i++)
                            lC7.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP7 = new LinkedList<>();
                        int size46 = inScanner.nextInt();
                        for(int i = 0; i < size46; i++)
                            lP7.add(inScanner.nextLine());
                        List<String> lPC7 = new LinkedList<>();
                        int size47 = inScanner.nextInt();
                        for(int i = 0; i < size47; i++)
                            lPC7.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction5(game15, nickname15, list5, wCard5, lC7, lP7, lPC7);
                        break;
                    case "Message Final Frenzy Turn Scoring":
                        server.messageFinalFrenzyTurnScoring(inScanner.nextInt());
                        break;
                    case "Message End Turn Final Frenzy":
                        server.messageEndTurnFinalFrenzy(inScanner.nextInt());
                        break;
                    case "Message Final Scoring":
                        server.messageFinalScoring(inScanner.nextInt());
                        break;
                    case "Message Get Players":
                        int game16 = inScanner.nextInt();
                        outPrinter.println(game16);
                        for(int i = 0; i < server.messageGetPlayers(game16).size(); i++)
                            outPrinter.println(server.messageGetPlayers(game16));
                        break;
                    case "Message Get Dead List":
                        int game17 = inScanner.nextInt();
                        outPrinter.println(server.messageGetDeadList(game17).size());
                        for(int i = 0; i < server.messageGetDeadList(game17).size(); i++)
                            outPrinter.println(server.messageGetDeadList(game17).get(i));
                        break;
                    case "Message Is Valid Discard Card For Spawn Point":
                        outPrinter.println(server.messageIsValidDiscardCardForSpawnPoint(inScanner.nextInt(), inScanner.nextLine(), inScanner.nextLine(), inScanner.nextLine()));
                        break;
                    case "Message Discard Card For Spawn Point":
                        server.messageDiscardCardForSpawnPoint(inScanner.nextInt(), inScanner.nextLine(), inScanner.nextLine(), inScanner.nextLine());
                        break;
                    case "Message Show Cards On Board":
                        outPrinter.println(server.messageShowCardsOnBoard(inScanner.nextInt()));
                        break;
                    case "Message Is Valid First Action Move":
                        int game18 = inScanner.nextInt();
                        String nickname16 = inScanner.nextLine();
                        int size48 = inScanner.nextInt();
                        List<Integer> directions2 = new LinkedList<>();
                        for(int i = 0; i < size48; i++)
                            directions2.add(inScanner.nextInt());
                        outPrinter.println(server.messageIsValidFirstActionMove(game18, nickname16, directions2));
                        break;
                    case "Message First Action Move":
                        int game19 = inScanner.nextInt();
                        String nickname17 = inScanner.nextLine();
                        int size49 = inScanner.nextInt();
                        List<Integer> directions3 = new LinkedList<>();
                        for(int i = 0; i < size49; i++)
                            directions3.add(inScanner.nextInt());
                        server.messageFirstActionMove(game19, nickname17, directions3);
                        break;
                    case "Message Is Valid Second Action Move":
                        int game20 = inScanner.nextInt();
                        String nickname18 = inScanner.nextLine();
                        int size50 = inScanner.nextInt();
                        List<Integer> directions4 = new LinkedList<>();
                        for(int i = 0; i < size50; i++)
                            directions4.add(inScanner.nextInt());
                        outPrinter.println(server.messageIsValidSecondActionMove(game20, nickname18, directions4));
                        break;
                    case "Message Second Action Move":
                        int game21 = inScanner.nextInt();
                        String nickname19 = inScanner.nextLine();
                        int size51 = inScanner.nextInt();
                        List<Integer> directions5 = new LinkedList<>();
                        for(int i = 0; i < size51; i++)
                            directions5.add(inScanner.nextInt());
                        server.messageSecondActionMove(game21, nickname19, directions5);
                        break;


                }
                if(exit)
                    break;
            }

            inScanner.close();
            outPrinter.close();
            socket.close();
        } catch (IOException | ClassNotFoundException | InterruptedException e ) {
            System.out.println("Socket Server Client Handler Exception");
        }
    }
}