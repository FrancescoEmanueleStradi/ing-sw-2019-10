package view;

import controller.Game;
import model.Colour;
import java.awt.*;
import javax.swing.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.time.chrono.JapaneseChronology;
import java.util.List;
import java.util.Scanner;

public class GUI implements View, Serializable {

    private int game;
    private int identifier;
    private int type;
    private ServerInterface server;
    private String nickName;
    private Colour colour;
    private JFrame gameGraphic;
    private Window gridGraphic;
    private JScrollPane scrollPane;
    private TextArea textArea;
    /*private ImageIcon Enjoy;
    private ImageIcon BlackPlayerBoard;
    private ImageIcon BlackPlayerBoardFF;
    private ImageIcon BluePlayerBoard;
    private ImageIcon BluePlayerBoardFF;
    private ImageIcon GreenPlayerBoard;
    private ImageIcon GreenPlayerBoardFF;
    private ImageIcon PurplePlayerBoard;
    private ImageIcon PurplePlayerBoardFF;
    private ImageIcon YellowPlayerBoard;
    private ImageIcon YellowPlayerBoardFF;
    private ImageIcon Left14Grid;
    private ImageIcon Left23Grid;
    private ImageIcon Right14Grid;
    private ImageIcon Right23Grid;
    private ImageIcon BRR;
    private ImageIcon BYY;
    private ImageIcon PBB;
    private ImageIcon PRB;
    private ImageIcon PRR;
    private ImageIcon PYB;
    private ImageIcon PYR;
    private ImageIcon PYY;
    private ImageIcon RBB;
    private ImageIcon RYY;
    private ImageIcon YBB;
    private ImageIcon YRR;
    private ImageIcon BlueNewton;
    private ImageIcon RedNewton;
    private ImageIcon YellowNewton;
    private ImageIcon BlueTagbackGrenade;
    private ImageIcon RedTagbackGrenade;
    private ImageIcon YellowTagbackGrenda;
    private ImageIcon BlueTargetingScope;
    private ImageIcon RedTargetingScope;
    private ImageIcon YellowTargetingScope;
    private ImageIcon BlueTeleporter;
    private ImageIcon RedTeleporter;
    private ImageIcon YellowTeleporter;
    private ImageIcon Cyberblade;
    private ImageIcon Electroscythe;
    private ImageIcon Flamethrower;
    private ImageIcon Furnace;
    private ImageIcon GrenadeLauncher;
    private ImageIcon Heatseeker;
    private ImageIcon Hellion;
    private ImageIcon LockRifle;
    private ImageIcon MachineGun;
    private ImageIcon PlasmaGun;
    private ImageIcon PowerGlove;
    private ImageIcon Railgun;
    private ImageIcon RocketLauncher;
    private ImageIcon Shockwave;
    private ImageIcon Shotgun;
    private ImageIcon Sledgehammer;
    private ImageIcon THOR;
    private ImageIcon TractorBeam;
    private ImageIcon VortexCannon;
    private ImageIcon Whisper;
    private ImageIcon ZX2;*/


