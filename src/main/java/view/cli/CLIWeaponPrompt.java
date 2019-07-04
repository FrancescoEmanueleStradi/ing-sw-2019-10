package view.cli;

import model.Colour;
import network.ServerInterface;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLIWeaponPrompt {

    private static final String ENTER_EFFECT = "Enter the number(s) of the effect(s) you want to use; 5 to finish";
    private static final String ENTER_RELEVANT_STRING = "Enter the relevant strings for the card; 5 to finish";
    private static final String ENTER_AMMO_COLOUR = "Enter the colour(s) of the required AmmoCube(s) needed for the effect; 5 to finish";
    private static final String ENTER_POWER_UP = "Enter the PowerUpCard(s) you want to use for paying during your turn; 5 to finish";
    private static final String ENTER_POWER_UP_COLOUR = "Enter the colour(s) of the PowerUpCard(s); 5 to finish";
    private static final String ENTER_ADRENALINE_DIR = "If you are in Adrenaline, enter the direction of the move, 5 otherwise";
    private static final String PROMPT_ERROR_RETRY = "Error: please retry";
    private static final String EXIT = CLI.EXITSTRING;
    private static final String YES_PROMPT = CLI.YESPROMPT;

    public void shootToUser1(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        Scanner intScan = new Scanner(System.in);

        int i;
        List<Integer> lI;
        List<String> lS;
        List<Colour> lC;
        List<String> lP;
        List<String> lPC;

        while(true) {
            System.out.println(ENTER_EFFECT);
            lI = enterEffect(intScan);

            System.out.println(ENTER_RELEVANT_STRING);
            lS = enterRelevantString(in);

            System.out.println(ENTER_AMMO_COLOUR);
            lC = enterAmmoColour(in);

            server.messageGetPlayerPowerUpCard(game, nickName).forEach(System.out::println);

            System.out.println(ENTER_POWER_UP);
            lP = enterPowerUp(in);

            System.out.println(ENTER_POWER_UP_COLOUR);
            lPC = enterPowerUpColour(in);

            System.out.println(ENTER_ADRENALINE_DIR);
            i = enterAdrenalineDir(intScan);

            if(server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(PROMPT_ERROR_RETRY);
                System.out.println(EXIT + YES_PROMPT);
                String exit = in.next();
                if(exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
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

        while(true) {
            System.out.println(ENTER_EFFECT);
            lI = enterEffect(intScan);

            System.out.println(ENTER_RELEVANT_STRING);
            lS = enterRelevantString(in);

            System.out.println(ENTER_AMMO_COLOUR);
            lC = enterAmmoColour(in);

            server.messageGetPlayerPowerUpCard(game, nickName).forEach(System.out::println);

            System.out.println(ENTER_POWER_UP);
            lP = enterPowerUp(in);

            System.out.println(ENTER_POWER_UP_COLOUR);
            lPC = enterPowerUpColour(in);

            System.out.println(ENTER_ADRENALINE_DIR);
            i = enterAdrenalineDir(intScan);

            if(server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(PROMPT_ERROR_RETRY);
                System.out.println(EXIT + YES_PROMPT);
                String exit = in.next();
                if(exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
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

       while(true) {
           System.out.println(ENTER_EFFECT);
           lI = enterEffect(intScan);

           System.out.println(ENTER_AMMO_COLOUR);
           lC = enterAmmoColour(in);

           server.messageGetPlayerPowerUpCard(game, nickName).forEach(System.out::println);

           System.out.println(ENTER_POWER_UP);
           lP = enterPowerUp(in);

           System.out.println(ENTER_POWER_UP_COLOUR);
           lPC = enterPowerUpColour(in);

           System.out.println(ENTER_ADRENALINE_DIR);
           i = enterAdrenalineDir(intScan);

           if(server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
               break;
           else {
               System.out.println(PROMPT_ERROR_RETRY);
               System.out.println(EXIT + YES_PROMPT);
               String exit = in.next();
               if(exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                   return;
               }
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

        while(true) {
            System.out.println(ENTER_EFFECT);
            lI = enterEffect(intScan);

            System.out.println(ENTER_AMMO_COLOUR);
            lC = enterAmmoColour(in);

            server.messageGetPlayerPowerUpCard(game, nickName).forEach(System.out::println);

            System.out.println(ENTER_POWER_UP);
            lP = enterPowerUp(in);

            System.out.println(ENTER_POWER_UP_COLOUR);
            lPC = enterPowerUpColour(in);

            System.out.println(ENTER_ADRENALINE_DIR);
            i = enterAdrenalineDir(intScan);

            if(server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(PROMPT_ERROR_RETRY);
                System.out.println(EXIT + YES_PROMPT);
                String exit = in.next();
                if(exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
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

        while(true) {
            System.out.println(ENTER_EFFECT);
            lI = enterEffect(intScan);

            System.out.println(ENTER_RELEVANT_STRING);
            lS = enterRelevantString(in);

            System.out.println(ENTER_ADRENALINE_DIR);
            i = enterAdrenalineDir(intScan);

            if(server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(PROMPT_ERROR_RETRY);
                System.out.println(EXIT + YES_PROMPT);
                String exit = in.next();
                if(exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
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

        while(true) {
            System.out.println(ENTER_EFFECT);
            lI = enterEffect(intScan);

            System.out.println(ENTER_RELEVANT_STRING);
            lS = enterRelevantString(in);

            System.out.println(ENTER_ADRENALINE_DIR);
            i = enterAdrenalineDir(intScan);

            if(server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(PROMPT_ERROR_RETRY);
                System.out.println(EXIT + YES_PROMPT);
                String exit = in.next();
                if(exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
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

        while(true) {
            System.out.println(ENTER_RELEVANT_STRING);
            lS = enterRelevantString(in);

            System.out.println(ENTER_ADRENALINE_DIR);
            i = enterAdrenalineDir(intScan);

            if(server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(PROMPT_ERROR_RETRY);
                System.out.println(EXIT + YES_PROMPT);
                String exit = in.next();
                if(exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
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

        while(true) {
            System.out.println(ENTER_RELEVANT_STRING);
            lS = enterRelevantString(in);

            System.out.println(ENTER_ADRENALINE_DIR);
            i = enterAdrenalineDir(intScan);

            if(server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC))
                break;
            else {
                System.out.println(PROMPT_ERROR_RETRY);
                System.out.println(EXIT + YES_PROMPT);
                String exit = in.next();
                if(exit.equals("Yes") || exit.equals("yes") || exit.equals("y")) {
                    return;
                }
            }
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    private List<Integer> enterEffect(Scanner in) {
        int effect;
        List<Integer> l = new LinkedList<>();
        while(in.hasNextInt()) {
            effect = in.nextInt();
            if(effect == 5)
                break;
            l.add(effect);
        }
        return l;
    }

    private List<String> enterRelevantString(Scanner in) {
        String str;
        List<String> l = new LinkedList<>();
        while(true) {
            str = in.nextLine();
            if(str.equals("5")) {
                break;
            } else
                l.add(str);
        }
        return l;
    }

    private List<Colour> enterAmmoColour(Scanner in) {
        String colour;
        List<Colour> l = new LinkedList<>();
        while(true) {
            colour = in.nextLine();
            if(colour.equals("5")) {
                break;
            } else
                l.add(Colour.valueOf(colour));
        }
        return l;
    }

    private List<String> enterPowerUp(Scanner in) {
        String pUC;
        List<String> l = new LinkedList<>();
        while(true) {
            pUC = in.next();
            if(pUC.equals("5")) {
                break;
            } else
                l.add(pUC);
        }
        return l;
    }

    private List<String> enterPowerUpColour(Scanner in) {
        String colour;
        List<String> l = new LinkedList<>();
        while(true) {
            colour = in.next();
            if(colour.equals("5")) {
                break;
            } else
                l.add(colour);
        }
        return l;
    }

    private int enterAdrenalineDir(Scanner in) {
        int direction = in.nextInt();
        while(direction < 0 || direction > 4 ) {
            System.out.println("Make sure the direction is between 0 and 4");
            direction = in.nextInt();
        }
        return direction;
    }
}