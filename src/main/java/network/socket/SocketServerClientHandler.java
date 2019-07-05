package network.socket;

import model.Colour;
import network.ServerMethods;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles socket input from client (multiple threads w/ more sockets originating from the Executor thread pool) so
 * that it is sent to the server safely.
 */
public class SocketServerClientHandler implements Runnable {

    private Socket socket;
    private ServerMethods server;

    /**
     * Creates a SocketServerClientHandler.
     *
     * @param socket socket
     * @param server server
     */
    public SocketServerClientHandler(Socket socket, ServerMethods server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * A thread is launched in order handle I/O between the client and server as well as message... and notify...
     * methods. outPrinter and inScanner are used to that end.
     */
    public synchronized void run() {
        try {
            PrintWriter outPrinter = new PrintWriter(socket.getOutputStream(), true);
            Scanner inScanner = new Scanner(socket.getInputStream());
            boolean exit = false;

            while(!exit) {
                switch (inScanner.nextLine()) {
                    //SocketProcesses calls
                    case "Get Games":
                        outPrinter.println(server.getGames());
                        break;
                    case "Is A Suspended Identifier":
                        int gameIsASuspendedIdentifier = Integer.parseInt(inScanner.nextLine());
                        int identifierIsASuspendedIdentifier = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.isASuspendedIdentifier(gameIsASuspendedIdentifier, identifierIsASuspendedIdentifier));
                        break;
                    case "Get Type":
                        int typeGetType = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.getType(typeGetType));
                        break;
                    case "Manage Reconnection":
                        int gameManageReconnection = Integer.parseInt(inScanner.nextLine());
                        int identifierManageReconnection = Integer.parseInt(inScanner.nextLine());
                        server.manageReconnection(gameManageReconnection, identifierManageReconnection);
                        break;
                    case "Too Many":
                        int gameTooMany = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.tooMany(gameTooMany));
                        break;
                    case "Set Game":
                        int gameSetGame = Integer.parseInt(inScanner.nextLine());
                        server.setGame(gameSetGame);
                        break;
                    case "Receive Identifier":
                        int gameReceiveIdentifier = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.receiveIdentifier(gameReceiveIdentifier));
                        break;
                    case "Merge Group":
                        int gameMergeGroup = Integer.parseInt(inScanner.nextLine());
                        server.mergeGroup(gameMergeGroup);
                        break;
                    case "Can Start":
                        int gameCanStart = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.canStart(gameCanStart));
                        break;
                    case "Stop Game":
                        int gameStopGame = Integer.parseInt(inScanner.nextLine());
                        String stopGame = String.valueOf(server.stopGame(gameStopGame));
                        outPrinter.println(stopGame);
                        break;
                    case "Is My Turn":
                        int gameIsMyTurn = Integer.parseInt(inScanner.nextLine());
                        int identifierIsMyTurn = Integer.parseInt(inScanner.nextLine());
                        String isMyTurn = String.valueOf(server.isMyTurn(gameIsMyTurn, identifierIsMyTurn));
                        outPrinter.println(isMyTurn);
                        break;
                    case "Is Not Final Frenzy":
                        int gameIsNotFinalFrenzy = Integer.parseInt(inScanner.nextLine());
                        String isNotFinalFrenzy = String.valueOf(server.isNotFinalFrenzy(gameIsNotFinalFrenzy));
                        outPrinter.println(isNotFinalFrenzy);
                        break;
                    case "Set Final Turn":
                        int gameSetFinalTurn = Integer.parseInt(inScanner.nextLine());
                        int identifierSetFinalTurn = Integer.parseInt(inScanner.nextLine());
                        String nicknameFinalTurn = inScanner.nextLine();
                        server.setFinalTurn(gameSetFinalTurn, identifierSetFinalTurn, nicknameFinalTurn);
                        break;
                    case "Game Is Finished":
                        int gameGameIsFinished = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.gameIsFinished(gameGameIsFinished));
                        break;
                    case "Manage Disconnection":
                        int gameManageDisconnection = Integer.parseInt(inScanner.nextLine());
                        int identifierManageDisconnection = Integer.parseInt(inScanner.nextLine());
                        String nicknameManageDisconnection = inScanner.nextLine();
                        server.manageDisconnection(gameManageDisconnection, identifierManageDisconnection, nicknameManageDisconnection);
                        break;
                    case "Finish Turn":
                        int gameFinishTurn = Integer.parseInt(inScanner.nextLine());
                        server.finishTurn(gameFinishTurn);
                        break;

                    //CLISocket calls
                    case "Get Suspended Name":
                        int gameGetSuspendedName = Integer.parseInt(inScanner.nextLine());
                        int identifierGetSuspendedName = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.getSuspendedName(gameGetSuspendedName, identifierGetSuspendedName));
                        break;
                    case "Get Suspended Colour":
                        int gameGetSuspendedColour = Integer.parseInt(inScanner.nextLine());
                        String nicknameGetSuspendedColour = inScanner.nextLine();
                        outPrinter.println(server.getSuspendedColour(gameGetSuspendedColour, nicknameGetSuspendedColour));
                        break;
                    case "Message Game Is Not Started":
                        int gameMessageGameIsNotStarted = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.messageGameIsNotStarted(gameMessageGameIsNotStarted));
                        break;
                    case "Set Nickname":
                        int gameSetNickname = Integer.parseInt(inScanner.nextLine());
                        int identifierSetNickname = Integer.parseInt(inScanner.nextLine());
                        String nicknameSetNickname = inScanner.nextLine();
                        server.setNickName(gameSetNickname, identifierSetNickname, nicknameSetNickname);
                        break;
                    case "Message Game Start":
                        int gameMessageGameStart = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageGameStart = inScanner.nextLine();
                        Colour colourMessageGameStart = Colour.valueOf(inScanner.nextLine());
                        server.messageGameStart(gameMessageGameStart, nicknameMessageGameStart, colourMessageGameStart);
                        break;
                    case "Message Is Valid Receive Type":
                        int gameMessageIsValidReceiveType = Integer.parseInt(inScanner.nextLine());
                        int typeMessageIsValidReceiveType = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidReceiveType(gameMessageIsValidReceiveType, typeMessageIsValidReceiveType));
                        break;
                    case "Message Receive Type":
                        int gameMessageReceiveType = Integer.parseInt(inScanner.nextLine());
                        int typeMessageReceiveType = Integer.parseInt(inScanner.nextLine());
                        server.messageReceiveType(gameMessageReceiveType, typeMessageReceiveType);
                        break;
                    case "Message Is Valid Add Player":
                        int gameMessageIsValidAddPlayer = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageIsValidAddPlayer = inScanner.nextLine();
                        Colour colourMessageIsValidAddPlayer = Colour.valueOf(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidAddPlayer(gameMessageIsValidAddPlayer, nicknameMessageIsValidAddPlayer, colourMessageIsValidAddPlayer));
                        break;
                    case "Message Add Player":
                        int gameMessageAddPlayer = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageAddPlayer = inScanner.nextLine();
                        Colour colourMessageAddPlayer = Colour.valueOf(inScanner.nextLine());
                        server.messageAddPlayer(gameMessageAddPlayer, nicknameMessageAddPlayer, colourMessageAddPlayer);
                        break;
                    case "Message Give Two PU Card":
                        int gameMessageGiveTwoPUCard = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageGiveTwoPUCard = inScanner.nextLine();
                        server.messageGiveTwoPUCard(gameMessageGiveTwoPUCard, nicknameMessageGiveTwoPUCard);
                        break;
                    case "Message Get Initial PowerUp Card":
                        int gameMessageGetInitialPowerUpCard = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageGetInitialPowerUpCard = inScanner.nextLine();
                        outPrinter.println(server.messageGetPlayerPowerUpCard(gameMessageGetInitialPowerUpCard, nicknameMessageGetInitialPowerUpCard).get(0));
                        outPrinter.println(server.messageGetPlayerPowerUpCard(gameMessageGetInitialPowerUpCard, nicknameMessageGetInitialPowerUpCard).get(1));
                        break;
                    case "Message Get Initial PowerUp Card Colour":
                        int gameMessageGetInitialPowerUpCardColour = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageGetInitialPowerUpCardColour = inScanner.nextLine();
                        outPrinter.println(server.messageGetPlayerPowerUpCardColour(gameMessageGetInitialPowerUpCardColour, nicknameMessageGetInitialPowerUpCardColour).get(0));
                        outPrinter.println(server.messageGetPlayerPowerUpCardColour(gameMessageGetInitialPowerUpCardColour, nicknameMessageGetInitialPowerUpCardColour).get(1));
                        break;
                    case "Message Is Valid Pick And Discard":
                        int gameMessageIsValidPickAndDiscard = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageIsValidPickAndDiscard = inScanner.nextLine();
                        String nameIsValidCardToKeep = inScanner.nextLine();
                        String colourIsValidCardToKeep = inScanner.nextLine();
                        outPrinter.println(server.messageIsValidPickAndDiscard(gameMessageIsValidPickAndDiscard, nicknameMessageIsValidPickAndDiscard, nameIsValidCardToKeep, colourIsValidCardToKeep));
                        break;
                    case "Message Pick And Discard":
                        int gameMessagePickAndDiscard = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessagePickAndDiscard = inScanner.nextLine();
                        String nameCardToKeep = inScanner.nextLine();
                        String colourCardToKeep = inScanner.nextLine();
                        server.messagePickAndDiscardCard(gameMessagePickAndDiscard, nicknameMessagePickAndDiscard, nameCardToKeep, colourCardToKeep);
                        break;
                    case "Message Get PowerUp Card Name And Colour":
                        int game = Integer.parseInt(inScanner.nextLine());
                        String nickname = inScanner.nextLine();
                        int size = server.messageGetPlayerPowerUpCard(game, nickname).size();
                        outPrinter.println(size);
                        for(int i = 0; i < size; i++) {
                            outPrinter.println(server.messageGetPlayerPowerUpCard(game, nickname).get(i));
                            outPrinter.println(server.messageGetPlayerPowerUpCardColour(game, nickname).get(i));
                        }
                        break;
                    case "Message Get Description PUC":
                        int gameDesc = Integer.parseInt(inScanner.nextLine());
                        String pUC = inScanner.nextLine();
                        String pUCCol = inScanner.nextLine();
                        String nickDesc = inScanner.nextLine();
                        String descriptionPUC = server.messageGetPlayerDescriptionPUC(gameDesc, pUC, pUCCol, nickDesc);
                        int counterPUC = 0;
                        for(char c : descriptionPUC.toCharArray()) {
                            if(c == '\n')
                                counterPUC++;
                        }
                        outPrinter.println(counterPUC);
                        outPrinter.println(descriptionPUC);
                        break;
                    case "Message Is Valid Use PowerUp Card":
                        int game1 = Integer.parseInt(inScanner.nextLine());
                        String nickname1 = inScanner.nextLine();
                        String namePC = inScanner.nextLine();
                        String colourPC = inScanner.nextLine();
                        int size1 = Integer.parseInt(inScanner.nextLine());
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
                        int game2 = Integer.parseInt(inScanner.nextLine());
                        String nickname2 = inScanner.nextLine();
                        String namePC1 = inScanner.nextLine();
                        String colourPC1 = inScanner.nextLine();
                        int size2 = Integer.parseInt(inScanner.nextLine());
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
                        int gameMessageCheckYourStatus = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageCheckYourStatus = inScanner.nextLine();
                        String messageCheckYourStatus = server.messageCheckYourStatus(gameMessageCheckYourStatus, nicknameMessageCheckYourStatus);
                        outPrinter.println(messageCheckYourStatus);
                        break;
                    case "Message Check Others Status":
                        int gameMessageCheckOthersStatus = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageCheckOthersStatus = inScanner.nextLine();
                        String messageCheckOthersStatus = server.messageCheckOthersStatus(gameMessageCheckOthersStatus, nicknameMessageCheckOthersStatus);
                        outPrinter.println(messageCheckOthersStatus);
                        break;
                    case "Message Get Weapon Card Unloaded":
                        int game3 = Integer.parseInt(inScanner.nextLine());
                        String nickname3 = inScanner.nextLine();
                        int size3 = server.messageGetPlayerWeaponCardUnloaded(game3, nickname3).size();
                        outPrinter.println(size3);
                        for(int i = 0; i < size3; i++)
                            outPrinter.println(server.messageGetPlayerWeaponCardUnloaded(game3, nickname3).get(i));
                        break;
                    case "Message Is Valid Reload":
                        int gameMessageIsValidReload = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageIsValidReload = inScanner.nextLine();
                        String selectionMessageIsValidReload = inScanner.nextLine();
                        outPrinter.println(server.messageIsValidReload(gameMessageIsValidReload, nicknameMessageIsValidReload, selectionMessageIsValidReload));
                        break;
                    case "Message Reload":
                        int gameMessageReload = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageReload = inScanner.nextLine();
                        String selectionMessageReload = inScanner.nextLine();
                        server.messageReload(gameMessageReload, nicknameMessageReload, selectionMessageReload);
                        break;
                    case "Message Is Valid Scoring":
                        int gameMessageIsValidScoring = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidScoring(gameMessageIsValidScoring));
                        break;
                    case "Message Scoring":
                        int gameMessageScoring = Integer.parseInt(inScanner.nextLine());
                        server.messageScoring(gameMessageScoring);
                        break;
                    case "Message Replace":
                        int gameMessageReplace = Integer.parseInt(inScanner.nextLine());
                        server.messageReplace(gameMessageReplace);
                        break;
                    case "Message Get Player Weapon Card":
                        int game4 = Integer.parseInt(inScanner.nextLine());
                        String nickname4 = inScanner.nextLine();
                        int size4 = server.messageGetPlayerWeaponCardUnloaded(game4, nickname4).size();
                        outPrinter.println(size4);
                        for(int i = 0; i < size4; i++)
                            outPrinter.println(server.messageGetPlayerWeaponCard(game4, nickname4).get(i));
                        break;
                    case "Message Is Valid Final Frenzy Action":
                        int game5 = Integer.parseInt(inScanner.nextLine());
                        String nickname5 = inScanner.nextLine();
                        int size5 = Integer.parseInt(inScanner.nextLine());
                        List<String> lS2 = new LinkedList<>();
                        for(int i = 0; i < size5; i++)
                            lS2.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction(game5, nickname5, lS2));
                        break;
                    case "Message Is Valid Final Frenzy Action 1":
                        int game6 = Integer.parseInt(inScanner.nextLine());
                        String nickname6 = inScanner.nextLine();
                        int direction = Integer.parseInt(inScanner.nextLine());
                        String wC = inScanner.nextLine();
                        List<Integer> lI = new LinkedList<>();
                        int size6 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size6; i++)
                            lI.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS3 = new LinkedList<>();
                        int size7 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size7; i++)
                            lS3.add(inScanner.nextLine());
                        List<Colour> lC = new LinkedList<>();
                        int size8 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size8; i++)
                            lC.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP = new LinkedList<>();
                        int size9 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size9; i++)
                            lP.add(inScanner.nextLine());
                        List<String> lPC = new LinkedList<>();
                        int size10 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size10; i++)
                            lPC.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction1(game6, nickname6, direction, wC, lI, lS3, lC, lP, lPC));
                        break;
                    case "Message Final Frenzy Action 1":
                        int game7 = Integer.parseInt(inScanner.nextLine());
                        String nickname7 = inScanner.nextLine();
                        int direction1 = Integer.parseInt(inScanner.nextLine());
                        List<String> lW = new LinkedList<>();
                        int size11 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size11; i++)
                            lW.add(inScanner.nextLine());
                        String wC1 = inScanner.nextLine();
                        List<Integer> lI1 = new LinkedList<>();
                        int size12 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size12; i++)
                            lI1.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS4 = new LinkedList<>();
                        int size13 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size13; i++)
                            lS4.add(inScanner.nextLine());
                        List<Colour> lC1 = new LinkedList<>();
                        int size14 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size14; i++)
                            lC1.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP1 = new LinkedList<>();
                        int size15 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size15; i++)
                            lP1.add(inScanner.nextLine());
                        List<String> lPC1 = new LinkedList<>();
                        int size16 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size16; i++)
                            lPC1.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction1(game7, nickname7, direction1, lW, wC1, lI1, lS4, lC1, lP1, lPC1);
                        break;
                    case "Message Is Valid Final Frenzy Action 2":
                        int game8 = Integer.parseInt(inScanner.nextLine());
                        String nickname8 = inScanner.nextLine();
                        List<Integer> directions = new LinkedList<>();
                        int size17 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size17; i++)
                            directions.add(Integer.parseInt(inScanner.nextLine()));
                        outPrinter.println(server.messageIsValidFinalFrenzyAction2(game8, nickname8, directions));
                        break;
                    case "Message Final Frenzy Action 2":
                        int game9 = Integer.parseInt(inScanner.nextLine());
                        String nickname9 = inScanner.nextLine();
                        List<Integer> directions1 = new LinkedList<>();
                        int size18 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size18; i++)
                            directions1.add(Integer.parseInt(inScanner.nextLine()));
                        server.messageFinalFrenzyAction2(game9, nickname9, directions1);
                        break;
                    case "Message Is Valid Final Frenzy Action 3":
                        int game10 = Integer.parseInt(inScanner.nextLine());
                        String nickname10 = inScanner.nextLine();
                        List<Integer> list = new LinkedList<>();
                        int size19 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size19; i++)
                            list.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard = inScanner.nextLine();
                        String wSlot = inScanner.nextLine();
                        List<Colour> lC2 = new LinkedList<>();
                        int size20 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size20; i++)
                            lC2.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP2 = new LinkedList<>();
                        int size21 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size21; i++)
                            lP2.add(inScanner.nextLine());
                        List<String> lPC2 = new LinkedList<>();
                        int size22 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size22; i++)
                            lPC2.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction3(game10, nickname10, list, wCard, wSlot, lC2, lP2, lPC2));
                        break;
                    case "Message Final Frenzy Action 3":
                        int game11 = Integer.parseInt(inScanner.nextLine());
                        String nickname11 = inScanner.nextLine();
                        List<Integer> list1 = new LinkedList<>();
                        int size23 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size23; i++)
                            list1.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard1 = inScanner.nextLine();
                        List<Colour> lC3 = new LinkedList<>();
                        int size24 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size24; i++)
                            lC3.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP3 = new LinkedList<>();
                        int size25 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size25; i++)
                            lP3.add(inScanner.nextLine());
                        List<String> lPC3 = new LinkedList<>();
                        int size26 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size26; i++)
                            lPC3.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction3(game11, nickname11, list1, wCard1, lC3, lP3, lPC3);
                        break;
                    case "Message Is Valid Final Frenzy Action 4":
                        int game12 = Integer.parseInt(inScanner.nextLine());
                        String nickname12 = inScanner.nextLine();
                        List<Integer> list2 = new LinkedList<>();
                        int size27 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size27; i++)
                            list2.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard2 = inScanner.nextLine();
                        List<Integer> lI2 = new LinkedList<>();
                        int size28 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size28; i++)
                            lI2.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS5 = new LinkedList<>();
                        int size29 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size29; i++)
                            lS5.add(inScanner.nextLine());
                        List<Colour> lC4 = new LinkedList<>();
                        int size30 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size30; i++)
                            lC4.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP4 = new LinkedList<>();
                        int size31 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size31; i++)
                            lP4.add(inScanner.nextLine());
                        List<String> lPC4 = new LinkedList<>();
                        int size32 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size32; i++)
                            lPC4.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction4(game12, nickname12, list2, wCard2, lI2, lS5, lC4, lP4, lPC4));
                        break;
                    case "Message Final Frenzy Action 4":
                        int game13 = Integer.parseInt(inScanner.nextLine());
                        String nickname13 = inScanner.nextLine();
                        List<Integer> list3 = new LinkedList<>();
                        int size33 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size33; i++)
                            list3.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> cards = new LinkedList<>();
                        int size34 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size34; i++)
                            cards.add(inScanner.nextLine());
                        String wCard3 = inScanner.nextLine();
                        List<Integer> lI3 = new LinkedList<>();
                        int size35 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size35; i++)
                            lI3.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS6 = new LinkedList<>();
                        int size36 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size36; i++)
                            lS6.add(inScanner.nextLine());
                        List<Colour> lC5 = new LinkedList<>();
                        int size37 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size37; i++)
                            lC5.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP5 = new LinkedList<>();
                        int size38 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size38; i++)
                            lP5.add(inScanner.nextLine());
                        List<String> lPC5 = new LinkedList<>();
                        int size39 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size39; i++)
                            lPC5.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction4(game13, nickname13, list3, cards, wCard3, lI3, lS6, lC5, lP5, lPC5);
                        break;
                    case "Message Is Valid Final Frenzy Action 5":
                        int game14 = Integer.parseInt(inScanner.nextLine());
                        String nickname14 = inScanner.nextLine();
                        List<Integer> list4 = new LinkedList<>();
                        int size40 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size40; i++)
                            list4.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard4 = inScanner.nextLine();
                        String wSlot1 = inScanner.nextLine();
                        List<Colour> lC6 = new LinkedList<>();
                        int size41 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size41; i++)
                            lC6.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP6 = new LinkedList<>();
                        int size42 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size42; i++)
                            lP6.add(inScanner.nextLine());
                        List<String> lPC6 = new LinkedList<>();
                        int size43 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size43; i++)
                            lPC6.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFinalFrenzyAction5(game14, nickname14, list4, wCard4, wSlot1, lC6, lP6, lPC6));
                        break;
                    case "Message Final Frenzy Action 5":
                        int game15 = Integer.parseInt(inScanner.nextLine());
                        String nickname15 = inScanner.nextLine();
                        List<Integer> list5 = new LinkedList<>();
                        int size44 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size44; i++)
                            list5.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard5 = inScanner.nextLine();
                        List<Colour> lC7 = new LinkedList<>();
                        int size45 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size45; i++)
                            lC7.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP7 = new LinkedList<>();
                        int size46 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size46; i++)
                            lP7.add(inScanner.nextLine());
                        List<String> lPC7 = new LinkedList<>();
                        int size47 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size47; i++)
                            lPC7.add(inScanner.nextLine());
                        server.messageFinalFrenzyAction5(game15, nickname15, list5, wCard5, lC7, lP7, lPC7);
                        break;
                    case "Message Final Frenzy Turn Scoring":
                        int gameMessageFFTurnScoring = Integer.parseInt(inScanner.nextLine());
                        server.messageFinalFrenzyTurnScoring(gameMessageFFTurnScoring);
                        break;
                    case "Message End Turn Final Frenzy":
                        int gameMessageEndTurnFF = Integer.parseInt(inScanner.nextLine());
                        server.messageEndTurnFinalFrenzy(gameMessageEndTurnFF);
                        break;
                    case "Message Final Scoring":
                        int gameMessageFinalScoring = Integer.parseInt(inScanner.nextLine());
                        server.messageFinalScoring(gameMessageFinalScoring);
                        break;
                    case "Message Get Players":
                        int game16 = Integer.parseInt(inScanner.nextLine());
                        int sizeMessageGetPlayers = server.messageGetPlayers(game16).size();
                        outPrinter.println(sizeMessageGetPlayers);
                        for(int i = 0; i < sizeMessageGetPlayers; i++)
                            outPrinter.println(server.messageGetPlayers(game16).get(i));
                        break;
                    case "Message Get Dead List":
                        int game17 = Integer.parseInt(inScanner.nextLine());
                        int sizeMessageGetDeadList = server.messageGetDeadList(game17).size();
                        outPrinter.println(sizeMessageGetDeadList);
                        for(int i = 0; i < sizeMessageGetDeadList; i++)
                            outPrinter.println(server.messageGetDeadList(game17).get(i));
                        break;
                    case "Message Is Valid Discard Card For Spawn Point":
                        int gameMessageIsValidDiscardCardForSpawnPoint = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageIsValidDiscardCardForSpawnPoint = inScanner.nextLine();
                        String cardMessageIsValidDiscardCardForSpawnPoint = inScanner.nextLine();
                        String colourMessageIsValidDiscardCardForSpawnPoint = inScanner.nextLine();
                        outPrinter.println(server.messageIsValidDiscardCardForSpawnPoint(gameMessageIsValidDiscardCardForSpawnPoint, nicknameMessageIsValidDiscardCardForSpawnPoint, cardMessageIsValidDiscardCardForSpawnPoint, colourMessageIsValidDiscardCardForSpawnPoint));
                        break;
                    case "Message Discard Card For Spawn Point":
                        int gameMessageDiscardCardForSpawnPoint = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageDiscardCardForSpawnPoint = inScanner.nextLine();
                        String cardMessageDiscardCardForSpawnPoint = inScanner.nextLine();
                        String colourMessageDiscardCardForSpawnPoint = inScanner.nextLine();
                        server.messageDiscardCardForSpawnPoint(gameMessageDiscardCardForSpawnPoint, nicknameMessageDiscardCardForSpawnPoint, cardMessageDiscardCardForSpawnPoint, colourMessageDiscardCardForSpawnPoint);
                        break;
                    case "Message Show Cards On Board":
                        int gameMessageShowCardsOnBoard = Integer.parseInt(inScanner.nextLine());
                        String messageShowCardsOnBoard = server.messageShowCardsOnBoard(gameMessageShowCardsOnBoard);
                        outPrinter.println(messageShowCardsOnBoard);
                        break;
                    case "Message Is Valid First Action Move":
                        int game18 = Integer.parseInt(inScanner.nextLine());
                        String nickname16 = inScanner.nextLine();
                        int size48 = Integer.parseInt(inScanner.nextLine());
                        List<Integer> directions2 = new LinkedList<>();
                        for(int i = 0; i < size48; i++)
                            directions2.add(Integer.parseInt(inScanner.nextLine()));
                        outPrinter.println(server.messageIsValidFirstActionMove(game18, nickname16, directions2));
                        break;
                    case "Message First Action Move":
                        int game19 = Integer.parseInt(inScanner.nextLine());
                        String nickname17 = inScanner.nextLine();
                        int size49 = Integer.parseInt(inScanner.nextLine());
                        List<Integer> directions3 = new LinkedList<>();
                        for(int i = 0; i < size49; i++)
                            directions3.add(Integer.parseInt(inScanner.nextLine()));
                        server.messageFirstActionMove(game19, nickname17, directions3);
                        break;
                    case "Message Is Valid Second Action Move":
                        int game20 = Integer.parseInt(inScanner.nextLine());
                        String nickname18 = inScanner.nextLine();
                        int size50 = Integer.parseInt(inScanner.nextLine());
                        List<Integer> directions4 = new LinkedList<>();
                        for(int i = 0; i < size50; i++)
                            directions4.add(Integer.parseInt(inScanner.nextLine()));
                        outPrinter.println(server.messageIsValidSecondActionMove(game20, nickname18, directions4));
                        break;
                    case "Message Second Action Move":
                        int game21 = Integer.parseInt(inScanner.nextLine());
                        String nickname19 = inScanner.nextLine();
                        int size51 = Integer.parseInt(inScanner.nextLine());
                        List<Integer> directions5 = new LinkedList<>();
                        for(int i = 0; i < size51; i++)
                            directions5.add(Integer.parseInt(inScanner.nextLine()));
                        server.messageSecondActionMove(game21, nickname19, directions5);
                        break;
                    case "Message Check Weapon Slot Contents":
                        int game22 = Integer.parseInt(inScanner.nextLine());
                        int n = Integer.parseInt(inScanner.nextLine());
                        int sizeMessageCheckWeaponSlotContents = server.messageCheckWeaponSlotContents(game22, n).size();
                        outPrinter.println(sizeMessageCheckWeaponSlotContents);
                        for(int i = 0; i < sizeMessageCheckWeaponSlotContents; i++)
                            outPrinter.println(server.messageCheckWeaponSlotContents(game22, n).get(i));
                        break;
                    case "Message Check Weapon Slot Contents Reduced":
                        int gameSlotReduced = Integer.parseInt(inScanner.nextLine());
                        int nSlotReduced = Integer.parseInt(inScanner.nextLine());
                        int sizeMessageCheckWeaponSlotContentsReduced = server.messageCheckWeaponSlotContentsReduced(gameSlotReduced, nSlotReduced).size();
                        outPrinter.println(sizeMessageCheckWeaponSlotContentsReduced);
                        for(int i = 0; i < sizeMessageCheckWeaponSlotContentsReduced; i++)
                            outPrinter.println(server.messageCheckWeaponSlotContentsReduced(gameSlotReduced, nSlotReduced).get(i));
                        break;
                    case "Message Is Valid First Action Grab":
                        int game23 = Integer.parseInt(inScanner.nextLine());
                        String nickname20 = inScanner.nextLine();
                        List<Integer> list6 = new LinkedList<>();
                        int size52 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size52; i++)
                            list6.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard6 = inScanner.nextLine();
                        String wSlot2 = inScanner.nextLine();
                        List<Colour> lC8 = new LinkedList<>();
                        int size53 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size53; i++)
                            lC8.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP8 = new LinkedList<>();
                        int size54 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size54; i++)
                            lP8.add(inScanner.nextLine());
                        List<String> lPC8 = new LinkedList<>();
                        int size55 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size55; i++)
                            lPC8.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFirstActionGrab(game23, nickname20, list6, wCard6, wSlot2, lC8, lP8, lPC8));
                        break;
                    case "Message First Action Grab":
                        int game24 = Integer.parseInt(inScanner.nextLine());
                        String nickname21 = inScanner.nextLine();
                        List<Integer> list7 = new LinkedList<>();
                        int size56 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size56; i++)
                            list7.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard7 = inScanner.nextLine();
                        List<Colour> lC9 = new LinkedList<>();
                        int size57 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size57; i++)
                            lC9.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP9 = new LinkedList<>();
                        int size58 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size58; i++)
                            lP9.add(inScanner.nextLine());
                        List<String> lPC9 = new LinkedList<>();
                        int size59 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size59; i++)
                            lPC9.add(inScanner.nextLine());
                        server.messageFirstActionGrab(game24, nickname21, list7, wCard7, lC9, lP9, lPC9);
                        break;
                    case "Message Is Discard":
                        int gameMessageIsDiscard = Integer.parseInt(inScanner.nextLine());
                        outPrinter.println(server.messageIsDiscard(gameMessageIsDiscard));
                        break;
                    case "Message Discard Weapon Card":
                        int gameMessageDiscardWeaponCard = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageDiscardWeaponCard = inScanner.nextLine();
                        String slotMessageDiscardWeaponCard = inScanner.nextLine();
                        String cardMessageDiscardWeaponCard = inScanner.nextLine();
                        server.messageDiscardWeaponCard(gameMessageDiscardWeaponCard, nicknameMessageDiscardWeaponCard, slotMessageDiscardWeaponCard, cardMessageDiscardWeaponCard);
                        break;
                    case "Message Is Valid Second Action Grab":
                        int game25 = Integer.parseInt(inScanner.nextLine());
                        String nickname22 = inScanner.nextLine();
                        List<Integer> list8 = new LinkedList<>();
                        int size60 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size60; i++)
                            list8.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard8 = inScanner.nextLine();
                        String wSlot3 = inScanner.nextLine();
                        List<Colour> lC10 = new LinkedList<>();
                        int size61 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size61; i++)
                            lC10.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP10 = new LinkedList<>();
                        int size62 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size62; i++)
                            lP10.add(inScanner.nextLine());
                        List<String> lPC10 = new LinkedList<>();
                        int size63 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size63; i++)
                            lPC10.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidSecondActionGrab(game25, nickname22, list8, wCard8, wSlot3, lC10, lP10, lPC10));
                        break;
                    case "Message Second Action Grab":
                        int game26 = Integer.parseInt(inScanner.nextLine());
                        String nickname23 = inScanner.nextLine();
                        List<Integer> list9 = new LinkedList<>();
                        int size64 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size64; i++)
                            list9.add(Integer.parseInt(inScanner.nextLine()));
                        String wCard9 = inScanner.nextLine();
                        List<Colour> lC11 = new LinkedList<>();
                        int size65 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size65; i++)
                            lC11.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP11 = new LinkedList<>();
                        int size66 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size66; i++)
                            lP11.add(inScanner.nextLine());
                        List<String> lPC11 = new LinkedList<>();
                        int size67 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size67; i++)
                            lPC11.add(inScanner.nextLine());
                        server.messageSecondActionGrab(game26, nickname23, list9, wCard9, lC11, lP11, lPC11);
                        break;
                    case "Message Get Weapon Card Loaded":
                        int game27 = Integer.parseInt(inScanner.nextLine());
                        String nickname24 = inScanner.nextLine();
                        outPrinter.println(server.messageGetPlayerWeaponCardLoaded(game27, nickname24).size());
                        for(int i = 0; i < server.messageGetPlayerWeaponCardLoaded(game27, nickname24).size(); i++)
                            outPrinter.println(server.messageGetPlayerWeaponCardLoaded(game27, nickname24).get(i));
                        break;
                    case "Message Is Valid Card":
                        int gameMessageIsValidCard = Integer.parseInt(inScanner.nextLine());
                        String nicknameMessageIsValidCard = inScanner.nextLine();
                        String cardMessageIsValidCard = inScanner.nextLine();
                        outPrinter.println(server.messageIsValidCard(gameMessageIsValidCard, nicknameMessageIsValidCard, cardMessageIsValidCard));
                        break;
                    case "Message Get Reload Cost":
                        int gameMessageGetReloadCost = Integer.parseInt(inScanner.nextLine());
                        String selectionMessageGetReloadCost = inScanner.nextLine();
                        String nicknameMessageGetReloadCost = inScanner.nextLine();
                        int sizeMessageGetReloadCost = server.messageGetPlayerReloadCost(gameMessageGetReloadCost, selectionMessageGetReloadCost, nicknameMessageGetReloadCost).size();
                        outPrinter.println(sizeMessageGetReloadCost);
                        for(int i = 0; i < sizeMessageGetReloadCost; i++)
                            outPrinter.println(server.messageGetPlayerReloadCost(gameMessageGetReloadCost, selectionMessageGetReloadCost, nicknameMessageGetReloadCost).get(i));
                        break;
                    case "Message Get Reload Cost Reduced":
                        int gameMessageGetReloadCostReduced = Integer.parseInt(inScanner.nextLine());
                        String selectionMessageGetReloadCostReduced = inScanner.nextLine();
                        outPrinter.println(server.messageGetReloadCostReduced(gameMessageGetReloadCostReduced, selectionMessageGetReloadCostReduced));
                        break;
                    case "Message Get Description WC":
                        int gameMessageGetDescriptionWC = Integer.parseInt(inScanner.nextLine());
                        String selectionMessageGetDescriptionWC = inScanner.nextLine();
                        String nicknameMessageGetDescriptionWC = inScanner.nextLine();
                        String descriptionWC = server.messageGetPlayerDescriptionWC(gameMessageGetDescriptionWC, selectionMessageGetDescriptionWC, nicknameMessageGetDescriptionWC);
                        int counterDesc = 0;
                        for(char c : descriptionWC.toCharArray()) {
                            if(c == '\n')
                                counterDesc++;
                        }
                        outPrinter.println(counterDesc);
                        outPrinter.println(descriptionWC);
                        break;
                    case "Message Is Valid First Action Shoot":
                        int game28 = Integer.parseInt(inScanner.nextLine());
                        String nickname25 = inScanner.nextLine();
                        String weapon = inScanner.nextLine();
                        List<Integer> lI11 = new LinkedList<>();
                        int size68 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size68; i++)
                            lI11.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS11 = new LinkedList<>();
                        int size69 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size69; i++)
                            lS11.add(inScanner.nextLine());
                        int adrenaline = Integer.parseInt(inScanner.nextLine());
                        List<Colour> lC12 = new LinkedList<>();
                        int size70 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size70; i++)
                            lC12.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP12 = new LinkedList<>();
                        int size71 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size71; i++)
                            lP12.add(inScanner.nextLine());
                        List<String> lPC12 = new LinkedList<>();
                        int size72 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size72; i++)
                            lPC12.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidFirstActionShoot(game28, nickname25, weapon, lI11, lS11, adrenaline, lC12, lP12, lPC12));
                        break;
                    case "Message Is Valid Second Action Shoot":
                        int game29 = Integer.parseInt(inScanner.nextLine());
                        String nickname26 = inScanner.nextLine();
                        String weapon1 = inScanner.nextLine();
                        List<Integer> lI12 = new LinkedList<>();
                        int size73 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size73; i++)
                            lI12.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS12 = new LinkedList<>();
                        int size74 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size74; i++)
                            lS12.add(inScanner.nextLine());
                        int adrenaline1 = Integer.parseInt(inScanner.nextLine());
                        List<Colour> lC13 = new LinkedList<>();
                        int size75 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size75; i++)
                            lC13.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP13 = new LinkedList<>();
                        int size76 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size76; i++)
                            lP13.add(inScanner.nextLine());
                        List<String> lPC13 = new LinkedList<>();
                        int size77 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size77; i++)
                            lPC13.add(inScanner.nextLine());
                        outPrinter.println(server.messageIsValidSecondActionShoot(game29, nickname26, weapon1, lI12, lS12, adrenaline1, lC13, lP13, lPC13));
                        break;
                    case "Message First Action Shoot":
                        int game30 = Integer.parseInt(inScanner.nextLine());
                        String nickname27 = inScanner.nextLine();
                        String weapon2 = inScanner.nextLine();
                        List<Integer> lI13 = new LinkedList<>();
                        int size78 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size78; i++)
                            lI13.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS13 = new LinkedList<>();
                        int size79 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size79; i++)
                            lS13.add(inScanner.nextLine());
                        int adrenaline2 = Integer.parseInt(inScanner.nextLine());
                        List<Colour> lC14 = new LinkedList<>();
                        int size80 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size80; i++)
                            lC14.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP14 = new LinkedList<>();
                        int size81 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size81; i++)
                            lP14.add(inScanner.nextLine());
                        List<String> lPC14 = new LinkedList<>();
                        int size82 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size82; i++)
                            lPC14.add(inScanner.nextLine());
                        server.messageFirstActionShoot(game30, nickname27, weapon2, lI13, lS13, adrenaline2, lC14, lP14, lPC14);
                        break;
                    case "Message Second Action Shoot":
                        int game31 = Integer.parseInt(inScanner.nextLine());
                        String nickname28 = inScanner.nextLine();
                        String weapon3 = inScanner.nextLine();
                        List<Integer> lI14 = new LinkedList<>();
                        int size83 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size83; i++)
                            lI14.add(Integer.parseInt(inScanner.nextLine()));
                        List<String> lS14 = new LinkedList<>();
                        int size84 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size84; i++)
                            lS14.add(inScanner.nextLine());
                        int adrenaline3 = Integer.parseInt(inScanner.nextLine());
                        List<Colour> lC15 = new LinkedList<>();
                        int size85 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size85; i++)
                            lC15.add(Colour.valueOf(inScanner.nextLine()));
                        List<String> lP15 = new LinkedList<>();
                        int size86 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size86; i++)
                            lP15.add(inScanner.nextLine());
                        List<String> lPC15 = new LinkedList<>();
                        int size87 = Integer.parseInt(inScanner.nextLine());
                        for(int i = 0; i < size87; i++)
                            lPC15.add(inScanner.nextLine());
                        server.messageSecondActionShoot(game31, nickname28, weapon3, lI14, lS14, adrenaline3, lC15, lP15, lPC15);
                        break;
                    case "Message Get Score":
                        int gameGetScore = Integer.parseInt(inScanner.nextLine());
                        int sizeGetScore = server.messageGetScore(gameGetScore).size();
                        outPrinter.println(sizeGetScore);
                        for(int i = 0; i < sizeGetScore; i++)
                            outPrinter.println(server.messageGetScore(gameGetScore).get(i));
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
        } catch (IOException e ) {
            System.out.println("Socket Server Client Handler IO Exception");
        } catch (InterruptedException e) {
            System.exit(0);
            Thread.currentThread().interrupt();
        }
    }
}