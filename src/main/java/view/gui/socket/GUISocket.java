package view.gui.socket;

import model.Colour;
import network.socket.MyTaskSocket;
import view.*;
import view.gui.common.Action1;
import view.gui.common.Action2;
import view.gui.common.GridGraphic;
import view.gui.common.WantUsePUCPanel;
import view.gui.common.WantUsePUCPanel2;
import view.gui.common.WantUsePUCPanel3;
import view.gui.socket.finalfrenzy.*;
import view.gui.socket.actions.grab.Grab1Socket;
import view.gui.socket.actions.grab.Grab2Socket;
import view.gui.socket.actions.move.Move1Socket;
import view.gui.socket.actions.move.Move2Socket;
import view.gui.socket.powerups.*;
import view.gui.socket.actions.shoot.Shoot1Socket;

import java.awt.*;
import javax.swing.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

/**
 * View class for GUISocket.
 * Note that many of the non-interface methods are merely extension of existing ones
 * and do not require additional documentation.
 */
public class GUISocket implements View, Serializable {

    private int game;
    private int identifier;
    private int type;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private String nickName;
    private JFrame gameGraphic;
    private GridGraphic gridGraphic;
    private Colour colour;

    /**
     * Creates new GUISocket, starting with a single base JFrame.
     *
     * @param game game
     * @param socket socket
     * @throws IOException IO exception
     */
    public GUISocket(int game, Socket socket) throws IOException {
        super();
        this.game = game;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.gameGraphic = new JFrame();
    }

    public int getGame() {
        return game;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public View getView() {
        return this;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getNickName() {
        return nickName;
    }

    public void setInformation(int identifier) {
        socketOut.println("Get Suspended Name");
        socketOut.println(game);
        socketOut.println(identifier);
        this.nickName = socketIn.nextLine();
        this.nickName = socketIn.nextLine();

        socketOut.println("Get Suspended Colour");
        socketOut.println(game);
        socketOut.println(this.nickName);
        this.colour = Colour.valueOf(socketIn.nextLine());
        this.identifier = identifier;
    }

    public void disconnected(int disconnected) {
        gridGraphic.changeText("Player number " + disconnected + " is disconnected");
        this.gameGraphic.revalidate();
    }

    public void askNameAndColour() {
        socketOut.println("Message Game Is Not Started");
        socketOut.println(game);
        String gameIsNotStarted = socketIn.nextLine();

        try {
            if (gameIsNotStarted.equals("true") && this.identifier == 1) {
                JFrame f = new JFrame("Name, colour and type");
                f.setLocation(10, 10);
                Container c = f.getContentPane();
                TakeInformationSocket p = new TakeInformationSocket(this, this.socket, this.game, this.identifier, f);
                p.setLayout(new FlowLayout(FlowLayout.LEFT));
                c.add(p);
                f.setSize(500, 500);
                f.setVisible(true);
            } else {
                JFrame f = new JFrame("Name and colour");
                f.setLocation(10, 10);
                Container c = f.getContentPane();
                TakeInformationSocket p = new TakeInformationSocket(this, this.socket, this.game, this.identifier, f);
                p.setLayout(new FlowLayout(FlowLayout.LEFT));
                c.add(p);
                f.setSize(500, 500);
                f.setVisible(true);
            }
        }catch (IOException ex) {

        }
    }

    public void selectSpawnPoint() throws RemoteException, InterruptedException {
        socketOut.println("Message Give Two PU Card");
        socketOut.println(game);
        socketOut.println(nickName);

        socketOut.println("Message Get Initial PowerUp Card");
        socketOut.println(game);
        socketOut.println(nickName);
        String pUCard1 = socketIn.nextLine();
        String pUCard2 = socketIn.nextLine();

        socketOut.println("Message Get Initial PowerUp Card Colour");
        socketOut.println(game);
        socketOut.println(nickName);
        String pUCard1Colour = socketIn.nextLine();
        String pUCard2Colour = socketIn.nextLine();

        try {
            JFrame spawnPoint = new JFrame("Spawn point selection");
            spawnPoint.setLocation(10, 10);
            Container c = spawnPoint.getContentPane();
            DiscardPUCSocket d = new DiscardPUCSocket(this, socket, game, nickName, pUCard1, pUCard2, pUCard1Colour, pUCard2Colour, spawnPoint);
            d.setLayout(new FlowLayout(FlowLayout.LEFT));
            c.add(d);
            spawnPoint.setSize(900, 500);
            spawnPoint.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void action1() throws InterruptedException {
        JFrame action = new JFrame(this.nickName + "'s FIRST ACTION");
        action.setLocation(50,50);
        Container c = action.getContentPane();
        Action1 a = new Action1(null, this, action);
        c.add(a);
        action.setSize(400, 400);
        action.setVisible(true);
    }

    public void moveFirstAction() {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 150000);
            JFrame move = new JFrame("First action - move");
            move.setLocation(50, 50);
            Container c = move.getContentPane();
            Move1Socket move1 = new Move1Socket(this, socket, game, identifier, nickName, move, timer);
            c.add(move1);
            move.setSize(400, 400);
            move.setVisible(true);
        }catch (IOException e){

        }
    }

    public void grabFirstAction() {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 150000);
            JFrame grab = new JFrame("First action - grab");
            grab.setLocation(50,50);
            Container c = grab.getContentPane();
            Grab1Socket grab1 = new Grab1Socket(this, socket, game, identifier, nickName, grab, timer);
            c.add(grab1);
            grab.setSize(500,500);
            grab.setVisible(true);
        }catch (IOException e){

        }
    }

