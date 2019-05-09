package view;

import controller.Game;
import model.Colour;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLIWeaponPrompt {

    private String promptA = "Enter the number(s) of the effect(s) you want to use\n" +
            "Press any letter char to finish";
    private String promptB = "Enter the relevant strings for the card\n" +
            "0 to finish";
    private String promptC = "Enter the colour(s) of the required AmmoCube(s) needed for the effect\n" +
            "0 to finish";
    private String promptD = "Enter the PowerUpCard(s) you want to use for paying during your turn\n" +
            "0 to finish";
    private String promptCOL = "Enter the colour(s) of the PowerUpCard(s)\n" +
            "0 to finish";
    private String promptE = "If you are in Adrenaline, enter the direction of the move:";
    private String promptErrorRetry = "Error: please retry";

    public void shootToUser1(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        int e, i;
        String str, a, p, c;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        while (true) {
            System.out.println(promptA);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(promptB);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(promptC);
            while (true) {
                a = in.next();
                if (a.equals("0")) {
                    break;
                } else
                    lC.add(Colour.valueOf(a));
            }
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (true) {
                p = in.next();
                if (p.equals("0")) {
                    break;
                } else
                    lP.add(p);
            }
            System.out.println(promptCOL);
            while (true) {
                c = in.next();
                if (c.equals("0")) {
                    break;
                } else
                    lPC.add(c);
            }
            System.out.println(promptE);
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
            System.out.println(promptA);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(promptB);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(promptC);
            while (true) {
                a = in.next();
                if (a.equals("0")) {
                    break;
                } else
                    lC.add(Colour.valueOf(a));
            }
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (true) {
                p = in.next();
                if (p.equals("0")) {
                    break;
                } else
                    lP.add(p);
            }
            System.out.println(promptCOL);
            while (true) {
                c = in.next();
                if (c.equals("0")) {
                    break;
                } else
                    lPC.add(c);
            }
            System.out.println(promptE);
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
           System.out.println(promptA);
           while (in.hasNextInt()) {
               e = in.nextInt();
               lI.add(e);
           }
           System.out.println(promptC);
           while (true) {
               a = in.next();
               if (a.equals("0")) {
                   break;
               } else
                   lC.add(Colour.valueOf(a));
           }
           server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
           System.out.println(promptD);
           while (true) {
               p = in.next();
               if (p.equals("0")) {
                   break;
               } else
                   lP.add(p);
           }
           System.out.println(promptCOL);
           while (true) {
               c = in.next();
               if (c.equals("0")) {
                   break;
               } else
                   lPC.add(c);
           }
           System.out.println(promptE);
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
            System.out.println(promptA);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(promptC);
            while (true) {
                a = in.next();
                if (a.equals("0")) {
                    break;
                } else
                    lC.add(Colour.valueOf(a));
            }
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (true) {
                p = in.next();
                if (p.equals("0")) {
                    break;
                } else
                    lP.add(p);
            }
            System.out.println(promptCOL);
            while (true) {
                c = in.next();
                if (c.equals("0")) {
                    break;
                } else
                    lPC.add(c);
            }
            System.out.println(promptE);
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
            System.out.println(promptA);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(promptB);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(promptE);
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
            System.out.println(promptA);
            while (in.hasNextInt()) {
                e = in.nextInt();
                lI.add(e);
            }
            System.out.println(promptB);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(promptE);
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
            System.out.println(promptB);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(promptE);
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
            System.out.println(promptB);
            while (true) {
                str = in.next();
                if (str.equals("0")) {
                    break;
                } else
                    lS.add(str);
            }
            System.out.println(promptE);
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