    public GUI(int game, ServerInterface server) throws RemoteException {
        super();
        this.game = game;
        this.server = server;
        this.gameGraphic = new JFrame();
        /*Enjoy = new ImageIcon("Images/Enjoy.png");
        BlackPlayerBoard = new ImageIcon("Images/BlackPlayerBoard.png");
        BlackPlayerBoardFF = new ImageIcon("Images/BlackPlayerBoardFF.png");
        BluePlayerBoard = new ImageIcon("Images/BluePlayerBoard.png");
        BluePlayerBoardFF = new ImageIcon("Images/BuePlayerBoardFF.png");
        GreenPlayerBoard = new ImageIcon("Images/GreenPlayerBoard.png");
        GreenPlayerBoardFF = new ImageIcon("Images/GreenPlayerBoardFF.png");
        PurplePlayerBoard = new ImageIcon("Images/PurplePlayerBoard.png");
        PurplePlayerBoardFF = new ImageIcon("Images/PurplePlayerBoardFF.png");
        YellowPlayerBoard = new ImageIcon("Images/YellowPlayerBoard.png");
        YellowPlayerBoardFF = new ImageIcon("Images/YellowPlayerBoardFF.png");
        Left14Grid = new ImageIcon("Images/Left14Grid.png");
        Left23Grid = new ImageIcon("Images/Left23Grid.png");
        Right14Grid = new ImageIcon("Images/Right14Grid.png");
        Right23Grid = new ImageIcon("Images/Right23Grid.png");
        BRR = new ImageIcon("Images/ammo/BRR.png");
        BYY = new ImageIcon("Images/ammo/BYY.png");
        PBB = new ImageIcon("Images/ammo/PBB.png");
        PRB = new ImageIcon("Images/ammo/PRB.png");
        PRR = new ImageIcon("Images/ammo/PRR.png");
        PYB = new ImageIcon("Images/ammo/PYB.png");
        PYR = new ImageIcon("Images/ammo/PYR.png");
        PYY = new ImageIcon("Images/ammo/PYY.png");
        RBB = new ImageIcon("Images/ammo/RBB.png");
        RYY = new ImageIcon("Images/ammo/RYY.png");
        YBB = new ImageIcon("Images/ammo/YBB.png");
        YRR = new ImageIcon("Images/ammo/YRR.png");
        BlueNewton = new ImageIcon("Images/cards/BlueNewton.png");
        RedNewton = new ImageIcon("Images/cards/RedNewton.png");
        YellowNewton = new ImageIcon("Images/cards/YellowNewton.png");
        BlueTagbackGrenade = new ImageIcon("Images/cards/BlueTagbackGrenade.png");
        RedTagbackGrenade = new ImageIcon("Images/cards/RedTagbackGrenade.png");
        YellowTagbackGrenda = new ImageIcon("Images/cards/YellowTagbackGrenda.png");
        BlueTargetingScope = new ImageIcon("Images/cards/BlueTargetingScope.png");
        RedTargetingScope = new ImageIcon("Images/cards/RedTargetingScope.png");
        YellowTargetingScope = new ImageIcon("Images/cards/YellowTargetingScope.png");
        BlueTeleporter = new ImageIcon("Images/cards/BlueTeleporter.png");
        RedTeleporter = new ImageIcon("Images/cards/RedTeleporter.png");
        YellowTeleporter = new ImageIcon("Images/cards/YellowTeleporter.png");
        Cyberblade = new ImageIcon("Images/cards/Cyberblade.png");
        Electroscythe = new ImageIcon("Images/cards/Electroscythe.png");
        Flamethrower = new ImageIcon("Images/cards/Flamethrower.png");
        Furnace = new ImageIcon("Images/cards/Furnace.png");
        GrenadeLauncher = new ImageIcon("Images/cards/GrenadeLauncher.png");
        Heatseeker = new ImageIcon("Images/cards/Heatseeker.png");
        Hellion = new ImageIcon("Images/cards/Hellion.png");
        LockRifle = new ImageIcon("Images/cards/LockRifle.png");
        MachineGun = new ImageIcon("Images/cards/MachineGun.png");
        PlasmaGun = new ImageIcon("Images/cards/PlasmaGun.png");
        PowerGlove = new ImageIcon("Images/cards/PowerGlove.png");
        Railgun = new ImageIcon("Images/cards/Railgun.png");
        RocketLauncher = new ImageIcon("Images/cards/RocketLauncher.png");
        Shockwave = new ImageIcon("Images/cards/Shockwave.png");
        Shotgun = new ImageIcon("Images/cards/Shotgun.png");
        Sledgehammer = new ImageIcon("Images/cards/Sledgehammer.png");
        THOR = new ImageIcon("Images/cards/THOR.png");
        TractorBeam = new ImageIcon("Images/cards/TractorBeam.png");
        VortexCannon = new ImageIcon("Images/cards/VortexCannon.png");
        Whisper = new ImageIcon("Images/cards/Whisper.png");
        ZX2 = new ImageIcon("Images/cards/ZX2.png");
        */
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }


    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void setGame(int game){
        this.game = game;
    }

