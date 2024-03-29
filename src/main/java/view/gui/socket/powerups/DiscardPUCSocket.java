package view.gui.socket.powerups;

import view.gui.common.CardLinkList;
import view.gui.socket.GUISocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Panel prompting to discard a powerup card for the spawn point.
 */
public class DiscardPUCSocket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private JFrame parent;
    private int game;
    private JButton firstButton;
    private JButton secondButton;
    private String nickName;
    private String n1;
    private String n2;
    private String c1;
    private String c2;

    /**
     * Creates a new DiscardPUCSocket.
     *
     * @param gui gui
     * @param socket socket
     * @param game game
     * @param nickName nickname
     * @param n1 powerup 1
     * @param n2 powerup 2
     * @param c1 powerup colour 1
     * @param c2 powerup colour 2
     * @param parent parent frame
     * @throws IOException I/O exception of some sort
     */
    public DiscardPUCSocket(GUISocket gui, Socket socket, int game, String nickName, String n1, String n2, String c1, String c2, JFrame parent) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.n1 = n1;
        this.n2 = n2;
        this.c1 = c1;
        this.c2 = c2;
        CardLinkList l = new CardLinkList();
        add(new JLabel("The following are " + this.nickName +"'s starting PowerUpCards")).setBounds(0,0, 5, 5);
        add(new JLabel("Enter the name of the card you want to keep; you will discard the other one corresponding to the " +
                "colour of your spawn point")).doLayout();
        firstButton = new JButton(this.n1 + " coloured " + this.c1);
        add(firstButton).doLayout();
        firstButton.addActionListener(this);
        secondButton = new JButton(this.n2 + " coloured " + this.c2);
        add(secondButton).doLayout();
        secondButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();
            String isValidPickAndDiscard;
            int counterValidPickAndDiscard = 0;

            if(action == firstButton) {
                do {
                    if(counterValidPickAndDiscard > 0)
                        gui.selectSpawnPoint();

                    socketOut.println("Message Is Valid Pick And Discard");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(n1);
                    socketOut.println(c1);

                    isValidPickAndDiscard = socketIn.nextLine();

                    counterValidPickAndDiscard++;
                }while(!isValidPickAndDiscard.equals("true"));

                socketOut.println("Message Pick And Discard");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(n1);
                socketOut.println(c1);

                parent.setVisible(false);
                parent.dispose();
                gui.printType();
            }
            else if(action == secondButton) {
                do {
                    if(counterValidPickAndDiscard > 0)
                        gui.selectSpawnPoint();

                    socketOut.println("Message Is Valid Pick And Discard");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(n2);
                    socketOut.println(c2);

                    isValidPickAndDiscard = socketIn.nextLine();

                    counterValidPickAndDiscard++;
                }while(!isValidPickAndDiscard.equals("true"));

                socketOut.println("Message Pick And Discard");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(n2);
                socketOut.println(c2);

                parent.setVisible(false);
                parent.dispose();
                gui.printType();
            }
        }catch (RemoteException r) {

        }catch (InterruptedException i) {

        }
    }

}