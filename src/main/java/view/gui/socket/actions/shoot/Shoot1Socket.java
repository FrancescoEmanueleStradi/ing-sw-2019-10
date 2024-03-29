package view.gui.socket.actions.shoot;

import view.gui.common.CardLinkList;
import view.gui.socket.GUISocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

/**
 * Panel prompting shoot(first action) input parameters.
 */
public class Shoot1Socket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private int game;
    private int identifier;
    private String nickName;
    private JComboBox weaponsList;
    private JButton b;
    private Timer timer;
    private JFrame parent;
    private CardLinkList l = new CardLinkList();

    /**
     * Creates a new Shoot1.
     *
     * @param gui gui
     * @param socket socket
     * @param game game
     * @param identifier identifier
     * @param nickName nickname
     * @param parent parentframe
     * @param timer timer
     * @throws IOException I/O exception of some sort
     */
    public Shoot1Socket(GUISocket gui, Socket socket, int game, int identifier, String nickName, JFrame parent, Timer timer) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        this.timer = timer;
        this.parent = parent;

        socketOut.println("Message Get Weapon Card Loaded");
        socketOut.println(game);
        socketOut.println(nickName);
        int size = Integer.parseInt(socketIn.nextLine());
        List<String> weaponLoaded = new LinkedList<>();
        for(int i = 0; i < size; i++)
            weaponLoaded.add(socketIn.nextLine());

        add(new JLabel("Select the weapon you want to use:"));
        for (String weaponName : weaponLoaded)
            add(new JLabel(l.getImageIconFromName(weaponName, null))).doLayout();
        Object[] weapons = weaponLoaded.toArray();
        weaponsList = new JComboBox(weapons);
        add(weaponsList).doLayout();

        b = new JButton("Confirm");
        add(b).doLayout();
        b.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        b.setEnabled(false);
        shootInput();
    }

    private synchronized void shootInput() {
        timer.cancel();
        String weaponSelected = (String) weaponsList.getSelectedItem();
        add(new JLabel("Below are the relevant strings you must enter for this card, with respect to any possible order of effects as " +
                "described in the manual.\nIn brackets is the additional ammo cost for certain effects and firing modes.\n"));
        switch (weaponSelected) {
            case "Cyberblade":
                add(new JLabel("basic effect: target in your cell\nshadowstep: direction you want to move to\n" +
                        "slice and dice: different target in your cell [1 yellow]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Electroscythe":
                add(new JLabel("basic mode: none\nreaper mode: none [1 red 1 blue]"));
                gui.shootToUser2(weaponSelected);
                break;

            case "Flamethrower":
                add(new JLabel("basic mode: target 1 move away, and possibly another target 1 more move away in the same direction\n" +
                        "barbecue mode: coordinates of cell of target(s) 1 move away, and possibly those of another cell 1 more more away in the same direction [2 yellow]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Furnace":
                add(new JLabel("basic mode: colour of room you can see that isn't your room\ncozy fire mode: coordinates of cell 1 move away"));
                gui.shootToUser3(weaponSelected);
                break;

            case "Grenade Launcher":
                add(new JLabel("basic effect: target you can see, and possibly the direction you wish to move him in\n" +
                        "extra grenade: coordinates of cell you can see [1 red]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Heatseeker":
                add(new JLabel("effect: target you canNOT see"));
                gui.shootToUser3(weaponSelected);
                break;

            case "Hellion":
                add(new JLabel("basic mode: target you can see at least 1 move away\nnano-tracer mode: as with basic mode [1 red]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Lock Rifle":
                add(new JLabel("basic effect: target you can see\nsecond lock: different target you can see [1 red]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Machine Gun":
                add(new JLabel("basic effect: 1 or 2 targets you can see\nfocus shot: one of those targets [1 yellow]\n" +
                        "turret tripod: the other of those targets and/or a different target you can see [1 blue]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Plasma Gun":
                add(new JLabel("basic effect: target you can see\nphase glide: number of cells you want to move (1 or 2) and the direction(s)\n" +
                        "charged shot: none [1 blue]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Power Glove":
                add(new JLabel("basic mode: target 1 move away\n" +
                        "rocket fist mode: coordinates of cell 1 move away, and possibly a target on that cell" +
                        "(you may repeat this once with a cell in the same direction just 1 square away, plus a target on that cell [1 blue]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Railgun":
                add(new JLabel("basic mode: target in a cardinal direction\npiercing mode: 1 or 2 targets in a cardinal direction" +
                        "(keep in mind this attack ignores walls)"));
                gui.shootToUser3(weaponSelected);
                break;

            case "Rocket Launcher":
                add(new JLabel("basic effect: target you can see but not in your cell, and possibly the direction in which to move them\n" +
                        "rocket jump: number of cells you want to move (1 or 2) and the direction(s) [1 blue]" +
                        "fragmenting warhead: none [1 yellow]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Shockwave":
                add(new JLabel("basic mode: up to 3 targets in different cells, each 1 move away\ntsunami mode: none [1 yellow]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Shotgun":
                add(new JLabel("basic mode: target in your cell, and possibly the direction you want to move them in\n" +
                        "long barrel mode: target 1 move away"));
                gui.shootToUser3(weaponSelected);
                break;

            case "Sledgehammer":
                add(new JLabel("basic mode: target in your cell\npulverize mode: target in your cell, and possibly the number of squares (1 or 2) you want to move them " +
                        "and the direction(s) [1 red]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "T.H.O.R.":
                add(new JLabel("basic effect: target you can see\nchain reaction: target your first target can see [1 blue]\n" +
                        "high voltage: target your second target can see [1 blue]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Tractor Beam":
                add(new JLabel("basic mode: target you may or may not see, coordinates of a cell you can see up to 2 squares away from you\n" +
                        "punisher mode: target up to 2 moves away [1 red 1 yellow]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Vortex Cannon":
                add(new JLabel("basic effect: target up to 1 move away from the 'vortex', coordinates of the cell the vortex is to be placed in " +
                        "(must not be your cell)\nblack hole: 1 or 2 targets on the vortex or 1 move away from it [1 red]"));
                gui.shootToUser1(weaponSelected);
                break;

            case "Whisper":
                add(new JLabel("effect: target you can see at least 2 moves away)"));
                gui.shootToUser4(weaponSelected);
                break;

            case "ZX-2":
                add(new JLabel("basic mode: target you can see\nscanner mode: up to 3 targets you can see"));
                gui.shootToUser3(weaponSelected);
                break;
            default:
                break;

        }
        parent.dispose();
    }
}