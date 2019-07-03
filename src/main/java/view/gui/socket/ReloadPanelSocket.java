package view.gui.socket;

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

public class ReloadPanelSocket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private JFrame parent;
    private int game;
    private String nickName;
    private List<JButton> l = new LinkedList<>();

    public ReloadPanelSocket(GUISocket gui, Socket socket, JFrame parent, int game, String nickName) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        add(new JLabel("Click on the name of the card you want to load, or on exit if you have finished"));
        add(new JButton("exit"));

        socketOut.println("Message Get Weapon Card Unloaded");
        socketOut.println(game);
        socketOut.println(nickName);
        int sizeUnloaded = Integer.parseInt(socketIn.nextLine());
        List<String> weaponUnloaded = new LinkedList<>();
        for(int i = 0; i < sizeUnloaded; i++)
            weaponUnloaded.add(socketIn.nextLine());

        for (String s : weaponUnloaded) {

            l.add(new JButton(s));
            this.add(l.get(l.size()-1)).doLayout();
            l.get(l.size()-1).addActionListener(this);

        }

    }



    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();

            if(action.getText().equals("exit")) {
                gui.reload();
            }

            else{
                socketOut.println("Message Is Valid Reload");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(action.getText());

                String isValidReload = socketIn.nextLine();

                if(isValidReload.equals("true")) {
                    socketOut.println("Message Reload");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println(action.getText());
                }
                gui.reload();
            }

            parent.setVisible(false);
            parent.dispose();


        }catch(RemoteException ex) {

        }catch (InterruptedException exc){

        }
    }
}