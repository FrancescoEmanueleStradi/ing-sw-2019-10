package view.gui.socket.powerups;

import view.gui.socket.GUISocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Panel prompting choice of powerup card.
 */
public class UsePUCPanelSocket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private JFrame parent;
    private int game;
    private String nickName;
    private java.util.Timer timer;
    private int turn;
    private List<JButton> l = new LinkedList<>();

    /**
     * Creates a new UsePUCPanelSocket.
     *
     * @param gui gui
     * @param socket socket
     * @param parent parent frame
     * @param game game
     * @param nickName nickname
     * @param timer timer
     * @param turn turn
     * @throws IOException I/O exception of some sort
     */
    public UsePUCPanelSocket(GUISocket gui, Socket socket, JFrame parent, int game, String nickName, java.util.Timer timer, int turn) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.timer = timer;
        this.turn = turn;
        add(new JLabel("The following are " + this.nickName + "'s PowerUpCards")).setBounds(0, 0, 5, 5);
        add(new JLabel("Press the button near the card you want to use")).doLayout();

        socketOut.println("Message Get PowerUp Card Name And Colour");
        socketOut.println(game);
        socketOut.println(nickName);
        int sizePUC = Integer.parseInt(socketIn.nextLine());
        List<String> namePUC = new LinkedList<>();
        List<String> colourPUC = new LinkedList<>();
        for(int i = 0; i < sizePUC; i++) {
            if(i % 2 == 0)
                namePUC.add(socketIn.nextLine());
            else
                colourPUC.add(socketIn.nextLine());
        }

        for (int i = 0; i < sizePUC; i++) {
            add(new JLabel(namePUC.get(i) + "coloured" + colourPUC.get(i))).doLayout();
            l.add(new JButton(Integer.toString(i)));
            this.add(l.get(l.size()-1)).doLayout();
            l.get(l.size()-1).addActionListener(this);
            socketOut.println("Message Get Description PUC");
            socketOut.println(game);
            socketOut.println(namePUC.get(i));
            socketOut.println(colourPUC.get(i));
            socketOut.println(nickName);
            add(new JLabel(socketIn.nextLine())).doLayout();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String action = e.getSource().toString();
            socketOut.println("Message Get PowerUp Card Name And Colour");
            socketOut.println(game);
            socketOut.println(nickName);
            int sizePUC = Integer.parseInt(socketIn.nextLine());
            List<String> namePUC = new LinkedList<>();
            List<String> colourPUC = new LinkedList<>();
            for(int i = 0; i < sizePUC; i++) {
                if(i % 2 == 0)
                    namePUC.add(socketIn.nextLine());
                else
                    colourPUC.add(socketIn.nextLine());
            }

            switch(namePUC.get(Integer.parseInt(action))){
                case "Tagback Grenade":
                    gui.TGPUC(timer, turn, colourPUC.get(Integer.parseInt(action)));
                    break;

                case "Targeting Scope":
                    gui.TSPUC(timer, turn, colourPUC.get(Integer.parseInt(action)));
                    break;

                case "Newton":
                    gui.NPUC(timer, turn, colourPUC.get(Integer.parseInt(action)));
                    break;

                case "Teleporter":
                    gui.TPUC(timer, turn, colourPUC.get(Integer.parseInt(action)));
                    break;
                default: break;
            }
            parent.setVisible(false);
            parent.dispose();

        }
        catch (RemoteException r) {

        } catch (InterruptedException i) {

        }
    }
}