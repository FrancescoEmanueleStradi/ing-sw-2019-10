package model;

import controller.Cli;

public class Actions {
                                                            //Probably it will be a view class
    private String shiftMove;
    private String collectMove;
    private String shootMove;

    public Actions(){

        this.shiftMove = "Move 1, 2 or 3 cells\n";
        this.collectMove = "Grab stuff\n" +
                "Make one move if you want.\n" +
                "Grab the thing in your cell\n";
        this.shootMove = "Shoot people\n";


    }

    public void printShift(Cli c){
        c.printShift(this.shiftMove);
    }

    public void printCollect(Cli c){
        c.printShift(this.collectMove);
    }

    public void printShoot(Cli c){
        c.printShift(this.shootMove);
    }

}
