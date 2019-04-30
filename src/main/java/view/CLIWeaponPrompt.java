package view;

import controller.Game;
import model.Colour;
import model.InvalidColourException;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLIWeaponPrompt {

    private String promptA = "\nEnter the number of the effect you want to use: ";
    private String promptB = "\nEnter the relevant strings for the card: ";
    private String promptC = "\nEnter the colour(s) of the required AmmoCube(s) needed for the effect: ";
    private String promptD = "\nEnter the PowerUpCard you want to use during your turn: ";
    private String promptE = "\nIf you are in Adrenaline, enter the direction of the move: ";
    private String promptErrorRetry = "\nError: retry your action\n";

    public void shootToUser1(Game game, String nickName) throws InvalidColourException {
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
        game.getPowerUpCard(nickName).stream().forEach(System.out::println);
        System.out.println(promptD);
        while (in.hasNext())
            lP.add(in.next());
        System.out.println(promptE);
        i = in.nextInt();                       //TODO nextint is a problem
        while(!game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)) {
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
            game.getPowerUpCard(nickName).stream().forEach(System.out::println);
            System.out.println(promptD);
            while (in.hasNext())
                lP.add(in.next());
            System.out.println(promptE);
            i = in.nextInt();
        }
        game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
    }

   public void shootToUser2(Game game, String nickName) throws InvalidColourException {
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
       game.getPowerUpCard(nickName).stream().forEach(System.out::println);
       System.out.println(promptD);
       while (in.hasNext())
           lP.add(in.next());
       System.out.println(promptE);
       i = in.nextInt() ;                       //TODO nextint is a problem
       while(game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
           System.out.println(promptErrorRetry);
           System.out.println(promptA);
           while (in.hasNext())
               lI.add(in.nextInt());
           System.out.println(promptC);
           while (in.hasNext())
               lC.add(Colour.valueOf(in.next()));
           game.getPowerUpCard(nickName).stream().forEach(System.out::println);
           System.out.println(promptD);
           while (in.hasNext())
               lP.add(in.next());
           System.out.println(promptE);
           i = in.nextInt() ;
       }
       game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
   }

    public void shootToUser3(Game game, String nickName) throws InvalidColourException {
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
        while(!game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
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
        game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
    }

    public void shootToUser4(Game game, String nickName) throws InvalidColourException {
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
        while(!game.isValidFirstActionShoot(nickName, s, lI, lS, i, lC, lP)){
            System.out.println(promptErrorRetry);
            System.out.println(promptB);
            while (in.hasNext())
                lS.add(in.next());
            System.out.println(promptE);
            i = in.nextInt() ;
        }
        game.firstActionShoot(nickName, s, lI, lS, i, lC, lP);
    }
}
