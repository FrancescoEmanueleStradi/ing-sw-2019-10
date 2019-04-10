package model.cards.weaponcards;

import model.*;
import model.board.Cell;
import model.cards.WeaponCard;

public class PowerGlove extends WeaponCard {

    private String alternativeEffect = "Rocket Fist Mode";

    public PowerGlove() throws InvalidColourException {
        super();
        this.cardName = "Power Glove";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = true;
        String description = "basic mode: Choose 1 target on any square exactly 1 move away. Move onto that square and give the target 1 damage and 2 marks.\n" +
                "in rocket fist mode: Choose a square exactly 1 move away. Move onto that square. You may deal 2 damage to 1 target there.\n" +
                "If you want, you may move 1 more square in that same direction (but only if it is a legal move). You may deal 2 damage to 1 target there, as well.\n" +
                "Notes: In rocket fist mode, you're flying 2 squares in a straight line, punching 1 person per square.\n";
    }

    public String getAlternativeEffect() {
        return alternativeEffect;
    }

    //before: let player p choose a target p1 in a cell exactly one move away from him.

    public void applyEffect(Grid grid, Player p, Player p1) {   //player p moves in the same cell as the selected p1, deals him 1 damage and 2 marks
        p.setCell(p1.getCell());
        grid.damage(p, p1, 1);
        grid.addMark(p, p1);
        grid.addMark(p, p1);
    }

    //before Rocket Fist Mode: Player p chooses one cell to go to.

    public void applySpecialEffectPart1(Player p, Cell cell1) {  //player p moves in the first cell selected
        p.setCell(cell1);
    }

    //Then, he can attack a player p1 there.

    public void applySpecialEffectPart2(Grid grid, Player p, Player p1) {   //player p deals 2 damage to a target in his cell
        grid.damage(p, p1, 2);
    }

    //Optional from here:

    //Then, if he wants, he can move again in the same direction (check if the direction is the same).

    public void applySpecialEffectPart3(Player p, Cell cell2) { //player p moves in the second cell selected
        p.setCell(cell2);
    }

    //Eventually, he can attack a player p2 there.

    public void applySpecialEffectPart4(Grid grid, Player p, Player p2) {   //player p deals 2 damage to a target in his cell
        grid.damage(p, p2, 2);
    }
}