    public void shootFirstAction() {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 150000);
            JFrame shoot = new JFrame("First action - shoot");
            shoot.setLocation(50,50);
            Container c = shoot.getContentPane();
            Shoot1Socket shoot1 = new Shoot1Socket(this, socket, game, identifier, nickName, shoot, timer);
            c.add(shoot1);
            shoot.setSize(500,500);
            shoot.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void shootToUser1(String weapon){
        //TODO

    }

    public void shootToUser2(String weapon){
        //TODO

    }

    public void shootToUser3(String weapon){
        //TODO

    }

    public void shootToUser4(String weapon){
        //TODO

    }

    public void action2() throws InterruptedException {
        JFrame action = new JFrame(this.nickName + "'s SECOND ACTION");
        action.setLocation(50,50);
        Container c = action.getContentPane();
        Action2 a = new Action2(null, this, action);
        c.add(a);
        action.setSize(400, 400);
        action.setVisible(true);
    }

    public void moveSecondAction() throws InterruptedException {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 150000);
            JFrame move = new JFrame("Second action - move");
            move.setLocation(50, 50);
            Container c = move.getContentPane();
            Move2Socket move2 = new Move2Socket(this, socket, game, identifier, nickName, move, timer);
            c.add(move2);
            move.setSize(400, 400);
            move.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public  void grabSecondAction() throws InterruptedException {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 150000);
            JFrame grab = new JFrame("Second action - grab");
            grab.setLocation(50, 50);
            Container c = grab.getContentPane();
            Grab2Socket grab2 = new Grab2Socket(this, socket, game, identifier, nickName, grab, timer);
            c.add(grab2);
            grab.setSize(500, 500);
            grab.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void shootSecondAction() throws InterruptedException {
        JFrame shoot = new JFrame("Second action - shoot");
        shoot.setLocation(50,50);
        Container c = shoot.getContentPane();
        //Shoot2Socket shoot2 = new Shoot2Socket(this, socket, game, identifier, nickName);
        //c.add(shoot2);
        shoot.setSize(500,500);
        shoot.setVisible(true);
    }

    public void shoot2ToUser1(String weapon){
        //TODO

    }

    public void shoot2ToUser2(String weapon){
        //TODO

    }

    public void shoot2ToUser3(String weapon){
        //TODO

    }

    public void shoot2ToUser4(String weapon){
        //TODO

    }

