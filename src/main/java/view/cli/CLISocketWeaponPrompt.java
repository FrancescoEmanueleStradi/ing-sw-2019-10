package view.cli;

import model.Colour;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLISocketWeaponPrompt implements Serializable {

    private final String enterEffect = "Enter the number(s) of the effect(s) you want to use; 0 to finish";
    private final String enterRelevantString = "Enter the relevant strings for the card; 0 to finish";
    private final String enterAmmoColour = "Enter the colour(s) of the required AmmoCube(s) needed for the effect; 0 to finish";
    private final String enterPowerUp = "Enter the PowerUpCard(s) you want to use for paying during your turn; 0 to finish";
    private final String enterPowerUpColour = "Enter the colour(s) of the PowerUpCard(s); 0 to finish";
    private final String enterAdrenalineDir = "If you are in Adrenaline, enter the direction of the move, 0 otherwise";
    private final String promptErrorRetry = "Error: please retry";
    private final String exit = CLI.EXITSTRING;
    private final String yesPrompt = CLI.YESPROMPT;

    public void shootToUser1(int game, Socket socket, String nickName, String s) throws RemoteException, IOException {
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int i;
        List<Integer> lI;
        List<String> lS;
        List<Colour> lC;
        List<String> lP;
        List<String> lPC;
        while (true) {
            System.out.println(enterEffect);
            lI = enterEffect(intScan);
            System.out.println(enterRelevantString);
            lS = enterRelevantString(in);
            System.out.println(enterAmmoColour);
            lC = enterAmmoColour(in);
            socketOut.println("Message Get PowerUp Card Name And Colour");
            socketOut.println(game);
            socketOut.println(nickName);
            int size = socketIn.nextInt();
            for(int i1 = 0; i1 < size; i1++) {
                System.out.println(socketIn.nextLine() + "coloured" + socketIn.nextLine());
            }
            System.out.println(enterPowerUp);
            lP = enterPowerUp(in);
            System.out.println(enterPowerUpColour);
            lPC = enterPowerUpColour(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);

            socketOut.println("Message Is Valid First Action Shoot");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);
            socketOut.println(lI.size());
            for(Integer i1 : lI)
                socketOut.println(i1);
            socketOut.println(i);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getAbbreviation());
            socketOut.println(lP.size());
            for(String s1 : lP)
                socketOut.println(s1);
            socketOut.println(lPC.size());
            for(String s1 : lPC)
                socketOut.println(s1);

            if (socketIn.nextBoolean())
                break;
            else {
                System.out.println(promptErrorRetry);
                System.out.println(this.exit + yesPrompt);
                String exit = in.next();
                if (exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }

        socketOut.println("Message First Action Shoot");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s);
        socketOut.println(lI.size());
        for(Integer i1 : lI)
            socketOut.println(i1);
        socketOut.println(i);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getAbbreviation());
        socketOut.println(lP.size());
        for(String s1 : lP)
            socketOut.println(s1);
        socketOut.println(lPC.size());
        for(String s1 : lPC)
            socketOut.println(s1);
    }

    public void shoot2ToUser1(int game, Socket socket, String nickName, String s) throws RemoteException, IOException {
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int i;
        List<Integer> lI;
        List<String> lS;
        List<Colour> lC;
        List<String> lP;
        List<String> lPC;

        while (true) {
            System.out.println(enterEffect);
            lI = enterEffect(intScan);
            System.out.println(enterRelevantString);
            lS = enterRelevantString(in);
            System.out.println(enterAmmoColour);
            lC = enterAmmoColour(in);
            socketOut.println("Message Get PowerUp Card Name And Colour");
            socketOut.println(game);
            socketOut.println(nickName);
            int size = socketIn.nextInt();
            for(int i1 = 0; i1 < size; i1++) {
                System.out.println(socketIn.nextLine() + "coloured" + socketIn.nextLine());
            }
            System.out.println(enterPowerUp);
            lP = enterPowerUp(in);
            System.out.println(enterPowerUpColour);
            lPC = enterPowerUpColour(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);

            socketOut.println("Message Is Valid Second Action Shoot");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);
            socketOut.println(lI.size());
            for(Integer i1 : lI)
                socketOut.println(i1);
            socketOut.println(i);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getAbbreviation());
            socketOut.println(lP.size());
            for(String s1 : lP)
                socketOut.println(s1);
            socketOut.println(lPC.size());
            for(String s1 : lPC)
                socketOut.println(s1);

            if (socketIn.nextBoolean())
                break;
            else {
                System.out.println(promptErrorRetry);
                System.out.println(this.exit + yesPrompt);
                String exit = in.next();
                if (exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }

        socketOut.println("Message Second Action Shoot");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s);
        socketOut.println(lI.size());
        for(Integer i1 : lI)
            socketOut.println(i1);
        socketOut.println(i);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getAbbreviation());
        socketOut.println(lP.size());
        for(String s1 : lP)
            socketOut.println(s1);
        socketOut.println(lPC.size());
        for(String s1 : lPC)
            socketOut.println(s1);
    }

    public void shootToUser2(int game, Socket socket, String nickName, String s) throws RemoteException, IOException {
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int i;
        List<Integer> lI;
        List<String> lS = new LinkedList<>();
        List<Colour> lC;
        List<String> lP;
        List<String> lPC;

        while (true) {
            System.out.println(enterEffect);
            lI = enterEffect(intScan);
            System.out.println(enterAmmoColour);
            lC = enterAmmoColour(in);
            socketOut.println("Message Get PowerUp Card Name And Colour");
            socketOut.println(game);
            socketOut.println(nickName);
            int size = socketIn.nextInt();
            for(int i1 = 0; i1 < size; i1++) {
                System.out.println(socketIn.nextLine() + "coloured" + socketIn.nextLine());
            }
            System.out.println(enterPowerUp);
            lP = enterPowerUp(in);
            System.out.println(enterPowerUpColour);
            lPC = enterPowerUpColour(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);

            socketOut.println("Message Is Valid First Action Shoot");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);
            socketOut.println(lI.size());
            for(Integer i1 : lI)
                socketOut.println(i1);
            socketOut.println(i);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getAbbreviation());
            socketOut.println(lP.size());
            for(String s1 : lP)
                socketOut.println(s1);
            socketOut.println(lPC.size());
            for(String s1 : lPC)
                socketOut.println(s1);

            if (socketIn.nextBoolean())
                break;
            else {
                System.out.println(promptErrorRetry);
                System.out.println(this.exit + yesPrompt);
                String exit = in.next();
                if (exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }

        socketOut.println("Message First Action Shoot");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s);
        socketOut.println(lI.size());
        for(Integer i1 : lI)
            socketOut.println(i1);
        socketOut.println(i);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getAbbreviation());
        socketOut.println(lP.size());
        for(String s1 : lP)
            socketOut.println(s1);
        socketOut.println(lPC.size());
        for(String s1 : lPC)
            socketOut.println(s1);
    }

    public void shoot2ToUser2(int game, Socket socket, String nickName, String s) throws RemoteException, IOException {
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int i;
        List<Integer> lI;
        List<String> lS = new LinkedList<>();
        List<Colour> lC;
        List<String> lP;
        List<String> lPC;

        while (true) {
            System.out.println(enterEffect);
            lI = enterEffect(intScan);
            System.out.println(enterAmmoColour);
            lC = enterAmmoColour(in);
            socketOut.println("Message Get PowerUp Card Name And Colour");
            socketOut.println(game);
            socketOut.println(nickName);
            int size = socketIn.nextInt();
            for(int i1 = 0; i1 < size; i1++) {
                System.out.println(socketIn.nextLine() + "coloured" + socketIn.nextLine());
            }
            System.out.println(enterPowerUp);
            lP = enterPowerUp(in);
            System.out.println(enterPowerUpColour);
            lPC = enterPowerUpColour(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);

            socketOut.println("Message Is Valid Second Action Shoot");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);
            socketOut.println(lI.size());
            for(Integer i1 : lI)
                socketOut.println(i1);
            socketOut.println(i);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getAbbreviation());
            socketOut.println(lP.size());
            for(String s1 : lP)
                socketOut.println(s1);
            socketOut.println(lPC.size());
            for(String s1 : lPC)
                socketOut.println(s1);

            if (socketIn.nextBoolean())
                break;
            else {
                System.out.println(promptErrorRetry);
                System.out.println(this.exit + yesPrompt);
                String exit = in.next();
                if (exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }

        socketOut.println("Message Second Action Shoot");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s);
        socketOut.println(lI.size());
        for(Integer i1 : lI)
            socketOut.println(i1);
        socketOut.println(i);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getAbbreviation());
        socketOut.println(lP.size());
        for(String s1 : lP)
            socketOut.println(s1);
        socketOut.println(lPC.size());
        for(String s1 : lPC)
            socketOut.println(s1);
    }

    public void shootToUser3(int game, Socket socket, String nickName, String s) throws RemoteException, IOException {
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int i;
        List<Integer> lI;
        List<String> lS;
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterEffect);
            lI = enterEffect(intScan);
            System.out.println(enterRelevantString);
            lS = enterRelevantString(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);

            socketOut.println("Message Is Valid First Action Shoot");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);
            socketOut.println(lI.size());
            for(Integer i1 : lI)
                socketOut.println(i1);
            socketOut.println(i);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getAbbreviation());
            socketOut.println(lP.size());
            for(String s1 : lP)
                socketOut.println(s1);
            socketOut.println(lPC.size());
            for(String s1 : lPC)
                socketOut.println(s1);

            if (socketIn.nextBoolean())
                break;
            else {
                System.out.println(promptErrorRetry);
                System.out.println(this.exit + yesPrompt);
                String exit = in.next();
                if (exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }

        socketOut.println("Message First Action Shoot");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s);
        socketOut.println(lI.size());
        for(Integer i1 : lI)
            socketOut.println(i1);
        socketOut.println(i);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getAbbreviation());
        socketOut.println(lP.size());
        for(String s1 : lP)
            socketOut.println(s1);
        socketOut.println(lPC.size());
        for(String s1 : lPC)
            socketOut.println(s1);
    }

    public void shoot2ToUser3(int game, Socket socket, String nickName, String s) throws RemoteException, IOException {
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int i;
        List<Integer> lI;
        List<String> lS;
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterEffect);
            lI = enterEffect(intScan);
            System.out.println(enterRelevantString);
            lS = enterRelevantString(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);

            socketOut.println("Message Is Valid Second Action Shoot");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);
            socketOut.println(lI.size());
            for(Integer i1 : lI)
                socketOut.println(i1);
            socketOut.println(i);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getAbbreviation());
            socketOut.println(lP.size());
            for(String s1 : lP)
                socketOut.println(s1);
            socketOut.println(lPC.size());
            for(String s1 : lPC)
                socketOut.println(s1);

            if (socketIn.nextBoolean())
                break;
            else {
                System.out.println(promptErrorRetry);
                System.out.println(this.exit + yesPrompt);
                String exit = in.next();
                if (exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }

        socketOut.println("Message Second Action Shoot");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s);
        socketOut.println(lI.size());
        for(Integer i1 : lI)
            socketOut.println(i1);
        socketOut.println(i);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getAbbreviation());
        socketOut.println(lP.size());
        for(String s1 : lP)
            socketOut.println(s1);
        socketOut.println(lPC.size());
        for(String s1 : lPC)
            socketOut.println(s1);
    }

    public void shootToUser4(int game, Socket socket, String nickName, String s) throws RemoteException, IOException {
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS;
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterRelevantString);
            lS = enterRelevantString(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);

            socketOut.println("Message Is Valid First Action Shoot");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);
            socketOut.println(lI.size());
            for(Integer i1 : lI)
                socketOut.println(i1);
            socketOut.println(i);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getAbbreviation());
            socketOut.println(lP.size());
            for(String s1 : lP)
                socketOut.println(s1);
            socketOut.println(lPC.size());
            for(String s1 : lPC)
                socketOut.println(s1);

            if (socketIn.nextBoolean())
                break;
            else {
                System.out.println(promptErrorRetry);
                System.out.println(this.exit + yesPrompt);
                String exit = in.next();
                if (exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }

        socketOut.println("Message First Action Shoot");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s);
        socketOut.println(lI.size());
        for(Integer i1 : lI)
            socketOut.println(i1);
        socketOut.println(i);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getAbbreviation());
        socketOut.println(lP.size());
        for(String s1 : lP)
            socketOut.println(s1);
        socketOut.println(lPC.size());
        for(String s1 : lPC)
            socketOut.println(s1);
    }

    public void shoot2ToUser4(int game, Socket socket, String nickName, String s) throws RemoteException, IOException {
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
        Scanner socketIn = new Scanner(socket.getInputStream());
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS;
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterRelevantString);
            lS = enterRelevantString(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);

            socketOut.println("Message Is Valid Second Action Shoot");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(s);
            socketOut.println(lI.size());
            for(Integer i1 : lI)
                socketOut.println(i1);
            socketOut.println(i);
            socketOut.println(lC.size());
            for(Colour c : lC)
                socketOut.println(c.getAbbreviation());
            socketOut.println(lP.size());
            for(String s1 : lP)
                socketOut.println(s1);
            socketOut.println(lPC.size());
            for(String s1 : lPC)
                socketOut.println(s1);

            if (socketIn.nextBoolean())
                break;
            else {
                System.out.println(promptErrorRetry);
                System.out.println(this.exit + yesPrompt);
                String exit = in.next();
                if (exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }

        socketOut.println("Message Second Action Shoot");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(s);
        socketOut.println(lI.size());
        for(Integer i1 : lI)
            socketOut.println(i1);
        socketOut.println(i);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getAbbreviation());
        socketOut.println(lP.size());
        for(String s1 : lP)
            socketOut.println(s1);
        socketOut.println(lPC.size());
        for(String s1 : lPC)
            socketOut.println(s1);
    }

    private List<Integer> enterEffect(Scanner in) {
        int effect;
        List<Integer> l = new LinkedList<>();
        while (in.hasNextInt()) {
            effect = in.nextInt();
            if (effect == 0)
                break;
            l.add(effect);
        }
        return l;
    }

    private List<String> enterRelevantString(Scanner in) {
        String str;
        List<String> l = new LinkedList<>();
        while (true) {
            str = in.nextLine();
            if (str.equals("0")) {
                break;
            } else
                l.add(str);
        }
        return l;
    }

    private List<Colour> enterAmmoColour(Scanner in) {
        String colour;
        List<Colour> l = new LinkedList<>();
        while (true) {
            colour = in.nextLine();
            if (colour.equals("0")) {
                break;
            } else
                l.add(Colour.valueOf(colour));
        }
        return l;
    }

    private List<String> enterPowerUp(Scanner in) {
        String pUC;
        List<String> l = new LinkedList<>();
        while (true) {
            pUC = in.next();
            if (pUC.equals("0")) {
                break;
            } else
                l.add(pUC);
        }
        return l;
    }

    private List<String> enterPowerUpColour(Scanner in) {
        String colour;
        List<String> l = new LinkedList<>();
        while (true) {
            colour = in.next();
            if (colour.equals("0")) {
                break;
            } else
                l.add(colour);
        }
        return l;
    }

    private int enterAdrenalineDir(Scanner in) {
        int direction = in.nextInt();
        while (direction < 0 || direction > 4 ) {
            System.out.println("Make sure the direction is between 0 and 4");
            direction = in.nextInt();
        }
        return direction;
    }
}