package view;

import controller.Game;
import model.Colour;
import model.InvalidColourException;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.player.Player;

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
            if(isValidReceiveType())
                game.receiveType(type);
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



}
