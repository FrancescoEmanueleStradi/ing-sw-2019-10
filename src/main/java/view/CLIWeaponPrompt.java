package view;

import controller.Game;
import model.Colour;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLIWeaponPrompt {

    private String promptA = "Enter the number of the effect you want to use:";
    private String promptB = "Enter the relevant strings for the card:";
    private String promptC = "Enter the colour(s) of the required AmmoCube(s) needed for the effect:";
    private String promptD = "Enter the PowerUpCard you want to use for paying during your turn:";
    private String promptE = "If you are in Adrenaline, enter the direction of the move:";
    private String promptErrorRetry = "Error: retry your action";

    public void shootToUser1(Server server, String nickName) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptC);
        while (in.hasNext())
            lC.add(Colour.valueOf(in.next()));
        server.messageGetPowerUpCard(nickName).stream().forEach(System.out::println);
        System.out.println(promptD);
        while (in.hasNext())
            lP.add(in.next());
        System.out.println(promptE);
        i = in.nextInt();                       //TODO nextint is a problem
        while(!server.messageIsValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)) {
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
            server.messageGetPowerUpCard(nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (in.hasNext())
                lP.add(in.next());
            System.out.println(promptE);
            i = in.nextInt();
        }
        server.messageFirstActionShoot(nickName, s, lI, lS, i, lC, lP);
    }

    public void shoot2ToUser1(Server server, String nickName) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptC);
        while (in.hasNext())
            lC.add(Colour.valueOf(in.next()));
        server.messageGetPowerUpCard(nickName).stream().forEach(System.out::println);
        System.out.println(promptD);
        while (in.hasNext())
            lP.add(in.next());
        System.out.println(promptE);
        i = in.nextInt();                       //TODO nextint is a problem
        while (!server.messageIsValidSecondActionShoot(nickName, s, lI, lS, i, lC, lP)) {
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
            server.messageGetPowerUpCard(nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (in.hasNext())
                lP.add(in.next());
            System.out.println(promptE);
            i = in.nextInt();
        }
        server.messageSecondActionShoot(nickName, s, lI, lS, i, lC, lP);
    }

   public void shootToUser2(Server server, String nickName) {
       Scanner in = new Scanner(System.in);
       String s = in.next();
       int i;
       List<Integer> lI = new LinkedList<>();
       List<String> lS = new LinkedList<>();
       List<Colour> lC = new LinkedList<>();
       List<String> lP = new LinkedList<>();

       System.out.println(promptA);
       while (in.hasNext())
           lI.add(in.nextInt());
       System.out.println(promptC);
       while (in.hasNext())
           lC.add(Colour.valueOf(in.next()));
       server.messageGetPowerUpCard(nickName).stream().forEach(System.out::println);
       System.out.println(promptD);
       while (in.hasNext())
           lP.add(in.next());
       System.out.println(promptE);
       i = in.nextInt() ;                       //TODO nextint is a problem
       while(server.messageIsValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
           System.out.println(promptErrorRetry);
           System.out.println(promptA);
           while (in.hasNext())
               lI.add(in.nextInt());
           System.out.println(promptC);
           while (in.hasNext())
               lC.add(Colour.valueOf(in.next()));
           server.messageGetPowerUpCard(nickName).stream().forEach(System.out::println);
           System.out.println(promptD);
           while (in.hasNext())
               lP.add(in.next());
           System.out.println(promptE);
           i = in.nextInt() ;
       }
       server.messageFirstActionShoot(nickName, s, lI, lS, i, lC, lP);
   }

    public void shoot2ToUser2(Server server, String nickName) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptC);
        while (in.hasNext())
            lC.add(Colour.valueOf(in.next()));
        server.messagegetPowerUpCard(nickName).stream().forEach(System.out::println);
        System.out.println(promptD);
        while (in.hasNext())
            lP.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(server.messageisValidSecondActionShoot(nickName, s, lI, lS, i, lC, lP)){
            System.out.println(promptErrorRetry);
            System.out.println(promptA);
            while (in.hasNext())
                lI.add(in.nextInt());
            System.out.println(promptC);
            while (in.hasNext())
                lC.add(Colour.valueOf(in.next()));
            server.messagegetPowerUpCard(nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (in.hasNext())
                lP.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        server.messageSecondActionShoot(nickName, s, lI, lS, i, lC, lP);
    }

    public void shootToUser3(Server server, String nickName) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(!server.messageisValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
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
        server.messageFirstActionShoot(nickName, s, lI, lS, i, lC, lP);
    }

    public void shoot2ToUser3(Server server, String nickName) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();

        System.out.println(promptA);
        while (in.hasNext())
            lI.add(in.nextInt());
        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(!server.messageisValidSecondActionShoot(nickName, s, lI, lS, i, lC, lP)){
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
        server.messageSecondActionShoot(nickName, s, lI, lS, i, lC, lP);
    }

    public void shootToUser4(Server server, String nickName) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();

        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(!server.messageisValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
            System.out.println(promptErrorRetry);
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        server.messageFirstActionShoot(nickName, s, lI, lS, i, lC, lP);
    }

    public void shoot2ToUser4(Server server, String nickName) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int i;
        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        List<Colour> lC = new LinkedList<>();
        List<String> lP = new LinkedList<>();

        System.out.println(promptB);
        while (in.hasNext())
            lS.add(in.next());
        System.out.println(promptE);
        i = in.nextInt() ;                       //TODO nextint is a problem
        while(!server.messageisValidSecondActionShoot(nickName, s, lI, lS, i, lC, lP)){
            System.out.println(promptErrorRetry);
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        server.messageSecondActionShoot(nickName, s, lI, lS, i, lC, lP);
    }
}
