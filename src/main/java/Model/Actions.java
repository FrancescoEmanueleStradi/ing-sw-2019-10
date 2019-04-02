package Model;

public class Actions {

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

    public void printShift(){
        System.out.println("First option: "+ this.shiftMove);
    }

    public void printCollect(){
        System.out.println("Second option: "+ this.collectMove);
    }

    public void printShoot(){
        System.out.println("Third option: "+ this.shootMove);
    }

}
