package network;

import model.Colour;
import view.View;
import view.cli.CLISocket;

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

    public synchronized void run() {
        try {
            PrintWriter outPrinter = new PrintWriter(socket.getOutputStream(), true);
            Scanner inScanner = new Scanner(socket.getInputStream());
            ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
            boolean exit = false;

            while(!exit) {
                switch (inScanner.nextLine()) {
                    //SocketProcesses calls
                    case "Get Games":
                        outPrinter.println(server.getGames());
                        break;
                    case "Is A Suspended Identifier":
                        int gameIsASuspendedIdentifier = inScanner.nextInt();
                        inScanner.nextLine();
                        int identifierIsASuspendedIdentifier = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.isASuspendedIdentifier(gameIsASuspendedIdentifier, identifierIsASuspendedIdentifier));
                        break;
                    case "Set View":
                        int gameSetView = inScanner.nextInt();
                        inScanner.nextLine();
                        int identifierSetView = inScanner.nextInt();
                        inScanner.nextLine();
                        View viewSetView = (View) inObject.readObject();
                        server.setView(gameSetView, identifierSetView, viewSetView);
                        break;
                    case "Get Type":
                        int typeGetType = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.getType(typeGetType));
                        break;
                    case "Manage Reconnection":
                        int gameManageReconnection = inScanner.nextInt();
                        inScanner.nextLine();
                        int identifierManageReconnection = inScanner.nextInt();
                        inScanner.nextLine();
                        server.manageReconnection(gameManageReconnection, identifierManageReconnection);
                        break;
                    case "Too Many":
                        int gameTooMany = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.tooMany(gameTooMany));
                        break;
                    case "Set Game":
                        int gameSetGame = inScanner.nextInt();
                        inScanner.nextLine();
                        server.setGame(gameSetGame);
                        break;
                    case "Receive Identifier":
                        int gameReceiveIdentifier = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.receiveIdentifier(gameReceiveIdentifier));
                        break;
                    case "Merge Group":
                        int gameMergeGroup = inScanner.nextInt();
                        inScanner.nextLine();
                        server.mergeGroup(gameMergeGroup);
                        break;
                    case "Can Start":
                        int gameCanStart = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.canStart(gameCanStart));
                        break;
                    case "Stop Game":
                        int gameStopGame = inScanner.nextInt();
                        inScanner.nextLine();
                        String stopGame = String.valueOf(server.stopGame(gameStopGame));
                        outPrinter.println(stopGame);
                        break;
                    case "Is My Turn":
                        int gameIsMyTurn = inScanner.nextInt();
                        inScanner.nextLine();
                        int identifierIsMyTurn = inScanner.nextInt();
                        inScanner.nextLine();
                        String isMyTurn = String.valueOf(server.isMyTurn(gameIsMyTurn, identifierIsMyTurn));
                        outPrinter.println(isMyTurn);
                        break;
                    case "Is Not Final Frenzy":
                        int gameIsNotFinalFrenzy = inScanner.nextInt();
                        inScanner.nextLine();
                        String isNotFinalFrenzy = String.valueOf(server.isNotFinalFrenzy(gameIsNotFinalFrenzy));
                        outPrinter.println(isNotFinalFrenzy);
                        break;
                    case "Set Final Turn":
                        int gameSetFinalTurn = inScanner.nextInt();
                        inScanner.nextLine();
                        int identifierSetFinalTurn = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameFinalTurn = inScanner.nextLine();
                        server.setFinalTurn(gameSetFinalTurn, identifierSetFinalTurn, nicknameFinalTurn);
                        break;
                    case "Game Is Finished":
                        int gameGameIsFinished = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.gameIsFinished(gameGameIsFinished));
                        break;
                    case "Manage Disconnection":
                        int gameManageDisconnection = inScanner.nextInt();
                        inScanner.nextLine();
                        int identifierManageDisconnection = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameManageDisconnection = inScanner.nextLine();
                        server.manageDisconnection(gameManageDisconnection, identifierManageDisconnection, nicknameManageDisconnection);
                        break;
                    case "Finish Turn":
                        int gameFinishTurn = inScanner.nextInt();
                        inScanner.nextLine();
                        server.finishTurn(gameFinishTurn);
                        break;

                    //CLISocket calls
                    case "Get Suspended Name":
                        int gameGetSuspendedName = inScanner.nextInt();
                        inScanner.nextLine();
                        int identifierGetSuspendedName = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.getSuspendedName(gameGetSuspendedName, identifierGetSuspendedName));
                        break;
                    case "Get Suspended Colour":
                        int gameGetSuspendedColour = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameGetSuspendedColour = inScanner.nextLine();
                        outPrinter.println(server.getSuspendedColour(gameGetSuspendedColour, nicknameGetSuspendedColour));
                        break;
                    case "Message Game Is Not Started":
                        int gameMessageGameIsNotStarted = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.messageGameIsNotStarted(gameMessageGameIsNotStarted));
                        break;
                    case "Set Nickname":
                        int gameSetNickname = inScanner.nextInt();
                        inScanner.nextLine();
                        int identifierSetNickname = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameSetNickname = inScanner.nextLine();
                        server.setNickName(gameSetNickname, identifierSetNickname, nicknameSetNickname);
                        break;
                    case "Message Game Start":
                        int gameMessageGameStart = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageGameStart = inScanner.nextLine();
                        Colour colourMessageGameStart = Colour.valueOf(inScanner.nextLine());
                        server.messageGameStart(gameMessageGameStart, nicknameMessageGameStart, colourMessageGameStart);
                        break;
                    case "Message Is Valid Receive Type":
                        int gameMessageIsValidReceiveType = inScanner.nextInt();
                        inScanner.nextLine();
                        int typeMessageIsValidReceiveType = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.messageIsValidReceiveType(gameMessageIsValidReceiveType, typeMessageIsValidReceiveType));
                        break;
                    case "Message Receive Type":
                        int gameMessageReceiveType = inScanner.nextInt();
                        inScanner.nextLine();
                        int typeMessageReceiveType = inScanner.nextInt();
                        inScanner.nextLine();
                        server.messageReceiveType(gameMessageReceiveType, typeMessageReceiveType);
                        break;
                    case "Message Is Valid Add Player":
                        int gameMessageIsValidAddPlayer = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageIsValidAddPlayer = inScanner.nextLine();
                        Colour colourMessageIsValidAddPlayer = Colour.valueOf(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidAddPlayer(gameMessageIsValidAddPlayer, nicknameMessageIsValidAddPlayer, colourMessageIsValidAddPlayer));
                        break;
                    case "Message Add Player":
                        int gameMessageAddPlayer = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageAddPlayer = inScanner.nextLine();
                        Colour colourMessageAddPlayer = Colour.valueOf(inScanner.nextLine());
                        server.messageAddPlayer(gameMessageAddPlayer, nicknameMessageAddPlayer, colourMessageAddPlayer);
                        break;
                    case "Message Give Two PU Card":
                        int gameMessageGiveTwoPUCard = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageGiveTwoPUCard = inScanner.nextLine();
                        server.messageGiveTwoPUCard(gameMessageGiveTwoPUCard, nicknameMessageGiveTwoPUCard);
                        break;
                    case "Message Get Initial PowerUp Card":
                        int gameMessageGetInitialPowerUpCard = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageGetInitialPowerUpCard = inScanner.nextLine();
                        outPrinter.println(server.messageGetPowerUpCard(gameMessageGetInitialPowerUpCard, nicknameMessageGetInitialPowerUpCard).get(0));
                        outPrinter.println(server.messageGetPowerUpCard(gameMessageGetInitialPowerUpCard, nicknameMessageGetInitialPowerUpCard).get(1));
                        break;
                    case "Message Get Initial PowerUp Card Colour":
                        int gameMessageGetInitialPowerUpCardColour = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageGetInitialPowerUpCardColour = inScanner.nextLine();
                        outPrinter.println(server.messageGetPowerUpCardColour(gameMessageGetInitialPowerUpCardColour, nicknameMessageGetInitialPowerUpCardColour).get(0));
                        outPrinter.println(server.messageGetPowerUpCardColour(gameMessageGetInitialPowerUpCardColour, nicknameMessageGetInitialPowerUpCardColour).get(1));
                        break;
                    case "Message Is Valid Pick And Discard":
                        int gameMessageIsValidPickAndDiscard = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageIsValidPickAndDiscard = inScanner.nextLine();
                        String nameIsValidCardToKeep = inScanner.nextLine();
                        String colourIsValidCardToKeep = inScanner.nextLine();
                        outPrinter.println(server.messageIsValidPickAndDiscard(gameMessageIsValidPickAndDiscard, nicknameMessageIsValidPickAndDiscard, nameIsValidCardToKeep, colourIsValidCardToKeep));
                        break;
                    case "Message Pick And Discard":
                        int gameMessagePickAndDiscard = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessagePickAndDiscard = inScanner.nextLine();
                        String nameCardToKeep = inScanner.nextLine();
                        String colourCardToKeep = inScanner.nextLine();
                        server.messagePickAndDiscardCard(gameMessagePickAndDiscard, nicknameMessagePickAndDiscard, nameCardToKeep, colourCardToKeep);
                        break;
                    case "Message Get PowerUp Card Name And Colour":
                        int game = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname = inScanner.nextLine();
                        int size = server.messageGetPowerUpCard(game, nickname).size();
                        inScanner.nextLine();
                        outPrinter.println(size);
                        for(int i = 0; i < size; i++) {
                            outPrinter.println(server.messageGetPowerUpCard(game, nickname).get(i));
                            outPrinter.println(server.messageGetPowerUpCardColour(game, nickname).get(i));
                        }
                        break;
                    case "Message Get Description PUC":
                        int gameDesc = inScanner.nextInt();
                        inScanner.nextLine();
                        String pUC = inScanner.nextLine();
                        String pUCCol = inScanner.nextLine();
                        String nickDesc = inScanner.nextLine();
                        outPrinter.println(server.messageGetDescriptionPUC(gameDesc, pUC, pUCCol, nickDesc));
                        break;
                    case "Message Is Valid Use PowerUp Card":
                        int game1 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname1 = inScanner.nextLine();
                        String namePC = inScanner.nextLine();
                        String colourPC = inScanner.nextLine();
                        int size1 = inScanner.nextInt();
                        inScanner.nextLine();
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
                        inScanner.nextLine();
                        String nickname2 = inScanner.nextLine();
                        String namePC1 = inScanner.nextLine();
                        String colourPC1 = inScanner.nextLine();
                        int size2 = inScanner.nextInt();
                        inScanner.nextLine();
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
                        int gameMessageCheckYourStatus = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageCheckYourStatus = inScanner.nextLine();
                        String messageCheckYourStatus = server.messageCheckYourStatus(gameMessageCheckYourStatus, nicknameMessageCheckYourStatus);
                        outPrinter.println(messageCheckYourStatus);
                        break;
                    case "Message Get Weapon Card Unloaded":
                        int game3 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname3 = inScanner.nextLine();
                        int size3 = server.messageGetWeaponCardUnloaded(game3, nickname3).size();
                        outPrinter.println(size3);
                        for(int i = 0; i < size3; i++)
                            outPrinter.println(server.messageGetWeaponCardUnloaded(game3, nickname3).get(i));
                        break;
                    case "Message Is Valid Reload":
                        int gameMessageIsValidReload = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageIsValidReload = inScanner.nextLine();
                        String selectionMessageIsValidReload = inScanner.nextLine();
                        outPrinter.println(server.messageIsValidReload(gameMessageIsValidReload, nicknameMessageIsValidReload, selectionMessageIsValidReload));
                        break;
                    case "Message Reload":
                        int gameMessageReload = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageReload = inScanner.nextLine();
                        String selectionMessageReload = inScanner.nextLine();
                        int endMessageReload = inScanner.nextInt();
                        inScanner.nextLine();
                        server.messageReload(gameMessageReload, nicknameMessageReload, selectionMessageReload, endMessageReload);
                        break;
                    case "Message Is Valid Scoring":
                        int gameMessageIsValidScoring = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.messageIsValidScoring(gameMessageIsValidScoring));
                        break;
                    case "Message Scoring":
                        int gameMessageScoring = inScanner.nextInt();
                        inScanner.nextLine();
                        server.messageScoring(gameMessageScoring);
                        break;
                    case "Message Is Valid To Replace":
                        int gameMessageIsValidToReplace = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.messageIsValidToReplace(gameMessageIsValidToReplace));
                        break;
                    case "Message Replace":
                        int gameMessageReplace = inScanner.nextInt();
                        inScanner.nextLine();
                        server.messageReplace(gameMessageReplace);
                        break;
                    case "Message Get Player Weapon Card":
                        int game4 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname4 = inScanner.nextLine();
                        int size4 = server.messageGetWeaponCardUnloaded(game4, nickname4).size();
                        outPrinter.println(size4);
                        for(int i = 0; i < size4; i++)
                            outPrinter.println(server.messageGetPlayerWeaponCard(game4, nickname4).get(i));
                        break;
                    case "Message Is Valid Final Frenzy Action":
                        int game5 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname5 = inScanner.nextLine();
                        int size5 = inScanner.nextInt();
                        List<String> lS2 = new LinkedList<>();
                        for(int i = 0; i < size5; i++)
                            lS2.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction(game5, nickname5, lS2));
                        break;
                    case "Message Is Valid Final Frenzy Action 1":
                        int game6 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname6 = inScanner.nextLine();
                        int direction = inScanner.nextInt();
                        inScanner.nextLine();
                        String wC = inScanner.nextLine();
                        List<Integer> lI = new LinkedList<>();
                        int size6 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size6; i++)
                            lI.add(inScanner.nextInt());
                        List<String> lS3 = new LinkedList<>();
                        int size7 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size7; i++)
                            lS3.add(inScanner.nextLine());
                        List<Colour> lC = new LinkedList<>();
                        int size8 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size8; i++)
                            lC.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP = new LinkedList<>();
                        int size9 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size9; i++)
                            lP.add(inScanner.nextLine());
                        List<String> lPC = new LinkedList<>();
                        int size10 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size10; i++)
                            lPC.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction1(game6, nickname6, direction, wC, lI, lS3, lC, lP, lPC));
                        break;
                    case "Message Final Frenzy Action 1":
                        int game7 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname7 = inScanner.nextLine();
                        int direction1 = inScanner.nextInt();
                        inScanner.nextLine();
                        List<String> lW = new LinkedList<>();
                        int size11 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size11; i++)
                            lW.add(inScanner.nextLine());
                        String wC1 = inScanner.nextLine();
                        List<Integer> lI1 = new LinkedList<>();
                        int size12 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size12; i++)
                            lI1.add(inScanner.nextInt());
                        List<String> lS4 = new LinkedList<>();
                        int size13 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size13; i++)
                            lS4.add(inScanner.nextLine());
                        List<Colour> lC1 = new LinkedList<>();
                        int size14 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size14; i++)
                            lC1.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP1 = new LinkedList<>();
                        int size15 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size15; i++)
                            lP1.add(inScanner.nextLine());
                        List<String> lPC1 = new LinkedList<>();
                        int size16 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size16; i++)
                            lPC1.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction1(game7, nickname7, direction1, lW, wC1, lI1, lS4, lC1, lP1, lPC1);
                        break;
                    case "Message Is Valid Final Frenzy Action 2":
                        int game8 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname8 = inScanner.nextLine();
                        List<Integer> directions = new LinkedList<>();
                        int size17 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size17; i++)
                            directions.add(inScanner.nextInt());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction2(game8, nickname8, directions));
                        break;
                    case "Message Final Frenzy Action 2":
                        int game9 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname9 = inScanner.nextLine();
                        List<Integer> directions1 = new LinkedList<>();
                        int size18 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size18; i++)
                            directions1.add(inScanner.nextInt());
                        server.messageFinalFrenzyAction2(game9, nickname9, directions1);
                        break;
                    case "Message Is Valid Final Frenzy Action 3":
                        int game10 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname10 = inScanner.nextLine();
                        List<Integer> list = new LinkedList<>();
                        int size19 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size19; i++)
                            list.add(inScanner.nextInt());
                        String wCard = inScanner.nextLine();
                        String wSlot = inScanner.nextLine();
                        List<Colour> lC2 = new LinkedList<>();
                        int size20 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size20; i++)
                            lC2.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP2 = new LinkedList<>();
                        int size21 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size21; i++)
                            lP2.add(inScanner.nextLine());
                        List<String> lPC2 = new LinkedList<>();
                        int size22 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size22; i++)
                            lPC2.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction3(game10, nickname10, list, wCard, wSlot, lC2, lP2, lPC2));
                        break;
                    case "Message Final Frenzy Action 3":
                        int game11 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname11 = inScanner.nextLine();
                        List<Integer> list1 = new LinkedList<>();
                        int size23 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size23; i++)
                            list1.add(inScanner.nextInt());
                        String wCard1 = inScanner.nextLine();
                        List<Colour> lC3 = new LinkedList<>();
                        int size24 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size24; i++)
                            lC3.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP3 = new LinkedList<>();
                        int size25 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size25; i++)
                            lP3.add(inScanner.nextLine());
                        List<String> lPC3 = new LinkedList<>();
                        int size26 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size26; i++)
                            lPC3.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction3(game11, nickname11, list1, wCard1, lC3, lP3, lPC3);
                        break;
                    case "Message Is Valid Final Frenzy Action 4":
                        int game12 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname12 = inScanner.nextLine();
                        List<Integer> list2 = new LinkedList<>();
                        int size27 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size27; i++)
                            list2.add(inScanner.nextInt());
                        String wCard2 = inScanner.nextLine();
                        List<Integer> lI2 = new LinkedList<>();
                        int size28 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size28; i++)
                            lI2.add(inScanner.nextInt());
                        List<String> lS5 = new LinkedList<>();
                        int size29 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size29; i++)
                            lS5.add(inScanner.nextLine());
                        List<Colour> lC4 = new LinkedList<>();
                        int size30 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size30; i++)
                            lC4.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP4 = new LinkedList<>();
                        int size31 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size31; i++)
                            lP4.add(inScanner.nextLine());
                        List<String> lPC4 = new LinkedList<>();
                        int size32 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size32; i++)
                            lPC4.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction4(game12, nickname12, list2, wCard2, lI2, lS5, lC4, lP4, lPC4));
                        break;
                    case "Message Final Frenzy Action 4":
                        int game13 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname13 = inScanner.nextLine();
                        List<Integer> list3 = new LinkedList<>();
                        int size33 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size33; i++)
                            list3.add(inScanner.nextInt());
                        List<String> cards = new LinkedList<>();
                        int size34 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size34; i++)
                            cards.add(inScanner.nextLine());
                        String wCard3 = inScanner.nextLine();
                        List<Integer> lI3 = new LinkedList<>();
                        int size35 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size35; i++)
                            lI3.add(inScanner.nextInt());
                        List<String> lS6 = new LinkedList<>();
                        int size36 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size36; i++)
                            lS6.add(inScanner.nextLine());
                        List<Colour> lC5 = new LinkedList<>();
                        int size37 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size37; i++)
                            lC5.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP5 = new LinkedList<>();
                        int size38 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size38; i++)
                            lP5.add(inScanner.nextLine());
                        List<String> lPC5 = new LinkedList<>();
                        int size39 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size39; i++)
                            lPC5.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction4(game13, nickname13, list3, cards, wCard3, lI3, lS6, lC5, lP5, lPC5);
                        break;
                    case "Message Is Valid Final Frenzy Action 5":
                        int game14 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname14 = inScanner.nextLine();
                        List<Integer> list4 = new LinkedList<>();
                        int size40 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size40; i++)
                            list4.add(inScanner.nextInt());
                        String wCard4 = inScanner.nextLine();
                        String wSlot1 = inScanner.nextLine();
                        List<Colour> lC6 = new LinkedList<>();
                        int size41 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size41; i++)
                            lC6.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP6 = new LinkedList<>();
                        int size42 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size42; i++)
                            lP6.add(inScanner.nextLine());
                        List<String> lPC6 = new LinkedList<>();
                        int size43 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size43; i++)
                            lPC6.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction5(game14, nickname14, list4, wCard4, wSlot1, lC6, lP6, lPC6));
                        break;
                    case "Message Final Frenzy Action 5":
                        int game15 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname15 = inScanner.nextLine();
                        List<Integer> list5 = new LinkedList<>();
                        int size44 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size44; i++)
                            list5.add(inScanner.nextInt());
                        String wCard5 = inScanner.nextLine();
                        List<Colour> lC7 = new LinkedList<>();
                        int size45 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size45; i++)
                            lC7.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP7 = new LinkedList<>();
                        int size46 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size46; i++)
                            lP7.add(inScanner.nextLine());
                        List<String> lPC7 = new LinkedList<>();
                        int size47 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size47; i++)
                            lPC7.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction5(game15, nickname15, list5, wCard5, lC7, lP7, lPC7);
                        break;
                    case "Message Final Frenzy Turn Scoring":
                        int gameMessageFFTurnScoring = inScanner.nextInt();
                        inScanner.nextLine();
                        server.messageFinalFrenzyTurnScoring(gameMessageFFTurnScoring);
                        break;
                    case "Message End Turn Final Frenzy":
                        int gameMessageEndTurnFF = inScanner.nextInt();
                        inScanner.nextLine();
                        server.messageEndTurnFinalFrenzy(gameMessageEndTurnFF);
                        break;
                    case "Message Final Scoring":
                        int gameMessageFinalScoring = inScanner.nextInt();
                        inScanner.nextLine();
                        server.messageFinalScoring(gameMessageFinalScoring);
                        break;
                    case "Message Get Players":
                        int game16 = inScanner.nextInt();
                        inScanner.nextLine();
                        int sizeMessageGetPlayers = server.messageGetPlayers(game16).size();
                        outPrinter.println(sizeMessageGetPlayers);
                        for(int i = 0; i < sizeMessageGetPlayers; i++)
                            outPrinter.println(server.messageGetPlayers(game16));
                        break;
                    case "Message Get Dead List":
                        int game17 = inScanner.nextInt();
                        inScanner.nextLine();
                        int sizeMessageGetDeadList = server.messageGetDeadList(game17).size();
                        outPrinter.println(sizeMessageGetDeadList);
                        for(int i = 0; i < sizeMessageGetDeadList; i++)
                            outPrinter.println(server.messageGetDeadList(game17).get(i));
                        break;
                    case "Message Is Valid Discard Card For Spawn Point":
                        int gameMessageIsValidDiscardCardForSpawnPoint = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageIsValidDiscardCardForSpawnPoint = inScanner.nextLine();
                        String cardMessageIsValidDiscardCardForSpawnPoint = inScanner.nextLine();
                        String colourMessageIsValidDiscardCardForSpawnPoint = inScanner.nextLine();
                        outPrinter.println(server.messageIsValidDiscardCardForSpawnPoint(gameMessageIsValidDiscardCardForSpawnPoint, nicknameMessageIsValidDiscardCardForSpawnPoint, cardMessageIsValidDiscardCardForSpawnPoint, colourMessageIsValidDiscardCardForSpawnPoint));
                        break;
                    case "Message Discard Card For Spawn Point":
                        int gameMessageDiscardCardForSpawnPoint = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageDiscardCardForSpawnPoint = inScanner.nextLine();
                        String cardMessageDiscardCardForSpawnPoint = inScanner.nextLine();
                        String colourMessageDiscardCardForSpawnPoint = inScanner.nextLine();
                        server.messageDiscardCardForSpawnPoint(gameMessageDiscardCardForSpawnPoint, nicknameMessageDiscardCardForSpawnPoint, cardMessageDiscardCardForSpawnPoint, colourMessageDiscardCardForSpawnPoint);
                        break;
                    case "Message Show Cards On Board":
                        int gameMessageShowCardsOnBoard = inScanner.nextInt();
                        inScanner.nextLine();
                        String messageShowCardsOnBoard = server.messageShowCardsOnBoard(gameMessageShowCardsOnBoard);
                        outPrinter.println(messageShowCardsOnBoard);
                        break;
                    case "Message Is Valid First Action Move":
                        int game18 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname16 = inScanner.nextLine();
                        int size48 = inScanner.nextInt();
                        inScanner.nextLine();
                        List<Integer> directions2 = new LinkedList<>();
                        for(int i = 0; i < size48; i++)
                            directions2.add(Integer.parseInt(inScanner.nextLine()));
                        outPrinter.println(server.messageIsValidFirstActionMove(game18, nickname16, directions2));
                        break;
                    case "Message First Action Move":
                        int game19 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname17 = inScanner.nextLine();
                        int size49 = inScanner.nextInt();
                        inScanner.nextLine();
                        List<Integer> directions3 = new LinkedList<>();
                        for(int i = 0; i < size49; i++)
                            directions3.add(inScanner.nextInt());
                        server.messageFirstActionMove(game19, nickname17, directions3);
                        break;
                    case "Message Is Valid Second Action Move":
                        int game20 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname18 = inScanner.nextLine();
                        int size50 = inScanner.nextInt();
                        inScanner.nextLine();
                        List<Integer> directions4 = new LinkedList<>();
                        for(int i = 0; i < size50; i++)
                            directions4.add(inScanner.nextInt());
                        outPrinter.println(server.messageIsValidSecondActionMove(game20, nickname18, directions4));
                        break;
                    case "Message Second Action Move":
                        int game21 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname19 = inScanner.nextLine();
                        int size51 = inScanner.nextInt();
                        inScanner.nextLine();
                        List<Integer> directions5 = new LinkedList<>();
                        for(int i = 0; i < size51; i++)
                            directions5.add(inScanner.nextInt());
                        server.messageSecondActionMove(game21, nickname19, directions5);
                        break;
                    case "Message Check Weapon Slot Contents":
                        int game22 = inScanner.nextInt();
                        inScanner.nextLine();
                        int n = inScanner.nextInt();
                        inScanner.nextLine();
                        int sizeMessageCheckWeaponSlotContents = server.messageCheckWeaponSlotContents(game22, n).size();
                        outPrinter.println(sizeMessageCheckWeaponSlotContents);
                        for(int i = 0; i < sizeMessageCheckWeaponSlotContents; i++)
                            outPrinter.println(server.messageCheckWeaponSlotContents(game22, n).get(i));
                        break;
                    case "Message Is Valid First Action Grab":
                        int game23 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname20 = inScanner.nextLine();
                        List<Integer> list6 = new LinkedList<>();
                        int size52 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size52; i++)
                            list6.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard6 = inScanner.nextLine();
                        String wSlot2 = inScanner.nextLine();
                        List<Colour> lC8 = new LinkedList<>();
                        int size53 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size53; i++)
                            lC8.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP8 = new LinkedList<>();
                        int size54 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size54; i++)
                            lP8.add(inScanner.nextLine());
                        List<String> lPC8 = new LinkedList<>();
                        int size55 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size55; i++)
                            lPC8.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFirstActionGrab(game23, nickname20, list6, wCard6, wSlot2, lC8, lP8, lPC8));
                        break;
                    case "Message First Action Grab":
                        int game24 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname21 = inScanner.nextLine();
                        List<Integer> list7 = new LinkedList<>();
                        int size56 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size56; i++)
                            list7.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard7 = inScanner.nextLine();
                        List<Colour> lC9 = new LinkedList<>();
                        int size57 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size57; i++)
                            lC9.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP9 = new LinkedList<>();
                        int size58 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size58; i++)
                            lP9.add(inScanner.nextLine());
                        List<String> lPC9 = new LinkedList<>();
                        int size59 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size59; i++)
                            lPC9.add(inScanner.nextLine());
                        server.messageFirstActionGrab(game24, nickname21, list7, wCard7, lC9, lP9, lPC9);
                        break;
                    case "Message Is Discard":
                        int gameMessageIsDiscard = inScanner.nextInt();
                        inScanner.nextLine();
                        outPrinter.println(server.messageIsDiscard(gameMessageIsDiscard));
                        break;
                    case "Message Discard Weapon Card":
                        int gameMessageDiscardWeaponCard = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageDiscardWeaponCard = inScanner.nextLine();
                        String slotMessageDiscardWeaponCard = inScanner.nextLine();
                        String cardMessageDiscardWeaponCard = inScanner.nextLine();
                        server.messageDiscardWeaponCard(gameMessageDiscardWeaponCard, nicknameMessageDiscardWeaponCard, slotMessageDiscardWeaponCard, cardMessageDiscardWeaponCard);
                        break;
                    case "Message Is Valid Second Action Grab":
                        int game25 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname22 = inScanner.nextLine();
                        List<Integer> list8 = new LinkedList<>();
                        int size60 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size60; i++)
                            list8.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard8 = inScanner.nextLine();
                        String wSlot3 = inScanner.nextLine();
                        List<Colour> lC10 = new LinkedList<>();
                        int size61 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size61; i++)
                            lC10.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP10 = new LinkedList<>();
                        int size62 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size62; i++)
                            lP10.add(inScanner.nextLine());
                        List<String> lPC10 = new LinkedList<>();
                        int size63 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size63; i++)
                            lPC10.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidSecondActionGrab(game25, nickname22, list8, wCard8, wSlot3, lC10, lP10, lPC10));
                        break;
                    case "Message Second Action Grab":
                        int game26 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname23 = inScanner.nextLine();
                        List<Integer> list9 = new LinkedList<>();
                        int size64 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size64; i++)
                            list9.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard9 = inScanner.nextLine();
                        List<Colour> lC11 = new LinkedList<>();
                        int size65 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size65; i++)
                            lC11.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP11 = new LinkedList<>();
                        int size66 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size66; i++)
                            lP11.add(inScanner.nextLine());
                        List<String> lPC11 = new LinkedList<>();
                        int size67 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size67; i++)
                            lPC11.add(inScanner.nextLine());
                        server.messageSecondActionGrab(game26, nickname23, list9, wCard9, lC11, lP11, lPC11);
                        break;
                    case "Message Get Weapon Card Loaded":
                        int game27 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname24 = inScanner.nextLine();
                        outPrinter.println(server.messageGetWeaponCardLoaded(game27, nickname24).size());
                        for(int i = 0; i < server.messageGetWeaponCardLoaded(game27, nickname24).size(); i++)
                            outPrinter.println(server.messageGetWeaponCardLoaded(game27, nickname24).get(i));
                        break;
                    case "Message Is Valid Card":
                        int gameMessageIsValidCard = inScanner.nextInt();
                        inScanner.nextLine();
                        String nicknameMessageIsValidCard = inScanner.nextLine();
                        String cardMessageIsValidCard = inScanner.nextLine();
                        outPrinter.println(server.messageIsValidCard(gameMessageIsValidCard, nicknameMessageIsValidCard, cardMessageIsValidCard));
                        break;
                    case "Message Get Reload Cost":
                        int gameMessageGetReloadCost = inScanner.nextInt();
                        inScanner.nextLine();
                        String selectionMessageGetReloadCost = inScanner.nextLine();
                        String nicknameMessageGetReloadCost = inScanner.nextLine();
                        outPrinter.println(server.messageGetReloadCost(gameMessageGetReloadCost, selectionMessageGetReloadCost, nicknameMessageGetReloadCost));
                        break;
                    case "Message Get Description WC":
                        int gameMessageGetDescriptionWC = inScanner.nextInt();
                        inScanner.nextLine();
                        String selectionMessageGetDescriptionWC = inScanner.nextLine();
                        String nicknameMessageGetDescriptionWC = inScanner.nextLine();
                        outPrinter.println(server.messageGetDescriptionWC(gameMessageGetDescriptionWC, selectionMessageGetDescriptionWC, nicknameMessageGetDescriptionWC));
                        break;
                    case "Message Is Valid First Action Shoot":
                        int game28 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname25 = inScanner.nextLine();
                        String weapon = inScanner.nextLine();
                        List<Integer> lI11 = new LinkedList<>();
                        int size68 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size68; i++)
                            lI11.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS11 = new LinkedList<>();
                        int size69 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size69; i++)
                            lS11.add(inScanner.nextLine());
                        int adrenaline = inScanner.nextInt();
                        inScanner.nextLine();
                        List<Colour> lC12 = new LinkedList<>();
                        int size70 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size70; i++)
                            lC12.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP12 = new LinkedList<>();
                        int size71 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size71; i++)
                            lP12.add(inScanner.nextLine());
                        List<String> lPC12 = new LinkedList<>();
                        int size72 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size72; i++)
                            lPC12.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFirstActionShoot(game28, nickname25, weapon, lI11, lS11, adrenaline, lC12, lP12, lPC12));
                        break;
                    case "Message Is Valid Second Action Shoot":
                        int game29 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname26 = inScanner.nextLine();
                        String weapon1 = inScanner.nextLine();
                        List<Integer> lI12 = new LinkedList<>();
                        int size73 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size73; i++)
                            lI12.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS12 = new LinkedList<>();
                        int size74 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size74; i++)
                            lS12.add(inScanner.nextLine());
                        int adrenaline1 = inScanner.nextInt();
                        inScanner.nextLine();
                        List<Colour> lC13 = new LinkedList<>();
                        int size75 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size75; i++)
                            lC13.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP13 = new LinkedList<>();
                        int size76 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size76; i++)
                            lP13.add(inScanner.nextLine());
                        List<String> lPC13 = new LinkedList<>();
                        int size77 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size77; i++)
                            lPC13.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidSecondActionShoot(game29, nickname26, weapon1, lI12, lS12, adrenaline1, lC13, lP13, lPC13));
                        break;
                    case "Message First Action Shoot":
                        int game30 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname27 = inScanner.nextLine();
                        String weapon2 = inScanner.nextLine();
                        List<Integer> lI13 = new LinkedList<>();
                        int size78 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size78; i++)
                            lI13.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS13 = new LinkedList<>();
                        int size79 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size79; i++)
                            lS13.add(inScanner.nextLine());
                        int adrenaline2 = inScanner.nextInt();
                        inScanner.nextLine();
                        List<Colour> lC14 = new LinkedList<>();
                        int size80 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size80; i++)
                            lC14.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP14 = new LinkedList<>();
                        int size81 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size81; i++)
                            lP14.add(inScanner.nextLine());
                        List<String> lPC14 = new LinkedList<>();
                        int size82 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size82; i++)
                            lPC14.add(inScanner.nextLine());
                        server.messageFirstActionShoot(game30, nickname27, weapon2, lI13, lS13, adrenaline2, lC14, lP14, lPC14);
                        break;
                    case "Message Second Action Shoot":
                        int game31 = inScanner.nextInt();
                        inScanner.nextLine();
                        String nickname28 = inScanner.nextLine();
                        String weapon3 = inScanner.nextLine();
                        List<Integer> lI14 = new LinkedList<>();
                        int size83 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size83; i++)
                            lI14.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS14 = new LinkedList<>();
                        int size84 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size84; i++)
                            lS14.add(inScanner.nextLine());
                        int adrenaline3 = inScanner.nextInt();
                        inScanner.nextLine();
                        List<Colour> lC15 = new LinkedList<>();
                        int size85 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size85; i++)
                            lC15.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP15 = new LinkedList<>();
                        int size86 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size86; i++)
                            lP15.add(inScanner.nextLine());
                        List<String> lPC15 = new LinkedList<>();
                        int size87 = inScanner.nextInt();
                        inScanner.nextLine();
                        for(int i = 0; i < size87; i++)
                            lPC15.add(inScanner.nextLine());
                        server.messageSecondActionShoot(game31, nickname28, weapon3, lI14, lS14, adrenaline3, lC15, lP15, lPC15);
                        break;

                    //Notify calls
                    case "Notify Player Size":
                        int gameNotifyPlayerSize = Integer.parseInt(inScanner.nextLine());
                        int idNotifyPlayerSize = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.notifyPlayerSize(gameNotifyPlayerSize, idNotifyPlayerSize));
                        break;
                    case "Get Notify Player":
                        int gameGetNotifyPlayer = Integer.parseInt(inScanner.nextLine());
                        int idGetNotifyPlayer = Integer.parseInt(inScanner.nextLine());
                        List<List<String>> notifyPlayerList = server.getNotifyPlayer(gameGetNotifyPlayer, idGetNotifyPlayer);
                        outPrinter.println(notifyPlayerList.get(0).get(0));
                        outPrinter.println(notifyPlayerList.get(0).get(2));
                        outPrinter.println(notifyPlayerList.get(0).get(1));
                        break;
                    case "Notify Score Size":
                        int gameNotifyScoreSize = Integer.parseInt(inScanner.nextLine());
                        int idNotifyScoreSize = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.notifyScoreSize(gameNotifyScoreSize, idNotifyScoreSize));
                        break;
                    case "Get Notify Score":
                        int gameGetNotifyScore = Integer.parseInt(inScanner.nextLine());
                        int idGetNotifyScore = Integer.parseInt(inScanner.nextLine());
                        List<List<String>> notifyScoreList = server.getNotifyScore(gameGetNotifyScore, idGetNotifyScore);
                        outPrinter.println(notifyScoreList.get(0).get(0));
                        outPrinter.println(notifyScoreList.get(0).get(1));
                        break;
                    case "Notify Position Size":
                        int gameNotifyPositionSize = Integer.parseInt(inScanner.nextLine());
                        int idNotifyPositionSize = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.notifyPositionSize(gameNotifyPositionSize, idNotifyPositionSize));
                        break;
                    case "Get Notify Position":
                        int gameGetNotifyPosition = Integer.parseInt(inScanner.nextLine());
                        int idGetNotifyPosition = Integer.parseInt(inScanner.nextLine());
                        List<List<String>> notifyPositionList = server.getNotifyPosition(gameGetNotifyPosition, idGetNotifyPosition);
                        outPrinter.println(notifyPositionList.get(0).get(0));
                        outPrinter.println(notifyPositionList.get(0).get(1));
                        outPrinter.println(notifyPositionList.get(0).get(2));
                        break;
                    case "Notify Mark Size":
                        int gameNotifyMarkSize = Integer.parseInt(inScanner.nextLine());
                        int idNotifyMarkSize = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.notifyMarkSize(gameNotifyMarkSize, idNotifyMarkSize));
                        break;
                    case "Get Notify Mark":
                        int gameGetNotifyMark = Integer.parseInt(inScanner.nextLine());
                        int idGetNotifyMark = Integer.parseInt(inScanner.nextLine());
                        List<List<String>> notifyMarkList = server.getNotifyMark(gameGetNotifyMark, idGetNotifyMark);
                        outPrinter.println(notifyMarkList.get(0).get(0));
                        outPrinter.println(notifyMarkList.get(0).get(1));
                        break;
                    case "Notify Damage Size":
                        int gameNotifyDamageSize = Integer.parseInt(inScanner.nextLine());
                        int idNotifyDamageSize = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.notifyDamageSize(gameNotifyDamageSize, idNotifyDamageSize));
                        break;
                    case "Get Notify Damage":
                        int gameGetNotifyDamage = Integer.parseInt(inScanner.nextLine());
                        int idGetNotifyDamage = Integer.parseInt(inScanner.nextLine());
                        List<List<String>> notifyDamageList = server.getNotifyDamage(gameGetNotifyDamage, idGetNotifyDamage);
                        outPrinter.println(notifyDamageList.get(0).get(0));
                        outPrinter.println(notifyDamageList.get(0).get(1));
                        outPrinter.println(notifyDamageList.get(0).get(2));
                        break;

                    //it can be useful
                    case "QUIT":
                        exit = true;
                        break;

                    default: break;
                }
            }

            inScanner.close();
            outPrinter.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e ) {
            System.out.println("Socket Server Client Handler Exception");
        } catch (InterruptedException e) {
            System.exit(0);
            Thread.currentThread().interrupt();
        }
    }
}