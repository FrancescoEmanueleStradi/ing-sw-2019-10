package view;

import controller.Game;
import model.Colour;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLIWeaponPrompt {

    private String promptA = "Enter the number(s) of the effect(s) you want to use:";
    private String promptB = "Enter the relevant strings for the card:";
    private String promptC = "Enter the colour(s) of the required AmmoCube(s) needed for the effect:";
    private String promptD = "Enter the PowerUpCard you want to use for paying during your turn:";
    private String promptCOL = "Enter the colour of the PowerUpCard:";
    private String promptE = "If you are in Adrenaline, enter the direction of the move:";
    private String promptErrorRetry = "Error: please retry";

    public void shootToUser1(int game, ServerInterface server, String nickName, String s) throws RemoteException {
        Scanner in = new Scanner(System.in);
        //String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptC);
        while (in.hasNext())
            lC.add(Colour.valueOf(in.next()));
        server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
        System.out.println(promptD);
        while (in.hasNext())
            lP.add(in.next());
        System.out.println(promptCOL);
        while (in.hasNext())
            lPC.add(in.next());
        System.out.println(promptE);
        i = in.nextInt();                       //TODO nextint is a problem
        while(!server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC)) {
            System.out.println(promptErrorRetry);
            System.out.println(promptA);
            while (in.hasNext())
                lI.add(in.nextInt());
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptC);
            while (in.hasNext())
                lC.add(Colour.valueOf(in.next()));
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (in.hasNext())
                lP.add(in.next());
            System.out.println(promptCOL);
            while (in.hasNext())
                lPC.add(in.next());
            System.out.println(promptE);
            i = in.nextInt();
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser1(int game, ServerInterface server, String nickName) throws RemoteException{
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptC);
        while (in.hasNext())
            lC.add(Colour.valueOf(in.next()));
        server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
        System.out.println(promptD);
        while (in.hasNext())
            lP.add(in.next());
        System.out.println(promptCOL);
        while (in.hasNext())
            lPC.add(in.next());
        System.out.println(promptE);
        i = in.nextInt();                       //TODO nextint is a problem
        while (!server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC)) {
            System.out.println(promptErrorRetry);
            System.out.println(promptA);
            while (in.hasNext())
                lI.add(in.nextInt());
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptC);
            while (in.hasNext())
                lC.add(Colour.valueOf(in.next()));
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (in.hasNext())
                lP.add(in.next());
            System.out.println(promptCOL);
            while (in.hasNext())
                lPC.add(in.next());
            System.out.println(promptE);
            i = in.nextInt();
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

   public void shootToUser2(int game, ServerInterface server, String nickName) throws RemoteException{
       Scanner in = new Scanner(System.in);
       String s = in.next();
       int i;
       List<Integer> lI = new LinkedList<>();
       List<String> lS = new LinkedList<>();
       List<Colour> lC = new LinkedList<>();
       List<String> lP = new LinkedList<>();
       List<String> lPC = new LinkedList<>();

       System.out.println(promptA);
       while (in.hasNext())
           lI.add(in.nextInt());
       System.out.println(promptC);
       while (in.hasNext())
           lC.add(Colour.valueOf(in.next()));
       server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
       System.out.println(promptD);
       while (in.hasNext())
           lP.add(in.next());
       System.out.println(promptCOL);
       while (in.hasNext())
           lPC.add(in.next());
       System.out.println(promptE);
       i = in.nextInt() ;                       //TODO nextint is a problem
       while(server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC)){
           System.out.println(promptErrorRetry);
           System.out.println(promptA);
           while (in.hasNext())
               lI.add(in.nextInt());
           System.out.println(promptC);
           while (in.hasNext())
               lC.add(Colour.valueOf(in.next()));
           server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
           System.out.println(promptD);
           while (in.hasNext())
               lP.add(in.next());
           System.out.println(promptCOL);
           while (in.hasNext())
               lPC.add(in.next());
           System.out.println(promptE);
           i = in.nextInt() ;
       }
       server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
   }

    public void shoot2ToUser2(int game, ServerInterface server, String nickName) throws RemoteException{
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptC);
        while (in.hasNext())
            lC.add(Colour.valueOf(in.next()));
        server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
        System.out.println(promptD);
        while (in.hasNext())
            lP.add(in.next());
        System.out.println(promptCOL);
        while (in.hasNext())
            lPC.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC)){
            System.out.println(promptErrorRetry);
            System.out.println(promptA);
            while (in.hasNext())
                lI.add(in.nextInt());
            System.out.println(promptC);
            while (in.hasNext())
                lC.add(Colour.valueOf(in.next()));
            server.messageGetPowerUpCard(game, nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (in.hasNext())
                lP.add(in.next());
            System.out.println(promptCOL);
            while (in.hasNext())
                lPC.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shootToUser3(int game, ServerInterface server, String nickName) throws RemoteException{
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(!server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC)){
            System.out.println(promptErrorRetry);
            System.out.println(promptA);
            while (in.hasNext())
                lI.add(in.nextInt());
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser3(int game, ServerInterface server, String nickName) throws RemoteException{
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(!server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC)){
            System.out.println(promptErrorRetry);
            System.out.println(promptA);
            while (in.hasNext())
                lI.add(in.nextInt());
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shootToUser4(int game, ServerInterface server, String nickName) throws RemoteException{
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(!server.messageIsValidFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC)){
            System.out.println(promptErrorRetry);
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        server.messageFirstActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }

    public void shoot2ToUser4(int game, ServerInterface server, String nickName) throws RemoteException{
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPC = new LinkedList<>();

        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(!server.messageIsValidSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC)){
            System.out.println(promptErrorRetry);
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        server.messageSecondActionShoot(game, nickName, s, lI, lS, i, lC, lP, lPC);
    }
}
