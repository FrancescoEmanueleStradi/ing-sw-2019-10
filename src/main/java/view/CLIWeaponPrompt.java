package view;

import model.Colour;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLIWeaponPrompt {

    private String enterEffect = "Enter the number(s) of the effect(s) you want to use\n" +
            "Press any letter char to finish";
    private String enterRelevantString = "Enter the relevant strings for the card\n" +
            "0 to finish";
    private String enterAmmoColour = "Enter the colour(s) of the required AmmoCube(s) needed for the effect\n" +
            "0 to finish";
    private String enterPowerUp = "Enter the PowerUpCard(s) you want to use for paying during your turn\n" +
            "0 to finish";
    private String enterPowerUpColour = "Enter the colour(s) of the PowerUpCard(s)\n" +
            "0 to finish";
    private String enterAdrenalineDir = "If you are in Adrenaline, enter the direction of the move:";
    private String promptErrorRetry = "Error: please retry";

    public void shootToUser1(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);
        int e, i;
        String str, a, p, c;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterEffect);
            while (intScan.hasNextInt()) {
                e = intScan.nextInt();
                if (e == 0)
                    break;
                else
                    lI.add(e);
            }
            System.out.println(enterRelevantString);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(enterAmmoColour);
            while (true) {
                a = in.next();
                if (a.equals("0")) {
                    break;
                } else
                    lC.add(Colour.valueOf(a));
            }
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(enterPowerUp);
            while (true) {
                p = in.next();
                if (p.equals("0")) {
                    break;
                } else
                    lP.add(p);
            }
            System.out.println(enterPowerUpColour);
            while (true) {
                c = in.next();
                if (c.equals("0")) {
                    break;
                } else
                    lPC.add(c);
            }
            System.out.println(enterAdrenalineDir);
            i = in.nextInt(); //TODO nextint is a problem
            if (server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
                lI.clear();
                lS.clear();
                lC.clear();
                lP.clear();
                lPC.clear();
            }
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser1(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        int e, i;
        String str, a, p, c;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterEffect);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(enterRelevantString);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(enterAmmoColour);
            while (true) {
                a = in.next();
                if (a.equals("0")) {
                    break;
                } else
                    lC.add(Colour.valueOf(a));
            }
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(enterPowerUp);
            while (true) {
                p = in.next();
                if (p.equals("0")) {
                    break;
                } else
                    lP.add(p);
            }
            System.out.println(enterPowerUpColour);
            while (true) {
                c = in.next();
                if (c.equals("0")) {
                    break;
                } else
                    lPC.add(c);
            }
            System.out.println(enterAdrenalineDir);
            i = in.nextInt(); //TODO nextint is a problem
            if (server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
                lI.clear();
                lS.clear();
                lC.clear();
                lP.clear();
                lPC.clear();
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

   public void shootToUser2(int game, ServerInterface server, String nickName, String s) throws RemoteException {
       Scanner in = new Scanner(System.in);
       int e, i;
       String a, p, c;
       List<Integer> lI = new LinkedList<>();
       List<String> lS = new LinkedList<>();
       List<Colour> lC = new LinkedList<>();
       List<String> lP = new LinkedList<>();
       List<String> lPC = new LinkedList<>();

       while (true) {
           System.out.println(enterEffect);
           while (in.hasNextInt()) {
               e = in.nextInt();
               lI.add(e);
           }
           System.out.println(enterAmmoColour);
           while (true) {
               a = in.next();
               if (a.equals("0")) {
                   break;
               } else
                   lC.add(Colour.valueOf(a));
           }
           server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
           System.out.println(enterPowerUp);
           while (true) {
               p = in.next();
               if (p.equals("0")) {
                   break;
               } else
                   lP.add(p);
           }
           System.out.println(enterPowerUpColour);
           while (true) {
               c = in.next();
               if (c.equals("0")) {
                   break;
               } else
                   lPC.add(c);
           }
           System.out.println(enterAdrenalineDir);
           i = in.nextInt(); //TODO nextint is a problem
           if (server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
               break;
           else {
               System.out.println(promptErrorRetry);
               lI.clear();
               lC.clear();
               lP.clear();
               lPC.clear();
           }
       }
       server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
   }

    public void shoot2ToUser2(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        int e, i;
        String a, p, c;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterEffect);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(enterAmmoColour);
            while (true) {
                a = in.next();
                if (a.equals("0")) {
                    break;
                } else
                    lC.add(Colour.valueOf(a));
            }
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(enterPowerUp);
            while (true) {
                p = in.next();
                if (p.equals("0")) {
                    break;
                } else
                    lP.add(p);
            }
            System.out.println(enterPowerUpColour);
            while (true) {
                c = in.next();
                if (c.equals("0")) {
                    break;
                } else
                    lPC.add(c);
            }
            System.out.println(enterAdrenalineDir);
            i = in.nextInt(); //TODO nextint is a problem
            if (server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
                lI.clear();
                lC.clear();
                lP.clear();
                lPC.clear();
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shootToUser3(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        int e, i;
        String str;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterEffect);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(enterRelevantString);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(enterAdrenalineDir);
            i = in.nextInt(); //TODO nextint is a problem
            if (server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
                lI.clear();
                lS.clear();
            }
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser3(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        int e, i;
        String str;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterEffect);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(enterRelevantString);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(enterAdrenalineDir);
            i = in.nextInt(); //TODO nextint is a problem
            if (server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
                lI.clear();
                lS.clear();
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shootToUser4(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        int i;
        String str;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterRelevantString);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(enterAdrenalineDir);
            i = in.nextInt(); //TODO nextint is a problem
            if (server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
                lI.clear();
                lS.clear();
            }
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser4(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        int i;
        String str;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(enterRelevantString);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(enterAdrenalineDir);
            i = in.nextInt(); //TODO nextint is a problem
            if (server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(promptErrorRetry);
                lI.clear();
                lS.clear();
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }
}

/*public List<Integer> enterEffect(Scanner in, List<Integer> l) throws RemoteException {
        int effect;
        System.out.println(enterEffect);
        while (in.hasNextInt()) {
            effect = in.nextInt();
            l.add(effect);
        }
        return l;
    }

    public List<String> enterRelevantString(Scanner in, List<String> l) throws RemoteException {
        String str;
        System.out.println(enterRelevantString);
        while (true) {
            str = in.next();
            if (str.equals("0")) {
                break;
            } else
                l.add(str);
        }
        return l;
    }

    public List<Colour> enterAmmoColour(Scanner in, List<Colour> l) throws RemoteException {
        String colour;
        System.out.println(enterAmmoColour);
        while (true) {
            colour = in.next();
            if (colour.equals("0")) {
                break;
            } else
                l.add(Colour.valueOf(colour));
        }
        return l;
    }

    public List<String> enterPowerUp(Scanner in, List<String> l) throws RemoteException {
        String pUC;
        System.out.println(enterPowerUp);
        while (true) {
            pUC = in.next();
            if (pUC.equals("0")) {
                break;
            } else
                l.add(pUC);
        }
        return l;
    }

    public List<String> enterPowerUpColour(Scanner in, List<String> l) throws RemoteException {
        String colour;
        System.out.println(enterPowerUpColour);
        while (true) {
            colour = in.next();
            if (colour.equals("0")) {
                break;
            } else
                l.add(colour);
        }
        return l;
    }*/