    public void usePowerUpCard() throws RemoteException {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 150000);
            JFrame jF = new JFrame("Use Power-Up Card");
            jF.setLocation(150, 150);
            Container c = jF.getContentPane();
            UsePUCPanelSocket u = new UsePUCPanelSocket(this, socket, jF, game, nickName, timer, 1);
            c.add(u);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void usePowerUpCard2() throws RemoteException {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 150000);
            JFrame jF = new JFrame("Use Power-Up Card");
            jF.setLocation(150, 150);
            Container c = jF.getContentPane();
            UsePUCPanelSocket u = new UsePUCPanelSocket(this, socket, jF, game, nickName, timer, 2);
            c.add(u);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void usePowerUpCard3() throws RemoteException {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 150000);
            JFrame jF = new JFrame("Use Power-Up Card");
            jF.setLocation(150, 150);
            Container c = jF.getContentPane();
            UsePUCPanelSocket u = new UsePUCPanelSocket(this, socket, jF, game, nickName, timer, 3);
            c.add(u);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void TGPUC(Timer timer, int turn, String col) throws RemoteException, InterruptedException {
        try {
            JFrame jF = new JFrame("Effect");
            jF.setLocation(150, 150);
            Container c = jF.getContentPane();
            TGPUCPanelSocket u = new TGPUCPanelSocket(this, socket, jF, game, nickName, timer, turn, col);
            c.add(u);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void TSPUC(Timer timer, int turn, String col) throws RemoteException, InterruptedException {
        try {
            JFrame jF = new JFrame("Effect");
            jF.setLocation(150, 150);
            Container c = jF.getContentPane();
            TSPUCPanelSocket u = new TSPUCPanelSocket(this, socket, jF, game, nickName, timer, turn, col);
            c.add(u);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void NPUC(Timer timer, int turn, String col) throws RemoteException, InterruptedException {
        try {
            JFrame jF = new JFrame("Effect");
            jF.setLocation(150,150);
            Container c = jF.getContentPane();
            NPUCPanelSocket u = new NPUCPanelSocket(this, socket, jF , game, nickName, timer, turn, col);
            c.add(u);
            jF.setSize(400,400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void TPUC(Timer timer, int turn, String col) throws RemoteException, InterruptedException {
        try {
            JFrame jF = new JFrame("Effect");
            jF.setLocation(150,150);
            Container c = jF.getContentPane();
            TPUCPanelSocket u = new TPUCPanelSocket(this, socket, jF , game, nickName, timer, turn, col);
            c.add(u);
            jF.setSize(400,400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public boolean doYouWantToUsePUC() throws RemoteException {
        socketOut.println("Stop Game");
        socketOut.println(game);
        String stopGame = socketIn.nextLine();

        if (stopGame.equals("true"))
            this.endFinalFrenzy();

        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel w = new WantUsePUCPanel(null, this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
        return true;
    }

    public void doYouWantToUsePUC2() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel2 w = new WantUsePUCPanel2(null, this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void doYouWantToUsePUC3() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel3 w = new WantUsePUCPanel3(null, this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void reload() throws RemoteException, InterruptedException {
        try {
            JFrame jF = new JFrame("Reload");
            jF.setLocation(50, 50);
            Container c = jF.getContentPane();
            ReloadPanelSocket reloadPanel = new ReloadPanelSocket(this, socket, jF, game, nickName);
            c.add(reloadPanel);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void scoring() throws RemoteException {
        socketOut.println("Message Is Valid Scoring");
        socketOut.println(game);

        String isValidScoring = socketIn.nextLine();

        if (isValidScoring.equals("true")) {
            socketOut.println("Message Scoring");
            socketOut.println(game);
        }

        this.replace();
    }

    public void newSpawnPoint() throws RemoteException {
        List<String> deadList = new LinkedList<>();
        socketOut.println("Message Get Dead List");
        socketOut.println(game);
        int size = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size; i++)
            deadList.add(socketIn.nextLine());

        if(deadList.contains(this.nickName)) {
            try {
                JFrame jF = new JFrame("Reload");
                jF.setLocation(50, 50);
                Container c = jF.getContentPane();
                NewSpawnPointPanelSocket newSpawnPointPanel = new NewSpawnPointPanelSocket(this, socket, jF, game, nickName);
                c.add(newSpawnPointPanel);
                jF.setSize(400, 400);
                jF.setVisible(true);
            }catch (IOException ex) {

            }
        }

        while(true){
            socketOut.println("Stop Game");
            socketOut.println(game);
            String stopGame = socketIn.nextLine();

            if (stopGame.equals("true"))
                this.endFinalFrenzy();

            socketOut.println("Is My Turn");
            socketOut.println(game);
            socketOut.println(identifier);
            String isMyTurn = socketIn.nextLine();

            if (isMyTurn.equals("true"))
                break;
        }

        socketOut.println("Is Not Final Frenzy");
        socketOut.println(game);
        String isNotFF = socketIn.nextLine();

        if (isNotFF.equals("true"))
            this.doYouWantToUsePUC();
        else
            this.finalFrenzyTurn();
    }

    public void replace() throws RemoteException {
        socketOut.println("Message Replace");
        socketOut.println(game);

        socketOut.println("Finish Turn");
        socketOut.println(game);

        socketOut.println("Stop Game");
        socketOut.println(game);
        String stopGame = socketIn.nextLine();

        if (stopGame.equals("true"))
            this.endFinalFrenzy();

        this.newSpawnPoint();
    }

    public void finalFrenzyTurn() {
        try {
            MyTaskSocket task = new MyTaskSocket(game, identifier, this.getNickName(), socket);
            Timer timer = new Timer();
            timer.schedule(task, 1500000);
            JFrame jF = new JFrame("Final Frenzy");
            jF.setLocation(50, 50);
            Container c = jF.getContentPane();
            FFPanelSocket ffPanel = new FFPanelSocket(this, socket, jF, game, nickName, timer);
            c.add(ffPanel);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public synchronized void firstFFAction(boolean end) {
        try {
            JFrame jF = new JFrame("Final Frenzy Action 1");
            jF.setLocation(50, 50);
            Container c = jF.getContentPane();
            FFAction1Socket ff1 = new FFAction1Socket(this, socket, jF, game, nickName);
            c.add(ff1);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public synchronized void secondFFAction(boolean end) {
        try {
            JFrame jF = new JFrame("Final Frenzy Action 2");
            jF.setLocation(50, 50);
            Container c = jF.getContentPane();
            FFAction2Socket ff2 = new FFAction2Socket(this, socket, jF, game, nickName);
            c.add(ff2);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public synchronized void thirdFFAction(boolean end) {
        try {
            JFrame jF = new JFrame("Final Frenzy Action 3");
            jF.setLocation(50, 50);
            Container c = jF.getContentPane();
            FFAction3Socket ff3 = new FFAction3Socket(this, socket, jF, game, nickName);
            c.add(ff3);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public synchronized void fourthFFAction(boolean end) {
        try {
            JFrame jF = new JFrame("Final Frenzy Action 4");
            jF.setLocation(50, 50);
            Container c = jF.getContentPane();
            FFAction4Socket ff4 = new FFAction4Socket(this, socket, jF, game, nickName);
            c.add(ff4);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public synchronized void fifthFFAction(boolean end) {
        try {
            JFrame jF = new JFrame("Final Frenzy Action 5");
            jF.setLocation(50, 50);
            Container c = jF.getContentPane();
            FFAction5Socket ff5 = new FFAction5Socket(this, socket, jF, game, nickName);
            c.add(ff5);
            jF.setSize(400, 400);
            jF.setVisible(true);
        }catch (IOException ex) {

        }
    }

    public void endFinalFrenzy() throws RemoteException {
        socketOut.println("Message End Turn Final Frenzy");
        socketOut.println(game);

        gridGraphic.changeText("We are calculating the result");
        this.gameGraphic.revalidate();
        this.finalScoring();
    }

    public void finalScoring() throws RemoteException {
        socketOut.println("Message Final Scoring");
        socketOut.println(game);

        gridGraphic.changeText("FINAL SCORE");

        socketOut.println("Message Get Players");
        socketOut.println(game);
        int size = Integer.parseInt(socketIn.nextLine());
        List<String> playersFinal = new LinkedList<>();
        for(int i = 0; i < size; i++)
            playersFinal.add(socketIn.nextLine());
        playersFinal.forEach(gridGraphic::changeText);

        gridGraphic.changeText("");

        socketOut.println("Message Get Score");
        List<Integer> score = new LinkedList<>();
        int size1 = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size1; i++)
            score.add(Integer.parseInt(socketIn.nextLine()));

        score.stream().map(a -> Integer.toString(a)).forEach(gridGraphic::changeText);

        gridGraphic.changeText("");
        gridGraphic.changeText("END GAME");
        this.gameGraphic.revalidate();
    }

    public void printPlayer(List<String> information) throws RemoteException {
        if(gridGraphic != null) {
            gridGraphic.changeText("Player " + information.get(0) + " (identifier " + information.get(2) + ") whose colour is " + information.get(1) + " is now a player of this game.");
            gameGraphic.revalidate();
        }
    }

    public void printScore(List<String> information) throws RemoteException {
        if(gridGraphic != null) {
            gridGraphic.changeText("Player: " + information.get(0) + " has now this score: " + information.get(1));
            this.gameGraphic.revalidate();
        }
    }

    public void printPosition(List<String> information) throws RemoteException {
        if(gridGraphic != null) {
            gridGraphic.changeText("Now Player: " + information.get(0) + " is in the cell " + information.get(1) + " " + information.get(2));
            this.gameGraphic.revalidate();
        }
    }

    public void printMark(List<String> information) throws RemoteException {
        if(gridGraphic != null) {
            gridGraphic.changeText("Player: " + information.get(0) + "give a new Mark to Player" + information.get(1));
            this.gameGraphic.revalidate();
        }
    }

    public void printDamage(List<String> information) throws RemoteException {
        if(gridGraphic != null) {
            gridGraphic.changeText("Player: " + information.get(0) + " give " + information.get(1) + " damages to Player: " + information.get(2));
            this.gameGraphic.revalidate();
        }
    }

    public void printType() throws RemoteException {
        this.gameGraphic.setSize(1200, 900);
        if(type == 1) {
            this.gridGraphic = new GridGraphic("Images/Grid1.png");
        }
        if(type == 2) {
            this.gridGraphic = new GridGraphic("Images/Grid2.png");
        }
        if(type == 3) {
            this.gridGraphic = new GridGraphic("Images/Grid3.png");
        }
        if(type == 4) {
            this.gridGraphic = new GridGraphic("Images/Grid4.png");
        }
        gameGraphic.getContentPane().add(gridGraphic);
        gameGraphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGraphic.setVisible(true);
    }

    public void setType(int type) throws RemoteException {
        this.type = type;
    }

    public void exit() {
        System.exit(0);
    }
}