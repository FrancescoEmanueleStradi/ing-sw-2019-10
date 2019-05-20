package view;

import model.Colour;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLIWeaponPrompt {

    private String enterEffect = "Enter the number(s) of the effect(s) you want to use; 0 to finish";
    private String enterRelevantString = "Enter the relevant strings for the card; 0 to finish";
    private String enterAmmoColour = "Enter the colour(s) of the required AmmoCube(s) needed for the effect; 0 to finish";
    private String enterPowerUp = "Enter the PowerUpCard(s) you want to use for paying during your turn; 0 to finish";
    private String enterPowerUpColour = "Enter the colour(s) of the PowerUpCard(s); 0 to finish";
    private String enterAdrenalineDir = "If you are in Adrenaline, enter the direction of the move, 0 otherwise";
    private String promptErrorRetry = "Error: please retry";

    public void shootToUser1(int game, ServerInterface server, String nickName, String s) throws RemoteException {
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
            server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
            System.out.println(enterPowerUp);
            lP = enterPowerUp(in);
            System.out.println(enterPowerUpColour);
            lPC = enterPowerUpColour(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);
            if (server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
            }
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser1(int game, ServerInterface server, String nickName, String s) throws RemoteException {
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
            server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
            System.out.println(enterPowerUp);
            lP = enterPowerUp(in);
            System.out.println(enterPowerUpColour);
            lPC = enterPowerUpColour(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);
            if (server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

   public void shootToUser2(int game, ServerInterface server, String nickName, String s) throws RemoteException {
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
           server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
           System.out.println(enterPowerUp);
           lP = enterPowerUp(in);
           System.out.println(enterPowerUpColour);
           lPC = enterPowerUpColour(in);
           System.out.println(enterAdrenalineDir);
           i = enterAdrenalineDir(intScan);
           if (server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
               break;
           else {
               System.out.println(promptErrorRetry);
           }
       }
       server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
   }

    public void shoot2ToUser2(int game, ServerInterface server, String nickName, String s) throws RemoteException {
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
            server.messageGetPowerUpCard(game, nickName).forEach(System.out::println);
            System.out.println(enterPowerUp);
            lP = enterPowerUp(in);
            System.out.println(enterPowerUpColour);
            lPC = enterPowerUpColour(in);
            System.out.println(enterAdrenalineDir);
            i = enterAdrenalineDir(intScan);
            if (server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shootToUser3(int game, ServerInterface server, String nickName, String s) throws RemoteException {
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
            if (server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
            }
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser3(int game, ServerInterface server, String nickName, String s) throws RemoteException {
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
            if (server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shootToUser4(int game, ServerInterface server, String nickName, String s) throws RemoteException {
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
            if (server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
            }
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser4(int game, ServerInterface server, String nickName, String s) throws RemoteException {
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
            if (server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
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









