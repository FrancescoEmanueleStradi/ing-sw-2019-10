package view;

import controller.Game;
import model.Colour;
import java.awt.*;
import javax.swing.*;

import java.rmi.RemoteException;
import java.util.List;

public class GUI implements View{

    private int game;
    private int identifier;
    private int type;
    private ServerInterface server;
    private String nickName;
    private Colour colour;
    private JFrame gameGraphic;
    private Window gridGraphic;
    private ImageIcon BlackPlayerBoard = new ImageIcon("Images/BlackPlayerBoard.png");
    private ImageIcon BlackPlayerBoardFF = new ImageIcon("Images/BlackPlayerBoardFF.png");
    private ImageIcon BluePlayerBoard = new ImageIcon("Images/BluePlayerBoard.png");
    private ImageIcon BluePlayerBoardFF = new ImageIcon("Images/BuePlayerBoardFF.png");
    private ImageIcon GreenPlayerBoard = new ImageIcon("Images/GreenPlayerBoard.png");
    private ImageIcon GreenPlayerBoardFF = new ImageIcon("Images/GreenPlayerBoardFF.png");
    private ImageIcon PurplePlayerBoard = new ImageIcon("Images/PurplePlayerBoard.png");
    private ImageIcon PurplePlayerBoardFF = new ImageIcon("Images/PurplePlayerBoardFF.png");
    private ImageIcon YellowPlayerBoard = new ImageIcon("Images/YellowPlayerBoard.png");
    private ImageIcon YellowPlayerBoardFF = new ImageIcon("Images/YellowPlayerBoardFF.png");
    private ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
    private ImageIcon Left23Grid = new ImageIcon("Images/Left23Grid.png");
    private ImageIcon Right14Grid = new ImageIcon("Images/Right14Grid.png");
    private ImageIcon Right23Grid = new ImageIcon("Images/Right23Grid.png");
    private ImageIcon BRR = new ImageIcon("Images/ammo/BRR.png");
    private ImageIcon BYY = new ImageIcon("Images/ammo/BYY.png");
    private ImageIcon PBB = new ImageIcon("Images/ammo/PBB.png");
    private ImageIcon PRB = new ImageIcon("Images/ammo/PRB.png");
    private ImageIcon PRR = new ImageIcon("Images/ammo/PRR.png");
    private ImageIcon PYB = new ImageIcon("Images/ammo/PYB.png");
    private ImageIcon PYR = new ImageIcon("Images/ammo/PYR.png");
    private ImageIcon PYY = new ImageIcon("Images/ammo/PYY.png");
    private ImageIcon RBB = new ImageIcon("Images/ammo/RBB.png");
    private ImageIcon RYY = new ImageIcon("Images/ammo/RYY.png");
    private ImageIcon YBB = new ImageIcon("Images/ammo/YBB.png");
    private ImageIcon YRR = new ImageIcon("Images/ammo/YRR.png");
    private ImageIcon BlueNewton = new ImageIcon("Images/cards/BlueNewton.png");
    private ImageIcon RedNewton = new ImageIcon("Images/cards/RedNewton.png");
    private ImageIcon YellowNewton = new ImageIcon("Images/cards/YellowNewton.png");
    private ImageIcon BlueTagbackGrenade = new ImageIcon("Images/cards/BlueTagbackGrenade.png");
    private ImageIcon RedTagbackGrenade = new ImageIcon("Images/cards/RedTagbackGrenade.png");
    private ImageIcon YellowTagbackGrenda = new ImageIcon("Images/cards/YellowTagbackGrenda.png");
    private ImageIcon BlueTargetingScope = new ImageIcon("Images/cards/BlueTargetingScope.png");
    private ImageIcon RedTargetingScope = new ImageIcon("Images/cards/RedTargetingScope.png");
    private ImageIcon YellowTargetingScope = new ImageIcon("Images/cards/YellowTargetingScope.png");
    private ImageIcon BlueTeleporter = new ImageIcon("Images/cards/BlueTeleporter.png");
    private ImageIcon RedTeleporter = new ImageIcon("Images/cards/RedTeleporter.png");
    private ImageIcon YellowTeleporter = new ImageIcon("Images/cards/YellowTeleporter.png");
    private ImageIcon Cyberblade = new ImageIcon("Images/cards/Cyberblade.png");
    private ImageIcon Electroscythe = new ImageIcon("Images/cards/Electroscythe.png");
    private ImageIcon Flamethrower = new ImageIcon("Images/cards/Flamethrower.png");
    private ImageIcon Furnace = new ImageIcon("Images/cards/Furnace.png");
    private ImageIcon GrenadeLauncher = new ImageIcon("Images/cards/GrenadeLauncher.png");
    private ImageIcon Heatseeker = new ImageIcon("Images/cards/Heatseeker.png");
    private ImageIcon Hellion = new ImageIcon("Images/cards/Hellion.png");
    private ImageIcon LockRifle = new ImageIcon("Images/cards/LockRifle.png");
    private ImageIcon MachineGun = new ImageIcon("Images/cards/MachineGun.png");
    private ImageIcon PlasmaGun = new ImageIcon("Images/cards/PlasmaGun.png");
    private ImageIcon PowerGlove = new ImageIcon("Images/cards/PowerGlove.png");
    private ImageIcon Railgun = new ImageIcon("Images/cards/Railgun.png");
    private ImageIcon RocketLauncher = new ImageIcon("Images/cards/RocketLauncher.png");
    private ImageIcon Shockwave = new ImageIcon("Images/cards/Shockwave.png");
    private ImageIcon Shotgun = new ImageIcon("Images/cards/Shotgun.png");
    private ImageIcon Sledgehammer = new ImageIcon("Images/cards/Sledgehammer.png");
    private ImageIcon THOR = new ImageIcon("Images/cards/THOR.png");
    private ImageIcon TractorBeam = new ImageIcon("Images/cards/TractorBeam.png");
    private ImageIcon VortexCannon = new ImageIcon("Images/cards/VortexCannon.png");
    private ImageIcon Whisper = new ImageIcon("Images/cards/Whisper.png");
    private ImageIcon ZX2 = new ImageIcon("Images/cards/ZX2.png");


    public GUI(int game, ServerInterface server) throws RemoteException {
        super();
        this.game = game;
        this.server = server;
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
        JFrame f = new JFrame("Disconnected");
        Container c = f.getContentPane();
        StandardPanel panel = new StandardPanel(disconnected, server);
        c.add(panel);
        f.setVisible(true);
    }

    @Override
    public void askNameAndColour() throws RemoteException{
        if (this.server.messageGameIsNotStarted(game)) {
            JFrame f = new JFrame("Enter your name");
            Container c = f.getContentPane();
            TakeInformation p = new TakeInformation();
            c.add(p);
            f.addWindowListener( new Terminator() );
            f.setSize(300,120);
            f.setVisible(true);


        }
    }



    @Override
    public void selectSpawnPoint(){
        //TODO
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
        //TODO
    }

    @Override
    public void printMark(java.util.List<String> information) throws RemoteException{
        //TODO
    }

    @Override
    public void printDamage(List<String> information) throws RemoteException{
        //TODO
    }

    @Override
    public void printType() throws RemoteException{
        //TODO
    }

    @Override
    public void setType(int type) throws RemoteException{
        this.type = type;
    }
}
