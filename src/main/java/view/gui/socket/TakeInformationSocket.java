package view.gui.socket;

import model.Colour;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Initial panel prompting players to enter their name and colour.
 */
public class TakeInformationSocket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private int game;
    private int identifier;
    private JFrame parent;
    private JButton b;
    private JComboBox colourList;
    private JComboBox arenaList;
    private JTextField txt1;

    /**
     * Creates a new TakeInformationSocket. Slightly different conditions for the first
     * player who must choose the arena type.
     *
     * @param gui gui
     * @param socket socket
     * @param game game
     * @param identifier identifier
     * @param parent parent frame
     * @throws IOException I/O exception of some sort
     */
    public TakeInformationSocket(GUISocket gui, Socket socket, int game, int identifier, JFrame parent) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.game = game;
        this.identifier = identifier;
        this.parent = parent;
        b = new JButton("Confirm");
        Object[] colours = {"YELLOW", "BLUE", "GREEN", "PURPLE", "BLACK"};
        Object[] arenas = {1, 2, 3, 4};
        txt1 = new JTextField("Write here", 25);
        colourList = new JComboBox(colours);
        arenaList = new JComboBox(arenas);
        b.addActionListener(this);
        add(new JLabel(("Enter your name:"))).doLayout();
        add(txt1).doLayout();
        add(new JLabel("Select your Colour:")).doLayout();
        add(colourList).doLayout();
        if (identifier == 1) {
            add(new JLabel("Select the type of arena:")).doLayout();
            add(arenaList).doLayout();
        }
        add(b);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            b.setEnabled(false);
            if (identifier == 1) {
                getInformation();
                gui.selectSpawnPoint();
            } else {
                getLessInformation();
                gui.selectSpawnPoint();
            }
        } catch (RemoteException | InterruptedException ex) {

        }

    }

    /**
     * Receives first player's information.
     *
     * @throws RemoteException RMI exception
     */
    private void getInformation() throws RemoteException {
        String colour = (String) colourList.getSelectedItem();
        Integer type = (Integer) arenaList.getSelectedItem();

        gui.setNickName(txt1.getText());
        socketOut.println("Set Nickname");
        socketOut.println(game);
        socketOut.println(identifier);
        socketOut.println(txt1.getText());

        gui.setColour(Colour.valueOf(colour));
        socketOut.println("Message Game Start");
        socketOut.println(game);
        socketOut.println(txt1.getText());
        socketOut.println(Colour.valueOf(colour));

        gui.setType(type);
        socketOut.println("Message Receive Type");
        socketOut.println(game);
        socketOut.println(type);

        parent.setVisible(false);
        parent.dispose();
    }

    /**
     * Receives all other players' information.
     *
     * @throws RemoteException RMI exception
     */
    private void getLessInformation() throws RemoteException {
        String colour = (String) colourList.getSelectedItem();

        socketOut.println("Get Type");
        socketOut.println(game);
        int typeReceived = Integer.parseInt(socketIn.nextLine());
        gui.setType(typeReceived);

        socketOut.println("Message Is Valid Add Player");
        socketOut.println(game);
        socketOut.println(txt1.getText());
        socketOut.println(Colour.valueOf(colour));

        int isValidAddPlayer = Integer.parseInt(socketIn.nextLine());

        if (isValidAddPlayer != 3) {
            gui.askNameAndColour();
        } else {
            gui.setNickName(txt1.getText());
            gui.setColour(Colour.valueOf(colour));

            socketOut.println("Set Nickname");
            socketOut.println(game);
            socketOut.println(identifier);
            socketOut.println(txt1.getText());

            socketOut.println("Message Add Player");
            socketOut.println(game);
            socketOut.println(txt1.getText());
            socketOut.println(Colour.valueOf(colour));

            parent.setVisible(false);
            parent.dispose();
        }
    }
}