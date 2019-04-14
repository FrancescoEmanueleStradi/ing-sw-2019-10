package view;

import controller.Game;
import model.Colour;
import model.InvalidColourException;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Cli implements View{

    private Game game;
    private String nickName;
    private Colour colour;

    public void askNameAndColour() throws InvalidColourException {

        Scanner in = new Scanner(System.in);
        if (this.game.gameIsNotStarted()) {
            System.out.println("Enter your name:");
            this.nickName = in.nextLine();

            System.out.println("Enter your colour(YELLOW, BLUE, GREEN, PURPLE, BLACK):");
            String stringColour = in.nextLine();
            this.colour = Colour.valueOf(stringColour);

            this.game.gameStart(nickName, colour);

            System.out.println("Choose the type of Arena(1, 2, 3, 4)");
            int type = in.nextInt();
            if(this.game.isValidReceiveType())
                this.game.receiveType(type);
            return;
        }

        System.out.println("Enter your name:");
        this.nickName = in.nextLine();

        System.out.println("Enter your colour:");
        String stringColour = in.nextLine();
        this.colour = Colour.valueOf(stringColour);

        while(!this.game.isValidAddPlayer( this.nickName, this.colour)){
            System.out.println("Enter your name:");
            this.nickName = in.nextLine();

            System.out.println("Enter your colour:");
            String stringColour1 = in.nextLine();
            this.colour = Colour.valueOf(stringColour);
        }

        this.game.addPlayer(this.nickName, this.colour);
    }


    public void selectSpawnPoint(){
        Scanner in = new Scanner(System.in);
        List<PowerUpCard> l = new LinkedList<>();
        for(PowerUpCard p : this.game.giveTwoPUCard(this.nickName)){
            System.out.println(p.getCardName());
            l.add(p);
        }
        System.out.println("Enter the name of the card you want to choose, you will discard the other one");
        String p1 = in.nextLine();
        if(this.game.isValidPickAndDiscard(this.nickName)) {
            if(l.get(0).getCardName().equals(p1))
                this.game.pickAndDiscardCard(this.nickName, l.get(0), l.get(1));
            else
                this.game.pickAndDiscardCard(this.nickName, l.get(1), l.get(0));
        }
    }


    public void action1(){
        Scanner in = new Scanner(System.in);
        System.out.println("choose the action you want to do(Move, Shoot, Grab)");
        String action = in.nextLine();
        if(action.equals("Move"))
            this.move();
        if(action.equals("Shoot"))
            this.shoot();
        if(action.equals("Grab"))
            this.grab();
    }

    private void move(){
        Scanner in = new Scanner(System.in);
        List<Integer> l = new LinkedList<>();
        while(!this.game.isValidFirstActionMove(l)) {
            System.out.println("choose the sequence of movement you want to do");
            while (in.hasNext())
                l.add(in.nextInt());
        }
        this.game.firstActionMove(this.nickName, l);
    }

    private void shoot(){
        //TODO
    }

    private void grab(){
        //TODO
    }
}