    @Override
    public void setIdentifier(int identifier) throws RemoteException{
        this.identifier = identifier;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public void setInformation(int identifier) throws RemoteException{
        this.nickName = server.getSuspendedName(game, identifier);
        this.colour = server.getSuspendedColour(game, this.nickName);
    }

    @Override
    public void disconnected(int disconnected) throws RemoteException, InterruptedException{
        /*JFrame f = new JFrame("Disconnected");
        Container c = f.getContentPane();
        StandardPanel panel = new StandardPanel(disconnected, server);
        c.add(panel);
        f.setVisible(true);*/
    }

    @Override
    public void askNameAndColour() throws RemoteException{
        if (this.server.messageGameIsNotStarted(game) && this.identifier == 1) {
            JFrame f = new JFrame("Name, colour and type");
            f.setLocation(10,10);
            Container c = f.getContentPane();
            TakeInformation p = new TakeInformation(this, this.server, this.game, this.identifier);
            c.add(p);
            //f.addWindowListener( new Terminator());
            f.setSize(500,500);
            f.setVisible(true);
        }
        else{
            JFrame f = new JFrame("Name and colour");
            f.setLocation(10,10);
            Container c = f.getContentPane();
            TakeInformation p = new TakeInformation(this, this.server, this.game, this.identifier);
            c.add(p);
            //f.addWindowListener( new Terminator());
            f.setSize(500,500);
            f.setVisible(true);
        }
    }


    @Override
    public void selectSpawnPoint() throws RemoteException{
        this.server.messageGiveTwoPUCard(game, this.nickName);
        String p;
        String c;
        System.out.println("The following are " + this.nickName +"'s starting PowerUpCards");
        System.out.println(this.server.messageGetPowerUpCard(game, this.nickName).get(0) + " coloured " + this.server.messageGetPowerUpCardColour(game, this.nickName).get(0));
        System.out.println(this.server.messageGetPowerUpCard(game, this.nickName).get(1) + " coloured " + this.server.messageGetPowerUpCardColour(game, this.nickName).get(1));
        System.out.println("\n---------SPAWN POINT SELECT---------\n");
        /*while (true) {
            System.out.println("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                    "colour of your spawn point");
            p = in.nextLine();
            System.out.println("Enter the colour of that card:");
            c = in.nextLine();
            if (this.server.messageIsValidPickAndDiscard(game, this.nickName, p, c))
                break;
            else
                System.out.println(errorRetry);
        }
        if (this.server.messageGetPowerUpCard(game, this.nickName).get(0).equals(p) && this.server.messageGetPowerUpCardColour(game, this.nickName).get(0).equals(c))
            this.server.messagePickAndDiscardCard(game, this.nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(0), this.server.messageGetPowerUpCardColour(game, this.nickName).get(0));
        else
            this.server.messagePickAndDiscardCard(game, this.nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(1), this.server.messageGetPowerUpCardColour(game, this.nickName).get(1));*/
    }


    @Override
    public void action1(){
        //TODO
    }

    @Override
    public void action2(){
        //TODO
    }

    @Override
    public void usePowerUpCard(){
        //TODO
    }

    @Override
    public boolean doYouWantToUsePUC(){
        return true;
        //TODO
    }

    @Override
    public void reload(){
        //TODO
    }

    @Override
    public void scoring() throws RemoteException{
        if(this.server.messageIsValidScoring(game))
            this.server.messageScoring(game);
    }

    @Override
    public void newSpawnPoint() throws RemoteException{
        //TODO
    }

    @Override
    public void replace() throws RemoteException{
        if(this.server.messageIsValidToReplace(game))
            this.server.messageReplace(game);
    }

    @Override
    public void finalFrenzyTurn() {
        //TODO
    }

    @Override
    public void endFinalFrenzy(){
        //TODO
    }

    @Override
    public void finalScoring(){
        //TODO
    }


    @Override
    public void printScore(java.util.List<String> information) throws RemoteException{
        //TODO
    }

    @Override
    public void printPosition(java.util.List<String> information) throws RemoteException{
        textArea.append("Now Player: " + information.get(0) + " is in the cell " + information.get(1) + " " + information.get(2));
        //TODO
        this.gameGraphic.revalidate();
    }

    @Override
    public void printMark(java.util.List<String> information) throws RemoteException{
        textArea.append("Player: " + information.get(0) + "give a new Mark to Player" + information.get(1));
        this.gameGraphic.revalidate();
    }

    @Override
    public void printDamage(List<String> information) throws RemoteException{
        textArea.append("Player: " + information.get(0) + " give " + information.get(1) + " damages to Player: " + information.get(2));
        //TODO
        this.gameGraphic.revalidate();
    }

    @Override
    public void printType() throws RemoteException{
        this.gameGraphic.setSize(700, 700);
        if(type == 1){
            ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
            ImageIcon Right12Grid = new ImageIcon("Images/Right12Grid.png");
            JLabel L14Grid = new JLabel(Left14Grid);
            JLabel R12Grid = new JLabel(Right12Grid);
            this.gameGraphic.add(L14Grid).setBounds(350, 700, 125, 0);
            this.gameGraphic.add(R12Grid).setBounds(475, 700, 350, 0);
        }
        if(type == 2){
            ImageIcon Left23Grid = new ImageIcon("Images/Left23Grid.png");
            ImageIcon Right12Grid = new ImageIcon("Images/Right12Grid.png");
            JLabel L23Grid = new JLabel(Left23Grid);
            JLabel R12Grid = new JLabel(Right12Grid);
            this.gameGraphic.add(L23Grid).setBounds(350, 700, 125, 0);
            this.gameGraphic.add(R12Grid).setBounds(475, 700, 350, 0);
        }
        if(type == 3){
            ImageIcon Left23Grid = new ImageIcon("Images/Left23Grid.png");
            ImageIcon Right34Grid = new ImageIcon("Images/Right34Grid.png");
            JLabel L23Grid = new JLabel(Left23Grid);
            JLabel R34Grid = new JLabel(Right34Grid);
            this.gameGraphic.add(L23Grid).setBounds(350, 700, 125, 0);
            this.gameGraphic.add(R34Grid).setBounds(475, 700, 350, 0);
        }
        if(type == 4){
            ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
            ImageIcon Right34Grid = new ImageIcon("Images/Right34Grid.png");
            JLabel L14Grid = new JLabel(Left14Grid);
            JLabel R34Grid = new JLabel(Right34Grid);
            this.gameGraphic.add(L14Grid).setBounds(350, 700, 125, 0);
            this.gameGraphic.add(R34Grid).setBounds(475, 700, 350, 0);
        }
        textArea = new TextArea();
        scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 100, 100, 0);
        gameGraphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGraphic.add(scrollPane);
        gameGraphic.setVisible(true);
    }

    @Override
    public void setType(int type) throws RemoteException{
        this.type = type;
    }
}
